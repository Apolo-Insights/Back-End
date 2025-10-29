package apolov2.insightsApolo.v2.core.application.usecase;

import apolov2.insightsApolo.v2.core.application.exception.RequestError;
import apolov2.insightsApolo.v2.core.domain.entity.Categoria;
import apolov2.insightsApolo.v2.core.domain.entity.Servico;
import apolov2.insightsApolo.v2.core.port.in.CategoriaGateway;
import apolov2.insightsApolo.v2.core.port.in.ServicoGateway;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosAtualizacaoServico;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroServico;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosListagemServico;
import apolov2.insightsApolo.v2.infrastructure.util.DataHoraUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ServicoUseCase {
    private final ServicoGateway gateway;
    private final CategoriaGateway categoriaGateway;

    public ServicoUseCase(ServicoGateway gateway, CategoriaGateway categoriaGateway) {
        this.gateway = gateway;
        this.categoriaGateway = categoriaGateway;
    }

    public Servico cadastrar(DadosCadastroServico dados) {
        Categoria categoria = categoriaGateway.buscarPorId(dados.idCategoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        Servico novoServico = new Servico(
                dados.nome(),
                dados.preco(),
                dados.descricao(),
                dados.fotoBase64(), // Aqui já vem a URL do Azure processada pelo controller
                DataHoraUtil.parseISO8601(dados.duracao()),
                categoria
        );

        return gateway.cadastrar(novoServico);
    }

    public Servico atualizar(Long id, DadosAtualizacaoServico dados) {
        Servico servico = gateway.buscarPorId(id);
        
        if (servico == null) {
            throw new EntityNotFoundException("Serviço não encontrado");
        }

        if (dados.nome() != null) servico.setNome(dados.nome());
        if (dados.descricao() != null) servico.setDescricao(dados.descricao());
        if (dados.preco() != null) servico.setPreco(dados.preco());
        if (dados.fotoBase64() != null) servico.setFoto(dados.fotoBase64());
        if (dados.duracao() != null) {
            servico.setDuracao(DataHoraUtil.parseISO8601(dados.duracao()));
        }

        return gateway.atualizar(servico);
    }

    public Page<DadosListagemServico> listar(Long idCategoria, Pageable paginacao) {
        Page<Servico> servicosPage = gateway.listar(idCategoria, paginacao);
        
        if (servicosPage.isEmpty()) {
            throw new RequestError(
                    HttpStatus.NOT_FOUND, 
                    "sem campo", 
                    "Nenhum Serviço encontrado na Categoria"
            );
        }
        
        return servicosPage.map(DadosListagemServico::new);
    }

    public void deletar(Long id) {
        Servico servico = gateway.buscarPorId(id);
        
        if (servico == null) {
            throw new EntityNotFoundException("Serviço não encontrado");
        }
        
        gateway.deletar(id);
    }
}
