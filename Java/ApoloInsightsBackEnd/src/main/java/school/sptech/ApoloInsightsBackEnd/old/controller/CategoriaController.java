package school.sptech.ApoloInsightsBackEnd.old.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.old.domain.Categoria;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.categoria.DadosAtualizacaoCategoria;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.categoria.DadosCadastroCategoria;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.categoria.DadosDetalhamentoCategoria;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.categoria.DadosListagemCategoria;
import school.sptech.ApoloInsightsBackEnd.old.service.AzureBlobService;
import school.sptech.ApoloInsightsBackEnd.old.service.CategoriaService;
import java.util.Base64;

@CrossOrigin(origins = "${cors.allowed.origin}")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    CategoriaService service;

    @Autowired
    private AzureBlobService azureBlobService;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoCategoria> cadastrarCategoria(
            @Valid @RequestBody DadosCadastroCategoria dados) {
        String urlFoto = null;

        if (dados.fotoBase64() != null && !dados.fotoBase64().isBlank()) {
            byte[] imagemBytes = Base64.getDecoder().decode(dados.fotoBase64());
        urlFoto = azureBlobService.upload(imagemBytes, "categorias");
        }

        DadosCadastroCategoria dadosCadastro = new DadosCadastroCategoria(
                dados.nome(),
                urlFoto
        );
        var categoria = service.cadastrarCategoria(dadosCadastro);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoCategoria(categoria));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemCategoria>> listarCategorias(
            @PageableDefault(size = 3, sort = {"nome"}) Pageable paginacao) {
        var categorias = service.listarCategorias(paginacao);
        return ResponseEntity.ok(categorias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoCategoria> atualizarCategoria(
            @RequestBody DadosAtualizacaoCategoria dados,
            @PathVariable Long id) {
        String urlFoto = null;

        if (dados.fotoBase64() != null && !dados.fotoBase64().isBlank()) {
            byte[] imagemBytes = Base64.getDecoder().decode(dados.fotoBase64());
            urlFoto = azureBlobService.upload(imagemBytes, "categorias");
        }
        DadosAtualizacaoCategoria dadosAtualizacao = new DadosAtualizacaoCategoria(
                dados.nome(),
                urlFoto
        );

        Categoria categoria = service.atualizarCategoria(id, dadosAtualizacao);
        return ResponseEntity.status(HttpStatus.OK).body(new DadosDetalhamentoCategoria(categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarCategoria(@PathVariable Long id) {
        service.deletarCategoria(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Categoria com id %d deletada com sucesso!".formatted(id));
    }


}
