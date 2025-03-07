package school.sptech.ApoloInsightsBackEnd.DTO.usuario;

import school.sptech.ApoloInsightsBackEnd.domain.Usuario;

public record DadosDetalhamentoUsuario(
        String nome,
        String cpf,
        String email,
        String telefone
) {
    public DadosDetalhamentoUsuario(Usuario dados) {
        this(
                dados.getNome(),
                dados.getCpf(),
                dados.getEmail(),
                dados.getTelefone()
        );
    }
}