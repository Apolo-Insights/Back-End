package school.sptech.ApoloInsightsBackEnd.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.ApoloInsightsBackEnd.domain.Produto;
import school.sptech.ApoloInsightsBackEnd.repository.ProdutoRepository;
import school.sptech.ApoloInsightsBackEnd.util.exception.ProdutoException;

import java.util.List;


@Service
public class ProdutoService {


    @Autowired
    private ProdutoRepository repository;


    public Produto cadastrar(Produto produto) {
        if (repository.existsById(produto.getId())) {
            throw  new ProdutoException
                    ("Produto com o id %d já existe");
        }
        return repository.save(produto);
}
    public Produto buscarPorId (Integer id){

        return repository.findById(id)
                .orElseThrow(
                        ()  -> new ProdutoException
                                ("Produto de id: %d não encontrado".formatted(id)));
    }

    public List<Produto> listar(){
        return repository.findAll();
    }

    public void removerPorId(Integer id) {

        if (!repository.existsById(id)) {
            throw new ProdutoException("Produto não encontrado");
        }
        repository.deleteById(id);
    }
}
