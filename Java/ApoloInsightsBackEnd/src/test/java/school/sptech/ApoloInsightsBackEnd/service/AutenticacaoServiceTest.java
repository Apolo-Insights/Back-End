package school.sptech.ApoloInsightsBackEnd.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import school.sptech.ApoloInsightsBackEnd.domain.Usuario;
import school.sptech.ApoloInsightsBackEnd.repository.UsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutenticacaoServiceTest {

    @InjectMocks
    private AutenticacaoService service;

    @Mock
    private UsuarioRepository repository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = mock(Usuario.class);
    }

    @Test
    void deveRetornarUsuarioQuandoEncontradoPorEmailOuCpf() {
        when(repository.findByEmailOrCpf("user", "user")).thenReturn(Optional.of(usuario));

        UserDetails result = service.loadUserByUsername("user");

        assertNotNull(result);
        assertEquals(usuario, result);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        when(repository.findByEmailOrCpf("user", "user")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("user"));
    }
}

