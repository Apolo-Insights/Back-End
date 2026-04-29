package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.horariodisponivel;

import java.util.List;

public record HorariosPorCategoriaDTO(
        String categoria,
        List<HorariosPorDiaDTO> horariosPorDia
) {
    public HorariosPorCategoriaDTO {
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("Categoria não pode ser vazia");
        }
        if (horariosPorDia == null) {
            throw new IllegalArgumentException("horariosPorDia não pode ser nulo");
        }
    }
}
