package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

import apolov2.insightsApolo.v2.core.domain.entity.Usuario;
import apolov2.insightsApolo.v2.core.domain.util.Role;

public record DadosDetalhamentoUsuario(
        String nome,
        String cpf,
        String email,
        String telefone,
        Role role
) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getRole()
        );
    }
}
