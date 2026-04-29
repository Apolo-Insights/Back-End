// src/test/java/school/sptech/ApoloInsightsBackEnd/service/CategoriaServiceTest.java
package school.sptech.ApoloInsightsBackEnd.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import school.sptech.ApoloInsightsBackEnd.old.domain.Categoria;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.categoria.DadosAtualizacaoCategoria;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.categoria.DadosCadastroCategoria;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.categoria.DadosListagemCategoria;
import school.sptech.ApoloInsightsBackEnd.old.exception.RequestError;
import school.sptech.ApoloInsightsBackEnd.old.repository.CategoriaRepository;
import school.sptech.ApoloInsightsBackEnd.old.service.CategoriaService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaServiceTest {

    @InjectMocks
    private CategoriaService service;

    @Mock
    private CategoriaRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarCategoriaComSucesso() {
        DadosCadastroCategoria dados = mock(DadosCadastroCategoria.class);
        when(dados.nome()).thenReturn("Beleza");
        when(repository.existsByNome(anyString())).thenReturn(false);
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Categoria categoria = service.cadastrarCategoria(dados);

        assertNotNull(categoria);
        verify(repository).save(any());
    }

    @Test
    void deveLancarExcecaoAoCadastrarCategoriaComNomeExistente() {
        DadosCadastroCategoria dados = mock(DadosCadastroCategoria.class);
        when(dados.nome()).thenReturn("Beleza");
        when(repository.existsByNome("Beleza")).thenReturn(true);

        RequestError ex = assertThrows(RequestError.class, () -> service.cadastrarCategoria(dados));
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
    }

    @Test
    void deveListarCategoriasComSucesso() {
        Pageable pageable = PageRequest.of(0, 10);
        Categoria categoria = new Categoria();
        when(repository.findAll()).thenReturn(List.of(categoria));
        Page<Categoria> page = new PageImpl<>(List.of(categoria));
        when(repository.findAll(pageable)).thenReturn(page);

        Page<DadosListagemCategoria> resultado = service.listarCategorias(pageable);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }

    @Test
    void deveLancarExcecaoAoListarCategoriasVazia() {
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAll()).thenReturn(Collections.emptyList());

        RequestError ex = assertThrows(RequestError.class, () -> service.listarCategorias(pageable));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void deveAtualizarCategoriaComSucesso() {
        DadosAtualizacaoCategoria dados = mock(DadosAtualizacaoCategoria.class);
        when(dados.nome()).thenReturn("NovaCategoria");
        when(repository.existsByNome(anyString())).thenReturn(false);
        Categoria categoria = mock(Categoria.class);
        when(repository.findById(1L)).thenReturn(Optional.of(categoria));
        when(repository.save(any())).thenReturn(categoria);

        Categoria atualizado = service.atualizarCategoria(1L, dados);

        assertNotNull(atualizado);
        verify(categoria).atualizarInformacoes(dados);
        verify(repository).save(categoria);
    }

    @Test
    void deveLancarExcecaoAoAtualizarCategoriaComNomeExistente() {
        DadosAtualizacaoCategoria dados = mock(DadosAtualizacaoCategoria.class);
        when(dados.nome()).thenReturn("Beleza");
        when(repository.existsByNome("Beleza")).thenReturn(true);

        RequestError ex = assertThrows(RequestError.class, () -> service.atualizarCategoria(1L, dados));
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
    }

    @Test
    void deveLancarExcecaoAoAtualizarCategoriaInexistente() {
        DadosAtualizacaoCategoria dados = mock(DadosAtualizacaoCategoria.class);
        when(dados.nome()).thenReturn("NovaCategoria");
        when(repository.existsByNome(anyString())).thenReturn(false);
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RequestError ex = assertThrows(RequestError.class, () -> service.atualizarCategoria(1L, dados));
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
    }

    @Test
    void deveDeletarCategoriaComSucesso() {
        Categoria categoria = new Categoria();
        when(repository.findById(1L)).thenReturn(Optional.of(categoria));

        assertDoesNotThrow(() -> service.deletarCategoria(1L));
        verify(repository).delete(categoria);
    }

    @Test
    void deveLancarExcecaoAoDeletarCategoriaInexistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RequestError ex = assertThrows(RequestError.class, () -> service.deletarCategoria(1L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }
}