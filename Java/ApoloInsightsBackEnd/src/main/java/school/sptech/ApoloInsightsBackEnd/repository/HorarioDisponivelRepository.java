package school.sptech.ApoloInsightsBackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.ApoloInsightsBackEnd.domain.HorarioDisponivel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface HorarioDisponivelRepository extends JpaRepository<HorarioDisponivel, Long> {
    List<HorarioDisponivel> findAllByCategoriaIdAndBloqueadoFalse(Long idCategoria);

    // Para bloqueios recorrentes (semanais)
    List<HorarioDisponivel> findAllByCategoriaIdAndDiaSemanaAndHoraInicioBetween(
            Long idCategoria, DayOfWeek diaSemana, LocalTime horaInicio, LocalTime horaFim);

    // Para bloqueios por data específica
    List<HorarioDisponivel> findAllByCategoriaIdAndDataAndHoraInicioBetween(
            Long idCategoria, LocalDate data, LocalTime horaInicio, LocalTime horaFim);
}

