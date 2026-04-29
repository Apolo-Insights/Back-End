package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.repository;

import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    Optional<UsuarioEntity> findByEmailOrCpf(String email, String cpf);
    Optional<UsuarioEntity> findByEmail(String email);
}
