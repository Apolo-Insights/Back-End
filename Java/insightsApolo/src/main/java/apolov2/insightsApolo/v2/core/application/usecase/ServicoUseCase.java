package apolov2.insightsApolo.v2.core.application.usecase;

import apolov2.insightsApolo.v2.core.application.command.CadastrarServicoCommand;
import apolov2.insightsApolo.v2.core.domain.entity.Servico;
import apolov2.insightsApolo.v2.core.port.in.ServicoGateway;
import org.springframework.stereotype.Service;

@Service
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
