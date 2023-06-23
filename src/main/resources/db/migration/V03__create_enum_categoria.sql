CREATE TYPE categoriaproduto AS ENUM ('REVENDA', 'MATERIAPRIMA', 'SERVICO');

ALTER TABLE tb_produto
ADD COLUMN categoria categoriaproduto;