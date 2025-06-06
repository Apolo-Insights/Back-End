package school.sptech.ApoloInsightsBackEnd.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.horariodisponivel.DadosBloqueioHorario;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.horariodisponivel.DadosCadastroHorario;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.horariodisponivel.DadosDetalhamentoHorario;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.horariodisponivel.HorariosPorCategoriaDTO;
import school.sptech.ApoloInsightsBackEnd.service.HorarioDisponivelService;

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

//    @GetMapping("/admin/{idCategoria}")
//    public ResponseEntity<Map<DayOfWeek, List<String>>> buscarTodosHorariosDisponiveisPorCategoria(@PathVariable Long idCategoria) {
//        Map<DayOfWeek, List<String>> horarios = service.listarHorariosPorCategoria(idCategoria);
//        return ResponseEntity.ok(horarios);
//    }

//    @GetMapping("/admin/todas-categorias")
//    public ResponseEntity<Map<String, Map<DayOfWeek, List<String>>>> buscarTodosHorariosDisponiveis() {
//        Map<String, Map<DayOfWeek, List<String>>> horarios = service.listarHorariosDisponiveisTodasCategorias();
//        return ResponseEntity.ok(horarios);
//    }

    @PutMapping("/admin/bloquear")
    public ResponseEntity<DadosDetalhamentoHorario> bloqueioHorario(@Valid @RequestBody DadosBloqueioHorario dados){
        service.bloquearHorario(dados);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/admin/{categoriaId}")
    public ResponseEntity<HorariosPorCategoriaDTO> getHorariosPorCategoria(
            @PathVariable Long categoriaId,
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer ano) {

        HorariosPorCategoriaDTO response = service.buscarHorariosPorCategoria(categoriaId, mes, ano);
        return ResponseEntity.ok(response);
    }
}
