package school.sptech.ApoloInsightsBackEnd.v2.core.application.usecase;

import school.sptech.ApoloInsightsBackEnd.v2.core.application.command.CadastrarServicoCommand;
import school.sptech.ApoloInsightsBackEnd.v2.core.domain.entity.Servico;
import school.sptech.ApoloInsightsBackEnd.v2.core.port.in.ServicoGateway;

public class ServicoUseCase {
    private final ServicoGateway gateway;

    public ServicoUseCase(ServicoGateway gateway) {
        this.gateway = gateway;
    }

    public Servico criarServico(CadastrarServicoCommand servico) {

        Servico novoServico = new Servico(
                servico.nome(),
                servico.preco(),
                servico.descricao(),
                servico.foto(),
                servico.duracao(),
                servico.categoria()
        );
        return gateway.cadastrar(novoServico);
    }
}
