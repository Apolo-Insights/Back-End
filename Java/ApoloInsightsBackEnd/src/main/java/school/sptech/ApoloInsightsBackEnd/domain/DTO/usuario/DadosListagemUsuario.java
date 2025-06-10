package school.sptech.ApoloInsightsBackEnd.domain.DTO.usuario;

import school.sptech.ApoloInsightsBackEnd.domain.Role;
import school.sptech.ApoloInsightsBackEnd.domain.Usuario;

import java.time.LocalDate;

public record DadosListagemUsuario(
        Long id,

        String nome,

        String telefone,

        LocalDate dataNascimento,

        String cpf,

        String email,

        Role funcao
) {
    public DadosListagemUsuario(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getTelefone(),
                usuario.getDataNascimento(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getRole());
    }
}
