package com.sptech.produtos.dto;

import java.util.List;

public class RecomendacaoResponse {

    private String tipoCabelo;
    private String problema;
    private String tratamento;
    private List<ProdutoRecomendadoResponse> produtos;

    public String getTipoCabelo() {
        return tipoCabelo;
    }

    public void setTipoCabelo(String tipoCabelo) {
        this.tipoCabelo = tipoCabelo;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public String getTratamento() {
        return tratamento;
    }

    public void setTratamento(String tratamento) {
        this.tratamento = tratamento;
    }

    public List<ProdutoRecomendadoResponse> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoRecomendadoResponse> produtos) {
        this.produtos = produtos;
    }
}