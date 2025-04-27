package school.sptech.ApoloInsightsBackEnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.ApoloInsightsBackEnd.domain.Agendamento;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento.DadosCadastroAgendamento;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento.DadosHistoricoAgendamento;
import school.sptech.ApoloInsightsBackEnd.domain.Servico;
import school.sptech.ApoloInsightsBackEnd.domain.Usuario;
import school.sptech.ApoloInsightsBackEnd.repository.AgendamentoRepository;
import school.sptech.ApoloInsightsBackEnd.repository.ServicoRepository;
import school.sptech.ApoloInsightsBackEnd.repository.UsuarioRepository;
import school.sptech.ApoloInsightsBackEnd.util.exception.RequestError;
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
        Usuario usuario = usuarioRepository.findById(dados.idUsuario())
                .orElseThrow(() -> new RequestError(HttpStatus.NOT_FOUND, "usuario", "Usuário não encontrado"));

        Servico servico = servicoRepository.findById(dados.idServico())
                .orElseThrow(() -> new RequestError(HttpStatus.NOT_FOUND, "servico", "Serviço não encontrado"));

        Agendamento agendamento = new Agendamento(usuario, servico, dados.data(), dados.hora(), dados.formaPagamento());
        return agendamentoRepository.save(agendamento);
    }

    public List<DadosHistoricoAgendamento> listarHistoricoServicos() {
        if (agendamentoRepository.findAll().isEmpty()) {
            throw new RequestError(HttpStatus.NOT_FOUND, "sem campo", "Nenhum agendamento de serviço encontrado");
        }
        return agendamentoRepository.findAll().stream().map(DadosHistoricoAgendamento::new).toList();
    }
}
