package apolov2.insightsApolo.v2.core.application.usecase;

import apolov2.insightsApolo.v2.core.domain.entity.*;
import apolov2.insightsApolo.v2.core.domain.port.BloqueioEspecificoGateway;
import apolov2.insightsApolo.v2.core.domain.port.BloqueioSemanalGateway;
import apolov2.insightsApolo.v2.core.port.in.CategoriaGateway;
import apolov2.insightsApolo.v2.core.domain.port.HorarioDisponivelGateway;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.horariodisponivel.DadosBloqueioHorario;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.horariodisponivel.DadosCadastroHorario;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.horariodisponivel.HorariosPorCategoriaDTO;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.horariodisponivel.HorariosPorDiaDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HorarioDisponivelUseCase {

    private final HorarioDisponivelGateway horarioDisponivelGateway;
    private final BloqueioSemanalGateway bloqueioSemanalGateway;
    private final BloqueioEspecificoGateway bloqueioEspecificoGateway;
    private final CategoriaGateway categoriaGateway;

    @Transactional
    public void cadastrarHorario(DadosCadastroHorario dados) {
        Categoria categoria = categoriaGateway.buscarPorId(dados.idCategoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada!"));

        for (DayOfWeek dia : DayOfWeek.values()) {
            if (diaSelecionado(dia, dados)) {
                HorarioDisponivel horarioDisponivel = new HorarioDisponivel(
                        categoria,
                        dia,
                        dados.horaInicio(),
                        dados.horaFim()
                );
                horarioDisponivelGateway.salvar(horarioDisponivel);
            }
        }
    }

    private boolean diaSelecionado(DayOfWeek dia, DadosCadastroHorario dados) {
        return switch (dia) {
            case SUNDAY -> dados.domingo();
            case MONDAY -> dados.segunda();
            case TUESDAY -> dados.terca();
            case WEDNESDAY -> dados.quarta();
            case THURSDAY -> dados.quinta();
            case FRIDAY -> dados.sexta();
            case SATURDAY -> dados.sabado();
        };
    }

    @Transactional
    public void bloquearHorario(DadosBloqueioHorario dados) {
        Categoria categoria = categoriaGateway.buscarPorId(dados.idCategoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada!"));

        LocalTime horaInicio = dados.horaInicio();
        LocalTime horaFim = dados.horaFim();

        if (Boolean.TRUE.equals(dados.diaInteiro())) {
            horaInicio = LocalTime.parse("00:00");
            horaFim = LocalTime.parse("23:59");
        }

        validarHorario(horaInicio, horaFim, dados.data());

        if (Boolean.TRUE.equals(dados.repetirSemanalmente())) {
            var bloqueio = new BloqueioSemanal(categoria, dados.data(), horaInicio, horaFim);
            bloqueioSemanalGateway.salvar(bloqueio);
        } else {
            var bloqueio = new BloqueioEspecifico(categoria, dados.data(), horaInicio, horaFim);
            bloqueioEspecificoGateway.salvar(bloqueio);
        }
    }

    private void validarHorario(LocalTime horaInicio, LocalTime horaFim, LocalDate data) {
        if (horaInicio.isAfter(horaFim)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horário de início não pode ser depois que o horário de fim.");
        }

        if (data != null && data.isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data não pode ser no passado.");
        }

        if (horaInicio.equals(horaFim)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horário de início não pode ser igual ao horário de fim.");
        }
    }

    public HorariosPorCategoriaDTO buscarHorariosPorCategoriaUnica(Long categoriaId, Integer mes, Integer ano) {
        Categoria categoria = categoriaGateway.buscarPorId(categoriaId)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        List<HorarioDisponivel> horariosDisponiveis = horarioDisponivelGateway.buscarPorCategoria(categoria);
        List<BloqueioSemanal> bloqueiosSemanais = bloqueioSemanalGateway.buscarPorCategoria(categoria);
        List<BloqueioEspecifico> bloqueiosEspecificos = bloqueioEspecificoGateway.buscarPorCategoria(categoria);

        List<HorariosPorDiaDTO> listaDias = processarHorariosPorMes(
                horariosDisponiveis, 
                bloqueiosSemanais, 
                bloqueiosEspecificos, 
                mes, 
                ano
        );

        return new HorariosPorCategoriaDTO(categoria.getNome(), listaDias);
    }

    public HorariosPorCategoriaDTO buscarHorariosPorVariasCategorias(List<Long> categoriaIds, Integer mes, Integer ano) {
        List<Categoria> categorias = categoriaIds.stream()
                .map(id -> categoriaGateway.buscarPorId(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        if (categorias.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma categoria encontrada");
        }

        List<HorarioDisponivel> horariosDisponiveis = horarioDisponivelGateway.buscarPorCategorias(categorias);
        List<BloqueioSemanal> bloqueiosSemanais = bloqueioSemanalGateway.buscarPorCategorias(categorias);
        List<BloqueioEspecifico> bloqueiosEspecificos = bloqueioEspecificoGateway.buscarPorCategorias(categorias);

        List<HorariosPorDiaDTO> listaDias = processarHorariosPorMes(
                horariosDisponiveis, 
                bloqueiosSemanais, 
                bloqueiosEspecificos, 
                mes, 
                ano
        );

        String nomeCategorias = categorias.stream()
                .map(Categoria::getNome)
                .collect(Collectors.joining(", "));

        return new HorariosPorCategoriaDTO(nomeCategorias, listaDias);
    }

    private List<HorariosPorDiaDTO> processarHorariosPorMes(
            List<HorarioDisponivel> horariosDisponiveis,
            List<BloqueioSemanal> bloqueiosSemanais,
            List<BloqueioEspecifico> bloqueiosEspecificos,
            Integer mes,
            Integer ano
    ) {
        LocalDate hoje = LocalDate.now();
        YearMonth anoMes;

        if (mes != null && ano != null) {
            anoMes = YearMonth.of(ano, mes);
        } else {
            anoMes = YearMonth.from(hoje);
        }

        List<HorariosPorDiaDTO> listaDias = new ArrayList<>();

        for (int diaDoMes = 1; diaDoMes <= anoMes.lengthOfMonth(); diaDoMes++) {
            LocalDate dia = anoMes.atDay(diaDoMes);

            // Se não passou mês/ano, limitar para os próximos 7 dias
            if (mes == null || ano == null) {
                if (dia.isBefore(hoje) || dia.isAfter(hoje.plusDays(6))) {
                    continue;
                }
            }

            DayOfWeek diaSemana = dia.getDayOfWeek();

            List<HorarioDisponivel> horariosDoDia = horariosDisponiveis.stream()
                    .filter(h -> h.getDiaSemana() == diaSemana)
                    .toList();

            List<BloqueioSemanal> bloqueiosSemanaisDoDia = bloqueiosSemanais.stream()
                    .filter(b -> b.getDiaSemana() != null && b.getDiaSemana() == diaSemana.getValue())
                    .toList();

            List<BloqueioEspecifico> bloqueiosEspecificosDoDia = bloqueiosEspecificos.stream()
                    .filter(b -> b.getData() != null && b.getData().equals(dia))
                    .toList();

            Set<String> horariosBloqueados = new HashSet<>();
            Set<String> horariosDisponiveisSet = new HashSet<>();

            for (HorarioDisponivel hd : horariosDoDia) {
                LocalTime inicio = hd.getHoraInicio();
                LocalTime fim = hd.getHoraFim();

                for (LocalTime t = inicio; !t.isAfter(fim.minusMinutes(10)); t = t.plusMinutes(10)) {
                    final LocalTime horarioAtual = t;

                    boolean bloqueado = bloqueiosSemanaisDoDia.stream()
                            .anyMatch(b -> estaDentroDoIntervalo(horarioAtual, b.getHoraInicio(), b.getHoraFim()))
                            || bloqueiosEspecificosDoDia.stream()
                            .anyMatch(b -> estaDentroDoIntervalo(horarioAtual, b.getHoraInicio(), b.getHoraFim()));

                    String horarioStr = horarioAtual.toString();

                    if (bloqueado) {
                        horariosBloqueados.add(horarioStr);
                    } else {
                        horariosDisponiveisSet.add(horarioStr);
                    }
                }
            }

            List<String> horariosBloqueadosList = new ArrayList<>(horariosBloqueados);
            horariosBloqueadosList.sort(String::compareTo);

            List<String> horariosDisponiveisList = new ArrayList<>(horariosDisponiveisSet);
            horariosDisponiveisList.sort(String::compareTo);

            listaDias.add(new HorariosPorDiaDTO(
                    dia.format(DateTimeFormatter.ofPattern("dd/MM")),
                    horariosBloqueadosList,
                    horariosDisponiveisList
            ));
        }

        return listaDias;
    }

    private boolean estaDentroDoIntervalo(LocalTime time, LocalTime inicio, LocalTime fim) {
        return !time.isBefore(inicio) && time.isBefore(fim);
    }
}
