package school.sptech.ApoloInsightsBackEnd.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import school.sptech.ApoloInsightsBackEnd.old.domain.*;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.agendamento.DadosCadastroAgendamento;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.agendamento.DadosCadastroAgendamentoAdmin;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.agendamento.DadosHistoricoAgendamento;
import school.sptech.ApoloInsightsBackEnd.old.exception.RequestError;
import school.sptech.ApoloInsightsBackEnd.old.repository.AgendamentoRepository;
import school.sptech.ApoloInsightsBackEnd.old.repository.ServicoRepository;
import school.sptech.ApoloInsightsBackEnd.old.repository.UsuarioRepository;
import school.sptech.ApoloInsightsBackEnd.old.service.AgendamentoService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgendamentoServiceTest {

    @InjectMocks
    private AgendamentoService service;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ServicoRepository servicoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveLancarExcecaoUsuarioNaoEncontradoAoAgendar() {
        DadosCadastroAgendamento dados = mock(DadosCadastroAgendamento.class);
        when(dados.idUsuario()).thenReturn(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        RequestError ex = assertThrows(RequestError.class, () -> service.agendar(dados));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("usuario", ex.getCampo());
        assertEquals("Usuário não encontrado", ex.getMensagem());
    }

    @Test
    void deveLancarExcecaoServicoNaoEncontradoAoAgendar() {
        DadosCadastroAgendamento dados = mock(DadosCadastroAgendamento.class);
        when(dados.idUsuario()).thenReturn(1L);
        when(dados.idServico()).thenReturn(2L);

        Usuario usuario = new Usuario();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(servicoRepository.findById(2L)).thenReturn(Optional.empty());

        RequestError ex = assertThrows(RequestError.class, () -> service.agendar(dados));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("servico", ex.getCampo());
        assertEquals("Serviço não encontrado", ex.getMensagem());
    }

    @Test
    void deveLancarExcecaoUsuarioNaoEncontradoAoAgendarAdmin() {
        DadosCadastroAgendamentoAdmin dados = mock(DadosCadastroAgendamentoAdmin.class);
        when(dados.idUsuario()).thenReturn(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        RequestError ex = assertThrows(RequestError.class, () -> service.agendar(dados));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("usuario", ex.getCampo());
        assertEquals("Usuário não encontrado", ex.getMensagem());
    }

    @Test
    void deveLancarExcecaoServicoNaoEncontradoAoAgendarAdmin() {
        DadosCadastroAgendamentoAdmin dados = mock(DadosCadastroAgendamentoAdmin.class);
        when(dados.idUsuario()).thenReturn(1L);
        when(dados.idServico()).thenReturn(2L);

        Usuario usuario = new Usuario();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(servicoRepository.findById(2L)).thenReturn(Optional.empty());

        RequestError ex = assertThrows(RequestError.class, () -> service.agendar(dados));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("servico", ex.getCampo());
        assertEquals("Serviço não encontrado", ex.getMensagem());
    }

    @Test
    void deveListarHistoricoServicosComSucesso() {
        Agendamento agendamento = mock(Agendamento.class);
        Usuario usuario = new Usuario();
        usuario.setNome("João");
        Servico servico = new Servico();
        servico.setNome("Corte");
        when(agendamento.getUsuario()).thenReturn(usuario);
        when(agendamento.getServico()).thenReturn(servico);
        when(agendamento.getData()).thenReturn(LocalDate.now());
        when(agendamento.getHora()).thenReturn(LocalTime.NOON);
        when(agendamento.getStatus()).thenReturn(Status.valueOf("FINALIZADO"));
        when(agendamento.getFormaPagamento()).thenReturn(FormaPagamento.PIX);

        when(agendamentoRepository.findByUsuarioId(1L)).thenReturn(List.of(agendamento));
        when(agendamentoRepository.findById(anyLong())).thenReturn(Optional.of(agendamento));

        when(agendamentoRepository.findByUsuarioId(1L)).thenReturn(List.of(agendamento));

        List<DadosHistoricoAgendamento> result = service.listarHistoricoServicos(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void deveLancarExcecaoSeNaoHouverHistorico() {
        when(agendamentoRepository.findByUsuarioId(1L)).thenReturn(List.of());

        RequestError ex = assertThrows(RequestError.class, () -> service.listarHistoricoServicos(1L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("sem campo", ex.getCampo());
        assertEquals("Nenhum agendamento de serviço encontrado", ex.getMensagem());
    }
}
