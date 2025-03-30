package school.sptech.ApoloInsightsBackEnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.erros.ProdutoConflitoException;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.erros.ProdutoNaoEcontradoException;
import school.sptech.ApoloInsightsBackEnd.domain.Produto;
import school.sptech.ApoloInsightsBackEnd.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public Produto cadastrarProduto(Produto produto){
        if (repository.existsByCodigoDoProdutoIgnoreCase(produto.getCodigoDoProduto())) {
            throw new ProdutoConflitoException("O produto com o código %s já existe no sistema");
        }
        return repository.save(produto);
    }

    public void buscarProdutoPorCodigo(String codigo) {

        Optional<Produto> produtos = repository.findByCodigoDoProduto(codigo);

        if (produtos.isEmpty()) {
            throw new ProdutoNaoEcontradoException(
                    ("O produto com o código %s não foi encontrado no sistema."
                            .formatted(codigo))
            );
        }
    }

    public List<Produto> listarProdutos(){
        return repository.findAll();
    }

    public Produto deletarProduto(){

    }
}
