package school.sptech.ApoloInsightsBackEnd.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.servico.DadosAtualizacaoServico;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.servico.DadosListagemServico;
import school.sptech.ApoloInsightsBackEnd.domain.Servico;
import school.sptech.ApoloInsightsBackEnd.repository.ServicoRepository;

@Service
public class ServicoService {

    @Autowired
    ServicoRepository repository;


    @Transactional
    public Servico cadastrar (Servico servico){
        return repository.save(servico);
    }

    @Transactional
    public Servico atualizar (DadosAtualizacaoServico dados){
        Servico servico = repository.findById(dados.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));
        servico.atualizarInformacoes(dados);
        return repository.save(servico);
    }

    public Page<DadosListagemServico> listar(Pageable paginacao){
        if (repository.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum serviço encontrado");
        }
        return repository.findAll(paginacao).map(DadosListagemServico::new);
    }

    public void deletar(Long id){
        Servico servico = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"));

        repository.delete(servico);
    }

}
