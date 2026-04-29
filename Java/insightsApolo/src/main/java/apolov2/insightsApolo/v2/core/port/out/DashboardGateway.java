package apolov2.insightsApolo.v2.core.port.out;

import java.util.List;
import java.util.Map;

public interface DashboardGateway {
    List<Map<String, Object>> buscarAtendimentosPorMes();
    List<Map<String, Object>> buscarDistribuicaoHorarios();
    List<Map<String, Object>> buscarMediaServicosPorCliente();
    List<Map<String, Object>> buscarDistribuicaoDiaHora();
    List<Map<String, Object>> buscarServicoDoMes();
    List<Map<String, Object>> buscarMediaAtendimentosPorSemana();
    List<Map<String, Object>> buscarMediaGeralPorSemana();
    List<Map<String, Object>> buscarTop10Clientes();
    List<Map<String, Object>> buscarOcupacaoDias();
}
