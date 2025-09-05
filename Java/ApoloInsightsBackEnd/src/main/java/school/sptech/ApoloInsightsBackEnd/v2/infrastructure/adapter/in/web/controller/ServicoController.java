package school.sptech.ApoloInsightsBackEnd.v2.infrastructure.adapter.in.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import school.sptech.ApoloInsightsBackEnd.v2.core.application.command.CadastrarServicoCommand;
import school.sptech.ApoloInsightsBackEnd.v2.core.application.usecase.ServicoUseCase;
import school.sptech.ApoloInsightsBackEnd.v2.core.domain.entity.Servico;
import school.sptech.ApoloInsightsBackEnd.v2.infrastructure.adapter.in.web.dto.DadosCadastroServico;
import school.sptech.ApoloInsightsBackEnd.v2.infrastructure.adapter.in.web.dto.DadosDetalhamentoServico;
import school.sptech.ApoloInsightsBackEnd.v2.infrastructure.adapter.out.jpa.mapper.ServicoMapper;

public class ServicoController {
    private final ServicoUseCase useCase;
    private final ServicoMapper mapper;

    public ServicoController(ServicoUseCase useCase, ServicoMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoServico> cadastrar(DadosCadastroServico dados) {
        CadastrarServicoCommand command = mapper.cadastroToCommand(dados);
        Servico servico = useCase.criarServico(command);
        return ResponseEntity.status(201).body(mapper.servicoToDetalhamento(servico));
    }
}
