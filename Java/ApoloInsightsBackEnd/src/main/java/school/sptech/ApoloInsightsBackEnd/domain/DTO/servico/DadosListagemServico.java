package school.sptech.ApoloInsightsBackEnd.domain.DTO.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import school.sptech.ApoloInsightsBackEnd.domain.Servico;
import school.sptech.ApoloInsightsBackEnd.util.DataHoraUtil;

public record DadosListagemServico(
        @NotBlank(message = "O nome do Serviço não foi inserido")
        String nome,
        String descricao,
        @NotNull(message = "O preço do Serviço não foi inserido")
        Double preco,
        String foto,
        String duracao
) {
        public DadosListagemServico(Servico servico){
                this(
                        servico.getNome(),
                        servico.getDescricao(),
                        servico.getPreco(),
                        servico.getFoto(),
                        DataHoraUtil.formatarDuracao(servico.getDuracao()));
        }
}
