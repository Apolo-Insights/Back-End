package school.sptech.ApoloInsightsBackEnd.domain.DTO.produto;

public record DadosAtualizacaoProduto(
        String nome,
        String descricao,
        Double preco,
        String fotoBase64
) {
}
