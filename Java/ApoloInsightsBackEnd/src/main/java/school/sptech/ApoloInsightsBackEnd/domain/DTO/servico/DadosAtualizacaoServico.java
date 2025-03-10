package school.sptech.ApoloInsightsBackEnd.domain.DTO.servico;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoServico(
        @NotNull(message = "O id do Serviço não foi inserido")
        Long id,
        String nome,
        String descricao,
        Double preco,
        String foto
) {
}
