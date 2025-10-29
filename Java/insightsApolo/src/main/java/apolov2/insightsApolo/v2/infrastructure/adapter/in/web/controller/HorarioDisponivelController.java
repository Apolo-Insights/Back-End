package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.controller;

import apolov2.insightsApolo.v2.core.application.usecase.HorarioDisponivelUseCase;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.horariodisponivel.DadosBloqueioHorario;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.horariodisponivel.DadosCadastroHorario;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.horariodisponivel.HorariosPorCategoriaDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/horarios-disponiveis")
@RequiredArgsConstructor
public class HorarioDisponivelController {

    private final HorarioDisponivelUseCase useCase;

    @PostMapping("/admin/cadastrar")
    public ResponseEntity<Void> cadastrarHorario(@Valid @RequestBody DadosCadastroHorario dados) {
        useCase.cadastrarHorario(dados);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/admin/bloquear")
    public ResponseEntity<Void> bloquearHorario(@Valid @RequestBody DadosBloqueioHorario dados) {
        useCase.bloquearHorario(dados);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{categoriaId}")
    public ResponseEntity<HorariosPorCategoriaDTO> getHorariosPorCategoriaUnica(
            @PathVariable Long categoriaId,
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer ano) {
        
        HorariosPorCategoriaDTO response = useCase.buscarHorariosPorCategoriaUnica(categoriaId, mes, ano);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<HorariosPorCategoriaDTO> getHorariosPorVariasCategorias(
            @RequestParam(name = "ids") List<Long> categoriaIds,
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer ano) {
        
        HorariosPorCategoriaDTO response = useCase.buscarHorariosPorVariasCategorias(categoriaIds, mes, ano);
        return ResponseEntity.ok(response);
    }
}
