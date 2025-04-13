package school.sptech.ApoloInsightsBackEnd.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.horarioDisponivel.DadosCadastroHorario;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.horarioDisponivel.DadosDetalhamentoHorario;
import school.sptech.ApoloInsightsBackEnd.service.HorarioDisponivelService;

@CrossOrigin(origins = "${cors.allowed.origin}")
@RestController
@RequestMapping("/horarios-disponiveis")
public class HorarioDisponivelController {

    @Autowired
    private HorarioDisponivelService service;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoHorario> cadastrar(@Valid @RequestBody DadosCadastroHorario dados){
        var novoHorario = service.cadastrar(dados);
        return null;
    }
}
