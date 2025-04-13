package school.sptech.ApoloInsightsBackEnd.service;

import jakarta.validation.Valid;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.horarioDisponivel.DadosCadastroHorario;
import school.sptech.ApoloInsightsBackEnd.domain.HorarioDisponivel;

public class HorarioDisponivelService {
    public HorarioDisponivel cadastrar(@Valid DadosCadastroHorario dados) {
        var novoHorario = new HorarioDisponivel();
        novoHorario.setServicoId(dados.servicoId());
        return novoHorario;
    }
}
