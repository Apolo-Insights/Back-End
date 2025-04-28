package school.sptech.ApoloInsightsBackEnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import school.sptech.ApoloInsightsBackEnd.domain.Categoria;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.categoria.DadosAtualizacaoCategoria;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.categoria.DadosCadastroCategoria;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.categoria.DadosListagemCategoria;
import school.sptech.ApoloInsightsBackEnd.repository.CategoriaRepository;
import school.sptech.ApoloInsightsBackEnd.exception.RequestError;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository repository;

    public Categoria cadastrarCategoria(DadosCadastroCategoria dados) {
        if (repository.existsByNome(dados.nome())) {
            throw new RequestError(HttpStatus.CONFLICT,"nome","Categoria com esse nome já cadastrada");
        }
        return repository.save(new Categoria(dados));
    }

    public Page<DadosListagemCategoria> listarCategorias(Pageable paginacao) {
        if (repository.findAll().isEmpty()) {
            throw new RequestError(HttpStatus.NOT_FOUND,"sem campo","Nenhuma categoria encontrada");
        }
        return repository.findAll(paginacao).map(DadosListagemCategoria::new);
    }

    public Categoria atualizarCategoria(Long id, DadosAtualizacaoCategoria dados) {

        if (repository.existsByNome(dados.nome())) {
            throw new RequestError(HttpStatus.CONFLICT,"nome","Categoria com esse nome já cadastrada");
        }

        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new RequestError(
                        HttpStatus.CONFLICT,"nome","Categoria com esse nome já cadastrada"));

        categoria.atualizarInformacoes(dados);
        return repository.save(categoria);
    }

    public void deletarCategoria(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new RequestError(HttpStatus.NOT_FOUND,"idCategoria", "Categoria não encontrada"));
        repository.delete(categoria);
    }
}
