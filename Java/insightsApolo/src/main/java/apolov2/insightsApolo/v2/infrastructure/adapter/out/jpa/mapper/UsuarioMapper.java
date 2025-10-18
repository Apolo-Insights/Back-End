package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper;

import apolov2.insightsApolo.v2.core.application.command.AlterarSenhaCommand;
import apolov2.insightsApolo.v2.core.application.command.CadastrarFuncionarioCommand;
import apolov2.insightsApolo.v2.core.application.command.CadastrarUsuarioCommand;
import apolov2.insightsApolo.v2.core.domain.entity.Usuario;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosAtualizarSenha;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroFuncionario;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroUsuario;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosDetalhamentoUsuario;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    CadastrarUsuarioCommand cadastroToCommand(DadosCadastroUsuario dados);
    CadastrarFuncionarioCommand cadastroFuncionarioToCommand(DadosCadastroFuncionario dados);
    AlterarSenhaCommand atualizarSenhaToCommand(DadosAtualizarSenha dados);
    DadosDetalhamentoUsuario usuarioToDetalhamento(Usuario usuario);
    UsuarioEntity usuarioToEntity(Usuario usuario);
    Usuario entityToUsuario(UsuarioEntity entity);
}
