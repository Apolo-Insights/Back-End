package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DadosDashboard {
    private List<Map<String, Object>> atendimentosPorMes;
    private List<Map<String, Object>> distribuicaoHorarios;
    private List<Map<String, Object>> mediaServicosPorCliente;
    private List<Map<String, Object>> distribuicaoDiaHora;
    private List<Map<String, Object>> servicoDoMes;
    private List<Map<String, Object>> mediaAtendimentosPorSemana;
    private List<Map<String, Object>> mediaGeralPorSemana;
    private List<Map<String, Object>> top10Clientes;
    private List<Map<String, Object>> ocupacaoDias;
}
