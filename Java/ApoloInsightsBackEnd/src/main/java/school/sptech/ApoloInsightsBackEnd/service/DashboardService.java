package school.sptech.ApoloInsightsBackEnd.service;

import school.sptech.ApoloInsightsBackEnd.domain.DTO.dashboard.DadosDashboard;
import school.sptech.ApoloInsightsBackEnd.repository.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    DashboardRepository dashboardRepository;

    public DadosDashboard gerarDadosDashboard() {
        DadosDashboard dados = new DadosDashboard();

        dados.setAtendimentosPorMes(dashboardRepository.buscarAtendimentosPorMes());
        dados.setDistribuicaoHorarios(dashboardRepository.buscarDistribuicaoHorarios());
        dados.setMediaServicosPorCliente(dashboardRepository.buscarMediaServicosPorCliente());
        dados.setDistribuicaoDiaHora(dashboardRepository.buscarDistribuicaoDiaHora());
        dados.setServicoDoMes(dashboardRepository.buscarServicoDoMes());
        dados.setMediaAtendimentosPorSemana(dashboardRepository.buscarMediaAtendimentosPorSemana());
        dados.setMediaGeralPorSemana(dashboardRepository.buscarMediaGeralPorSemana());
        dados.setTop10Clientes(dashboardRepository.buscarTop10Clientes());
        dados.setOcupacaoDias(dashboardRepository.buscarOcupacaoDias());

        return dados;
    }
}
