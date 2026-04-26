package com.sptech.produtos.controller;

import com.sptech.produtos.dto.HairAnalysisResponse;
import com.sptech.produtos.service.OpenAiHairService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/analise-capilar")
public class OpenAiHairController {

    private final OpenAiHairService openAiHairService;

    public OpenAiHairController(OpenAiHairService openAiHairService) {
        this.openAiHairService = openAiHairService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HairAnalysisResponse> analysis(@RequestPart("photo") MultipartFile photo) throws Exception {
        return ResponseEntity.ok(openAiHairService.analisarImagem(photo));
    }
}