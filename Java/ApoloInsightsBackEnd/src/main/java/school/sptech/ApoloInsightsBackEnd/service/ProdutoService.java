package school.sptech.ApoloInsightsBackEnd.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.produto.*;
import school.sptech.ApoloInsightsBackEnd.domain.Produto;
import school.sptech.ApoloInsightsBackEnd.repository.ProdutoRepository;
import school.sptech.ApoloInsightsBackEnd.exception.RequestError;


@Service
public class ProdutoService {


    @Autowired
    private ProdutoRepository repository;

    @Transactional
    public Produto cadastrar (DadosCadastroProduto dados){
        if (repository.existsByNome(dados.nome())) {
            throw new RequestError(
                    HttpStatus.CONFLICT,"nome","Produto com esse nome já cadastrado");
        }
        return repository.save(new Produto(dados));
    }

    @Transactional
    public Produto atualizar (Long id, DadosAtualizacaoProduto dados){
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RequestError(HttpStatus.NOT_FOUND,"idProduto", "Produto não encontrado"));
        produto.atualizarInformacoes(dados);
        return repository.save(produto);
    }

    public Page<DadosListagemProduto> listar(Pageable paginacao){
        if (repository.findAll().isEmpty()) {
            throw new RequestError(HttpStatus.NOT_FOUND,"sem campo", "Nenhum Produto encontrado");
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
                .orElseThrow(() -> new RequestError(HttpStatus.NOT_FOUND,"idProduto", "Produto não encontrado"));

        repository.delete(servico);
    }
}
