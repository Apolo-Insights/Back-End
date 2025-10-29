package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.controller;

import apolov2.insightsApolo.v2.core.application.command.AgendarCommand;
import apolov2.insightsApolo.v2.core.application.command.AgendarMultiploCommand;
import apolov2.insightsApolo.v2.core.application.usecase.AgendamentoUseCase;
import apolov2.insightsApolo.v2.core.domain.entity.Agendamento;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroAgendamento;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroAgendamentoAdmin;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroAgendamentoMultiplo;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosDetalhamentoAgendamento;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosDetalhamentoAgendamentoMultiplo;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosHistoricoAgendamento;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosListagemPorCategoria;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper.AgendamentoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Agendamentos", description = "Gerenciamento de agendamentos")
@CrossOrigin(origins = "${cors.allowed.origin}")
@RestController
@RequestMapping("/v2/agendamentos")
public class AgendamentoController {
    private final AgendamentoUseCase useCase;
    private final AgendamentoMapper mapper;

    public AgendamentoController(AgendamentoUseCase useCase, AgendamentoMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Agendar serviço", description = "Cria um novo agendamento para um usuário com forma de pagamento")
    public ResponseEntity<DadosDetalhamentoAgendamento> agendar(@Valid @RequestBody DadosCadastroAgendamento dados) {
        AgendarCommand command = mapper.cadastroToCommand(dados);
        Agendamento agendamento = useCase.agendar(command);
        return ResponseEntity.status(201).body(new DadosDetalhamentoAgendamento(agendamento));
    }

    @PostMapping("/agendar-multiplo")
    @Operation(summary = "Agendar múltiplos serviços", description = "Cria múltiplos agendamentos para um usuário com vários serviços")
    public ResponseEntity<DadosDetalhamentoAgendamentoMultiplo> agendarMultiplo(
            @Valid @RequestBody DadosCadastroAgendamentoMultiplo dados) {
        AgendarMultiploCommand command = mapper.cadastroToCommand(dados);
        List<Agendamento> agendamentos = useCase.agendarMultiplo(command);
        return ResponseEntity.status(201).body(new DadosDetalhamentoAgendamentoMultiplo(agendamentos));
    }

    @PostMapping("/admin")
    @Operation(summary = "Agendar serviço (Admin)", description = "Cria um agendamento pelo administrador sem forma de pagamento")
    public ResponseEntity<DadosDetalhamentoAgendamento> agendarAdmin(
            @Valid @RequestBody DadosCadastroAgendamentoAdmin dados) {
        AgendarCommand command = mapper.cadastroAdminToCommand(dados);
        Agendamento agendamento = useCase.agendar(command);
        return ResponseEntity.ok(new DadosDetalhamentoAgendamento(agendamento));
    }

    @GetMapping("/{idUsuario}")
    @Operation(summary = "Listar histórico de agendamentos", description = "Lista todos os agendamentos de um usuário")
    public ResponseEntity<List<DadosHistoricoAgendamento>> listarHistoricoServicos(
            @PathVariable Long idUsuario) {
        List<Agendamento> agendamentos = useCase.listarHistoricoServicos(idUsuario);
        List<DadosHistoricoAgendamento> historico = agendamentos.stream()
                .map(DadosHistoricoAgendamento::new)
                .toList();
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/admin/{idCategoria}")
    @Operation(summary = "Listar agendamentos por categoria", description = "Lista agendamentos de uma categoria em um período específico")
    public ResponseEntity<List<DadosListagemPorCategoria>> listarAgendamentosPorCategoria(
            @PathVariable Long idCategoria,
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer ano) {
        List<Agendamento> agendamentos = useCase.listarAgendamentosPorCategoria(idCategoria, mes, ano);
        List<DadosListagemPorCategoria> listagem = agendamentos.stream()
                .map(agendamento -> new DadosListagemPorCategoria(
                        agendamento.getId(),
                        agendamento.getUsuario().getNome(),
                        agendamento.getServico().getNome(),
                        agendamento.getData(),
                        agendamento.getHora(),
                        agendamento.getStatus(),
                        agendamento.getFormaPagamento() != null ? agendamento.getFormaPagamento().toString().toLowerCase() : null
                ))
                .toList();
        return ResponseEntity.ok(listagem);
    }
}
