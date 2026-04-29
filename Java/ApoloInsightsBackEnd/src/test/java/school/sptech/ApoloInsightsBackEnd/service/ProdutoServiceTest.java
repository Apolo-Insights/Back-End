package school.sptech.ApoloInsightsBackEnd.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.produto.DadosAtualizacaoProduto;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.produto.DadosCadastroProduto;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.produto.DadosListagemProduto;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.produto.ProdutoDetalhesDTO;
import school.sptech.ApoloInsightsBackEnd.old.domain.Produto;
import school.sptech.ApoloInsightsBackEnd.old.repository.ProdutoRepository;
import school.sptech.ApoloInsightsBackEnd.old.service.ProdutoService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService service;

    @Mock
    private ProdutoRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarProdutoComSucesso() {
        DadosCadastroProduto dados = mock(DadosCadastroProduto.class);
        when(dados.nome()).thenReturn("Shampoo");
        when(repository.existsByNome(anyString())).thenReturn(false);
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Produto produto = service.cadastrar(dados);

        assertNotNull(produto);
        verify(repository).save(any());
    }

    @Test
    void deveAtualizarProdutoComSucesso() {
        Produto produto = mock(Produto.class);
        DadosAtualizacaoProduto dados = mock(DadosAtualizacaoProduto.class);
        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(repository.save(any())).thenReturn(produto);

        Produto atualizado = service.atualizar(1L, dados);

        assertNotNull(atualizado);
        verify(produto).atualizarInformacoes(dados);
        verify(repository).save(produto);
    }

    @Test
    void deveListarProdutosComSucesso() {
        Pageable pageable = PageRequest.of(0, 10);
        Produto produto = new Produto();
        when(repository.findAll()).thenReturn(List.of(produto));
        Page<Produto> page = new PageImpl<>(List.of(produto));
        when(repository.findAll(pageable)).thenReturn(page);

        Page<DadosListagemProduto> resultado = service.listar(pageable);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }

    @Test
    void deveBuscarProdutoPorIdComSucesso() {
        Produto produto = new Produto();
        when(repository.findById(1L)).thenReturn(Optional.of(produto));

        ProdutoDetalhesDTO dto = service.buscarPorId(1L);

        assertNotNull(dto);
    }

    @Test
    void deveLancarExcecaoAoBuscarProdutoPorIdNaoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.buscarPorId(1L));
    }

    @Test
    void deveDeletarProdutoComSucesso() {
        Produto produto = new Produto();
        when(repository.findById(1L)).thenReturn(Optional.of(produto));

        assertDoesNotThrow(() -> service.deletar(1L));
        verify(repository).delete(produto);
    }

    @Test
    void deveAdicionarEstoqueComSucesso() {
        Produto produto = mock(Produto.class);
        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(repository.save(any())).thenReturn(produto);

        assertDoesNotThrow(() -> service.adicionarEstoque(1L));
        verify(produto).adicionarEstoque();
        verify(repository).save(produto);
    }

    @Test
    void deveRemoverEstoqueComSucesso() {
        Produto produto = mock(Produto.class);
        when(produto.getEstoque()).thenReturn(1);
        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(repository.save(any())).thenReturn(produto);

        assertDoesNotThrow(() -> service.removerEstoque(1L));
        verify(produto).removerEstoque();
        verify(repository).save(produto);
    }

}