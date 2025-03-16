create database apolo;
USE apolo;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL,
    telefone VARCHAR(20),
    genero VARCHAR(20),
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    status BOOLEAN NOT NULL
);

CREATE TABLE servicos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255),
    preco DECIMAL(10,2) NOT NULL,
    foto VARCHAR(255)
);

CREATE TABLE produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255),
    preco DECIMAL(10,2) NOT NULL,
    quantidade_estoque INT NOT NULL,
    foto VARCHAR(255)
);

CREATE TABLE historico_atendimentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    atendente_id INT NOT NULL,
    nome_cliente VARCHAR(255) NOT NULL,
    data_atendimento DATE NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (atendente_id) REFERENCES usuarios(id)
);