package school.sptech.ApoloInsightsBackEnd.controller;

import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.ApoloInsightsBackEnd.service.ProdutoService;

@RestController
@RequestMapping("/usuarios")
public class ProdutoController {

@Autowired
private ProdutoService protudosServices;

@PostMapping
public ResponseEntity
}
