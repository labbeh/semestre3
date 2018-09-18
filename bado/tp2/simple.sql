drop function if exists ftrig1() cascade;

create or replace function ftrig1() returns trigger as $$
	begin
		raise notice 'execution du trigger trig1';
		return null;
	end
	$$language plpgsql;

drop trigger if exists trig1 on achat;

create trigger trig1 after update on achat for each statement execute procedure ftrig1();

/* trig2 */
create or replace function ftrig2() returns trigger as $$
	declare
		nb_tuples bigint;
	begin
		select count(*) into nb_tuples from pac_achat;
		raise notice 'il y a maintenant % tuples dans la table pac_achat', nb_tuples;
		return null;
	end
	$$language plpgsql;

create trigger trig2 after insert on pac_achat for each statement execute procedure ftrig2();


/* trig2_bis */
create or replace function f_trig2bis() returns trigger as $$
	declare
		nb_tuples bigint;
	begin
		select count(*) into nb_tuples from pac_achat;
		if(nb_tuples > 33) then raise exception 'quota achats dépassé'; -- donc jusqu'à 34 tuples
		else 					raise notice 'il y a % tuples dans la table', nb_tuples;
		end if;
	return null;
	end
	$$language plpgsql

create trigger trig2bis before insert on pac_achat for each statement execute procedure f_trig2bis();

/* trig3 */
create or replace function f_trig3() returns trigger as $$
	begin
		raise notice 'execution du trigger %', TG_NAME;
		raise notice 'l operation % est effectuée sur la table % en mode %', TG_OP, TG_TABLE_NAME, TG_WHEN;
		return null;
	end
	$$language plpgsql;

create trigger trig3 after insert or update or delete on produit for each instruction execute procedure f_trig3();

/*  */