package school.sptech.ApoloInsightsBackEnd.domain.DTO.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import school.sptech.ApoloInsightsBackEnd.domain.Role;

public record DadosCadastroFuncionario(
        @NotBlank(message = "Nome inválido")
        String nome,

        @NotBlank(message = "Telefone inválido")
        String telefone,

        @NotBlank(message = "CPF inválido")
        String cpf,

        @NotBlank(message = "Email inválido")
        String email,

        @NotNull
        Role funcao
) {
}
