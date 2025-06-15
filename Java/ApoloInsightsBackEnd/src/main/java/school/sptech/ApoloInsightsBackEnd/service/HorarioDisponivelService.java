package school.sptech.ApoloInsightsBackEnd.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.ApoloInsightsBackEnd.domain.*;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.horariodisponivel.DadosBloqueioHorario;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.horariodisponivel.DadosCadastroHorario;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.horariodisponivel.HorariosPorCategoriaDTO;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.horariodisponivel.HorariosPorDiaDTO;
import school.sptech.ApoloInsightsBackEnd.exception.RequestError;
import school.sptech.ApoloInsightsBackEnd.repository.BloqueioEspecificoRepository;
import school.sptech.ApoloInsightsBackEnd.repository.BloqueioSemanalRepository;
import school.sptech.ApoloInsightsBackEnd.repository.CategoriaRepository;
import school.sptech.ApoloInsightsBackEnd.repository.HorarioDisponivelRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HorarioDisponivelService {

    @Autowired
    private HorarioDisponivelRepository horarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private BloqueioSemanalRepository bloqueioSemanalRepository;

    @Autowired
    private BloqueioEspecificoRepository bloqueioEspecificoRepository;

    @Transactional
    public void cadastrarHorario(DadosCadastroHorario dados) {
        Categoria categoria = categoriaRepository.findById(dados.idCategoria())
               .orElseThrow(() -> new RequestError(HttpStatus.NOT_FOUND,"Categoria", "Categoria não encontrada!"));
        for (DayOfWeek dia : DayOfWeek.values()) {
            if (diaSelecionado(dia, dados)) {
                HorarioDisponivel horarioDisponivel = new HorarioDisponivel(
                        categoria,
                        dia,
                        dados.horaInicio(),
                        dados.horaFim()
                );
                horarioRepository.save(horarioDisponivel);
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



    public void bloquearHorario(@Valid DadosBloqueioHorario dados) {
        Categoria categoria = categoriaRepository.findById(dados.idCategoria())
                .orElseThrow(() -> new RequestError(HttpStatus.NOT_FOUND, "Categoria", "Categoria não encontrada!"));
        LocalTime horaInicio = dados.horaInicio();
        LocalTime horaFim = dados.horaFim();

        if (dados.diaInteiro()){
            horaInicio = LocalTime.parse("00:00");
            horaFim = LocalTime.parse("23:59");
        }

        validarHorario(horaInicio, horaFim, dados.data());

        if (Boolean.TRUE.equals(dados.repetirSemanalmente())) {
            var bloqueio = new BloqueioSemanal(categoria, dados.data(), horaInicio, horaFim);
            bloqueioSemanalRepository.save(bloqueio);
        } else {
            var bloqueio = new BloqueioEspecifico(categoria, dados.data(), horaInicio, horaFim);
            bloqueioEspecificoRepository.save(bloqueio);
        }
    }




    private List<HorarioDisponivel> buscarHorariosParaBloqueio(DadosBloqueioHorario dados) {
        if (Boolean.TRUE.equals(dados.repetirSemanalmente())) {
            // Bloqueio recorrente: busca pelo dia da semana
            DayOfWeek diaSemana = dados.data().getDayOfWeek(); // MONDAY = 1, SUNDAY = 7
            // Converte domingo = 0, segunda = 1, ..., sábado = 6

            return horarioRepository.buscarHorariosPorIntervaloSemanal(
                    dados.idCategoria(),
                    diaSemana,
                    dados.horaInicio(),
                    dados.horaFim()
            );
        } else {
            // Bloqueio específico: busca pela data exata
            return horarioRepository.findAllByCategoriaIdAndDataAndHoraInicioBetween(
                    dados.idCategoria(),
                    dados.data(),
                    dados.horaInicio(),
                    dados.horaFim()
            );
        }
    }

    public HorariosPorCategoriaDTO buscarHorariosPorCategoriaUnica(Long categoriaId, Integer mes, Integer ano) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        List<HorarioDisponivel> horariosDisponiveis = horarioRepository.findByCategoria(categoria);
        List<BloqueioSemanal> bloqueiosSemanais = bloqueioSemanalRepository.findByCategoria(categoria);
        List<BloqueioEspecifico> bloqueiosEspecificos = bloqueioEspecificoRepository.findByCategoria(categoria);

        LocalDate hoje = LocalDate.now();

        YearMonth anoMes;
        if (mes != null && ano != null) {
            anoMes = YearMonth.of(ano, mes);
        } else {
            anoMes = YearMonth.from(hoje);
        }

        List<HorariosPorDiaDTO> listaDias = new ArrayList<>();

        // Percorrer todos os dias do mês selecionado
        for (int diaDoMes = 1; diaDoMes <= anoMes.lengthOfMonth(); diaDoMes++) {
            LocalDate dia = anoMes.atDay(diaDoMes);

            // Se não passou mês/ano, limitar para os próximos 7 dias a partir de hoje
            if (mes == null || ano == null) {
                if (dia.isBefore(hoje) || dia.isAfter(hoje.plusDays(6))) {
                    continue; // pula dias fora dos próximos 7 dias
                }
            }

            DayOfWeek diaSemana = dia.getDayOfWeek();

            List<HorarioDisponivel> horariosDoDia = horariosDisponiveis.stream()
                    .filter(h -> h.getDiaSemana() == diaSemana)
                    .toList();

            List<BloqueioSemanal> bloqueiosSemanaisDoDia = bloqueiosSemanais.stream()
                    .filter(b -> b.getDiaSemana() == diaSemana.getValue())
                    .toList();

            List<BloqueioEspecifico> bloqueiosEspecificosDoDia = bloqueiosEspecificos.stream()
                    .filter(b -> b.getData().equals(dia))
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

        return new HorariosPorCategoriaDTO(categoria.getNome(), listaDias);
    }

    public HorariosPorCategoriaDTO buscarHorariosPorVariasCategorias(List<Long> categoriaIds, Integer mes, Integer ano) {
        List<Categoria> categorias = categoriaRepository.findAllById(categoriaIds);
        if (categorias.isEmpty()) {
            throw new RuntimeException("Nenhuma categoria encontrada");
        }

        List<HorarioDisponivel> horariosDisponiveis = horarioRepository.findByCategoriaIn(categorias);
        List<BloqueioSemanal> bloqueiosSemanais = bloqueioSemanalRepository.findByCategoriaIn(categorias);
        List<BloqueioEspecifico> bloqueiosEspecificos = bloqueioEspecificoRepository.findByCategoriaIn(categorias);

        LocalDate hoje = LocalDate.now();

        YearMonth anoMes = (mes != null && ano != null) ? YearMonth.of(ano, mes) : YearMonth.from(hoje);

        List<HorariosPorDiaDTO> listaDias = new ArrayList<>();

        for (int diaDoMes = 1; diaDoMes <= anoMes.lengthOfMonth(); diaDoMes++) {
            LocalDate dia = anoMes.atDay(diaDoMes);

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
                    .filter(b -> b.getDiaSemana() == diaSemana.getValue())
                    .toList();

            List<BloqueioEspecifico> bloqueiosEspecificosDoDia = bloqueiosEspecificos.stream()
                    .filter(b -> b.getData().equals(dia))
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

        // Caso queira juntar os nomes das categorias
        String nomeCategorias = categorias.stream().map(Categoria::getNome).collect(Collectors.joining(", "));

        return new HorariosPorCategoriaDTO(nomeCategorias, listaDias);
    }



    private boolean estaDentroDoIntervalo(LocalTime time, LocalTime inicio, LocalTime fim) {
        return !time.isBefore(inicio) && time.isBefore(fim);
    }




private void validarHorario(LocalTime horaInicio, LocalTime horaFim, LocalDate data) {
    if (horaInicio.isAfter(horaFim)) {
        throw new RequestError(HttpStatus.BAD_REQUEST, "horarioInicio", "Horário de início não pode ser depois que o horário de fim.");
    }

    if (data != null && data.isBefore(LocalDate.now())) {
        throw new RequestError(HttpStatus.BAD_REQUEST, "data", "Data não pode ser no passado.");
    }

    if (horaInicio.equals(horaFim)) {
        throw new RequestError(HttpStatus.BAD_REQUEST, "horario", "Horário de início não pode ser igual ao horário de fim.");
    }
}
}

