package school.sptech.ApoloInsightsBackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.ApoloInsightsBackEnd.util.security.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

//    @PostMapping
//    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
//        var authenticationtoken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
//        var authentication = manager.authenticate(authenticationtoken);
//
//        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
//        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
//    }


}
