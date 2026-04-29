package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.usuario;

import school.sptech.ApoloInsightsBackEnd.old.domain.Role;
import school.sptech.ApoloInsightsBackEnd.old.domain.Usuario;

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
