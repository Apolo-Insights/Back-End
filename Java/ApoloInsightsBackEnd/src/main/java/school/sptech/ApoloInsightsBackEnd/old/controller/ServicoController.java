package school.sptech.ApoloInsightsBackEnd.old.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.servico.DadosAtualizacaoServico;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.servico.DadosCadastroServico;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.servico.DadosDetalhamentoServico;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.servico.DadosListagemServico;
import school.sptech.ApoloInsightsBackEnd.old.domain.Servico;
import school.sptech.ApoloInsightsBackEnd.old.service.AzureBlobService;
import school.sptech.ApoloInsightsBackEnd.old.service.ServicoService;

import java.util.Base64;

@CrossOrigin(origins = "${cors.allowed.origin}")
@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService service;

    @Autowired
    private AzureBlobService azureBlobService;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoServico> cadastrarServico(@Valid @RequestBody DadosCadastroServico dados) {
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
        Servico servico = service.cadastrar(dadosCadastro);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoServico(servico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoServico> atualizarServico(
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
        Servico servico = service.atualizar(id, dadosAtualizacao);
        return ResponseEntity.status(HttpStatus.OK).body(new DadosDetalhamentoServico(servico));
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<Page<DadosListagemServico>> listar(@PathVariable Long idCategoria,  @PageableDefault(size = 6, sort = {"nome"}) Pageable paginacao) {
        var page = service.listar(idCategoria, paginacao);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Livro deletado com sucesso!");
    }
}
