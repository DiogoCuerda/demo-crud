create table tb_embalagem(
id UUID PRIMARY KEY,
nome VARCHAR(40),
idproduto UUID,
FOREIGN KEY (idproduto)
      REFERENCES tb_produto (id)
)