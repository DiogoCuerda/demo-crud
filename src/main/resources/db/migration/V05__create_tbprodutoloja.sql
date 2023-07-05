CREATE TABLE tb_loja(
id UUID PRIMARY KEY,
nome VARCHAR(40),
credito NUMERIC(10,2),
data_registro TIMESTAMP
);

CREATE TABLE tb_produtoloja(
produto_id UUID,
loja_id UUID,
estoque INT,
PRIMARY KEY (produto_id, loja_id),
FOREIGN KEY (produto_id)
      REFERENCES tb_produto (id),
FOREIGN KEY (loja_id)
      REFERENCES tb_loja (id)
);