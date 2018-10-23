----------------------------------------
-- Auteur: LABBE Hugo
-- Date	 : 2018-10-23
-- Cas 	 : Contrôle de TP 1, semestre 3
----------------------------------------

-- question 1: trigger qui entre le résultat dans la table resultat après chaque insertion de note
drop table resultat cascade;

create table resulat(
	num_e int, --num etud
	num_m int, -- num mat
	note numeric check (note between 0 and 20),
	res varchar(15),
	primary key(num_e, num_m)
);

/* résultat, qui est : 
	REDOUBLE (si moy < 10),
	PASSABLE (si moy est dans [10-12[)
	AB (si moy est dans [12-14[),
	B  (si moy est dans [14-16[),
	TB (si moy > = 16)
*/

drop function if exists f_trig_res() cascade;

create or replace function f_trig_res() returns trigger as
	$$
		declare
			result varchar(15);
		begin
			if(new.moy < 10) then
				result = 'REDOUBLE';
			end if;

			if(new.moy between 10 and 12) then
				result = 'PASSABLE';
			end if;

			if(new.moy between 12 and 14) then
				result = 'AB';
			end if;

			if(new.moy between 14 and 16) then
				result = 'B';
			end if;

			if(new.moy >= 16) then
				result = 'TB';
			end if;

			insert into resulat values(new.netud, new.nmat, new.moy, result);
			return new;
		end
	$$language plpgsql;

drop trigger if exists res on note;
create trigger res after insert on note for each row execute procedure f_trig_res();

-- test:
insert into note values(5,1,8);
insert into note values(5,2,17);

-- resultat:
/*
	 num_e | num_m | note |   res    
	-------+-------+------+----------
	     5 |     1 |    8 | REDOUBLE
	     5 |     2 |   17 | TB
*/

-- question 2: vérifie que le nombre max d'étudiant (ici 10) n'est pas atteint, sinon rejet de l'insertion
drop function if exists f_trig_seuil() cascade;

create or replace function f_trig_seuil() returns trigger as
	$$
		declare
			nb_etud bigint;
		begin
			select count(*) into nb_etud from etudiant;
			nb_etud = nb_etud+1; -- on incrémente de 1 car comme on lance le trigger avant insertion (before)
								 -- avant la première insertion count(*) retorunera 0 et on aura un décalge
								 -- de 1 tuple

			if(nb_etud > 10) then
				raise exception 'L effectif maximum d étudiants est atteint';
			else
				raise notice 'il y a maintenant % tuples dans la table %', nb_etud, TG_TABLE_NAME;
			end if;

			return null;
		end
	$$language plpgsql;

drop trigger if exists trig_seuil on etudiant;

create trigger trig_seuil before insert on etudiant for each statement execute procedure f_trig_seuil();

-- test: (il y a déjà 6 étudiants dans la base)
insert into Etudiant values (7,'LABBE','C',true);
insert into Etudiant values (8,'SADEG','C',false);
insert into Etudiant values (9,'LE PIVERT','A',false);
insert into Etudiant values (10,'ROBERT','A',false);

-- affichage:
-- a l'insertion du dernier tuple: NOTICE:  il y a maintenant 10 tuples dans la table etudiant

insert into Etudiant values (11,'ENTROP','A',false);
/*
	ERREUR:  L effectif maximum d étudiants est atteint
	CONTEXTE : fonction PL/pgsql f_trig_seuil(), ligne 9 à RAISE
*/
-- suppression: commandes écrites avant création du trigger et de sa fontion associée

-- question 3.1: affiche avant insertion qu'un étudiant va être inséré

drop function if exists f_trig_2_1() cascade;

create or replace function f_trig_2_1() returns trigger as
	$$
		begin
			raise notice 'un nouvel etudiant va être insere dans la base';
			return null;
		end
	$$language plpgsql;

create trigger trig_2_1 before insert on etudiant execute procedure f_trig_2_1();

-- test:
insert into Etudiant values (12,'AUTREETUD','B',false);

-- affichage:

/*
	NOTICE:  un nouvel etudiant va être insere dans la base
	INSERT 0 1
*/

-- question 3.2: affiche qu'un etudiant a été insérer après insertion avec ses valeurs
drop function if exists f_trig_2_2() cascade;

create or replace function f_trig_2_2() returns trigger as
	$$
		begin
			raise notice 'un nouvel etudiant a été inséré: ( %, %, %, % )', new.netud, new.nometud,
																			new.groupetud, new.redouble;
			return null;
		end
	$$language plpgsql;

create trigger trig_2_2 after insert on etudiant for each row execute procedure f_trig_2_2();

-- test
insert into etudiant values (15, 'JEAN', 'C', false);

-- affichage
/*
	NOTICE:  un nouvel etudiant va être insere dans la base
	NOTICE:  un nouvel etudiant a été inséré: ( 15, JEAN, C, f )
	INSERT 0 1
*/

-- question 3.3: affiche les infos sur le trigger
drop function if exists f_trig_2_3() cascade;

create or replace function f_trig_2_3() returns trigger as
	$$
		begin
			raise notice 'trigger % sur table % effectue opération % déclenchement %' ,TG_NAME,
																						TG_TABLE_NAME,
																						TG_OP,
																						TG_WHEN;
			return null;
		end
	$$language plpgsql;

create trigger trig_2_3 after insert on etudiant for each statement execute procedure f_trig_2_3();

-- question 3.4: test des 2 derniers triggers
insert into etudiant values(29, 'MARCUS', 'A', false);

-- affichage:
/*
	NOTICE:  un nouvel etudiant va être insere dans la base
	NOTICE:  un nouvel etudiant a été inséré: ( 29, MARCUS, A, f )
	NOTICE:  trigger trig_2_3 sur table etudiant effectue opération INSERT déclenchement AFTER
	INSERT 0 1
*/

-- question 3.5: suppresion des 3 triggers de la question 3
drop trigger if exists f_trig_2_1 on etudiant;
drop trigger if exists f_trig_2_2 on etudiant;
drop trigger if exists f_trig_2_3 on etudiant;

-- suppression des fonctions associées aux triggers
drop function if exists f_trig_2_1() cascade;
drop function if exists f_trig_2_2() cascade;
drop function if exists f_trig_2_3() cascade;