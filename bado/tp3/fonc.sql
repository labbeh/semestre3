-- q4
drop function if exists f_verif_prod() cascade;

create or replace function f_verif_prod() returns trigger as
	$$
		declare
			ligne produit%ROWTYPE;
		begin
			for ligne in select * from produit
			loop
				if(new.lib=ligne.lib and new.coul=ligne.coul) then
					raise exception 'ERREUR: produit similaire déjà dans la base';
				end if;
			end loop;

			if(not found) then
				insert into produit values(new.np, new.lib, new.coul, new.qs);
			end if;

			return new;
		end
	$$language plpgsql;

drop trigger if exists verif_prod on produit;

create trigger verif_prod before insert on produit for each row execute procedure f_verif_prod();

-- q5

drop function if exists f_trig_maj_stock() cascade;

create or replace function f_trig_maj_stock() returns trigger as
	$$
	declare
		stock produit.qs%type;
		quantite_achat   achat.qa%type;
	begin
		raise notice 'execution du trigger %', TG_NAME;
		if(TG_OP = 'INSERT') then
			raise notice'insertion';
			quantite_achat = new.qa;
		else if(TG_OP = 'UPDATE') then
			raise notice 'mise à jours';
			quantite_achat = new.qa-old.qa;
		end if;
		select qs into stock from produit where np=new.np;
		if(quantite_achat > stock) or (stock is null) then
			raise exception 'stock insuffisant';
		else
			update produit set qs=qs_quantite_achat;
		end if;

		return new;
	end
	$$language plpgsql;
