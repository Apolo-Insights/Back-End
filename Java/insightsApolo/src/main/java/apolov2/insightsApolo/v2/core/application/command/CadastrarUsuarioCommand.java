package apolov2.insightsApolo.v2.core.application.command;

import apolov2.insightsApolo.v2.core.domain.util.Genero;

import java.time.LocalDate;

public record CadastrarUsuarioCommand(
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String telefone,
        Genero genero,
        String email,
        String senha
) {
}
