package school.sptech.ApoloInsightsBackEnd.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento.DadosCadastroAgendamento;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento.DadosDetalhamentoAgendamento;
import school.sptech.ApoloInsightsBackEnd.service.AgendamentoService;

@CrossOrigin(origins = "${cors.allowed.origin}")
@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService service;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoAgendamento> agendar(@Valid @RequestBody DadosCadastroAgendamento dados) {
        var agendamento = service.agendar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoAgendamento(agendamento));
    }
}
