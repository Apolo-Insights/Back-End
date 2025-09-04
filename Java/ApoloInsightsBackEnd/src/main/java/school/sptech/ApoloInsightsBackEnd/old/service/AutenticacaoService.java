package school.sptech.ApoloInsightsBackEnd.old.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import school.sptech.ApoloInsightsBackEnd.old.repository.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(AutenticacaoService.class);

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        logger.info("Tentando autenticar usuário com login: {}", login);
        return repository.findByEmailOrCpf(login, login)
                .orElseThrow(() -> {
                    logger.error("Usuário não encontrado: {}", login);
                    return new UsernameNotFoundException("Usuário não encontrado: " + login);
                });
    }
}
