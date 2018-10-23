------------------------------------------
-- cas NOTE
------------------------------------------
-- script init.sql
-- insertion de tuples
-- execution sous postgresql:	=>\i init.sql
------------------------------------------

-- suppression des tuples dans les tables

delete from note;
delete from matiere;
delete from etudiant;

-- initialisation de la table Etudiant

insert into Etudiant values (1,'SIMON','A',false);
insert into Etudiant values (2,'COLETTA','B',true);
insert into Etudiant values (3,'BOUKACHOUR','C',false);
insert into Etudiant values (4,'BOUDEBOUS','A',true);
insert into Etudiant values (5,'DELARUELLE','B',false);
insert into Etudiant values (6,'SIMON','A',true);

-- initialisation de la table Matiere

insert into Matiere values (1,'MATH',5);
insert into Matiere values (2,'ALGO',5);
insert into Matiere values (3,'BADO',2);
insert into Matiere values (4,'ARCHI',2);
insert into Matiere values (5,'ANGLAIS',3);
insert into Matiere values (6,'SYSTEME',NULL);

-- initialisation  de la table Note

insert into Note values (1,1,10.5);
insert into Note values (1,2,16);
insert into Note values (1,3,8);
insert into Note values (1,4,9);
insert into Note values (1,5,14);
insert into Note values (1,6,12);
insert into Note values (2,1,14);
insert into Note values (2,2,11);
insert into Note values (2,3,8);
insert into Note values (2,6,16);
insert into Note values (3,1,14);
insert into Note values (3,2,12);
insert into Note values (3,3,14);
insert into Note values (3,5,14);
insert into Note values (3,6,12);
insert into Note values (4,2,14);
insert into Note values (4,3,12);
insert into Note values (4,4,14);
insert into Note values (5,5,7);
insert into Note values (5,6,9);

