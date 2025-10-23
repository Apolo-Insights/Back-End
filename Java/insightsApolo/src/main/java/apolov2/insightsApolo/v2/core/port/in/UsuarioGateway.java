package apolov2.insightsApolo.v2.core.port.in;

import apolov2.insightsApolo.v2.core.domain.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioGateway {
    Usuario cadastrar(Usuario domain);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    Optional<Usuario> buscarPorEmail(String email);
    Optional<Usuario> buscarPorId(Long id);
    Usuario atualizar(Usuario usuario);
    List<Usuario> listarTodos();
    void deletar(Long id);
}
