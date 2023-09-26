DROP DATABASE if EXISTS dbZoo;

create database dbZoo;
use dbZoo;

/*USUÁRIO*/
create table tbUsuario(
IdUsuario int auto_increment primary key,
Nome varchar(150) not null,
Email varchar(200) not null,
Senha char(8) not null
);

create table tbFuncionario(
IdFuncionario int auto_increment primary key,
IdUsuario int,
CPF char(11) not null,
RG char(9) not null,
DataNasc date not null,
Cargo varchar(50) not null,
DataAdm date not null,
foreign key (IdUsuario) references tbUsuario(IdUsuario)
);

create table tbVisitante(
IdVisitante int auto_increment primary key,
IdUsuario int, 
DataNasc date not null,
CPF char(11) not null,
DataCadastro date not null,
foreign key (IdUsuario) references tbUsuario(IdUsuario)
);

/*ANIMAL*/
-- Habitat
create table tbTipoHabitat(
IdTipoHabitat int auto_increment primary key,
NomeTipoHabitat varchar(100) not null
);
insert into tbTipoHabitat(NomeTipoHabitat) 
values("Terrestre"),
	  ("Aquático"), 
	  ("Aéreo");

create table tbHabitat(
IdHabitat int auto_increment primary key,
NomeHabitat varchar(100) not null,
IdTipoHabitat int not null,
foreign key (IdTipoHabitat) references tbTipoHabitat(IdTipoHabitat),
Capacidade int not null,
QtdAnimal int not null
);
-- Procedures
delimiter $$
create procedure spInsertHabitat(vNomeHabitat varchar(100), vNomeTipoHabitat varchar(100), vCapacidade int)
begin
	if not exists(select * from tbHabitat where NomeHabitat = vNomeHabitat) then
    set @NomeTipoHabitat = (select IdTipoHabitat from tbTipoHabitat where NomeTipoHabitat = vNomeTipoHabitat);
    set @QtdAnimal = 0;
	insert into tbHabitat(NomeHabitat, IdTipoHabitat, Capacidade, QtdAnimal) values (vNomeHabitat, @NomeTipoHabitat, vCapacidade, @QtdAnimal);
    else 
			select ("Habitat já cadastrado");
    end if;
end
$$
call spInsertHabitat("Floresta tropical", "Terrestre", 20);
call spInsertHabitat("Mata Atlântica", "Aéreo", 10);
call spInsertHabitat("Mata Atlântica teste", "Aéreo", 0);
select * from tbHabitat;

-- Espécie 
create table tbEspecie(
IdEspecie int auto_increment primary key,
NomeEspecie varchar(100) not null,
NomeCientifico varchar(100) not null
);
-- Procedures
delimiter $$
create procedure spInsertEspecie(vNomeEspecie varchar(100), vNomeCientifico varchar(100))
begin
	if not exists(select * from tbEspecie where NomeEspecie = vNomeEspecie) then
	insert into tbEspecie(NomeEspecie, NomeCientifico) values (vNomeEspecie, vNomeCientifico);
    else 
			select ("Espécie já cadastrada");
    end if;
end
$$
call spInsertEspecie("Jaguatirica", "Leopardus pardalis");
call spInsertEspecie("Arara-Azul", "Anodorhynchus hyacinthinus");
select * from tbEspecie;

-- Porte
create table tbPorte(
IdPorte int auto_increment primary key,
NomePorte varchar(100) not null
);
insert into tbPorte(NomePorte)
	values("Grande"),
    ("Médio"),
    ("Pequeno");

-- Animal
create table tbAnimal(
IdAnimal int auto_increment primary key,
NomeAnimal varchar(100) not null,
IdEspecie int not null,
foreign key (IdEspecie) references tbEspecie(IdEspecie),
IdHabitat int not null,
foreign key (IdHabitat) references tbHabitat(IdHabitat),
DataNasc date not null,
IdPorte int not null,
foreign key (IdPorte) references tbPorte(IdPorte),
Peso double(2,2) not null,
Sexo char(1),
DescricaoAnimal varchar(2000) 
);

create table tbDieta(
IdDieta int auto_increment primary key,
IdAnimal int not null,
foreign key (IdAnimal) references tbAnimal(IdAnimal),
HoraAlimento char(5) not null,
DescricaoDieta varchar(2000)
);

