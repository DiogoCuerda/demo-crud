CREATE TABLE tb_produto (
id UUID PRIMARY KEY,
nome VARCHAR(40),
preco NUMERIC (10,2),
estoque INT,
ativo BOOLEAN,
data_registro TIMESTAMP
);

CREATE TABLE tb_usuario (
id UUID PRIMARY KEY,
nome VARCHAR(40),
senha VARCHAR(80)
);