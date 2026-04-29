package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

import apolov2.insightsApolo.v2.core.domain.util.Role;

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
}
