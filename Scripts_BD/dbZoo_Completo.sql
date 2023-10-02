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

DROP DATABASE if EXISTS dbZoo;

create database dbZoo;
use dbZoo;

/*ANIMAL*/
-- Habitat
create table tbTipoHabitat(
IdTipoHabitat int auto_increment primary key,
NomeTipoHabitat varchar(100) not null
);

create table tbHabitat(
IdHabitat int auto_increment primary key,
NomeHabitat varchar(100) not null,
IdTipoHabitat int not null,
foreign key (IdTipoHabitat) references tbTipoHabitat(IdTipoHabitat),
Capacidade int not null,
QtdAnimal int not null
);

-- Espécie 
create table tbEspecie(
IdEspecie int auto_increment primary key,
NomeEspecie varchar(100) not null
);

-- Porte
create table tbPorte(
IdPorte int auto_increment primary key,
NomePorte varchar(100) not null
);

-- Dieta
create table tbDieta(
IdDieta int auto_increment primary key,
NomeDieta varchar(100) not null,
DescricaoDieta varchar(2000)
);

-- Animal
create table tbAnimal(
IdAnimal int auto_increment primary key,
NomeAnimal varchar(100) not null,
IdEspecie int not null,
foreign key (IdEspecie) references tbEspecie(IdEspecie),
IdDieta int not null,
foreign key (IdDieta) references tbDieta(IdDieta),
IdHabitat int not null,
foreign key (IdHabitat) references tbHabitat(IdHabitat),
DataNasc date not null,
IdPorte int not null,
foreign key (IdPorte) references tbPorte(IdPorte),
Peso double(2,2) not null,
Sexo char(1),
DescricaoAnimal varchar(2000) 
);

create table tbProntuario(
IdProntuario int auto_increment primary key,
IdAnimal int not null,
foreign key (IdAnimal) references tbAnimal(IdAnimal),
ObsProntuario varchar(2000)
);

-- Prontuário
create table tbAlergia(
IdAlergia int auto_increment primary key,
NomeAlergia varchar(100) not null,
Descricao varchar(2000) not null,
IdProntuario int not null,
foreign key (IdProntuario) references tbProntuario(IdProntuario)
);

create table tbHistoricoProntuario(
IdHistorico int auto_increment primary key,
IdProntuario int not null,
foreign key (IdProntuario) references tbProntuario(IdProntuario),
DataCadas date not null,
DescricaoHistorico varchar(2000) not null
);

-- Insert
insert into tbTipoHabitat(NomeTipoHabitat) 
	values("Terrestre"),
		  ("Aquático"), 
		  ("Aéreo");
      
insert into tbPorte(NomePorte)
	values("Grande"),
    ("Médio"),
    ("Pequeno");

insert into tbDieta(NomeDieta)
	values("Carnívoro"),
		  ("Onívoro"),
          ("Herbívoro");

-- Procedures 
-- HABITAT 
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

call spInsertHabitat("Floresta Tropical", "Terrestre", 10);
call spInsertHabitat("Mata Atlântica", "Terrestre", 1);

delimiter $$
create procedure spDeleteHabitat(vNomeHabitat varchar(100))
begin
	set @IdHabitat = (select IdHabitat from tbHabitat where NomeHabitat = vNomeHabitat);
    set @Qtd = (select QtdAnimal from tbHabitat where NomeHabitat = vNomeHabitat);
    
	if (@Qtd != 0) then
		select ("Existem animais cadastrados no habitat");
	elseif not exists(select * from tbHabitat where NomeHabitat = vNomeHabitat) then
    select ("Habitat não cadastrado");
    else
	delete from tbHabitat where IdHabitat = @IdHabitat;
    end if;
end
$$

delimiter $$
create procedure spUpdateHabitat(vNomeHabitat varchar(100), vCapacidade int)
begin
	set @IdHabitat = (select IdHabitat from tbHabitat where NomeHabitat = vNomeHabitat);
	if not exists(select * from tbHabitat where NomeHabitat = vNomeHabitat) then
    select ("Habitat não cadastrado");
    else 
	update tbHabitat set Capacidade = vCapacidade where IdHabitat = @IdHabitat;
    end if;
end
$$

delimiter $$
create procedure spSelectHabitat()
begin
SELECT 
	tbHabitat.IdHabitat as "Id do Habitat",
	tbHabitat.NomeHabitat as "Nome",
    tbTipoHabitat.NomeTipoHabitat as "Tipo",
    tbHabitat.Capacidade,
    tbHabitat.QtdAnimal as "Animais"
