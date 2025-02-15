package school.sptech.ApoloInsightsBackEnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.ApoloInsightsBackEnd.DTO.DadosAtualizacaoUsuario;
import school.sptech.ApoloInsightsBackEnd.domain.Usuario;
import school.sptech.ApoloInsightsBackEnd.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository repository;

    @Transactional
    public Usuario cadastrar(Usuario usuario){
        return repository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(DadosAtualizacaoUsuario dados){
        Usuario usuario = repository.findById(dados.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        usuario.atualizarInformacoes(dados);
        return repository.save(usuario);
    };
}
