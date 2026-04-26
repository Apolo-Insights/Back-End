package com.sptech.produtos.service;

import com.sptech.produtos.dto.ProdutoRecomendadoResponse;
import com.sptech.produtos.dto.RecomendacaoRequest;
import com.sptech.produtos.dto.RecomendacaoResponse;
import com.sptech.produtos.model.Produto;
import com.sptech.produtos.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RecomendacaoService {

    private final ProdutoRepository produtoRepository;

    public RecomendacaoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public RecomendacaoResponse recomendar(RecomendacaoRequest request) {
        List<ProdutoRecomendadoResponse> produtos = produtoRepository.findAll().stream()
                .filter(produto -> atendeTipoCabeloEliminatorio(produto, request.getTipoCabelo()))
                .map(produto -> mapearComScore(produto, request))
                .filter(produto -> produto.getScore() > 0)
            .sorted(Comparator.comparingInt(ProdutoRecomendadoResponse::getScore).reversed()
                .thenComparing(ProdutoRecomendadoResponse::getNome, Comparator.nullsLast(String::compareToIgnoreCase)))
                .collect(Collectors.toList());

        RecomendacaoResponse response = new RecomendacaoResponse();
        response.setTipoCabelo(request.getTipoCabelo());
        response.setProblema(request.getProblema());
        response.setTratamento(request.getTratamento());
        response.setProdutos(produtos);
        return response;
    }

    private ProdutoRecomendadoResponse mapearComScore(Produto produto, RecomendacaoRequest request) {
        ProdutoRecomendadoResponse response = new ProdutoRecomendadoResponse();
        response.setId(produto.getId());
        response.setProduto(produto.getProduto());
        response.setNome(obterNomeExibicao(produto));
        response.setMarca(produto.getMarca());
        response.setCategoria(produto.getCategoria());
        response.setTipoCabelo(produto.getTipoCabelo());
        response.setProblema(produto.getProblema());
        response.setTratamento(produto.getTratamento());
        response.setScore(calcularScore(produto, request));
        return response;
    }

    private int calcularScore(Produto produto, RecomendacaoRequest request) {
        int score = 0;

        score += pontuarIgualdade(produto.getProblema(), request.getProblema(), 60);
        score += pontuarIgualdade(produto.getTratamento(), request.getTratamento(), 40);

        return score;
    }

    private boolean atendeTipoCabeloEliminatorio(Produto produto, String tipoSolicitado) {
        String solicitado = normalize(tipoSolicitado);
        if (solicitado.isBlank()) {
            return true;
        }

        String tipoProduto = produto.getTipoCabelo();
        return matches(tipoProduto, tipoSolicitado)
                || contains(tipoProduto, tipoSolicitado)
                || contains(tipoSolicitado, tipoProduto);
    }

    private int pontuarIgualdade(String valorProduto, String valorSolicitado, int pontos) {
        if (matches(valorProduto, valorSolicitado)) {
            return pontos;
        }
        if (contains(valorProduto, valorSolicitado) || contains(valorSolicitado, valorProduto)) {
            return Math.max(1, pontos / 2);
        }
        return 0;
    }

    private boolean matches(String valorA, String valorB) {
        return normalize(valorA).equals(normalize(valorB));
    }

    private boolean contains(String valorA, String valorB) {
        String a = normalize(valorA);
        String b = normalize(valorB);
        return !a.isBlank() && !b.isBlank() && (a.contains(b) || b.contains(a));
    }

    private String normalize(String value) {
        if (Objects.isNull(value)) {
            return "";
        }

        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .trim();
        return normalized.replaceAll("\\s+", " ");
    }

    private String obterNomeExibicao(Produto produto) {
        if (produto.getNome() != null && !produto.getNome().isBlank()) {
            return produto.getNome();
        }
        return produto.getProduto();
    }
}