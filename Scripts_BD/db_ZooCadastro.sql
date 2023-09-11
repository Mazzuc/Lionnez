create database db_ZooCadastro;
use db_ZooCadastro;

-- Criação das tabelas

CREATE TABLE Compra (
    CompraID INT PRIMARY KEY AUTO_INCREMENT,
    DataCompra TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ValorTotal DECIMAL(10, 2),
    QtdTotal INT,
    FormaPag VARCHAR(50)
);

CREATE TABLE Ingresso (
    IngressoID INT PRIMARY KEY AUTO_INCREMENT,
    Tipo VARCHAR(10), -- Meia ou Inteira
    Valor DECIMAL(10, 2),
    CompraId int,
    FOREIGN KEY (CompraId) REFERENCES Compra(CompraId)
);

CREATE TABLE NotaFiscal (
    NotaFiscalID INT PRIMARY KEY AUTO_INCREMENT,
    DataEmissao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ValorTotal DECIMAL(10, 2),
    CompraID INT,
    FOREIGN KEY (CompraID) REFERENCES Compra(CompraID)
);

-- Procedure para realizar a compra e gerar a nota fiscal
DELIMITER //

CREATE PROCEDURE RealizarCompra(
    IN CompraID INT,
    IN Quantidade INT
)
BEGIN
    DECLARE ValorCompra DECIMAL(10, 2);
    
    -- Cálculo do valor total da compra
    SELECT SUM(Valor * Quantidade) INTO ValorCompra
    FROM Ingresso
    WHERE IngressoID = CompraID;

    -- Inserindo a compra na tabela Compra
    INSERT INTO Compra (ValorTotal, QtdTotal, FormaPag)
    VALUES (ValorCompra, Quantidade, 'Forma de Pagamento Aqui');

    -- Inserindo a nota fiscal na tabela NotaFiscal
    INSERT INTO NotaFiscal (ValorTotal, CompraID)
    VALUES (ValorCompra, LAST_INSERT_ID());

END //

DELIMITER ;

-- Exemplo Inserindo ingressos 
INSERT INTO Ingresso (Tipo, Valor) VALUES ('Meia', 20.00);
INSERT INTO Ingresso (Tipo, Valor) VALUES ('Inteira', 40.00);


-- Compra 3 ingressos do tipo Meia
CALL RealizarCompra(1, 3); 
-- Compra 3 ingressos do tipo Inteira
CALL RealizarCompra(2, 3); 


SELECT * FROM Compra;
SELECT * FROM NotaFiscal;

SELECT * FROM Compra WHERE CompraID = 1;

drop database db_ZooCadastro;
