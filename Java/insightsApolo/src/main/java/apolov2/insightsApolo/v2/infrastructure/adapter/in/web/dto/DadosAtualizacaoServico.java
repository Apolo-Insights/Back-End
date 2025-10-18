package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

public record DadosAtualizacaoServico(
        String nome,
        String descricao,
        Double preco,
        String fotoBase64,
        String duracao
) {
}

