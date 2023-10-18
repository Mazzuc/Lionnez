DROP DATABASE if EXISTS dbZoologico;

create database dbZoologico;
use dbZoologico;

/*USUÁRIO*/

-- Cadastro
create table tbCadastro(
IdCadastro int primary key auto_increment, 
CPF char(11) not null,
Nome varchar(150) not null,
Email varchar(200) not null
); 

-- Login
create table tbLogin(
IdLogin int primary key auto_increment, 
IdCadastro int not null,
foreign key (IdCadastro) references tbCadastro(IdCadastro),
Usuario varchar(100) not null,
Senha char(8) not null,
Acesso int not null
); 

-- Funcionário
create table tbCadastroFuncionario(
IdFuncionario int primary key auto_increment, 
IdCadastro int, 
foreign key (IdCadastro) references tbCadastro(IdCadastro),
Cargo varchar(50) not null,
RGCad char(9) not null,
DataNasc date not null,
DataAdm date not null
);

-- Visitante
create table tbCadastroVisitante(
IdCadastroVisitante int primary key auto_increment,
IdCadastro int, 
foreign key (IdCadastro) references tbCadastro(IdCadastro),
DataCadastro date not null
);

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
Vegetacao varchar(100),
Clima varchar(100),
Solo varchar(100),
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

-- Prontuário
create table tbProntuario(
IdProntuario int auto_increment primary key,
IdAnimal int not null,
foreign key (IdAnimal) references tbAnimal(IdAnimal),
ObsProntuario varchar(2000)
);

create table tbHistoricoProntuario(
IdHistorico int auto_increment primary key,
IdProntuario int not null,
foreign key (IdProntuario) references tbProntuario(IdProntuario),
DataCadas date not null,
Alergia varchar(200) not null,
DescricaoHistorico varchar(2000) not null
);

/*INGRESSO*/

-- Tabela Compra
CREATE TABLE Compra (
    IDCompra INT AUTO_INCREMENT PRIMARY KEY,
    DataCompra DATE,
    ValorTotal DECIMAL(10, 2),
    QtdTotal INT,
    FormaPag ENUM('pix', 'cartao_de_credito', 'boleto'),
    IdCadastro int, 
    foreign key (IdCadastro) references tbCadastro(IdCadastro)
);

-- Tabela Ingresso
CREATE TABLE Ingresso (
    IDIngresso INT AUTO_INCREMENT PRIMARY KEY,
    Nome ENUM('meia', 'inteira'),
    Valor DECIMAL(5, 2)
);

-- Tabela CompraIngresso
CREATE TABLE CompraIngresso (
    IDCompraIngresso INT AUTO_INCREMENT PRIMARY KEY,
    IDCompra INT,
    IDIngresso INT,
    Quantidade INT,
    FOREIGN KEY (IDCompra) REFERENCES Compra(IDCompra),
    FOREIGN KEY (IDIngresso) REFERENCES Ingresso(IDIngresso)
);

