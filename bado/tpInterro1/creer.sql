-------------------------------------------------------
-- cas NOTE
-------------------------------------------------------
-- script creer.sql
-- connexion a postgresql:    	$plsql di-a2-17
-- execution du script:		=>\i creer.sql
-- verif:			=>\dt
-------------------------------------------------------

-- suppression des tables si elles existent déjà
-- NB : cela supprime donc les éventuels tuples contenus

/*drop table note;
drop table etudiant;
drop table matiere;*/

-- creation de la table Matiere

create table Matiere (
  nmat int primary key,
  nommat varchar(7) check (nommat in ('ARCHI','ANGLAIS','MATH','ALGO','BADO','SYSTEME')) not null,
  coeffmat float check (coeffmat between 1 and 5)
);

-- creation de la table Etudiant

create table Etudiant (
  netud int primary key,
  nometud varchar(20) not null,
  groupetud char(1) check (groupetud in ('A','B','C')),
  redouble boolean
);

-- creation de la table Note

create table Note (
  netud int references Etudiant(netud),
  nmat int references Matiere(nmat),
  moy float check (moy between 0 and 20) not null,
  primary key(netud,nmat)
);
