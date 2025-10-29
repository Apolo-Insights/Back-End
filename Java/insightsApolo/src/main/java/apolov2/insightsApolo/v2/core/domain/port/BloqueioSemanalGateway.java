package apolov2.insightsApolo.v2.core.domain.port;

import apolov2.insightsApolo.v2.core.domain.entity.BloqueioSemanal;
import apolov2.insightsApolo.v2.core.domain.entity.Categoria;

import java.util.List;

public interface BloqueioSemanalGateway {
    BloqueioSemanal salvar(BloqueioSemanal bloqueioSemanal);
    List<BloqueioSemanal> buscarPorCategoria(Categoria categoria);
    List<BloqueioSemanal> buscarPorCategorias(List<Categoria> categorias);
}
