DROP DATABASE if EXISTS dbZoo;

create database dbZoo;
use dbZoo;

/*ANIMAL*/
create table tbDieta(
IdDieta int auto_increment primary key,
NomeDieta varchar(100) not null,
HoraAlimento char(5) not null,
QtdAlimento int not null, 
DescricaoDieta varchar(2000)
);

create table tbTipoHabitat(
IdTipoHabitat int auto_increment primary key,
NomeTipoHabitat varchar(100) not null
);

create table tbHabitat(
IdHabitat int auto_increment primary key,
NomeHabitat varchar(100) not null,
IdTipoHabitat int not null,
foreign key (IdTipoHabitat) references tbTipoHabitat(IdTipoHabitat),
Capacidadde int not null,
QtdAnimal int not null
);

create table tbEspecie(
IdEspecie int auto_increment primary key,
NomeEspecie varchar(100) not null,
NomeCientifico varchar(100) not null
);

create table tbPorte(
IdPorte int auto_increment primary key,
NomePorte varchar(100) not null
);

create table tbAnimal(
IdAnimal int auto_increment primary key,
NomeAnimal varchar(100) not null,
IdEspecie int not null,
foreign key (IdEspecie) references tbEspecie(IdEspecie),
IdDieta int not null,
foreign key (IdDieta) references tbDieta(IdDieta),
IdHabitat int not null,
foreign key (IdHabitat) references tbHabitat(IdHabitat),
AnoNasc int not null,
IdPorte int not null,
foreign key (IdPorte) references tbPorte(IdPorte),
Peso double not null,
Sexo char(1),
DescricaoAnimal varchar(2000) 
);

create table tbProntuario(
IdProntuario int auto_increment primary key,
IdAnimal int not null,
foreign key (IdAnimal) references tbAnimal(IdAnimal),
ObsProntuario varchar(2000)
);

create table tbAlergiaProntuario(
IdAlergiaProntuario int auto_increment primary key,
NomeAlergia varchar(100) not null,
DescricaoHistorico varchar(2000) not null,
IdProntuario int not null,
foreign key (IdProntuario) references tbProntuario(IdProntuario)
);

create table tbHistoricoProntuario(
IdHistorico int auto_increment primary key,
IdProntuario int not null,
foreign key (IdProntuario) references tbProntuario(IdProntuario),
DescricaoHistorico varchar(2000) not null
);

/*USUÁRIO*/
create table tbUsuario(
IdUsuario int auto_increment primary key,
Nome varchar(150) not null,
Email varchar(200) not null,
Senha char(8) not null,
CPF char(11) not null,
RG char(9) not null,
DataNasc date not null,
UsuStatus boolean not null
);

create table tbFuncionario(
IdFuncionario int auto_increment primary key,
IdUsuario int,
Cargo varchar(50) not null,
DataAdm date not null,
foreign key (IdUsuario) references tbUsuario(IdUsuario)
);

create table tbVisitante(
IdVisitante int auto_increment primary key,
IdUsuario int, 
DataCadastro date not null,
foreign key (IdUsuario) references tbUsuario(IdUsuario)
);

/*INGRESSO*/
CREATE TABLE tbCompra (
    IdCompra INT PRIMARY KEY AUTO_INCREMENT,
    DataCompra TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ValorTotal DECIMAL(10, 2),
    QtdTotal INT,
    FormaPag VARCHAR(50)
);

CREATE TABLE tbIngresso (
    IdIngresso INT PRIMARY KEY AUTO_INCREMENT,
    Tipo VARCHAR(10), -- Meia ou Inteira
    Valor DECIMAL(10, 2),
    IdCompra int,
    FOREIGN KEY (IdCompra) REFERENCES tbCompra(IdCompra)
);

CREATE TABLE tbNotaFiscal (
    IdNotaFiscal INT PRIMARY KEY AUTO_INCREMENT,
    DataEmissao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ValorTotal DECIMAL(10, 2),
    IdCompra INT,
    FOREIGN KEY (IdCompra) REFERENCES tbCompra(IdCompra)
);

-- Procedure para realizar a compra e gerar a nota fiscal
DELIMITER //

CREATE PROCEDURE RealizarCompra(
    IN IdCompra INT,
    IN Quantidade INT
)
BEGIN
    DECLARE ValorCompra DECIMAL(10, 2);
    
    -- Cálculo do valor total da compra
    SELECT SUM(Valor * Quantidade) INTO ValorCompra
    FROM tbIngresso
    WHERE IdIngresso = IdCompra;

    -- Inserindo a compra na tabela Compra
    INSERT INTO tbCompra (ValorTotal, QtdTotal, FormaPag)
    VALUES (ValorCompra, Quantidade, 'Forma de Pagamento Aqui');

    -- Inserindo a nota fiscal na tabela NotaFiscal
    INSERT INTO tbNotaFiscal (ValorTotal, IdCompra)
    VALUES (ValorCompra, LAST_INSERT_ID());

END //

DELIMITER ;

-- Exemplo Inserindo ingressos 
INSERT INTO tbIngresso (Tipo, Valor) VALUES ('Meia', 20.00);
INSERT INTO tbIngresso (Tipo, Valor) VALUES ('Inteira', 40.00);


-- Compra 3 ingressos do tipo Meia
CALL RealizarCompra(1, 3); 
-- Compra 3 ingressos do tipo Inteira
CALL RealizarCompra(2, 3); 


SELECT * FROM tbCompra;
SELECT * FROM tbNotaFiscal;

SELECT * FROM Compra WHERE CompraID = 1;

