package school.sptech.ApoloInsightsBackEnd.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.produto.ProdutoResponse;
import school.sptech.ApoloInsightsBackEnd.domain.Produto;
import school.sptech.ApoloInsightsBackEnd.service.ProdutoService;
import school.sptech.ApoloInsightsBackEnd.util.exception.ProdutoException;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping
    public ResponseEntity<Produto> cadastrarProduto
            (@Valid @RequestBody Produto produto) throws ProdutoException {

        Produto produtoCadastrado = service.cadastrar(produto);
        return ResponseEntity.status(201).body(produtoCadastrado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId (
            @PathVariable Integer id
    ) throws ProdutoException
    {
        Produto produto = service.buscarPorId(id);
        return ResponseEntity.status(200).body(produto);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listar(){
        List<Produto> produtos = service.listar();

        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(produtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProdutoResponse> remover(@PathVariable Integer id){
        service.removerPorId(id);
        return ResponseEntity.status(204).build();
    }
}
