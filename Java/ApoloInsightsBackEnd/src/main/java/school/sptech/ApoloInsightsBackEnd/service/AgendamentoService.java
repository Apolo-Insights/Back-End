package school.sptech.ApoloInsightsBackEnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.ApoloInsightsBackEnd.domain.Agendamento;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento.DadosCadastroAgendamento;
import school.sptech.ApoloInsightsBackEnd.domain.Servico;
import school.sptech.ApoloInsightsBackEnd.domain.Usuario;
import school.sptech.ApoloInsightsBackEnd.repository.AgendamentoRepository;
import school.sptech.ApoloInsightsBackEnd.repository.ServicoRepository;
import school.sptech.ApoloInsightsBackEnd.repository.UsuarioRepository;

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
        System.out.println("Salvando agendamento: " + agendamento);
        return agendamentoRepository.save(agendamento);
    }
}
