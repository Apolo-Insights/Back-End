package school.sptech.ApoloInsightsBackEnd.old.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.dashboard.DadosDashboard;
import school.sptech.ApoloInsightsBackEnd.old.service.DashboardService;
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
