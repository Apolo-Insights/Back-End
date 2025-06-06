package school.sptech.ApoloInsightsBackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.financeiro.DadosKPIAtendimentos;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.financeiro.DadosKPIFaturamento;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.financeiro.DadosKPINovosClientes;
import school.sptech.ApoloInsightsBackEnd.service.FinanceiroService;

@CrossOrigin(origins = "${cors.allowed.origin}")
@RestController
@RequestMapping("/admin/financeiro")
public class FinanceiroController {

    @Autowired
    private FinanceiroService service;

    @GetMapping("/faturamento")
    public ResponseEntity<DadosKPIFaturamento> obterFaturamentoPorCategoriaEMes(
            @RequestParam Long idCategoria,
            @RequestParam String anoMes){
        DadosKPIFaturamento faturamento = service.obterFaturamentoPorCategoriaEMes(idCategoria, anoMes);
        return ResponseEntity.ok(faturamento);
    }

    @GetMapping("/atendimentos")
    public ResponseEntity<DadosKPIAtendimentos> obterAtendimentosPorCategoriaEMes(
            @RequestParam Long idCategoria,
            @RequestParam String anoMes
    ) {
       DadosKPIAtendimentos atendimentos = service.obterAtendimentosPorCategoriaEMes(idCategoria, anoMes);
       return ResponseEntity.ok(atendimentos);
    }

    @GetMapping("/novos-clientes")
    public ResponseEntity<DadosKPINovosClientes> obterNovosClientesPorMes(
            @RequestParam String anoMes
    ) {
        DadosKPINovosClientes novosClientes = service.obterNovosClientesPorMes(anoMes);
        return ResponseEntity.ok(novosClientes);
    }




}
