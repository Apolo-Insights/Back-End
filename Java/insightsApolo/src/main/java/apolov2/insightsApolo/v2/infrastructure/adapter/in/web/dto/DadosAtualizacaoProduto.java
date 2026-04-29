package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

public record DadosAtualizacaoProduto(
        String nome,
        String descricao,
        Double preco,
        String fotoBase64
) {
}