FROM tbHabitat
LEFT JOIN tbTipoHabitat
ON tbHabitat.IdTipoHabitat = tbTipoHabitat.IdTipoHabitat;
end
$$

delimiter $$
create procedure spSelectHabitatAnimais(vNomeHabitat varchar(200))
begin
	set @IdHabitat = (select IdHabitat from tbHabitat where NomeHabitat = vNomeHabitat);
    
	if not exists(select * from tbHabitat where NomeHabitat = vNomeHabitat) then
		select ("Habitat não cadastrado");
    else 
		SELECT
			tbAnimal.IdAnimal as "Id do Animal",
			tbAnimal.NomeAnimal as "Nome",
			tbAnimal.DataNasc as "Nascimento",
			tbEspecie.NomeEspecie as "Espécie",
			tbPorte.NomePorte as "Porte",
			tbDieta.NomeDieta as "Dieta",
			tbAnimal.Peso,
			tbAnimal.Sexo,
			tbAnimal.DescricaoAnimal as "Descrição"
		FROM
			tbHabitat
		INNER JOIN
		  tbAnimal
		ON tbAnimal.IdHabitat = @IdHabitat
        INNER JOIN tbEspecie
		ON tbAnimal.IdEspecie = tbEspecie.IdEspecie
		INNER JOIN tbDieta
		ON tbAnimal.IdDieta = tbDieta.IdDieta
		INNER JOIN tbPorte
		ON tbAnimal.IdPorte = tbPorte.IdPorte
        GROUP BY tbAnimal.IdAnimal
;
	end if;
end
$$

drop procedure spSelectHabitatAnimais;
call spSelectHabitatAnimais("Floresta Tropical");
call spSelectHabitat;

-- ESPÉCIE
delimiter $$
create procedure spInsertEspecie(vNomeEspecie varchar(100))
begin
	if not exists(select * from tbEspecie where NomeEspecie = vNomeEspecie) then
	insert into tbEspecie(NomeEspecie) values (vNomeEspecie);
    else 
			select ("Espécie já cadastrada");
    end if;
end
$$

-- ANIMAL 
delimiter $$
create procedure spInsertAnimal(vNomeAnimal varchar(100), vNomeEspecie varchar(100), vNomeHabitat varchar(100), vDataNasc date, vNomePorte varchar(100), vPeso double, vSexo char(1), vDescricaoAnimal varchar(2000), vNomeDieta varchar(100), vObsProntuario varchar(2000))
begin
	set @Habitat = (select IdHabitat from tbHabitat where NomeHabitat = vNomeHabitat);
	set @Capacidade = (select Capacidade from tbHabitat where IdHabitat = @Habitat);
	set @QtdAtual = (select QtdAnimal from tbHabitat where IdHabitat = @Habitat);
    
    set @QtdAtualizada = 1+@QtdAtual;
    
    if not exists(select * from tbEspecie where NomeEspecie = vNomeEspecie) then
		call spInsertEspecie (vNomeEspecie);
	end if;
    
    if (@QtdAtualizada > @Capacidade) then
    select ("Capacidade Máxima do Habitat atingida");
    
	elseif not exists(select * from tbHabitat where NomeHabitat = vNomeHabitat) then
		select ("Habitat não cadastrado");
	elseif not exists(select * from tbDieta where NomeDieta = vNomeDieta) then
		select ("Dieta não cadastrada");
	
	elseif not exists(select * from tbAnimal where NomeAnimal = vNomeAnimal) then
    set @Especie = (select IdEspecie from tbEspecie where NomeEspecie = vNomeEspecie);
    set @Porte = (select IdPorte from tbPorte where NomePorte = vNomePorte);
    set @Dieta = (select IdDieta from tbDieta where NomeDieta = vNomeDieta);
    
	update tbHabitat set QtdAnimal = @QtdAtualizada where IdHabitat = @Habitat;
    
	insert into tbAnimal(NomeAnimal, IdEspecie, IdHabitat, IdDieta, DataNasc, IdPorte, Peso, Sexo, DescricaoAnimal) values (vNomeAnimal, @Especie, @Habitat, @Dieta, vDataNasc, @Porte, vPeso, vSexo, vDescricaoAnimal);
	
	set @IdAnimal = (select IdAnimal from tbAnimal where NomeAnimal = vNomeAnimal);
    
    insert into tbProntuario(IdAnimal, ObsProntuario) values(@IdAnimal, vObsProntuario);
    else 
			select ("Animal já cadastrado");
    end if;
