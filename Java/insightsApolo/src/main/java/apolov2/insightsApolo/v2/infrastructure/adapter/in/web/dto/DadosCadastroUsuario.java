package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import apolov2.insightsApolo.v2.core.domain.util.Genero;

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

        @NotBlank(message = "Senha inválida")
        String senha
) {
}
