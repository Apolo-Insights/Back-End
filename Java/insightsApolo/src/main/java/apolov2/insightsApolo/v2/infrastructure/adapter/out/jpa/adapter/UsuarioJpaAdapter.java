package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.adapter;

import apolov2.insightsApolo.v2.core.domain.entity.Usuario;
import apolov2.insightsApolo.v2.core.port.in.UsuarioGateway;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.UsuarioEntity;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper.UsuarioMapper;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioJpaAdapter implements UsuarioGateway {
    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    public UsuarioJpaAdapter(UsuarioRepository repository, UsuarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Usuario cadastrar(Usuario usuario) {
        UsuarioEntity entity = mapper.usuarioToEntity(usuario);
        UsuarioEntity savedEntity = repository.save(entity);
        return mapper.entityToUsuario(savedEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return repository.existsByCpf(cpf);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::entityToUsuario);
    }

    @Override
    public Usuario atualizar(Usuario usuario) {
        UsuarioEntity entity = mapper.usuarioToEntity(usuario);
        UsuarioEntity savedEntity = repository.save(entity);
        return mapper.entityToUsuario(savedEntity);
    }
}
