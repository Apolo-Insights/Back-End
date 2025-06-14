package school.sptech.ApoloInsightsBackEnd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.dashboard.DadosDashboard;
import school.sptech.ApoloInsightsBackEnd.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dados")
    public DadosDashboard obterDadosDashboard() {
        return dashboardService.gerarDadosDashboard();
    }
}
