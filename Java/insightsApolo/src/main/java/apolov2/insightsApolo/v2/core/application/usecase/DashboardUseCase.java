package apolov2.insightsApolo.v2.core.application.usecase;

import apolov2.insightsApolo.v2.core.port.out.DashboardGateway;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.dashboard.DadosDashboard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardUseCase {

    private final DashboardGateway dashboardGateway;

    public DadosDashboard gerarDadosDashboard() {
        DadosDashboard dados = new DadosDashboard();

        dados.setAtendimentosPorMes(dashboardGateway.buscarAtendimentosPorMes());
        dados.setDistribuicaoHorarios(dashboardGateway.buscarDistribuicaoHorarios());
        dados.setMediaServicosPorCliente(dashboardGateway.buscarMediaServicosPorCliente());
        dados.setDistribuicaoDiaHora(dashboardGateway.buscarDistribuicaoDiaHora());
        dados.setServicoDoMes(dashboardGateway.buscarServicoDoMes());
        dados.setMediaAtendimentosPorSemana(dashboardGateway.buscarMediaAtendimentosPorSemana());
        dados.setMediaGeralPorSemana(dashboardGateway.buscarMediaGeralPorSemana());
        dados.setTop10Clientes(dashboardGateway.buscarTop10Clientes());
        dados.setOcupacaoDias(dashboardGateway.buscarOcupacaoDias());

        return dados;
    }
}
