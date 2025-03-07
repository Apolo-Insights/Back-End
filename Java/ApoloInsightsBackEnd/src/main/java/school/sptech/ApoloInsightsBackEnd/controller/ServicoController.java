package school.sptech.ApoloInsightsBackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.DTO.servico.DadosAtualizacaoServico;
import school.sptech.ApoloInsightsBackEnd.DTO.servico.DadosCadastroServico;
import school.sptech.ApoloInsightsBackEnd.DTO.servico.DadosDetalhamentoServico;
import school.sptech.ApoloInsightsBackEnd.DTO.servico.DadosListagemServico;
import school.sptech.ApoloInsightsBackEnd.domain.Servico;
import school.sptech.ApoloInsightsBackEnd.service.ServicoService;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    ServicoService service;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoServico> cadastrarServico(DadosCadastroServico dados) {
        Servico servico = service.cadastrar(new Servico(dados));
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoServico(servico));
    }

    @PutMapping
    public ResponseEntity<DadosDetalhamentoServico> atualizarServico(DadosAtualizacaoServico dados) {
        Servico servico = service.atualizar(dados);
        return ResponseEntity.status(HttpStatus.OK).body(new DadosDetalhamentoServico(servico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemServico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = service.listar(paginacao);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
