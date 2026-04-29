// src/test/java/school/sptech/ApoloInsightsBackEnd/service/DashboardServiceTest.java
package school.sptech.ApoloInsightsBackEnd.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import school.sptech.ApoloInsightsBackEnd.old.repository.DashboardRepository;
import school.sptech.ApoloInsightsBackEnd.old.service.DashboardService;

import java.util.Collections;

import static org.mockito.Mockito.*;

class DashboardServiceTest {

    private DashboardRepository dashboardRepository;
    private DashboardService dashboardService;

    @BeforeEach
    void setUp() {
        dashboardRepository = mock(DashboardRepository.class);
        dashboardService = new DashboardService();
        // Injeção manual do mock
        dashboardService.dashboardRepository = dashboardRepository;
    }

    @Test
    void deveChamarTodosMetodosDoRepository() {
        when(dashboardRepository.buscarAtendimentosPorMes()).thenReturn(Collections.emptyList());
        when(dashboardRepository.buscarDistribuicaoHorarios()).thenReturn(Collections.emptyList());
        when(dashboardRepository.buscarMediaServicosPorCliente()).thenReturn(Collections.emptyList());
        when(dashboardRepository.buscarDistribuicaoDiaHora()).thenReturn(Collections.emptyList());
        when(dashboardRepository.buscarServicoDoMes()).thenReturn(Collections.emptyList());
        when(dashboardRepository.buscarMediaAtendimentosPorSemana()).thenReturn(Collections.emptyList());
        when(dashboardRepository.buscarMediaGeralPorSemana()).thenReturn(Collections.emptyList());
        when(dashboardRepository.buscarTop10Clientes()).thenReturn(Collections.emptyList());
        when(dashboardRepository.buscarOcupacaoDias()).thenReturn(Collections.emptyList());

        dashboardService.gerarDadosDashboard();

        verify(dashboardRepository).buscarAtendimentosPorMes();
        verify(dashboardRepository).buscarDistribuicaoHorarios();
        verify(dashboardRepository).buscarMediaServicosPorCliente();
        verify(dashboardRepository).buscarDistribuicaoDiaHora();
        verify(dashboardRepository).buscarServicoDoMes();
        verify(dashboardRepository).buscarMediaAtendimentosPorSemana();
        verify(dashboardRepository).buscarMediaGeralPorSemana();
        verify(dashboardRepository).buscarTop10Clientes();
        verify(dashboardRepository).buscarOcupacaoDias();
    }
}