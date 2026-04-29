package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.horariodisponivel;

import java.util.List;

public record HorariosPorDiaDTO(
        String data,
        List<String> horariosBloqueados,
        List<String> horariosDisponiveis
) {
}
