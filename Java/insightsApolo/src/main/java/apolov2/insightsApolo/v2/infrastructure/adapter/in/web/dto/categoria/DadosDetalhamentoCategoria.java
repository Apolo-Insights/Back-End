package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.categoria;

import apolov2.insightsApolo.v2.core.domain.entity.Categoria;

public record DadosDetalhamentoCategoria(
        Long id,
        String nome,
        String foto
) {
    public DadosDetalhamentoCategoria(Categoria categoria) {
        this(
                categoria.getId(),
                categoria.getNome(),
                categoria.getFoto()
        );
    }
}
