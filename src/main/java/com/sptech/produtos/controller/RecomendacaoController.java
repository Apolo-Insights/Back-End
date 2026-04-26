package com.sptech.produtos.controller;

import com.sptech.produtos.dto.RecomendacaoRequest;
import com.sptech.produtos.dto.RecomendacaoResponse;
import com.sptech.produtos.service.RecomendacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recomendacoes")
public class RecomendacaoController {

    private final RecomendacaoService recomendacaoService;

    public RecomendacaoController(RecomendacaoService recomendacaoService) {
        this.recomendacaoService = recomendacaoService;
    }

    @PostMapping
    public ResponseEntity<RecomendacaoResponse> recomendar(@RequestBody RecomendacaoRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(recomendacaoService.recomendar(request));
    }
}