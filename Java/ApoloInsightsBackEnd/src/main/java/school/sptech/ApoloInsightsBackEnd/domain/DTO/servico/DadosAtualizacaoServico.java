package school.sptech.ApoloInsightsBackEnd.domain.DTO.servico;

public record DadosAtualizacaoServico(
        String nome,
        String descricao,
        Double preco,
        String foto,
        String duracao
) {
}
