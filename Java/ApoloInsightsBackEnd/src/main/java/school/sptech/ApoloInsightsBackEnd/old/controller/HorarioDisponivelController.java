package school.sptech.ApoloInsightsBackEnd.old.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.horariodisponivel.DadosBloqueioHorario;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.horariodisponivel.DadosCadastroHorario;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.horariodisponivel.DadosDetalhamentoHorario;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.horariodisponivel.HorariosPorCategoriaDTO;
import school.sptech.ApoloInsightsBackEnd.old.service.HorarioDisponivelService;
import java.util.List;

@CrossOrigin(origins = "${cors.allowed.origin}")
@RestController
@RequestMapping("/horarios-disponiveis")
public class HorarioDisponivelController {

    @Autowired
    private HorarioDisponivelService service;

    @PostMapping("/admin/cadastrar")
    public ResponseEntity<DadosDetalhamentoHorario> cadastrarHorario(@Valid @RequestBody DadosCadastroHorario dados){
        service.cadastrarHorario(dados);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/admin/bloquear")
    public ResponseEntity<DadosDetalhamentoHorario> bloqueioHorario(@Valid @RequestBody DadosBloqueioHorario dados){
        service.bloquearHorario(dados);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{categoriaId}")
    public ResponseEntity<HorariosPorCategoriaDTO> getHorariosPorCategoriaUnica(
            @PathVariable Long categoriaId,
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer ano) {

        HorariosPorCategoriaDTO response = service.buscarHorariosPorCategoriaUnica(categoriaId, mes, ano);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<HorariosPorCategoriaDTO> getHorariosPorVariasCategorias(
            @RequestParam (name = "ids") List<Long> categoriaIds,
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer ano) {

        HorariosPorCategoriaDTO response = service.buscarHorariosPorVariasCategorias(categoriaIds, mes, ano);
        return ResponseEntity.ok(response);
    }
}
