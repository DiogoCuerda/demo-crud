INSERT INTO tb_usuario(id,nome,senha,read_only) VALUES
('f1daca55-6bb0-413b-8bfe-2bfdc8030234','admin','$2a$10$Uw/e2P.ZP/XNIqsLAqorzu56q98AkeCKAfp7ECwVfnL2mwYfWVNrq',FALSE),
('34cfdea3-64c7-4e34-86d9-f43fc5773318','diogo','$2a$10$Uw/e2P.ZP/XNIqsLAqorzu56q98AkeCKAfp7ECwVfnL2mwYfWVNrq',TRUE);


INSERT INTO tb_produto(id,nome,preco,estoque,ativo,categoria,data_registro) VALUES
('20bfdbf2-4b64-452e-8376-c2a230ef6265','Coca-Cola',8.99,10,TRUE,'REVENDA',current_timestamp),
('f07db05a-639d-4d4b-b0c3-1114214340cb','Doritos',  5.89,18,TRUE,'REVENDA',current_timestamp),
('c50bb329-5be3-431c-b623-050b163df16a','Presunto',83.21,10,TRUE,'REVENDA',current_timestamp),
('6698b118-26fc-4541-8d8f-d00945b015c7','toDelete',8.21,10,TRUE,'REVENDA',current_timestamp);

INSERT INTO tb_embalagem(id,nome,produto_id) VALUES
('4ddbfdfe-ed3c-4307-8519-55bd2a29e8c7','2 Litros','20bfdbf2-4b64-452e-8376-c2a230ef6265'),
('52183bd9-6d71-416f-ae89-9a1c915109c5','1 Litro','20bfdbf2-4b64-452e-8376-c2a230ef6265'),
('bf1d1e97-bd0b-4e88-8775-e6bd89699a62','600 mL','20bfdbf2-4b64-452e-8376-c2a230ef6265');

INSERT INTO tb_embalagem(id,nome,produto_id) VALUES
('c93f9ae2-1eb1-427d-9cf2-08591385c946','90 gramas','f07db05a-639d-4d4b-b0c3-1114214340cb'),
('b5c46c86-2734-4f23-a780-5e8f9ed56300','200 gramas','f07db05a-639d-4d4b-b0c3-1114214340cb');

INSERT INTO tb_embalagem(id,nome,produto_id) VALUES
('186fd939-bb77-4832-a8db-74be87f79aa5','grama','c50bb329-5be3-431c-b623-050b163df16a');

INSERT INTO tb_loja(id,nome,credito) VALUES
('ca81274e-6518-4bd9-8035-060aaa9af1f0','Mercado',56998.56),
('9518f5b5-bb56-467e-b877-61cc51037886','Padaria',1242.69);

INSERT INTO tb_produto_loja(produto_id,loja_id) VALUES
('20bfdbf2-4b64-452e-8376-c2a230ef6265','ca81274e-6518-4bd9-8035-060aaa9af1f0'),
('f07db05a-639d-4d4b-b0c3-1114214340cb','ca81274e-6518-4bd9-8035-060aaa9af1f0'),
('c50bb329-5be3-431c-b623-050b163df16a','ca81274e-6518-4bd9-8035-060aaa9af1f0');

INSERT INTO tb_produto_loja(produto_id,loja_id) VALUES
('20bfdbf2-4b64-452e-8376-c2a230ef6265','9518f5b5-bb56-467e-b877-61cc51037886'),
('c50bb329-5be3-431c-b623-050b163df16a','9518f5b5-bb56-467e-b877-61cc51037886');


