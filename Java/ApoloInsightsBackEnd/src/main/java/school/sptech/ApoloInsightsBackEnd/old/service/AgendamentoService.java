package school.sptech.ApoloInsightsBackEnd.old.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.ApoloInsightsBackEnd.old.domain.Agendamento;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.agendamento.*;
import school.sptech.ApoloInsightsBackEnd.old.domain.Servico;
import school.sptech.ApoloInsightsBackEnd.old.domain.Usuario;
import school.sptech.ApoloInsightsBackEnd.old.repository.AgendamentoRepository;
import school.sptech.ApoloInsightsBackEnd.old.repository.ServicoRepository;
import school.sptech.ApoloInsightsBackEnd.old.repository.UsuarioRepository;
import school.sptech.ApoloInsightsBackEnd.old.exception.RequestError;

import java.time.LocalDate;
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

    public List<Agendamento> agendarMultiplo(@Valid DadosCadastroAgendamentoMultiplo dados) {
        Usuario usuario = usuarioRepository.findById(dados.idUsuario())
                .orElseThrow(() -> new RequestError(HttpStatus.NOT_FOUND, "usuario", "Usuário não encontrado"));

        List<Servico> servicos = servicoRepository.findAllById(dados.idsServicos());
        if (servicos.isEmpty()) {
            throw new RequestError(HttpStatus.NOT_FOUND, "servico", "Nenhum serviço encontrado");
        }

        List<Agendamento> agendamentos = servicos.stream()
                .map(servico -> new Agendamento(usuario, servico, dados.data(), dados.hora(), dados.formaPagamento()))
                .toList();

        return agendamentoRepository.saveAll(agendamentos);
    }

    @Transactional
    public Agendamento agendar(DadosCadastroAgendamentoAdmin dados) {
        Usuario usuario = usuarioRepository.findById(dados.idUsuario())
                .orElseThrow(() -> new RequestError(HttpStatus.NOT_FOUND, "usuario", "Usuário não encontrado"));

        Servico servico = servicoRepository.findById(dados.idServico())
                .orElseThrow(() -> new RequestError(HttpStatus.NOT_FOUND, "servico", "Serviço não encontrado"));

        Agendamento agendamento = new Agendamento(usuario, servico, dados.data(), dados.hora());
        return agendamentoRepository.save(agendamento);
    }

    public List<DadosHistoricoAgendamento> listarHistoricoServicos(Long idUsuario) {
        if (agendamentoRepository.findByUsuarioId(idUsuario).isEmpty()) {
            throw new RequestError(HttpStatus.NOT_FOUND, "sem campo", "Nenhum agendamento de serviço encontrado");
        }
        return agendamentoRepository.findByUsuarioId(idUsuario).stream().map(DadosHistoricoAgendamento::new).toList();
    }

    public List<DadosListagemPorCategoria> listarAgendamentosPorCategoria(Long idCategoria, Integer mes, Integer ano) {
        LocalDate inicio = LocalDate.of(ano, mes, 1);
        LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());
        List<Agendamento> agendamentos = agendamentoRepository
                .findByServico_Categoria_IdAndDataBetween(idCategoria, inicio, fim);

        return agendamentos.stream()
                .map(agendamento -> new DadosListagemPorCategoria(
                        agendamento.getId(),
                        agendamento.getUsuario().getNome(),
                        agendamento.getServico().getNome(),
                        agendamento.getData(),
                        agendamento.getHora(),
                        agendamento.getStatus(),
                        agendamento.getFormaPagamento().toString().toLowerCase()
                ))
                .toList();
    }


}
