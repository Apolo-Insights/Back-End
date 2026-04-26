package com.sptech.produtos.bootstrap;

import com.sptech.produtos.model.Produto;
import com.sptech.produtos.repository.ProdutoRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvProdutosLoader implements ApplicationRunner {

    private final ProdutoRepository produtoRepository;

    public CsvProdutosLoader(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (produtoRepository.count() > 0) {
            return;
        }

        List<Produto> produtos = carregarProdutosDoCsv();
        if (!produtos.isEmpty()) {
            produtoRepository.saveAll(produtos);
        }
    }

    private List<Produto> carregarProdutosDoCsv() throws Exception {
        ClassPathResource resource = new ClassPathResource("base_produtos.csv");
        List<Produto> produtos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean primeiraLinha = true;

            while ((line = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                if (line.isBlank()) {
                    continue;
                }

                String[] colunas = line.split(";", -1);
                if (colunas.length < 7) {
                    continue;
                }

                Produto produto = new Produto();
                produto.setNome(colunas[2].trim());
                produto.setMarca(colunas[1].trim());
                produto.setCategoria(colunas[3].trim());
                produto.setTipoCabelo(colunas[4].trim());
                produto.setProblema(colunas[5].trim());
                produto.setTratamento(colunas[6].trim());
                produtos.add(produto);
            }
        }

        return produtos;
    }
}