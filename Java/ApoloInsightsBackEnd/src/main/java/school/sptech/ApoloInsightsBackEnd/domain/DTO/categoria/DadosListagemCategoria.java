package school.sptech.ApoloInsightsBackEnd.domain.DTO.categoria;

import school.sptech.ApoloInsightsBackEnd.domain.Categoria;

public record DadosListagemCategoria(
        String nome,
        String foto
) {
    public DadosListagemCategoria(Categoria categoria) {
        this(
                categoria.getNome(),
                categoria.getFoto());
    }
}
