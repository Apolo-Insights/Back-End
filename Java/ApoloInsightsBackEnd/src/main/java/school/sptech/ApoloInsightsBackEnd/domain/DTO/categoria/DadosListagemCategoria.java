package school.sptech.ApoloInsightsBackEnd.domain.DTO.categoria;

import school.sptech.ApoloInsightsBackEnd.domain.Categoria;

public record DadosListagemCategoria(
        Long id,
        String nome,
        String foto
) {
    public DadosListagemCategoria(Categoria categoria) {
        this(
                categoria.getId(),
                categoria.getNome(),
                categoria.getFoto());
    }
}
