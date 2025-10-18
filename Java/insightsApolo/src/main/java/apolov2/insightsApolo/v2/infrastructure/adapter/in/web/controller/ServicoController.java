package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.controller;

import apolov2.insightsApolo.v2.core.application.command.CadastrarServicoCommand;
import apolov2.insightsApolo.v2.core.application.usecase.ServicoUseCase;
import apolov2.insightsApolo.v2.core.domain.entity.Servico;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroServico;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosDetalhamentoServico;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper.ServicoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "${cors.allowed.origin}")
@RestController
@RequestMapping("/v2/servicos")
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
