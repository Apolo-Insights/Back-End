package school.sptech.ApoloInsightsBackEnd.old.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.ApoloInsightsBackEnd.old.domain.Usuario;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailOrCpf(String email, String cpf);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    Optional<Usuario> findByEmail(String email);
}
