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

    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoServico> atualizarServico(
            @Valid @RequestBody DadosAtualizacaoServico dados,
            @PathVariable Long id) {
        Servico servico = service.atualizar(id, dados);
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
