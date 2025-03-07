package school.sptech.ApoloInsightsBackEnd.DTO.usuario;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosLogin(
        @Pattern(
                regexp = "(^\\d{11}$|^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$)?",
                message = "CPF inválido"
        )
        String cpf,

        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        String senha
) {
    @AssertTrue(message = "É necessário informar um CPF ou um e-mail.")
    public boolean cpfOuEmailValidos() {
        return (cpf != null && !cpf.isBlank()) || (email != null && !email.isBlank());
    }
}