package com.sptech.produtos.dto;

public class RecomendacaoRequest {

    private String tipoCabelo;
    private String problema;
    private String tratamento;

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
}