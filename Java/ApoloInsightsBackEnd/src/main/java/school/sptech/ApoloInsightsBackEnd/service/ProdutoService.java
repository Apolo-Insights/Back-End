package school.sptech.ApoloInsightsBackEnd.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.produto.DadosAtualizacaoProduto;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.produto.DadosDetalhamentoProduto;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.produto.DadosListagemProduto;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.produto.ProdutoDetalhesDTO;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.servico.DadosAtualizacaoServico;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.servico.DadosListagemServico;
import school.sptech.ApoloInsightsBackEnd.domain.Produto;
import school.sptech.ApoloInsightsBackEnd.domain.Servico;
import school.sptech.ApoloInsightsBackEnd.repository.ProdutoRepository;


@Service
public class ProdutoService {


    @Autowired
    private ProdutoRepository repository;


    @Transactional
    public Produto cadastrar (Produto produto){
        return repository.save(produto);
    }

    @Transactional
    public Produto atualizar (DadosAtualizacaoProduto dados){
        Produto produto = repository.findById(dados.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        produto.atualizarInformacoes(dados);
        return repository.save(produto);
    }

    public Page<DadosListagemProduto> listar(Pageable paginacao){
        if (repository.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum Produto encontrado");
        }
        return repository.findAll(paginacao).map(DadosListagemProduto::new);
    }

    public ProdutoDetalhesDTO buscarPorId(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        return new ProdutoDetalhesDTO(produto);
    }

    public void deletar(Long id){
        Produto servico = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        repository.delete(servico);
    }
}
