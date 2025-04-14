package school.sptech.ApoloInsightsBackEnd.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.horarioDisponivel.DadosCadastroHorario;
import school.sptech.ApoloInsightsBackEnd.domain.HorarioDisponivel;

@Service
public class HorarioDisponivelService {
    public HorarioDisponivel cadastrar(@Valid DadosCadastroHorario dados) {
        return null;
    }
}
