package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.controller;

import apolov2.insightsApolo.v2.core.application.usecase.AtendimentoUseCase;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.atendimento.DadosDetalhamentoAtendimento;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/admin/atendimentos")
@RequiredArgsConstructor
public class AtendimentoController {

    private final AtendimentoUseCase useCase;

    @PostMapping("/{idAgendamento}")
    public ResponseEntity<DadosDetalhamentoAtendimento> confirmarAtendimento(@PathVariable Long idAgendamento) {
        DadosDetalhamentoAtendimento atendimento = useCase.confirmarAtendimento(idAgendamento);
        return ResponseEntity.status(201).body(atendimento);
    }

    @DeleteMapping("/{idAgendamento}")
    public ResponseEntity<Void> cancelarAtendimento(@PathVariable Long idAgendamento) {
        useCase.cancelarAtendimento(idAgendamento);
        return ResponseEntity.noContent().build();
    }
}
