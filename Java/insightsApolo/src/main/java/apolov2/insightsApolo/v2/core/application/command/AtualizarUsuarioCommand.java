package apolov2.insightsApolo.v2.core.application.command;

import apolov2.insightsApolo.v2.core.domain.util.Genero;
import apolov2.insightsApolo.v2.core.domain.util.Role;

public class AtualizarUsuarioCommand {
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private Genero genero;
    private String cpf;
    private Role funcao;

    public AtualizarUsuarioCommand() {
    }

    public AtualizarUsuarioCommand(Long id, String nome, String telefone, String email, Genero genero, String cpf, Role funcao) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.genero = genero;
        this.cpf = cpf;
        this.funcao = funcao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Role getFuncao() {
        return funcao;
    }

    public void setFuncao(Role funcao) {
        this.funcao = funcao;
    }
}
