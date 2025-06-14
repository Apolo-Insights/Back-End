package school.sptech.ApoloInsightsBackEnd.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.ApoloInsightsBackEnd.domain.Categoria;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.servico.DadosAtualizacaoServico;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.servico.DadosCadastroServico;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.servico.DadosListagemServico;
import school.sptech.ApoloInsightsBackEnd.domain.Servico;
import school.sptech.ApoloInsightsBackEnd.exception.RequestError;
import school.sptech.ApoloInsightsBackEnd.repository.CategoriaRepository;
import school.sptech.ApoloInsightsBackEnd.repository.ServicoRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicoServiceTest {

    @InjectMocks
    private ServicoService service;

    @Mock
    private ServicoRepository repository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarServicoComSucesso() {
        DadosCadastroServico dados = mock(DadosCadastroServico.class);
        when(dados.nome()).thenReturn("Corte");
        when(dados.idCategoria()).thenReturn(1L);
        when(dados.duracao()).thenReturn("01:30"); // Corrige NPE ao criar Servico
        when(repository.existsByNome(anyString())).thenReturn(false);
        Categoria categoria = new Categoria();
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Servico servico = service.cadastrar(dados);

        assertNotNull(servico);
        verify(repository).save(any());
    }

    @Test
    void deveAtualizarServicoComSucesso() {
        Servico servico = mock(Servico.class);
        DadosAtualizacaoServico dados = mock(DadosAtualizacaoServico.class);
        when(repository.findById(1L)).thenReturn(Optional.of(servico));
        when(repository.save(any())).thenReturn(servico);

        Servico atualizado = service.atualizar(1L, dados);

        assertNotNull(atualizado);
        verify(servico).atualizarInformacoes(dados);
        verify(repository).save(servico);
    }

    @Test
    void deveLancarExcecaoAoAtualizarServicoNaoEncontrado() {
        DadosAtualizacaoServico dados = mock(DadosAtualizacaoServico.class);
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.atualizar(1L, dados));
    }

    @Test
    void deveListarServicosComSucesso() {
        Pageable pageable = PageRequest.of(0, 10);
        Servico servico = mock(Servico.class);
        when(servico.getDuracao()).thenReturn(java.time.Duration.ofMinutes(30));
        when(servico.getNome()).thenReturn("Corte");
        when(servico.getPreco()).thenReturn(50.0);
        when(servico.getCategoria()).thenReturn(new Categoria());
        Page<Servico> page = new PageImpl<>(List.of(servico));
        when(repository.findByCategoriaId(1L, pageable)).thenReturn(page);

        Page<DadosListagemServico> resultado = service.listar(1L, pageable);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }

    @Test
    void deveDeletarServicoComSucesso() {
        Servico servico = new Servico();
        when(repository.findById(1L)).thenReturn(Optional.of(servico));

        assertDoesNotThrow(() -> service.deletar(1L));
        verify(repository).delete(servico);
    }

    @Test
    void deveLancarExcecaoAoDeletarServicoNaoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.deletar(1L));
    }
}