create table tbProntuario(
IdProntuario int auto_increment primary key,
IdAnimal int not null,
foreign key (IdAnimal) references tbAnimal(IdAnimal),
ObsProntuario varchar(2000)
);
-- Procedures
delimiter $$
create procedure spInsertAnimal(vNomeAnimal varchar(100), vNomeEspecie varchar(100), vNomeHabitat varchar(100), vDataNasc date, vNomePorte varchar(100), vPeso double, vSexo char(1), vDescricaoAnimal varchar(2000), vHoraAlimento char(5), vDescricaoDieta varchar(2000), vObsProntuario varchar(2000))
begin
	 set @Habitat = (select IdHabitat from tbHabitat where NomeHabitat = vNomeHabitat);
	set @Capacidade = (select Capacidade from tbHabitat where IdHabitat = @Habitat);
	set @QtdAtual = (select QtdAnimal from tbHabitat where IdHabitat = @Habitat);
    update tbHabitat set QtdAnimal = 1+@QtdAtual where IdHabitat = @Habitat;
    set @QtdAtualizada = (select QtdAnimal from tbHabitat where IdHabitat = @Habitat);
    
    if (@QtdAtualizada > @Capacidade) then
    select ("Capacidade Máxima do Habitat atingida");
    
	elseif not exists(select * from tbEspecie where NomeEspecie = vNomeEspecie) then
		select ("Espécie não cadastrada");
	elseif not exists(select * from tbHabitat where NomeHabitat = vNomeHabitat) then
		select ("Habitat não cadastrado");
	
	elseif not exists(select * from tbAnimal where NomeAnimal = vNomeAnimal) then
    set @Especie = (select IdEspecie from tbEspecie where NomeEspecie = vNomeEspecie);
    set @Porte = (select IdPorte from tbPorte where NomePorte = vNomePorte);
    
	insert into tbAnimal(NomeAnimal, IdEspecie, IdHabitat, DataNasc, IdPorte, Peso, Sexo, DescricaoAnimal) values (vNomeAnimal, @Especie, @Habitat, vDataNasc, @Porte, vPeso, vSexo, vDescricaoAnimal);
	
	set @IdAnimal = (select IdAnimal from tbAnimal where NomeAnimal = vNomeAnimal);
    
    insert into tbDieta(IdAnimal, HoraAlimento, DescricaoDieta) values (@IdAnimal, vHoraAlimento, vDescricaoDieta);
    insert into tbProntuario(IdAnimal, ObsProntuario) values(@IdAnimal, vObsProntuario);
    else 
			select ("Animal já cadastrado");
    end if;
end
$$
call spInsertAnimal("Azula","Arara-Azul", "Mata Atlântica", '2006-05-25', "Pequeno", 20.00, "F", "ela é legal","16:30", "Carnívoro", "Mancha asa direita");
select * from tbAnimal;
select * from tbDieta;
select * from tbProntuario;

-- Prontuário
create table tbAlergia(
IdAlergiaProntuario int auto_increment primary key,
NomeAlergia varchar(100) not null,
Descricao varchar(2000) not null,
IdProntuario int not null,
foreign key (IdProntuario) references tbProntuario(IdProntuario)
);
-- procedures
delimiter $$
create procedure spInsertAlergia(vNomeAnimal varchar(100), vNomeAlergia varchar(100), vDescricao varchar(2000))
begin
	set @IdAnimal = (select IdAnimal from tbAnimal where NomeAnimal = vNomeAnimal);
	set @IdProntuario = (select IdProntuario from tbProntuario where IdAnimal = @IdAnimal);
    
	if not exists(select * from tbAlergia where NomeAlergia = vNomeAlergia) then
	insert into tbAlergia(NomeAlergia, Descricao, IdProntuario) values (vNomeAlergia, vDescricao, @IdProntuario);
    else 
			select ("Alergia já cadastrada");
    end if;
end
$$
call spInsertAlergia("Azul", "dermatite alimentar", "não tenho ideia");
select * from tbAlergia;

create table tbHistoricoProntuario(
IdHistorico int auto_increment primary key,
IdProntuario int not null,
foreign key (IdProntuario) references tbProntuario(IdProntuario),
DataCadas date not null,
DescricaoHistorico varchar(2000) not null
);
-- Procedure
delimiter $$
create procedure spInsertHistorico(vNomeAnimal varchar(100), vDescricao varchar(2000))
begin
	set @IdAnimal = (select IdAnimal from tbAnimal where NomeAnimal = vNomeAnimal);
	set @IdProntuario = (select IdProntuario from tbProntuario where IdAnimal = @IdAnimal);

	insert into tbHistoricoProntuario(DataCadas, DescricaoHistorico, IdProntuario) values (curdate(), vDescricao, @IdProntuario);
end
$$
call spInsertHistorico("Azula", "Exames de Rotina");
select * from tbHistoricoProntuario;

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

