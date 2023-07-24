create table tb_embalagem(
id UUID PRIMARY KEY,
nome VARCHAR(40),
produto_id UUID,
FOREIGN KEY (produto_id)
      REFERENCES tb_produto (id)
);