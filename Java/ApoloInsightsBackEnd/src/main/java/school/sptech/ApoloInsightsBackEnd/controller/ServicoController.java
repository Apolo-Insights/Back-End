package school.sptech.ApoloInsightsBackEnd.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.servico.DadosAtualizacaoServico;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.servico.DadosCadastroServico;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.servico.DadosDetalhamentoServico;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.servico.DadosListagemServico;
import school.sptech.ApoloInsightsBackEnd.domain.Servico;
import school.sptech.ApoloInsightsBackEnd.service.ServicoService;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    ServicoService service;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoServico> cadastrarServico(@Valid @RequestBody DadosCadastroServico dados) {
        Servico servico = service.cadastrar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoServico(servico));
    }

    @PutMapping
    public ResponseEntity<DadosDetalhamentoServico> atualizarServico(@Valid @RequestBody DadosAtualizacaoServico dados) {
        Servico servico = service.atualizar(dados);
        return ResponseEntity.status(HttpStatus.OK).body(new DadosDetalhamentoServico(servico));
    }




    @GetMapping("/{id}")
    public ResponseEntity<Page<DadosListagemServico>> listar(@PathVariable Long id,  @PageableDefault(size = 6, sort = {"nome"}) Pageable paginacao) {
        var page = service.listar(id, paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemServico>> listarT(Pageable pageable){
        var listaTudo = service.listarTodos(pageable);
        return ResponseEntity.ok(listaTudo);
    }

    @GetMapping("/category")
    public ResponseEntity<Page<DadosListagemServico>> listarServicosPorCategoria(
            @RequestParam(required = false) Long categoryId,
            Pageable pageable) {

        Page<DadosListagemServico> listaServico;

        if (categoryId != null) {
            listaServico = service.listarPorCategoria(categoryId, pageable);
        } else {
            listaServico = service.listarTodos(pageable);
        }

        return ResponseEntity.ok(listaServico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Livro deletado com sucesso!");
    }
}
