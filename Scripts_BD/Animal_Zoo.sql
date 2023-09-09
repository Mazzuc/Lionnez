create database dbZoo;
use dbZoo;

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