package apolov2.insightsApolo.v2.core.port.out;

import apolov2.insightsApolo.v2.core.domain.entity.Atendimento;

import java.time.LocalDate;
import java.util.List;

public interface AtendimentoGateway {
    List<Atendimento> buscarPorCategoriaEPeriodo(Long categoriaId, LocalDate inicio, LocalDate fim);
    List<Atendimento> buscarPorPeriodo(LocalDate inicio, LocalDate fim);
    Atendimento salvar(Atendimento atendimento);
}
