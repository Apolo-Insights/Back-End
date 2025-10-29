package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.controller;

import apolov2.insightsApolo.v2.core.application.usecase.ServicoUseCase;
import apolov2.insightsApolo.v2.core.domain.entity.Servico;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosAtualizacaoServico;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroServico;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosDetalhamentoServico;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosListagemServico;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.azure.AzureBlobService;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper.ServicoMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@CrossOrigin(origins = "${cors.allowed.origin}")
@RestController
@RequestMapping("/v2/servicos")
public class ServicoController {
    private final ServicoUseCase useCase;
    private final ServicoMapper mapper;
    private final AzureBlobService azureBlobService;

    public ServicoController(ServicoUseCase useCase, ServicoMapper mapper, AzureBlobService azureBlobService) {
        this.useCase = useCase;
        this.mapper = mapper;
        this.azureBlobService = azureBlobService;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoServico> cadastrar(@Valid @RequestBody DadosCadastroServico dados) {
        String urlFoto = null;

        if (dados.fotoBase64() != null && !dados.fotoBase64().isBlank()) {
            byte[] imagemBytes = Base64.getDecoder().decode(dados.fotoBase64());
            urlFoto = azureBlobService.upload(imagemBytes, "servicos");
        }

        DadosCadastroServico dadosCadastro = new DadosCadastroServico(
                dados.idCategoria(),
                dados.nome(),
                dados.descricao(),
                dados.preco(),
                urlFoto,
                dados.duracao()
        );

        Servico servico = useCase.cadastrar(dadosCadastro);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.servicoToDetalhamento(servico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoServico> atualizar(
            @Valid @RequestBody DadosAtualizacaoServico dados,
            @PathVariable Long id) {
        String urlFoto = null;

        if (dados.fotoBase64() != null && !dados.fotoBase64().isBlank()) {
            byte[] imagemBytes = Base64.getDecoder().decode(dados.fotoBase64());
            urlFoto = azureBlobService.upload(imagemBytes, "servicos");
        }

        DadosAtualizacaoServico dadosAtualizacao = new DadosAtualizacaoServico(
                dados.nome(),
                dados.descricao(),
                dados.preco(),
                urlFoto,
                dados.duracao()
        );

        Servico servico = useCase.atualizar(id, dadosAtualizacao);
        return ResponseEntity.ok(mapper.servicoToDetalhamento(servico));
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<Page<DadosListagemServico>> listar(
            @PathVariable Long idCategoria,
            @PageableDefault(size = 6, sort = {"nome"}) Pageable paginacao) {
        Page<DadosListagemServico> page = useCase.listar(idCategoria, paginacao);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        useCase.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
