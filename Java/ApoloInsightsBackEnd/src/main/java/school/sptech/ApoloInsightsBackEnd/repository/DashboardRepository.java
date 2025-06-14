package school.sptech.ApoloInsightsBackEnd.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DashboardRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> buscarAtendimentosPorMes() {
        return jdbcTemplate.queryForList(
                "SELECT c.nome AS categoria, MONTH(a.data) AS mes, YEAR(a.data) AS ano, COUNT(a.id) AS quantidade_atendimentos " +
                        "FROM atendimentos a " +
                        "JOIN categorias c ON a.categoria_id = c.id " +
                        "GROUP BY c.nome, ano, mes " +
                        "ORDER BY ano, mes, c.nome"
        );
    }

    public List<Map<String, Object>> buscarDistribuicaoHorarios() {
        return jdbcTemplate.queryForList(
                "SELECT c.nome AS categoria, HOUR(a.hora) AS hora_atendimento, COUNT(a.id) AS quantidade_atendimentos " +
                        "FROM agendamentos a " +
                        "JOIN servicos s ON a.servico_id = s.id " +
                        "JOIN categorias c ON s.categoria_id = c.id " +
                        "WHERE a.status = 'FINALIZADO' " +
                        "GROUP BY c.nome, hora_atendimento " +
                        "ORDER BY c.nome, hora_atendimento"
        );
    }

    public List<Map<String, Object>> buscarMediaServicosPorCliente() {
        return jdbcTemplate.queryForList(
                "SELECT YEAR(a.data) AS ano, MONTH(a.data) AS mes, " +
                        "ROUND(COUNT(a.id) * 1.0 / COUNT(DISTINCT a.usuario_id), 2) AS media_servicos_por_cliente " +
                        "FROM agendamentos a " +
                        "WHERE a.status = 'FINALIZADO' " +
                        "GROUP BY ano, mes " +
                        "ORDER BY ano, mes"
        );
    }

    public List<Map<String, Object>> buscarDistribuicaoDiaHora() {
        return jdbcTemplate.queryForList(
                "SELECT " +
                        "    CASE DAYOFWEEK(a.data) " +
                        "        WHEN 1 THEN 'DOMINGO' " +
                        "        WHEN 2 THEN 'SEGUNDA' " +
                        "        WHEN 3 THEN 'TERCA' " +
                        "        WHEN 4 THEN 'QUARTA' " +
                        "        WHEN 5 THEN 'QUINTA' " +
                        "        WHEN 6 THEN 'SEXTA' " +
                        "        WHEN 7 THEN 'SABADO' " +
                        "    END AS dia_semana, " +
                        "    HOUR(a.hora) AS hora_atendimento, " +
                        "    COUNT(a.id) AS quantidade_atendimentos " +
                        "FROM agendamentos a " +
                        "WHERE a.status = 'FINALIZADO' " +
                        "  AND a.data >= DATE_SUB(CURDATE(), INTERVAL 3 MONTH) " +
                        "GROUP BY dia_semana, hora_atendimento " +
                        "ORDER BY FIELD(dia_semana, 'DOMINGO', 'SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA', 'SABADO'), " +
                        "         hora_atendimento"
        );
    }

    public List<Map<String, Object>> buscarServicoDoMes() {
        return jdbcTemplate.queryForList(
                "SELECT s.nome AS servico, COUNT(a.id) AS quantidade, " +
                        "ROUND(COUNT(a.id) * 100.0 / (SELECT COUNT(*) FROM agendamentos WHERE status = 'FINALIZADO' AND MONTH(data) = MONTH(CURDATE()) AND YEAR(data) = YEAR(CURDATE())), 2) AS frequencia_percent " +
                        "FROM agendamentos a " +
                        "JOIN servicos s ON a.servico_id = s.id " +
                        "WHERE a.status = 'FINALIZADO' AND MONTH(a.data) = MONTH(CURDATE()) AND YEAR(a.data) = YEAR(CURDATE()) " +
                        "GROUP BY s.nome " +
                        "ORDER BY quantidade DESC"
        );
    }

    public List<Map<String, Object>> buscarMediaAtendimentosPorSemana() {
        return jdbcTemplate.queryForList(
                "SELECT YEAR(a.data) AS ano, WEEK(a.data, 1) AS semana, COUNT(a.id) AS total_atendimentos " +
                        "FROM agendamentos a " +
                        "WHERE a.status = 'FINALIZADO' " +
                        "GROUP BY ano, semana " +
                        "ORDER BY ano, semana"
        );
    }

    public List<Map<String, Object>> buscarMediaGeralPorSemana() {
        return jdbcTemplate.queryForList(
                "SELECT ROUND(AVG(semanal.total_atendimentos), 2) AS media_atendimentos_por_semana " +
                        "FROM ( " +
                        "   SELECT YEAR(a.data) AS ano, WEEK(a.data, 1) AS semana, COUNT(a.id) AS total_atendimentos " +
                        "   FROM agendamentos a " +
                        "   WHERE a.status = 'FINALIZADO' " +
                        "   GROUP BY ano, semana " +
                        ") semanal"
        );
    }

    public List<Map<String, Object>> buscarTop10Clientes() {
        return jdbcTemplate.queryForList(
                "SELECT u.nome AS cliente, " +
                        "u.role AS role_code, " +
                        "COUNT(a.id) AS total_atendimentos " +
                        "FROM agendamentos a " +
                        "JOIN usuarios u ON a.usuario_id = u.id " +
                        "WHERE a.status = 'FINALIZADO' " +
                        "GROUP BY u.id, u.nome, u.role " +
                        "ORDER BY total_atendimentos DESC " +
                        "LIMIT 10"
        );
    }

    public List<Map<String, Object>> buscarOcupacaoDias() {
        return jdbcTemplate.queryForList(
                "SELECT " +
                        "    ag.dia_semana, " +
                        "    ag.total_agendado, " +
                        "    disp.total_slots_disponiveis, " +
                        "    ROUND((ag.total_agendado * 100.0) / disp.total_slots_disponiveis, 2) AS taxa_ocupacao_percent " +
                        "FROM ( " +
                        "    SELECT " +
                        "        CASE DAYOFWEEK(a.data) " +
                        "            WHEN 1 THEN 'DOMINGO' " +
                        "            WHEN 2 THEN 'SEGUNDA' " +
                        "            WHEN 3 THEN 'TERCA' " +
                        "            WHEN 4 THEN 'QUARTA' " +
                        "            WHEN 5 THEN 'QUINTA' " +
                        "            WHEN 6 THEN 'SEXTA' " +
                        "            WHEN 7 THEN 'SABADO' " +
                        "        END AS dia_semana, " +
                        "        COUNT(a.id) AS total_agendado " +
                        "    FROM agendamentos a " +
                        "    WHERE a.status = 'FINALIZADO' " +
                        "      AND a.data >= DATE_SUB(CURDATE(), INTERVAL 3 MONTH) " +
                        "    GROUP BY dia_semana " +
                        ") ag " +
                        "JOIN ( " +
                        "    SELECT " +
                        "        CASE h.dia_semana " +
                        "            WHEN 0 THEN 'DOMINGO' " +
                        "            WHEN 1 THEN 'SEGUNDA' " +
                        "            WHEN 2 THEN 'TERCA' " +
                        "            WHEN 3 THEN 'QUARTA' " +
                        "            WHEN 4 THEN 'QUINTA' " +
                        "            WHEN 5 THEN 'SEXTA' " +
                        "            WHEN 6 THEN 'SABADO' " +
                        "        END AS dia_semana, " +
                        "        SUM(TIMESTAMPDIFF(MINUTE, h.hora_inicio, h.hora_fim) / 30) AS total_slots_disponiveis " +
                        "    FROM horarios_disponiveis h " +
                        "    GROUP BY h.dia_semana " +
                        ") disp " +
                        "ON ag.dia_semana = disp.dia_semana " +
                        "ORDER BY FIELD(ag.dia_semana, 'DOMINGO', 'SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA', 'SABADO')"
        );
    }
}
