package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.controller;

import apolov2.insightsApolo.v2.core.application.usecase.FinanceiroUseCase;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.financeiro.DadosKPIAtendimentos;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.financeiro.DadosKPIFaturamento;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.financeiro.DadosKPINovosClientes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/admin/financeiro")
@RequiredArgsConstructor
public class FinanceiroController {

    private final FinanceiroUseCase useCase;

    @GetMapping("/faturamento")
    public ResponseEntity<DadosKPIFaturamento> obterFaturamentoPorCategoriaEMes(
            @RequestParam Long idCategoria,
            @RequestParam String anoMes) {
        DadosKPIFaturamento faturamento = useCase.obterFaturamentoPorCategoriaEMes(idCategoria, anoMes);
        return ResponseEntity.ok(faturamento);
    }

    @GetMapping("/atendimentos")
    public ResponseEntity<DadosKPIAtendimentos> obterAtendimentosPorCategoriaEMes(
            @RequestParam Long idCategoria,
            @RequestParam String anoMes) {
        DadosKPIAtendimentos atendimentos = useCase.obterAtendimentosPorCategoriaEMes(idCategoria, anoMes);
        return ResponseEntity.ok(atendimentos);
    }

    @GetMapping("/novos-clientes")
    public ResponseEntity<DadosKPINovosClientes> obterNovosClientesPorMes(
            @RequestParam String anoMes) {
        DadosKPINovosClientes novosClientes = useCase.obterNovosClientesPorMes(anoMes);
        return ResponseEntity.ok(novosClientes);
    }
}
