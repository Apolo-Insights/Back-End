CREATE DATABASE IF NOT EXISTS ETL;
USE ETL;

CREATE TABLE IF NOT EXISTS produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    produto VARCHAR(255) NOT NULL,
    nome VARCHAR(255),
    marca VARCHAR(255),
    categoria VARCHAR(100),
    tipo_cabelo VARCHAR(100),
    problema VARCHAR(100),
    tratamento VARCHAR(100)
);