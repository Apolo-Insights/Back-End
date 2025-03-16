package school.sptech.ApoloInsightsBackEnd.domain.DTO.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import school.sptech.ApoloInsightsBackEnd.domain.Genero;
import school.sptech.ApoloInsightsBackEnd.domain.Servico;
import school.sptech.ApoloInsightsBackEnd.domain.Usuario;

import java.time.LocalDate;

public record DadosListagemUsuario(
        @NotBlank(message = "Nome inválido")
        String nome,

        @NotBlank(message = "Telefone inválido")
        String telefone,

        @NotNull(message = "Data de Nascimento inválida")
        LocalDate dataNascimento,

        @NotBlank(message = "CPF inválido")
        String cpf,

        @NotNull(message = "Gênero inválido")
        Genero genero,

        @NotBlank(message = "Email inválido")
        String email
) {
    public DadosListagemUsuario(Usuario usuario){
        this(   usuario.getNome(),
                usuario.getTelefone(),
                usuario.getDataNascimento(),
                usuario.getCpf(),
                usuario.getGenero(),
                usuario.getEmail());
    }
}