end
$$

call spInsertAnimal("Lulu", "Jaguatirica", "Floresta Tropical", '2006-05-25', "Médio", 10.00, "F", "Animal Legal", "Carnívoro", "Mancha na pata esquerda");
call spInsertAnimal("Azula", "Arara-Azul", "Mata Atlântica", '2006-05-25', "Médio", 10.00, "F", "Animal Legal", "Herbívoro", "Mancha na asa esquerda");
call spInsertAnimal("Azul", "Jaguatirica", "Floresta Tropical", '2006-05-25', "Médio", 10.00, "M", "Animal Legal", "Carnívoro", "Mancha na pata direita");

delimiter $$
create procedure spUpdateAnimal(vNomeAnimal varchar(100), vNomeHabitat varchar(100), vPeso double, vDescricaoAnimal varchar(2000), vObsProntuario varchar(2000))
begin
	set @AntigoHabitat = (select IdHabitat from tbAnimal where NomeAnimal = vNomeAnimal);
    set @QtdAtualAntigo = (select QtdAnimal from tbHabitat where IdHabitat = @AntigoHabitat);

	set @IdAnimal = (select IdAnimal from tbAnimal where NomeAnimal = vNomeAnimal);
	set @IdProntuario = (select IdProntuario from tbProntuario where IdAnimal = @IdAnimal);
    
    set @Habitat = (select IdHabitat from tbHabitat where NomeHabitat = vNomeHabitat);
	set @Capacidade = (select Capacidade from tbHabitat where IdHabitat = @Habitat);
	set @QtdAtual = (select QtdAnimal from tbHabitat where IdHabitat = @Habitat);
    
    set @QtdAtualizada = 1+@QtdAtual;
    
    if (@QtdAtualizada > @Capacidade) then
    select ("Capacidade Máxima do Habitat atingida");
    
	elseif not exists(select * from tbAnimal where NomeAnimal = vNomeAnimal) then
    select ("Animal não cadastrado");
    else 
		update tbAnimal set IdHabitat = @Habitat, Peso = vPeso, DescricaoAnimal = vDescricaoAnimal where IdAnimal = @IdAnimal;
        update tbProntuario set ObsProntuario = vObsProntuario where IdProntuario = @IdProntuario; 
        
        update tbHabitat set QtdAnimal = @QtdAtualizada where IdHabitat = @Habitat;
        update tbHabitat set QtdAnimal = @QtdAtualAntigo-1 where IdHabitat = @AntigoHabitat;
    end if;
end
$$

delimiter $$
create procedure spDeleteAnimal(vNomeAnimal varchar(100))
begin
	set @IdAnimal = (select IdAnimal from tbAnimal where NomeAnimal = vNomeAnimal);
    
    set @IdHabitat = (select IdHabitat from tbAnimal where NomeAnimal = vNomeAnimal);
    set @IdProntuario = (select IdProntuario from tbProntuario where IdAnimal = @IdAnimal);
    
    set @QtdAtual = (select QtdAnimal from tbHabitat where IdHabitat = @IdHabitat);
    
	if not exists(select * from tbAnimal where NomeAnimal = vNomeAnimal) then
    select ("Animal não cadastrado");
    else 
    delete from tbAlergia where IdProntuario = @IdProntuario;
    delete from tbHistoricoProntuario where IdProntuario = @IdProntuario;
	delete from tbProntuario where IdProntuario = @IdProntuario;
    
    delete from tbAnimal where IdAnimal = @IdAnimal;
    update tbHabitat set QtdAnimal = @QtdAtual-1 where IdHabitat = @IdHabitat;
    end if;
end
$$

delimiter $$
create procedure spSelectAnimal()
begin
SELECT 
	tbAnimal.IdAnimal as "Id do Animal",
    tbAnimal.NomeAnimal as "Nome",
    tbAnimal.DataNasc as "Nascimento",
    tbEspecie.NomeEspecie as "Espécie",
    tbPorte.NomePorte as "Porte",
    tbHabitat.NomeHabitat as "Habitat",
    tbDieta.NomeDieta as "Dieta",
    tbAnimal.Peso,
    tbAnimal.Sexo,
    tbAnimal.DescricaoAnimal as "Descrição"
