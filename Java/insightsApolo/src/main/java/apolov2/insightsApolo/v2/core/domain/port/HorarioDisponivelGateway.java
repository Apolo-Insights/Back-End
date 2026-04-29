package apolov2.insightsApolo.v2.core.domain.port;

import apolov2.insightsApolo.v2.core.domain.entity.Categoria;
import apolov2.insightsApolo.v2.core.domain.entity.HorarioDisponivel;

import java.util.List;

public interface HorarioDisponivelGateway {
    HorarioDisponivel salvar(HorarioDisponivel horarioDisponivel);
    List<HorarioDisponivel> buscarPorCategoria(Categoria categoria);
    List<HorarioDisponivel> buscarPorCategorias(List<Categoria> categorias);
}
