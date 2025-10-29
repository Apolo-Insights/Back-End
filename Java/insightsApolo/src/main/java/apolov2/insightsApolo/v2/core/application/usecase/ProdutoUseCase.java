package apolov2.insightsApolo.v2.core.application.usecase;

import apolov2.insightsApolo.v2.core.domain.entity.Produto;
import apolov2.insightsApolo.v2.core.port.in.ProdutoGateway;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosAtualizacaoProduto;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroProduto;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosListagemProduto;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.ProdutoDetalhesDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdutoUseCase {
    private final ProdutoGateway gateway;

    public ProdutoUseCase(ProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public Produto cadastrar(DadosCadastroProduto dados) {
        Produto novoProduto = new Produto(
                dados.nome(),
                dados.descricao(),
                dados.preco(),
                dados.fotoBase64() // Aqui já vem a URL do Azure processada pelo controller
        );

        return gateway.cadastrar(novoProduto);
    }

    public Produto atualizar(Long id, DadosAtualizacaoProduto dados) {
        Produto produto = gateway.buscarPorId(id);

        if (produto == null) {
            throw new EntityNotFoundException("Produto não encontrado");
        }

        if (dados.nome() != null) produto.setNome(dados.nome());
        if (dados.descricao() != null) produto.setDescricao(dados.descricao());
        if (dados.preco() != null) produto.setPreco(dados.preco());
        if (dados.fotoBase64() != null) produto.setFoto(dados.fotoBase64());

        return gateway.atualizar(produto);
    }

    public Page<DadosListagemProduto> listar(Pageable paginacao) {
        Page<Produto> produtosPage = gateway.listar(paginacao);
        return produtosPage.map(DadosListagemProduto::new);
    }

    public ProdutoDetalhesDTO buscarPorId(Long id) {
        Produto produto = gateway.buscarPorId(id);

        if (produto == null) {
            throw new EntityNotFoundException("Produto não encontrado");
        }

        return new ProdutoDetalhesDTO(produto);
    }

    public void deletar(Long id) {
        Produto produto = gateway.buscarPorId(id);

        if (produto == null) {
            throw new EntityNotFoundException("Produto não encontrado");
        }

        gateway.deletar(id);
    }

    public void adicionarEstoque(Long id) {
        Produto produto = gateway.buscarPorId(id);

        if (produto == null) {
            throw new EntityNotFoundException("Produto não encontrado");
        }

        produto.adicionarEstoque();
        gateway.atualizar(produto);
    }

    public void removerEstoque(Long id) {
        Produto produto = gateway.buscarPorId(id);

        if (produto == null) {
            throw new EntityNotFoundException("Produto não encontrado");
        }

        produto.removerEstoque();
        gateway.atualizar(produto);
    }
}
