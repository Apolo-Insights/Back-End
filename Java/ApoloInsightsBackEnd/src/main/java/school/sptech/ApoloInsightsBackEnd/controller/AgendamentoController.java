package school.sptech.ApoloInsightsBackEnd.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.domain.Agendamento;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento.*;
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

    @PostMapping("/agendar-multiplo")
    public ResponseEntity<DadosDetalhamentoAgendamentoMultiplo> agendarMultiplo(@Valid @RequestBody DadosCadastroAgendamentoMultiplo dados) {
        List<Agendamento> agendamentos = service.agendarMultiplo(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoAgendamentoMultiplo(agendamentos));
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

    @GetMapping("/admin/{idCategoria}")
    public ResponseEntity<List<DadosListagemPorCategoria>> listarAgendamentosPorCategoria(
            @PathVariable Long idCategoria,
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer ano
    ) {
        var agendamentos = service.listarAgendamentosPorCategoria(idCategoria, mes, ano);


        return ResponseEntity.ok(agendamentos);
    }

}
