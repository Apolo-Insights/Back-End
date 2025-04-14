package school.sptech.ApoloInsightsBackEnd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String nome;
    private String descricao;
    private Double valor;



    public Produto() {

    }

    public Produto(Integer id,  String nome, String descricao, Double valor) {
        this.id = id;

        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
    }
}
