package school.sptech.ApoloInsightsBackEnd.DTO;

import school.sptech.ApoloInsightsBackEnd.domain.Usuario;

public record DadosDetalhamentoUsuario(
        String nome,
        String email,
        String telefone
) {
    public DadosDetalhamentoUsuario(Usuario dados) {
        this(
                dados.getNome(),
                dados.getEmail(),
                dados.getTelefone()
        );
    }
}