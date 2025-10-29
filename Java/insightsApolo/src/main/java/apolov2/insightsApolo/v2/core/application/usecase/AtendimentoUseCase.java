package apolov2.insightsApolo.v2.core.application.usecase;

import apolov2.insightsApolo.v2.core.domain.entity.Agendamento;
import apolov2.insightsApolo.v2.core.domain.entity.Atendimento;
import apolov2.insightsApolo.v2.core.domain.entity.Categoria;
import apolov2.insightsApolo.v2.core.domain.entity.Usuario;
import apolov2.insightsApolo.v2.core.domain.util.Status;
import apolov2.insightsApolo.v2.core.port.in.AgendamentoGateway;
import apolov2.insightsApolo.v2.core.port.out.AtendimentoGateway;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.atendimento.DadosDetalhamentoAtendimento;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AtendimentoUseCase {

    private final AtendimentoGateway atendimentoGateway;
    private final AgendamentoGateway agendamentoGateway;

    @Transactional
    public DadosDetalhamentoAtendimento confirmarAtendimento(Long idAgendamento) {
        Agendamento agendamento = agendamentoGateway.buscarPorId(idAgendamento)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado!"));

        // Verificar se já foi finalizado ou cancelado
        if (agendamento.getStatus() != Status.AGENDADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O atendimento já foi finalizado ou cancelado.");
        }

        Double valor = agendamento.getServico().getPreco();
        if (valor == null || valor <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor do atendimento deve ser maior que zero.");
        }

        // Criar atendimento
        LocalDate data = agendamento.getData();
        Categoria categoria = agendamento.getServico().getCategoria();
        Usuario cliente = agendamento.getUsuario();

        Atendimento atendimento = new Atendimento(categoria, valor, data, cliente);
        Atendimento atendimentoSalvo = atendimentoGateway.salvar(atendimento);

        // Atualizar status do agendamento para FINALIZADO
        agendamento.setStatus(Status.FINALIZADO);
        agendamentoGateway.atualizar(agendamento);

        return new DadosDetalhamentoAtendimento(
                atendimentoSalvo.getCategoria().getId(),
                atendimentoSalvo.getValor()
        );
    }

    @Transactional
    public void cancelarAtendimento(Long idAgendamento) {
        Agendamento agendamento = agendamentoGateway.buscarPorId(idAgendamento)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado!"));

        // Verificar se já foi finalizado ou cancelado
        if (agendamento.getStatus() != Status.AGENDADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O atendimento já foi finalizado ou cancelado.");
        }

        // Atualizar status para CANCELADO
        agendamento.setStatus(Status.CANCELADO);
        agendamentoGateway.atualizar(agendamento);

        // TODO: Implementar envio de email de cancelamento
        // A lógica de envio de email pode ser implementada aqui ou via evento
    }
}
