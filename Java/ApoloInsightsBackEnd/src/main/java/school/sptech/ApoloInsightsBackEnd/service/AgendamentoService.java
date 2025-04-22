package school.sptech.ApoloInsightsBackEnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.ApoloInsightsBackEnd.domain.Agendamento;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento.DadosCadastroAgendamento;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento.DadosHistoricoAgendamento;
import school.sptech.ApoloInsightsBackEnd.domain.Servico;
import school.sptech.ApoloInsightsBackEnd.domain.Usuario;
import school.sptech.ApoloInsightsBackEnd.repository.AgendamentoRepository;
import school.sptech.ApoloInsightsBackEnd.repository.ServicoRepository;
import school.sptech.ApoloInsightsBackEnd.repository.UsuarioRepository;

import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ServicoRepository servicoRepository;

    @Transactional
    public Agendamento agendar(DadosCadastroAgendamento dados) {
        Usuario usuario = usuarioRepository.findById(dados.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Servico servico = servicoRepository.findById(dados.servicoId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        Agendamento agendamento = new Agendamento(usuario, servico, dados.data(), dados.hora());
        return agendamentoRepository.save(agendamento);
    }

    public List<DadosHistoricoAgendamento> listarHistoricoServicos() {
        if (agendamentoRepository.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum serviço encontrado");
        }
        return agendamentoRepository.findAll().stream().map(DadosHistoricoAgendamento::new).toList();
    }
}
