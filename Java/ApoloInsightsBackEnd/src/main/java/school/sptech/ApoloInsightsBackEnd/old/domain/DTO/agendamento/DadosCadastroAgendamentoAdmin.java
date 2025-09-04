package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.agendamento;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record DadosCadastroAgendamentoAdmin(
        @NotNull(message = "Usuario não informado")
        Long idUsuario,
        @NotNull(message = "Serviço não informado")
        Long idServico,
        @NotNull(message = "Data não informada")
        LocalDate data,
        @NotNull(message = "horário não informado")
        LocalTime hora
) { }
