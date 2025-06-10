package school.sptech.ApoloInsightsBackEnd.domain.DTO.usuario;

import school.sptech.ApoloInsightsBackEnd.domain.Role;
import school.sptech.ApoloInsightsBackEnd.domain.Usuario;

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