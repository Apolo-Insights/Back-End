package school.sptech.ApoloInsightsBackEnd.service;

import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.usuario.*;
import school.sptech.ApoloInsightsBackEnd.domain.Usuario;
import school.sptech.ApoloInsightsBackEnd.repository.UsuarioRepository;
import school.sptech.ApoloInsightsBackEnd.exception.RequestError;
import school.sptech.ApoloInsightsBackEnd.security.SenhaUtil;
import school.sptech.ApoloInsightsBackEnd.util.TokenGenerator;
import java.util.List;

@Service
public class UsuarioService {

    @Value("${spring.mail.username}")
    private String emailBerthollo;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    UsuarioRepository repository;

    @Transactional
    public Usuario cadastrar(DadosCadastroUsuario dados){
        if (repository.existsByEmail(dados.email())) {
            throw new RequestError(
                    HttpStatus.CONFLICT, "email", "Esse E-mail já está cadastrado");
        }
        if (repository.existsByCpf(dados.cpf())) {
            throw new RequestError(
                    HttpStatus.CONFLICT, "cpf", "Esse CPF já está cadastrado");
        }

        Usuario usuario = new Usuario(dados);
        usuario.setSenha(SenhaUtil.hashSenha(usuario.getSenha()));
        return repository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(DadosAtualizacaoUsuario dados){
        Usuario usuario = repository.findById(dados.id())
                .orElseThrow(() -> new RequestError(HttpStatus.NOT_FOUND, "id", "Usuário não encontrado"));

        usuario.atualizarInformacoes(dados);
        return repository.save(usuario);
    }

    @Transactional
    public void alterarSenha(DadosAtualizarSenha dados) {
        Usuario usuario = repository.findByEmail(dados.email())
                .orElseThrow(() -> new RequestError(HttpStatus.NOT_FOUND, "email", "Email não encontrado"));
        usuario.setSenha(SenhaUtil.hashSenha(usuario.getSenha()));
    }

    public void deletar(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RequestError(HttpStatus.NOT_FOUND, "id", "Usuário não encontrado"));

        repository.delete(usuario);
    }

    public List<DadosDetalhamentoUsuario> listarUsuarios() {
        List<DadosDetalhamentoUsuario> usuarios = repository.findAll()
                .stream()
                .map(DadosDetalhamentoUsuario::new)
                .toList();

        if (usuarios.isEmpty()) {
            throw new RequestError(
                    HttpStatus.NOT_FOUND,
                    "Sem campo",
                    "Nenhum Usuario encontrado");
        }
        return usuarios;
    }

    @Transactional
    public Usuario cadastrarFuncionario(@Valid DadosCadastroFuncionario dados) {
        if (repository.existsByEmail(dados.email())) {
            throw new RequestError(
                    HttpStatus.CONFLICT, "email", "Esse E-mail já está cadastrado");
        }
        if (repository.existsByCpf(dados.cpf())) {
            throw new RequestError(
                    HttpStatus.CONFLICT, "cpf", "Esse CPF já está cadastrado");
        }
        Usuario usuario = new Usuario(dados);
        usuario.setSenha(SenhaUtil.hashSenha(usuario.getCpf()));
        return repository.save(usuario);
    }

    public String gerarToken(String email) {
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new RequestError(HttpStatus.NOT_FOUND, "email", "Usuário não encontrado"));
        String token = TokenGenerator.gerarTokenAlfanumerico();

        enviarEmail(token, usuario);
        return token;
    }


    private void enviarEmail(String token, Usuario usuario) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(usuario.getEmail());
            helper.setSubject("Token de Redefinição de Senha");
            helper.setText(gerarHTML(usuario.getNome(), usuario.getEmail(), token), true);
            helper.setFrom(emailBerthollo);

            emailSender.send(message);
        } catch (Exception e) {
            throw new RequestError(HttpStatus.INTERNAL_SERVER_ERROR, "sem campo", "Erro ao enviar o e-mail de redefinição de senha, verifique se o seu email está correto ou crie uma nova conta. ");
        }
    }



    private String gerarHTML(String nomeUsuario, String email, String token) {
        return String.format("""
        <!DOCTYPE html>
        <html lang="pt">
        <head>
            <meta charset="UTF-8">
            <title>Redefinição de Senha</title>
        </head>
        <body style="margin:0; padding:0;">
            <table width="100%%" cellpadding="0" cellspacing="0" border="0" background="https://img.freepik.com/vetores-gratis/padrao-de-salao-de-beleza-desenhado-a-mao_23-2150110656.jpg?semt=ais_items_boosted&w=740" style="background-size: cover; background-repeat: no-repeat;">
                <tr>
                    <td align="center" style="padding: 40px 0; background-color: rgba(255,255,255,0.85);">
                        <table width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border: 1px solid #ddd; font-family: Arial, sans-serif;">
                            <tr>
                                <td align="center" bgcolor="#e83e8c" style="color: #ffffff; padding: 20px; font-size: 22px; font-weight: bold;">
                                    Redefinição de Senha
                                </td>
                            </tr>
                            <tr>
                                <td style="padding: 30px; text-align: center; color: #333333;">
                                    <p style="font-size: 16px; margin-bottom: 20px;">Olá, <strong>%s</strong>,</p>
                                    <p style="font-size: 16px; margin-bottom: 20px;">Recebemos uma solicitação para redefinir a senha associada ao e-mail <strong>%s</strong>.</p>
                                    <p style="font-size: 16px; margin-bottom: 20px;">Use o código abaixo para redefinir sua senha:</p>
                                    <div style="font-size: 24px; font-weight: bold; margin: 20px 0; color: #e83e8c;">%s</div>
                                    <p style="font-size: 14px; color: #777;">Se você não solicitou essa alteração, por favor ignore este e-mail.</p>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" style="padding: 20px; background-color: #f4f4f4; color: #777777; font-size: 14px;">
                                    <p>Atenciosamente,<br><strong>Equipe de Atendimento Berthollo</strong></p>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </body>
        </html>
        """, nomeUsuario, email, token);
    }
}
