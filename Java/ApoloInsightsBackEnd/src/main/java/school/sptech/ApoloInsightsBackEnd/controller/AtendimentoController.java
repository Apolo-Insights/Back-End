package school.sptech.ApoloInsightsBackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.atendimento.DadosDetalhamentoAtendimento;
import school.sptech.ApoloInsightsBackEnd.service.AtendimentoService;

@CrossOrigin(origins = "${cors.allowed.origin}")
@RestController
@RequestMapping("/admin/atendimentos")
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    @PostMapping("/{idAgendamento}")
    public ResponseEntity<DadosDetalhamentoAtendimento> confirmarAtendimento(@PathVariable Long idAgendamento) {
        var atendimento = atendimentoService.cadastrarAtendimento(idAgendamento);
        return ResponseEntity.status(201).body(new DadosDetalhamentoAtendimento(atendimento));
    }

    @DeleteMapping("/{idAgendamento}")
    public ResponseEntity<Void> cancelarAtendimento(@PathVariable Long idAgendamento) {
        atendimentoService.cancelarAtendimento(idAgendamento);
        return ResponseEntity.noContent().build();
    }
}
