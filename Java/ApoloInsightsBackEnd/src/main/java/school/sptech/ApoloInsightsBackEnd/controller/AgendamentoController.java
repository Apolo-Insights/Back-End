package school.sptech.ApoloInsightsBackEnd.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento.DadosCadastroAgendamento;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento.DadosCadastroAgendamentoAdmin;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento.DadosDetalhamentoAgendamento;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento.DadosHistoricoAgendamento;
import school.sptech.ApoloInsightsBackEnd.service.AgendamentoService;
import java.util.List;

@CrossOrigin(origins = "${cors.allowed.origin}")
@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService service;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoAgendamento> agendar(@Valid @RequestBody DadosCadastroAgendamento dados) {
        var agendamento = service.agendar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoAgendamento(agendamento));
    }

    @PostMapping("/admin")
    public ResponseEntity<DadosDetalhamentoAgendamento> agendarAdmin(@Valid @RequestBody DadosCadastroAgendamentoAdmin dados) {
        var agendamento = service.agendar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoAgendamento(agendamento));
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<DadosHistoricoAgendamento>> listarHistoricoServicos(
            @PathVariable Long idUsuario
    ) {
        var agendamentos = service.listarHistoricoServicos(idUsuario);
        return ResponseEntity.ok(agendamentos);
    }
}
