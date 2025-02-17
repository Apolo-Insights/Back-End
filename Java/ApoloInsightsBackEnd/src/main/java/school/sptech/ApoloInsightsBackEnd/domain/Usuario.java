package school.sptech.ApoloInsightsBackEnd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import school.sptech.ApoloInsightsBackEnd.DTO.DadosAtualizacaoUsuario;
import school.sptech.ApoloInsightsBackEnd.DTO.DadosCadastroUsuario;
import java.util.Optional;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "usuarios")
@Entity(name = "Usuario")
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String telefone;
    private String email;
    private String senha;


    public Usuario(DadosCadastroUsuario dados) {
        this.nome = dados.nome();
        this.telefone = dados.telefone();
        this.email = dados.email();
        this.senha = dados.senha();
    }

    public void atualizarInformacoes(DadosAtualizacaoUsuario dados){
        Optional.ofNullable(dados.nome()).ifPresent(nome -> this.nome = nome);
        Optional.ofNullable(dados.telefone()).ifPresent(telefone -> this.telefone = telefone);
        Optional.ofNullable(dados.email()).ifPresent(email -> this.email = email);
    }
}
