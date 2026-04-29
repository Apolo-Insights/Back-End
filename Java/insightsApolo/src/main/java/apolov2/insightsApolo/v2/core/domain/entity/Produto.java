package apolov2.insightsApolo.v2.core.domain.entity;

//Victor
public class Produto {
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private String foto;
    private Integer estoque;

    public Produto() {
    }

    public Produto(String nome, String descricao, Double preco, String foto) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.foto = foto;
        this.estoque = 0;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public void adicionarEstoque() {
        this.estoque++;
    }

    public void removerEstoque() {
        this.estoque--;
    }
}
