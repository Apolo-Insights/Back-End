package apolov2.insightsApolo.v2.core.application.command;

public class AlterarSenhaCommand {
    private String email;
    private String novaSenha;

    public AlterarSenhaCommand() {
    }

    public AlterarSenhaCommand(String email, String novaSenha) {
        this.email = email;
        this.novaSenha = novaSenha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
