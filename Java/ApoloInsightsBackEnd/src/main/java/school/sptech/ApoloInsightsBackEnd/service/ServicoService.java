package school.sptech.ApoloInsightsBackEnd.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.ApoloInsightsBackEnd.domain.Categoria;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.servico.DadosAtualizacaoServico;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.servico.DadosCadastroServico;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.servico.DadosListagemServico;
import school.sptech.ApoloInsightsBackEnd.domain.Servico;
import school.sptech.ApoloInsightsBackEnd.repository.CategoriaRepository;
import school.sptech.ApoloInsightsBackEnd.repository.ServicoRepository;
import school.sptech.ApoloInsightsBackEnd.util.exception.RequestError;

@Service
public class ServicoService {

    @Autowired
    ServicoRepository repository;

    @Autowired
    CategoriaRepository categoriaRepository;


    @Transactional
    public Servico cadastrar (DadosCadastroServico dados){
        if (repository.existsByNome(dados.nome())) {
            throw new RequestError(HttpStatus.CONFLICT,"nome","Serviço com esse nome já cadastrado");
        }
        Categoria categoria = categoriaRepository.findById(dados.idCategoria())
                .orElseThrow(() -> new RequestError(
                        HttpStatus.NOT_FOUND, "categoria", "Categoria não encontrada"));

        return repository.save(new Servico(dados, categoria));
    }

    @Transactional
    public Servico atualizar (Long id, DadosAtualizacaoServico dados){
        Servico servico = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));
        servico.atualizarInformacoes(dados);
        return repository.save(servico);
    }

    public Page<DadosListagemServico> listar(Long id, Pageable paginacao){
        if (repository.findByCategoriaId(id, paginacao).isEmpty()) {
            throw new RequestError(
                    HttpStatus.NOT_FOUND, "sem campo", "Nenhum Serviço encontrado na Categoria");
        }
        return repository.findAll(paginacao).map(DadosListagemServico::new);
    }

    public void deletar(Long id){
        Servico servico = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"));

        repository.delete(servico);
    }
}