FROM tbAnimal
LEFT JOIN tbEspecie
ON tbAnimal.IdEspecie = tbEspecie.IdEspecie
LEFT JOIN tbDieta
ON tbAnimal.IdDieta = tbDieta.IdDieta
LEFT JOIN tbHabitat
ON tbAnimal.IdHabitat = tbHabitat.IdHabitat
LEFT JOIN tbPorte
ON tbAnimal.IdPorte = tbPorte.IdPorte;
end
$$

delimiter $$
create procedure spSelectAnimalEspecifico(vNomeAnimal varchar(200))
begin
	set @IdAnimal = (select IdAnimal from tbAnimal where NomeAnimal = vNomeAnimal);
    
	if not exists(select * from tbAnimal where NomeAnimal = vNomeAnimal) then
		select ("Animal não cadastrado");
    else 
		SELECT
			tbAnimal.IdAnimal as "Id do Animal",
			tbAnimal.NomeAnimal as "Nome",
			tbAnimal.DataNasc as "Nascimento",
			tbEspecie.NomeEspecie as "Espécie",
			tbPorte.NomePorte as "Porte",
			tbHabitat.NomeHabitat as "Habitat",
			tbDieta.NomeDieta as "Dieta",
			tbAnimal.Peso,
			tbAnimal.Sexo,
			tbAnimal.DescricaoAnimal as "Descrição"
		FROM
			tbAnimal
		INNER JOIN
			tbHabitat
		ON tbAnimal.IdAnimal = @IdAnimal
        INNER JOIN tbEspecie
		ON tbAnimal.IdEspecie = tbEspecie.IdEspecie
		INNER JOIN tbDieta
		ON tbAnimal.IdDieta = tbDieta.IdDieta
		INNER JOIN tbPorte
		ON tbAnimal.IdPorte = tbPorte.IdPorte
;
	end if;
end
$$

call spSelectAnimal;
call spSelectAnimalEspecifico("Floresta Tropical");

-- PRONTUÁRIO
delimiter $$
create procedure spInsertAlergia(vNomeAnimal varchar(100), vNomeAlergia varchar(100), vDescricao varchar(2000))
begin
	set @IdAnimal = (select IdAnimal from tbAnimal where NomeAnimal = vNomeAnimal);
	set @IdProntuario = (select IdProntuario from tbProntuario where IdAnimal = @IdAnimal);
    	if not exists(select * from tbAnimal where NomeAnimal = vNomeAnimal) then
    select ("Prontuário não cadastrado");
    else   
	insert into tbAlergia(NomeAlergia, Descricao, IdProntuario) values (vNomeAlergia, vDescricao, @IdProntuario);
    end if;
end
$$

delimiter $$
create procedure spUpdateAlergia(vNomeAnimal varchar(100), vNomeAlergia varchar(100), vDescricao varchar(2000))
begin
	set @IdAnimal = (select IdAnimal from tbAnimal where NomeAnimal = vNomeAnimal);
	set @IdProntuario = (select IdProntuario from tbProntuario where IdAnimal = @IdAnimal);
    
    set @IdAlergia = (select IdAlergia from tbAlergia where IdProntuario = @IdProntuario and NomeAlergia = vNomeAlergia);
    
	if not exists(select * from tbAnimal where NomeAnimal = vNomeAnimal) then
    select ("Prontuário não cadastrado");
    elseif not exists(select * from tbAlergia where IdAlergia = @IdAlergia) then
    select ("Alergia não cadastrada");
    else
	update tbAlergia set Descricao = vDescricao where IdAlergia = @IdAlergia;
    end if;
end
$$

delimiter $$
create procedure spInsertHistorico(vNomeAnimal varchar(100), vDescricao varchar(2000))
begin
	set @IdAnimal = (select IdAnimal from tbAnimal where NomeAnimal = vNomeAnimal);
	set @IdProntuario = (select IdProntuario from tbProntuario where IdAnimal = @IdAnimal);

	if not exists(select * from tbAnimal where NomeAnimal = vNomeAnimal) then
    select ("Prontuário não cadastrado");
    else    
	insert into tbHistoricoProntuario(DataCadas, DescricaoHistorico, IdProntuario) values (curdate(), vDescricao, @IdProntuario);
	end if;
end
$$

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