-- Tabela NotaFiscal
CREATE TABLE NotaFiscal (
    IDNotaFiscal INT AUTO_INCREMENT PRIMARY KEY,
    DataEmissao DATE,
    ValorTotal DECIMAL(10, 2),
    IDCompra INT,
    FOREIGN KEY (IDCompra) REFERENCES Compra(IDCompra)
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
          
INSERT INTO Ingresso (Nome, Valor) VALUES ('Meia', 20.00);
INSERT INTO Ingresso (Nome, Valor) VALUES ('Inteira', 40.00);

-- Procedures 

-- CADASTRO 
delimiter $$
create procedure spInsertFuncionario(vNome varchar(200), vEmail varchar(200), vCPF char(11), vCargo varchar(50), vSenha char(8), vUsuario varchar(20), vRG char(9), vDataNasc date, vDataAdm date)
begin
	if exists(select * from tbLogin where Usuario = vUsuario) then
   	select ("Usuário Inválido");
	elseif not exists(select * from tbCadastro where CPF = vCPF) then
	insert into tbCadastro(CPF, Nome, Email) values (vCPF, VNome, vEmail);
    
    set @IdCadastro = (select IdCadastro from tbCadastro where CPF = vCPF);
    set @Acesso = 1;
    insert into tbLogin(IdCadastro, Usuario, Senha, Acesso) values (@IdCadastro, vUsuario, vSenha, @Acesso);
    insert into tbCadastroFuncionario(IdCadastro, Cargo, RGCad, DataNasc, DataAdm) values (@IdCadastro, vCargo, vRG, vDataNasc, vDataAdm);
    else 
			select ("Funcionário já cadastrado");
    end if;
end
$$

delimiter $$
create procedure spInsertVisitante(vNome varchar(200), vEmail varchar(200), vCPF char(11), vSenha char(8), vUsuario varchar(20))
begin
	if exists(select * from tbLogin where Usuario = vUsuario) then
   	select ("Usuário Inválido");
	elseif not exists(select * from tbCadastro where CPF = vCPF) then
	insert into tbCadastro(CPF, Nome, Email) values (vCPF, VNome, vEmail);
    
    set @IdCadastro = (select IdCadastro from tbCadastro where CPF = vCPF);
    set @Acesso = 0;
    insert into tbLogin(IdCadastro, Usuario, Senha, Acesso) values (@IdCadastro, vUsuario, vSenha, @Acesso);
    insert into tbCadastroVisitante(IdCadastro, DataCadastro) values (@IdCadastro, curdate());
    else 
			select ("Visitante já cadastrado");
    end if;
end
$$

delimiter $$
create procedure spSelectVisitante()
begin
	select 
	tbCadastro.IdCadastro, 
	tbCadastro.Nome,
	tbCadastro.Email,
     tbCadastro.CPF,
	tbLogin.Usuario,
	tbLogin.Senha,
	tbCadastroVisitante.DataCadastro
	from tbCadastroVisitante
	left join tbCadastro on tbCadastro.IdCadastro = tbCadastroVisitante.IdCadastro
	left join tbLogin on tbCadastro.IdCadastro = tbLogin.IdCadastro;
end
$$

delimiter $$
create procedure spSelectFuncionario()
begin
	select 
	tbCadastro.IdCadastro, 
	tbCadastro.Nome,
	tbCadastro.Email,
    tbCadastro.CPF,
    tbCadastroFuncionario.RGCad,
	tbLogin.Usuario,
	tbLogin.Senha,
	tbCadastroFuncionario.Cargo,
    tbCadastroFuncionario.DataNasc,
    tbCadastroFuncionario.DataAdm
	from tbCadastroFuncionario
	left join tbCadastro on tbCadastro.IdCadastro = tbCadastroFuncionario.IdCadastro
	left join tbLogin on tbCadastro.IdCadastro = tbLogin.IdCadastro;
end
$$

delimiter $$
create procedure spDeleteCadastro(vUsuario varchar(100))
begin
	set @IdCadastro = (select IdCadastro from tbLogin where Usuario = vUsuario);
    set @Acesso = (select Acesso from tbLogin where Usuario = vUsuario);
    
    if(@Acesso = 0) then
		select ("Usuário não pode ser excluído");
    else
	if exists(select * from tbcadastro where IdCadastro = @IdCadastro) then
		delete from tbCadastroFuncionario where IdCadastro = @IdCadastro;
		delete from tblogin where IdCadastro = @IdCadastro;
		delete from tbCadastro where IdCadastro = @IdCadastro;
    else
		select ("Usuário não existe");
    end if;
    
    end if;
end
$$

delimiter $$
create procedure spLogin(vUsuario varchar(100), vSenha char(8))
begin
	set @IdCadastro = (select IdCadastro from tbLogin where Usuario = vUsuario);
	set @Acesso = (select Acesso from tbLogin where Usuario = vUsuario);
    
    set @Login = (select IdCadastro from tbLogin where Usuario = vUsuario);
    set @Senha = (select Senha from tbLogin where Usuario = vUsuario);
        
	if(@Acesso = 0) then
    
		if(@Login = @IdCadastro and @Senha = vSenha) then
		select ("Usuário logado - Visitante");
        else
        select ("Usuário ou senha inválidos");
        end if;
        
    else
    
		if(@Login = @IdCadastro and @Senha = vSenha) then
		select ("Usuário logado - Funcionário");
        else
        select ("Usuário ou senha inválidos");
        end if;
        
    end if;
end
$$

-- HABITAT 
delimiter $$
create procedure spInsertHabitat(vNomeHabitat varchar(100), vNomeTipoHabitat varchar(100), vCapacidade int, vVegetacao varchar(100), vClima varchar(100), vSolo varchar(100))
begin
	if not exists(select * from tbHabitat where NomeHabitat = vNomeHabitat) then
    set @NomeTipoHabitat = (select IdTipoHabitat from tbTipoHabitat where NomeTipoHabitat = vNomeTipoHabitat);
    set @QtdAnimal = 0;
	insert into tbHabitat(NomeHabitat, IdTipoHabitat, Capacidade, QtdAnimal, Vegetacao, Clima, Solo) values (vNomeHabitat, @NomeTipoHabitat, vCapacidade, @QtdAnimal, vVegetacao, vClima, vSolo);
    else 
			select ("Habitat já cadastrado");
    end if;
end
$$

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
create procedure spUpdateHabitat(vNomeHabitat varchar(100), vCapacidade int, vVegetacao varchar(100), vClima varchar(100), vSolo varchar(100))
begin
	set @IdHabitat = (select IdHabitat from tbHabitat where NomeHabitat = vNomeHabitat);
	if not exists(select * from tbHabitat where NomeHabitat = vNomeHabitat) then
    select ("Habitat não cadastrado");
    else 
	update tbHabitat set NomeHabitat = vNomeHabitat, Capacidade = vCapacidade, Vegetacao = vVegetacao, Clima = vClima, Solo = vSolo where IdHabitat = @IdHabitat;
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
    tbHabitat.Vegetacao as "Vegetação",
    tbHabitat.Solo as "Solo",
    tbHabitat.Clima as "Clima",
    tbHabitat.Capacidade,
    tbHabitat.QtdAnimal as "Animais"
FROM tbHabitat
LEFT JOIN tbTipoHabitat
ON tbHabitat.IdTipoHabitat = tbTipoHabitat.IdTipoHabitat;
end
$$

delimiter $$
create procedure spSelectHabitatUnic(vNomeHabitat int)
begin
	if not exists(select * from tbHabitat where IdHabitat = vNomeHabitat) then
		select ("Habitat não cadastrado");
    else 
	SELECT
		tbHabitat.IdHabitat as "Id do Habitat",
		tbHabitat.NomeHabitat as "Nome",
		tbHabitat.Vegetacao as "Vegetação",
		tbHabitat.Solo as "Solo",
		tbHabitat.Clima as "Clima",
		tbHabitat.Capacidade
	FROM
	tbHabitat where IdHabitat = vNomeHabitat;
	end if;
end
$$

delimiter $$
create procedure spSelectHabitatAnimais(vNomeHabitat int)
begin
	set @IdHabitat = (select IdHabitat from tbHabitat where IdHabitat = vNomeHabitat);
	if not exists(select * from tbHabitat where IdHabitat = @IdHabitat) then
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
    tbHabitat.NomeHabitat as "Habitat"
FROM tbAnimal
LEFT JOIN tbHabitat
ON tbAnimal.IdHabitat = tbHabitat.IdHabitat;
end
$$
call spSelectAnimalEspecifico(1);

delimiter $$
create procedure spSelectAnimalEspecifico(vNomeAnimal int)
begin
	set @IdAnimal = (select IdAnimal from tbAnimal where IdAnimal = vNomeAnimal);
    
	if not exists(select * from tbAnimal where IdAnimal = @IdAnimal) then
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
        tbProntuario on tbAnimal.IdAnimal = @IdAnimal
        LEFT JOIN tbEspecie
		ON tbAnimal.IdEspecie = tbEspecie.IdEspecie
		LEFT JOIN tbDieta
		ON tbAnimal.IdDieta = tbDieta.IdDieta
		LEFT JOIN tbHabitat
		ON tbAnimal.IdHabitat = tbHabitat.IdHabitat
		LEFT JOIN tbPorte
		ON tbAnimal.IdPorte = tbPorte.IdPorte
        limit 1;
	end if;
end
$$

-- PRONTUÁRIO
delimiter $$
create procedure spInsertHistorico(vNomeAnimal varchar(100), vAlergia varchar(200), vDescricao varchar(2000))
begin
	set @IdAnimal = (select IdAnimal from tbAnimal where NomeAnimal = vNomeAnimal);
	set @IdProntuario = (select IdProntuario from tbProntuario where IdAnimal = @IdAnimal);

	if not exists(select * from tbAnimal where NomeAnimal = vNomeAnimal) then
    select ("Prontuário não cadastrado");
    else    
	insert into tbHistoricoProntuario(DataCadas, DescricaoHistorico, IdProntuario, Alergia) values (curdate(), vDescricao, @IdProntuario, vAlergia);
	end if;
end
$$

delimiter $$
create procedure spSelectProntuario(vNomeAnimal varchar(100))
begin
	set @IdAnimal = (select IdAnimal from tbAnimal where NomeAnimal = vNomeAnimal);
    
	if not exists(select * from tbAnimal where NomeAnimal = vNomeAnimal) then
		select ("Animal não cadastrado");
    else
		-- geral
		SELECT
			tbProntuario.IdProntuario as "Id do Prontuário",
			tbAnimal.NomeAnimal as "Nome",
			tbAnimal.DataNasc as "Nascimento",
			tbEspecie.NomeEspecie as "Espécie",
			tbAnimal.Peso,
			tbAnimal.Sexo,
            tbProntuario.ObsProntuario as "Observação"
		FROM
			tbProntuario
		INNER JOIN 
        tbAnimal on tbProntuario.IdAnimal = tbAnimal.IdAnimal and tbAnimal.IdAnimal = @IdAnimal
        LEFT JOIN tbEspecie
		ON tbAnimal.IdEspecie = tbEspecie.IdEspecie;
        
		-- Historico
		SELECT
			tbHistoricoProntuario.DataCadas as "Data",
            tbHistoricoProntuario.Alergia as "Alergia",
			tbHistoricoProntuario.DescricaoHistorico as "Descrição"
		FROM
			tbProntuario
        LEFT JOIN tbHistoricoProntuario
		ON tbProntuario.IdAnimal = @IdAnimal and tbProntuario.IdProntuario = tbHistoricoProntuario.IdProntuario
        group by IdHistorico;
	end if;
end
$$

-- Procedure para realizar a compra e gerar a nota fiscal
DELIMITER //

CREATE  PROCEDURE RealizarCompra(
    IN FormaPagParam ENUM('pix', 'cartao_de_credito', 'boleto'),
    IN IngressosMeia INT,
    IN IngressosInteira INT,
    IN IdCadastro INT 
)
BEGIN
    --  data atual
    SET @DataCompra = CURDATE();

    -- Inserindo dados na tabela Compra
    INSERT INTO Compra (DataCompra, FormaPag, ValorTotal, QtdTotal,IdCadastro)
    VALUES (@DataCompra, FormaPagParam, 0.00, IngressosMeia + IngressosInteira, IdCadastro);

    -- ID da compra inserida
    SET @IDCompra = LAST_INSERT_ID();

    -- Inserir ingressos meia na tabela CompraIngresso
    INSERT INTO CompraIngresso (IDCompra, IDIngresso, Quantidade)
    SELECT @IDCompra, IDIngresso, IngressosMeia
    FROM Ingresso
    WHERE Nome = 'meia';

    -- Inserindo ingressos inteira na tabela CompraIngresso
    INSERT INTO CompraIngresso (IDCompra, IDIngresso, Quantidade)
    SELECT @IDCompra, IDIngresso, IngressosInteira
    FROM Ingresso
    WHERE Nome = 'inteira';

    -- Calcular o valor da compra
    SELECT SUM(I.Valor * CI.Quantidade)
    INTO @ValorCompra
    FROM CompraIngresso CI
    INNER JOIN Ingresso I ON CI.IDIngresso = I.IDIngresso
    WHERE CI.IDCompra = @IDCompra;

    -- Atualizando o valor total da compra
    UPDATE Compra
    SET ValorTotal = @ValorCompra
    WHERE IDCompra = @IDCompra;

    -- Inserindo a nota fiscal associada à compra
    INSERT INTO NotaFiscal (DataEmissao, ValorTotal, IDCompra)
    VALUES (@DataCompra, @ValorCompra, @IDCompra);
    
    if NOT exists(select * from tbLogin where IdLogin = IdCadastro) then
   	select ("Usuário Inválido");
 END IF; 
 
END //
DELIMITER ;

CREATE USER 'Zoo'@'localhost' IDENTIFIED WITH mysql_native_password BY '12345678';
GRANT ALL PRIVILEGES ON dbZoologico.* TO 'Zoo'@'localhost' WITH GRANT OPTION;
