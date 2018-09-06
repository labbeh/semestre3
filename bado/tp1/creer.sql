drop table utilisateur cascade;
drop table serveur cascade;
drop table  connexion cascade;

create table utilisateur
(idu integer primary key,
 nom varchar(10),
 role varchar(20) check (role in ('etudiant', 'enseignant', 'sysres', 'admin'))
);


create table serveur
(ids integer primary key,
 nom varchar(40),
 os varchar(20) check (os in ('windows', 'linux', 'macos'))
);


create table connexion
(idu integer not null references utilisateur(idu),
 ids integer not null references serveur(ids),
 datec timestamp,
 primary key (idu, ids, datec),
 login varchar(20),
  duree integer
);

insert into utilisateur values (1, 'A', 'etudiant');
insert into utilisateur values (2, 'B', 'etudiant');
insert into utilisateur values (3, 'C', 'enseignant');
insert into utilisateur values (4, 'D', 'sysres');
insert into utilisateur values (5, 'E', 'admin');
insert into utilisateur values (6, 'F', 'etudiant');

insert into serveur values (10, 'corton.iut.univ-lehavre.fr', 'windows');
insert into serveur values (11, 'woody.iut.univ-lehavre.fr', 'linux');
insert into serveur values (12, 'litis.univ-lehavre.fr', 'windows');
insert into serveur values (13, 'clematite.univ-lehavre.fr', 'macos');
insert into serveur values (14, 'iut.univ-lehavre.fr', 'windows');
insert into serveur values (15, 'uranus.iut.univ-lehavre.fr', 'windows');
insert into serveur values (16, 'pluton.univ-lehavre.fr', 'windows');

insert into connexion values((select idu from utilisateur where nom ='A'),(select ids from serveur where nom ='corton.iut.univ-lehavre.fr'), '03/03/2014 01:05:10', 'LOG1', 100);
insert into connexion values((select idu from utilisateur where nom ='B'),(select ids from serveur where nom ='corton.iut.univ-lehavre.fr'), '03/03/2015 01:05:10', 'LOG3', 90);
insert into connexion values(2,12, '03/03/2009 01:05:10', 'LOG4', 190);


