package school.sptech.ApoloInsightsBackEnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.ApoloInsightsBackEnd.domain.Categoria;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.horarioDisponivel.DadosCadastroHorario;
import school.sptech.ApoloInsightsBackEnd.domain.HorarioDisponivel;
import school.sptech.ApoloInsightsBackEnd.exception.RequestError;
import school.sptech.ApoloInsightsBackEnd.repository.CategoriaRepository;
import school.sptech.ApoloInsightsBackEnd.repository.HorarioDisponivelRepository;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HorarioDisponivelService {

    @Autowired
    private HorarioDisponivelRepository horarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

//    @Transactional
//    public HorarioDisponivel cadastrarHorario(DadosCadastroHorario dados) {
//        Categoria categoria = categoriaRepository.findById(dados.idCategoria())
//                .orElseThrow(() -> new RequestError(HttpStatus.NOT_FOUND,"Categoria", "Categoria não encontrada!"));
//
//        if (horarioRepository.existsHorarioConflitante(dados., dados.horaInicio(), dados.horaFim())) {
//            throw new RequestError(HttpStatus.BAD_REQUEST, "Horário", "Horário já cadastrado!");
//        }
//    }


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

    public Map<DayOfWeek, List<String>> listarHorariosPorCategoria(Long idCategoria) {
        List<HorarioDisponivel> horarios = horarioRepository.findAllByCategoriaIdAndBloqueadoFalse(idCategoria);

        if (horarios.isEmpty()) {
            throw new RuntimeException("Nenhum horário encontrado para a categoria: " + idCategoria);
        }

        Map<DayOfWeek, List<String>> resultado = new HashMap<>();

        for (HorarioDisponivel horario : horarios) {
            List<String> listaHorarios = new ArrayList<>();
            LocalTime atual = horario.getHoraInicio();

            while (!atual.isAfter(horario.getHoraFim().minusMinutes(10))) {
                listaHorarios.add(atual.toString());
                atual = atual.plusMinutes(10);
            }

            resultado.put(horario.getDiaSemana(), listaHorarios);
        }

        return resultado;
    }
}
