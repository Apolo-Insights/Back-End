package school.sptech.ApoloInsightsBackEnd.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.produto.DadosAtualizacaoProduto;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.produto.DadosCadastroProduto;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.produto.DadosDetalhamentoProduto;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.produto.DadosListagemProduto;
import school.sptech.ApoloInsightsBackEnd.domain.Produto;
import school.sptech.ApoloInsightsBackEnd.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoProduto> cadastrarProduto(@Valid @RequestBody DadosCadastroProduto dados) {
        Produto produto = service.cadastrar(new Produto(dados));
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoProduto(produto));
    }

    @PutMapping
    public ResponseEntity<DadosDetalhamentoProduto> atualizarProduto(@Valid @RequestBody DadosAtualizacaoProduto dados) {
        Produto produto = service.atualizar(dados);
        return ResponseEntity.status(HttpStatus.OK).body(new DadosDetalhamentoProduto(produto));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemProduto>> listar(@PageableDefault(size = 6, sort = {"nome"}) Pageable paginacao) {
        var page = service.listar(paginacao);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Produto com id %d deletado com sucesso!".formatted(id));
    }
}
