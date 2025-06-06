package school.sptech.ApoloInsightsBackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.ApoloInsightsBackEnd.domain.Categoria;
import school.sptech.ApoloInsightsBackEnd.domain.HorarioDisponivel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface HorarioDisponivelRepository extends JpaRepository<HorarioDisponivel, Long> {

    // Para bloqueios recorrentes (semanais)
    @Query("""
    SELECT h FROM HorarioDisponivel h
    WHERE h.categoria.id = :categoriaId
    AND h.diaSemana = :diaSemana
    AND h.horaInicio < :horaFim
    AND h.horaFim > :horaInicio
""")
    List<HorarioDisponivel> buscarHorariosPorIntervaloSemanal(
            @Param("categoriaId") Long categoriaId,
            @Param("diaSemana") DayOfWeek diaSemana,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFim") LocalTime horaFim
    );

    // Para bloqueios por data específica
    List<HorarioDisponivel> findAllByCategoriaIdAndDataAndHoraInicioBetween(
            Long idCategoria, LocalDate data, LocalTime horaInicio, LocalTime horaFim);



    List<HorarioDisponivel> findByCategoria(Categoria categoria);
}

