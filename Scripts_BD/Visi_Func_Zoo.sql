drop database dbZoo;
create database dbZoo;
use dbZoo;

select * from tbCadastroVisitante;
select * from tbCadastro;
select * from tbCadastroFuncionario;

create table tbCadastro(
Nome varchar(150) not null,
IdCadFunc int not null,
foreign key (IdCadFunc) references tbCadastroFuncionario(IdCadFunc),
IdCadVisi int not null,
foreign key (IdCadVisi) references tbCadastroVisitante(IdCadVisi),
Email varchar(200) not null,
Senha varchar(8) not null
);

create table tbCadastroFuncionario(
IdCadFunc int auto_increment primary key,
CPFFunc char(11) not null,
RGFunc char(9) not null,
Cargo varchar(50) not null,
DataNasc date not null,
DataAdm date not null
);

create table tbCadastroVisitante(
IdCadVisi int auto_increment primary key,
CPFVisi char(11) not null,
RGVisi char(9) not null,
DataCadastro date not null
);
