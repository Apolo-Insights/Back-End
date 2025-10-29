package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.controller;

import apolov2.insightsApolo.v2.core.application.usecase.CategoriaUseCase;
import apolov2.insightsApolo.v2.core.domain.entity.Categoria;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.categoria.DadosAtualizacaoCategoria;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.categoria.DadosCadastroCategoria;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.categoria.DadosDetalhamentoCategoria;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.categoria.DadosListagemCategoria;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaUseCase categoriaUseCase;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoCategoria> cadastrarCategoria(
            @Valid @RequestBody DadosCadastroCategoria dados) {
        Categoria categoria = categoriaUseCase.cadastrarCategoria(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoCategoria(categoria));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemCategoria>> listarCategorias(
            @PageableDefault(size = 3, sort = {"nome"}) Pageable paginacao) {
        Page<DadosListagemCategoria> categorias = categoriaUseCase.listarCategorias(paginacao);
        return ResponseEntity.ok(categorias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoCategoria> atualizarCategoria(
            @RequestBody DadosAtualizacaoCategoria dados,
            @PathVariable Long id) {
        Categoria categoria = categoriaUseCase.atualizarCategoria(id, dados);
        return ResponseEntity.ok(new DadosDetalhamentoCategoria(categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        categoriaUseCase.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
