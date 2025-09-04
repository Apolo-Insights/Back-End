package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.horariodisponivel;

import java.util.List;

public record HorariosPorDiaDTO(
        String data,
        List<String> horariosBloqueados,
        List<String> horariosDisponiveis
) {
}
