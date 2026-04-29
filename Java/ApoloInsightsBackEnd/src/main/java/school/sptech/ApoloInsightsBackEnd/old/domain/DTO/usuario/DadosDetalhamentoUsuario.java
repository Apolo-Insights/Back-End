package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.usuario;

import school.sptech.ApoloInsightsBackEnd.old.domain.Role;
import school.sptech.ApoloInsightsBackEnd.old.domain.Usuario;

public record DadosDetalhamentoUsuario(
        String nome,
        String cpf,
        String email,
        String telefone,
        Role role
) {
    public DadosDetalhamentoUsuario(Usuario dados) {
        this(
                dados.getNome(),
                dados.getCpf(),
                dados.getEmail(),
                dados.getTelefone(),
                Role.valueOf(dados.getRole().name())
        );
    }
}