package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.controller;

import apolov2.insightsApolo.v2.core.application.usecase.DashboardUseCase;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.dashboard.DadosDashboard;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardUseCase dashboardUseCase;

    @GetMapping("/dados")
    public DadosDashboard obterDadosDashboard() {
        return dashboardUseCase.gerarDadosDashboard();
    }
}
