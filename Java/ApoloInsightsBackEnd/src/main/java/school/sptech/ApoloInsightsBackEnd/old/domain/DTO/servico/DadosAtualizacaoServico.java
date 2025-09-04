package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.servico;

public record DadosAtualizacaoServico(
        String nome,
        String descricao,
        Double preco,
        String fotoBase64,
        String duracao
) {
}
