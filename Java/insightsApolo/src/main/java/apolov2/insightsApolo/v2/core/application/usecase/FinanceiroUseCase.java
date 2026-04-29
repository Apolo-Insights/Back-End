package apolov2.insightsApolo.v2.core.application.usecase;

import apolov2.insightsApolo.v2.core.domain.entity.Atendimento;
import apolov2.insightsApolo.v2.core.port.out.AtendimentoGateway;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.financeiro.DadosKPIAtendimentos;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.financeiro.DadosKPIFaturamento;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.financeiro.DadosKPINovosClientes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinanceiroUseCase {

    private final AtendimentoGateway atendimentoGateway;

    public DadosKPIFaturamento obterFaturamentoPorCategoriaEMes(Long idCategoria, String anoMes) {
        PeriodoDatas periodo = obterPeriodos(anoMes);

        double totalAtual = somarValorAtendimentos(
                atendimentoGateway.buscarPorCategoriaEPeriodo(idCategoria, periodo.inicioAtual(), periodo.fimAtual())
        );

        double totalAnterior = somarValorAtendimentos(
                atendimentoGateway.buscarPorCategoriaEPeriodo(idCategoria, periodo.inicioAnterior(), periodo.fimAnterior())
        );

        double variacao = calcularVariacao(totalAtual, totalAnterior);

        return new DadosKPIFaturamento(totalAtual, variacao);
    }

    public DadosKPIAtendimentos obterAtendimentosPorCategoriaEMes(Long idCategoria, String anoMes) {
        PeriodoDatas periodo = obterPeriodos(anoMes);

        int totalAtual = atendimentoGateway.buscarPorCategoriaEPeriodo(
                idCategoria, periodo.inicioAtual(), periodo.fimAtual()
        ).size();

        int totalAnterior = atendimentoGateway.buscarPorCategoriaEPeriodo(
                idCategoria, periodo.inicioAnterior(), periodo.fimAnterior()
        ).size();

        double variacao = calcularVariacao(totalAtual, totalAnterior);

        return new DadosKPIAtendimentos(totalAtual, variacao);
    }

    public DadosKPINovosClientes obterNovosClientesPorMes(String anoMes) {
        PeriodoDatas periodo = obterPeriodos(anoMes);

        Set<Long> clientesMesAtual = obterIdsClientesUnicos(periodo.inicioAtual(), periodo.fimAtual());
        Set<Long> clientesMesAnterior = obterIdsClientesUnicos(periodo.inicioAnterior(), periodo.fimAnterior());

        Set<Long> novosClientes = new HashSet<>(clientesMesAtual);
        novosClientes.removeAll(clientesMesAnterior);

        int totalNovos = novosClientes.size();
        int totalAnterior = clientesMesAnterior.size();
        double variacao = calcularVariacao(totalNovos, totalAnterior);

        return new DadosKPINovosClientes(totalNovos, variacao);
    }

    private PeriodoDatas obterPeriodos(String anoMes) {
        YearMonth mesAtual = YearMonth.parse(anoMes);
        YearMonth mesAnterior = mesAtual.minusMonths(1);

        return new PeriodoDatas(
                mesAtual.atDay(1),
                mesAtual.atEndOfMonth(),
                mesAnterior.atDay(1),
                mesAnterior.atEndOfMonth()
        );
    }

    private double somarValorAtendimentos(List<Atendimento> atendimentos) {
        return atendimentos.stream()
                .mapToDouble(a -> a.getValor() != null ? a.getValor() : 0.0)
                .sum();
    }

    private double calcularVariacao(double valorAtual, double valorAnterior) {
        if (valorAnterior > 0) {
            return ((valorAtual - valorAnterior) / valorAnterior) * 100;
        } else if (valorAtual > 0) {
            return 100.0;
        } else {
            return 0.0;
        }
    }

    private Set<Long> obterIdsClientesUnicos(LocalDate inicio, LocalDate fim) {
        return atendimentoGateway.buscarPorPeriodo(inicio, fim).stream()
                .map(a -> a.getCliente().getId())
                .collect(Collectors.toSet());
    }

    private record PeriodoDatas(
            LocalDate inicioAtual,
            LocalDate fimAtual,
            LocalDate inicioAnterior,
            LocalDate fimAnterior
    ) {}
}
