package apolov2.insightsApolo.v2.core.application.command;

import apolov2.insightsApolo.v2.core.domain.util.Role;

public record CadastrarFuncionarioCommand(
        String nome,
        String cpf,
        String telefone,
        String email,
        Role funcao
) {
}
