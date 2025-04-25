package school.sptech.ApoloInsightsBackEnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.sptech.ApoloInsightsBackEnd.domain.Categoria;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.categoria.DadosCadastroCategoria;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.categoria.DadosListagemCategoria;
import school.sptech.ApoloInsightsBackEnd.repository.CategoriaRepository;
import school.sptech.ApoloInsightsBackEnd.util.exception.RequestError;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository repository;

    public Categoria cadastrarCategoria(DadosCadastroCategoria dados) {
        if (repository.existsByNome(dados.nome())) {
            throw new RequestError("nome","Categoria com esse nome já cadastrada");
        }
        return repository.save(new Categoria(dados));
    }

    public Page<DadosListagemCategoria> listarCategorias(Pageable paginacao) {
        if (repository.findAll().isEmpty()) {
            throw new RuntimeException("Nenhuma categoria encontrada");
        }
        return repository.findAll(paginacao).map(DadosListagemCategoria::new);
    }
}
