package school.sptech.ApoloInsightsBackEnd.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.categoria.DadosAtualizacaoCategoria;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.categoria.DadosCadastroCategoria;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.categoria.DadosDetalhamentoCategoria;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.categoria.DadosListagemCategoria;
import school.sptech.ApoloInsightsBackEnd.service.CategoriaService;

@CrossOrigin(origins = "${cors.allowed.origin}")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    CategoriaService service;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoCategoria> cadastrarCategoria(@Valid @RequestBody DadosCadastroCategoria dados) {
        var categoria = service.cadastrarCategoria(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoCategoria(categoria));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemCategoria>> listarCategorias(@PageableDefault(size = 3, sort = {"nome"}) Pageable paginacao) {
        var categorias = service.listarCategorias(paginacao);
        return ResponseEntity.ok(categorias);
    }

    @PutMapping
    public ResponseEntity<DadosDetalhamentoCategoria> atualizarCategoria(@RequestBody DadosAtualizacaoCategoria dados) {
        var categoria = service.atualizarCategoria(dados);
        return ResponseEntity.status(HttpStatus.OK).body(new DadosDetalhamentoCategoria(categoria));
    }


}
