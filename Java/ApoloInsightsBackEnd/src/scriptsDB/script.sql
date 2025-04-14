drop database apoloinsights;
create database apoloInsights;
use apoloInsights;

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


CREATE TABLE servicos (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          descricao VARCHAR(255),
                          preco DECIMAL(10,2) NOT NULL,
                          foto VARCHAR(255)
);

CREATE TABLE produtos (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          descricao VARCHAR(255),
                          preco DECIMAL(10,2) NOT NULL,
                          foto VARCHAR(255)
);

select * from produtos;

CREATE TABLE horarios_disponiveis (
                                      id SERIAL PRIMARY KEY,
                                      data DATE,
                                      hora TIME,
                                      reservado BOOLEAN DEFAULT false
);

CREATE TABLE agendamentos (
                              id SERIAL PRIMARY KEY,
                              usuario_id BIGINT REFERENCES usuarios(id),
                              servico_id BIGINT REFERENCES servicos(id),
                              data DATE,
                              hora TIME,
                              UNIQUE(data, hora), -- Evita dois agendamentos no mesmo horário
                              CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
                              CONSTRAINT fk_servico FOREIGN KEY (servico_id) REFERENCES servicos(id)
);

select * from usuarios;
select * from servicos;


INSERT INTO servicos (nome, descricao, preco, foto)
VALUES ('Corte de cabelo', 'Corte completo', 50.00, 'foto.jpg');

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

select* from agendamentos;

