package apolov2.insightsApolo.v2.core.domain.port;

import apolov2.insightsApolo.v2.core.domain.entity.BloqueioEspecifico;
import apolov2.insightsApolo.v2.core.domain.entity.Categoria;

import java.util.List;

public interface BloqueioEspecificoGateway {
    BloqueioEspecifico salvar(BloqueioEspecifico bloqueioEspecifico);
    List<BloqueioEspecifico> buscarPorCategoria(Categoria categoria);
    List<BloqueioEspecifico> buscarPorCategorias(List<Categoria> categorias);
}
