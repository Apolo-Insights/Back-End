package school.sptech.ApoloInsightsBackEnd.domain.DTO.horarioDisponivel;


import java.util.List;

public record DadosCadastroHorario(
        Long servicoId,
        List<DiasDisponiveis> diasDisponiveis
) {
}
