package school.sptech.ApoloInsightsBackEnd.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.horarioDisponivel.DadosCadastroHorario;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.horarioDisponivel.DadosDetalhamentoHorario;
import school.sptech.ApoloInsightsBackEnd.service.HorarioDisponivelService;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "${cors.allowed.origin}")
@RestController
@RequestMapping("/horarios-disponiveis")
public class HorarioDisponivelController {

    @Autowired
    private HorarioDisponivelService service;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoHorario> cadastrarHorario(@Valid @RequestBody DadosCadastroHorario dados){
        service.cadastrarHorario(dados);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<Map<DayOfWeek, List<String>>> buscarTodosHorariosDisponiveis(@PathVariable Long idCategoria) {
        Map<DayOfWeek, List<String>> horarios = service.listarHorariosPorCategoria(idCategoria);
        return ResponseEntity.ok(horarios);
    }

//    @PostMapping
//    public ResponseEntity<DadosDetalhamentoHorario> bloqueioHorario(@Valid @RequestBody DadosBloqueioHorario dados){
//
//
//    }
}
