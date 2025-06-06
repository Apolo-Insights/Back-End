package school.sptech.ApoloInsightsBackEnd.domain.DTO.horariodisponivel;

import java.util.List;

public record HorariosPorDiaDTO(
        String data,
        List<String> horariosBloqueados,
        List<String> horariosDisponiveis
) {
}
