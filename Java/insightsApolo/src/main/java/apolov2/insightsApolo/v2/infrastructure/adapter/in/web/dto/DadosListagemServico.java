package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

import apolov2.insightsApolo.v2.core.domain.entity.Categoria;
import apolov2.insightsApolo.v2.core.domain.entity.Servico;
import apolov2.insightsApolo.v2.infrastructure.util.DataHoraUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosListagemServico(
        Long id,
        @NotBlank(message = "O nome do Serviço não foi inserido")
        String nome,
        String descricao,
        @NotNull(message = "O preço do Serviço não foi inserido")
        Double preco,
        String foto,
        String duracao,
        Categoria categoria
) {
    public DadosListagemServico(Servico servico) {
        this(
                servico.getId(),
                servico.getNome(),
                servico.getDescricao(),
                servico.getPreco(),
                servico.getFoto(),
                DataHoraUtil.formatarDuracao(servico.getDuracao()),
                servico.getCategoria()
        );
    }
}
