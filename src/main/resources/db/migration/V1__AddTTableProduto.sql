CREATE TABLE produto (
     id UUID NOT NULL PRIMARY KEY,
     descricao VARCHAR(40) NOT NULL,
     ativo BOOLEAN NOT NULL,
     preco FLOAT NOT NULL,
     estoque FLOAT NOT NULL,
     registrationDate TIMESTAMP);
