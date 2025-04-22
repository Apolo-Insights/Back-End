package school.sptech.ApoloInsightsBackEnd.domain.DTO.categoria;

import school.sptech.ApoloInsightsBackEnd.domain.Categoria;

public record DadosDetalhamentoCategoria(
        String nome,
        String foto
) {
    public DadosDetalhamentoCategoria(Categoria categoria) {
        this(
                categoria.getNome(),
                categoria.getFoto());
    }
}
