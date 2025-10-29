package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.controller;

import apolov2.insightsApolo.v2.core.application.usecase.ProdutoUseCase;
import apolov2.insightsApolo.v2.core.domain.entity.Produto;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosAtualizacaoProduto;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroProduto;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosDetalhamentoProduto;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosListagemProduto;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.ProdutoDetalhesDTO;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.azure.AzureBlobService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/v2/produtos")
public class ProdutoController {
    private final ProdutoUseCase useCase;
    private final AzureBlobService azureBlobService;

    public ProdutoController(ProdutoUseCase useCase, AzureBlobService azureBlobService) {
        this.useCase = useCase;
        this.azureBlobService = azureBlobService;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoProduto> cadastrar(@Valid @RequestBody DadosCadastroProduto dados) {
        String urlFoto = null;

        if (dados.fotoBase64() != null && !dados.fotoBase64().isBlank()) {
            byte[] imagemBytes = Base64.getDecoder().decode(dados.fotoBase64());
            urlFoto = azureBlobService.upload(imagemBytes, "produtos");
        }

        DadosCadastroProduto dadosCadastro = new DadosCadastroProduto(
                dados.nome(),
                dados.descricao(),
                dados.preco(),
                urlFoto
        );

        Produto produto = useCase.cadastrar(dadosCadastro);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoProduto(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoProduto> atualizar(
            @Valid @RequestBody DadosAtualizacaoProduto dados,
            @PathVariable Long id) {
        String urlFoto = null;

        if (dados.fotoBase64() != null && !dados.fotoBase64().isBlank()) {
            byte[] imagemBytes = Base64.getDecoder().decode(dados.fotoBase64());
            urlFoto = azureBlobService.upload(imagemBytes, "produtos");
        }

        DadosAtualizacaoProduto dadosAtualizacao = new DadosAtualizacaoProduto(
                dados.nome(),
                dados.descricao(),
                dados.preco(),
                urlFoto
        );

        Produto produto = useCase.atualizar(id, dadosAtualizacao);
        return ResponseEntity.ok(new DadosDetalhamentoProduto(produto));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemProduto>> listar(
            @PageableDefault(size = 6, sort = {"nome"}) Pageable paginacao) {
        Page<DadosListagemProduto> page = useCase.listar(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDetalhesDTO> buscarPorId(@PathVariable Long id) {
        ProdutoDetalhesDTO detalhesProduto = useCase.buscarPorId(id);
        return ResponseEntity.ok(detalhesProduto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        useCase.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("admin/estoque/adicionar/{id}")
    public ResponseEntity<Void> adicionarEstoque(@PathVariable Long id) {
        useCase.adicionarEstoque(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("admin/estoque/remover/{id}")
    public ResponseEntity<Void> removerEstoque(@PathVariable Long id) {
        useCase.removerEstoque(id);
        return ResponseEntity.ok().build();
    }
}
