package apolov2.insightsApolo.v2.core.application.usecase;

import apolov2.insightsApolo.v2.core.domain.entity.Categoria;
import apolov2.insightsApolo.v2.core.port.in.CategoriaGateway;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.categoria.DadosAtualizacaoCategoria;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.categoria.DadosCadastroCategoria;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.categoria.DadosListagemCategoria;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.azure.AzureBlobService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class CategoriaUseCase {

    private final CategoriaGateway categoriaGateway;
    private final AzureBlobService azureBlobService;

    @Transactional
    public Categoria cadastrarCategoria(DadosCadastroCategoria dados) {
        if (categoriaGateway.existePorNome(dados.nome())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Categoria com esse nome já cadastrada");
        }

        String urlFoto = null;

        if (dados.fotoBase64() != null && !dados.fotoBase64().isBlank()) {
            byte[] imagemBytes = Base64.getDecoder().decode(dados.fotoBase64());
            urlFoto = azureBlobService.upload(imagemBytes, "categorias");
        }

        Categoria categoria = new Categoria();
        categoria.setNome(dados.nome());
        categoria.setFoto(urlFoto);

        return categoriaGateway.salvar(categoria);
    }

    public Page<DadosListagemCategoria> listarCategorias(Pageable paginacao) {
        Page<Categoria> categorias = categoriaGateway.listarTodas(paginacao);
        
        if (categorias.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma categoria encontrada");
        }
        
        return categorias.map(DadosListagemCategoria::new);
    }

    @Transactional
    public Categoria atualizarCategoria(Long id, DadosAtualizacaoCategoria dados) {
        Categoria categoria = categoriaGateway.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        if (dados.nome() != null && categoriaGateway.existePorNome(dados.nome())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Categoria com esse nome já cadastrada");
        }

        String urlFoto = null;

        if (dados.fotoBase64() != null && !dados.fotoBase64().isBlank()) {
            byte[] imagemBytes = Base64.getDecoder().decode(dados.fotoBase64());
            urlFoto = azureBlobService.upload(imagemBytes, "categorias");
        }

        // Atualizar campos
        if (dados.nome() != null) {
            categoria.setNome(dados.nome());
        }
        if (urlFoto != null) {
            categoria.setFoto(urlFoto);
        }

        return categoriaGateway.atualizar(categoria);
    }

    @Transactional
    public void deletarCategoria(Long id) {
        Categoria categoria = categoriaGateway.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        
        categoriaGateway.deletar(categoria);
    }
}
