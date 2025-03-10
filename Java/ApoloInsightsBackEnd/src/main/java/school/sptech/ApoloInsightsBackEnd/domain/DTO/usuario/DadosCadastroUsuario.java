package school.sptech.ApoloInsightsBackEnd.domain.DTO.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import school.sptech.ApoloInsightsBackEnd.domain.Genero;

import java.time.LocalDate;

public record DadosCadastroUsuario(
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
        String email,

        @NotBlank(message = "")

        @NotBlank(message = "Senha inválida")
        String senha
){}
