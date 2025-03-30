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
            throw new ProdutoConflitoException("O produto com o código %s já existe no sistema"
                    .formatted(produto));
        }
        return repository.save(produto);
    }

    public Produto buscarProdutoPorCodigo(String codigo) {

        return repository.findByCodigoDoProduto(codigo)
                .orElseThrow(() -> new ProdutoNaoEcontradoException(
                        String.format("O produto com o código %s não foi encontrado no sistema.", codigo)
                ));
    }

    public List<Produto> listarProdutos(){
        return repository.findAll();
    }



    public void deletarProduto(Integer id){
        if (!repository.existsById(id)) {
            throw new ProdutoNaoEcontradoException("Produto com o id %d não foi encontrado no sistema!");
        }
         repository.deleteById(id);
    }
}
