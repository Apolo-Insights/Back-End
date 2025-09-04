package school.sptech.ApoloInsightsBackEnd.v2.core.application.usecase;

import school.sptech.ApoloInsightsBackEnd.old.domain.Categoria;

import school.sptech.ApoloInsightsBackEnd.v2.core.application.exception.RegistroExistenteException;
import school.sptech.ApoloInsightsBackEnd.v2.core.domain.entity.Servico;
import school.sptech.ApoloInsightsBackEnd.v2.core.port.in.ServicoGateway;

public class ServicoUseCase {
    private final ServicoGateway gateway;

    public ServicoUseCase(ServicoGateway gateway) {
        this.gateway = gateway;
    }

    public Servico criarServico(Servico servico) {
        if (gateway.buscarPorId(servico.getId()) != null) throw new RegistroExistenteException("arrumar dps");

        Servico novoServico = new Servico(
                servico.getNome(),
                servico.getPreco(),
                servico.getDescricao(),
                servico.getFoto(),
                servico.getDuracao(),
                servico.getCategoria()

        );
        return gateway.cadastrar(servico);
    }
}
