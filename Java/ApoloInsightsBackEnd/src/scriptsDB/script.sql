DROP DATABASE IF EXISTS apoloInsights;
CREATE DATABASE apoloInsights;
USE apoloInsights;

CREATE TABLE usuarios (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          cpf VARCHAR(14) UNIQUE NOT NULL,
                          data_nascimento DATE NOT NULL,
                          telefone VARCHAR(20),
                          genero VARCHAR(20),
                          email VARCHAR(255) UNIQUE NOT NULL,
                          senha VARCHAR(255) NOT NULL
);

CREATE TABLE categorias (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            nome VARCHAR(255) NOT NULL,
                            foto VARCHAR(255)
);

CREATE TABLE servicos (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          categoria_id BIGINT,
                          nome VARCHAR(255) NOT NULL,
                          descricao VARCHAR(255),
                          preco DECIMAL(10,2) NOT NULL,
                          foto VARCHAR(255),
                          CONSTRAINT fk_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

CREATE TABLE produtos (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          descricao VARCHAR(255),
                          preco DECIMAL(10,2) NOT NULL,
                          foto VARCHAR(255)
);

CREATE TABLE horarios_disponiveis (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      data DATE,
                                      hora TIME,
                                      reservado BOOLEAN DEFAULT false
);


CREATE TABLE agendamentos (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              usuario_id BIGINT,
                              servico_id BIGINT,
                              data DATE,
                              hora TIME,
                              forma_pagamento VARCHAR(20),
                              UNIQUE(data, hora),
                              CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
                              CONSTRAINT fk_servico FOREIGN KEY (servico_id) REFERENCES servicos(id)
);


INSERT INTO categorias (nome, foto)
VALUES ('Cabelereiro', 'foto_categoria.jpg');

INSERT INTO servicos (categoria_id, nome, descricao, preco, foto)
VALUES (1, 'Corte de cabelo', 'Corte completo', 50.00, 'foto.jpg');

INSERT INTO usuarios (nome, cpf, data_nascimento, telefone, genero, email, senha)
VALUES (
           'João Silva',
           '123.456.789-00',
           '1990-01-01',
           '11999999999',
           'MASCULINO',
           'joao@email.com',
           '123456'
       );

SELECT * FROM usuarios;
SELECT * FROM servicos;
SELECT * FROM produtos;
SELECT * FROM agendamentos;
select * from categorias;