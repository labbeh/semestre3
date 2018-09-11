-- 1. Ecrire   une   requête   qui   renvoie   le   nombre   total   de   connexions   effectuées   sur   le   serveur
-- «corton.iut.univ­lehavre.fr».
select count(*) from connexion join serveur on connexion.ids=serveur.ids where nom='corton.iut.univ-lehavre.fr';

--2. Ecrire   une   requête   renvoyant   l'identifiant   de   l'utilisateur   ayant   fait   la   connexion   la   pluslongue le 01/09/2017 (ie entre le 01/09/2017 00:00:00 et le 01/09/2017 23:59:59).
select idu from connexion where datec <'2017/09/01 00:00:00' and datec < '2017/09/01 23:59:59'
															 and duree = (select max(duree) from connexion where datec <'2017/09/01  00:00:00' and datec < '2017/09/01 23:59:59');

-- 3. Ecrire une requête renvoyant pour chaque utilisateur le nombre de connexions effectuées sur le serveur corton  le 01/09/2017. 
select idu, count(*) from connexion join serveur on connexion.ids=serveur.ids where nom='corton.iut.univ-lehavre.fr'
	and datec <'2017/09/01 00:00:00' and datec < '2017/09/01 23:59:59'
	group by connexion.idu;


-- partie 2
create or replace function supprimerServeur(un_ids serveur.ids%Type) returns void as $$
	declare
		un_nom serveur.nom%type;
	begin
		select nom into un_nom from serveur where ids=un_ids;
	if(truc exeption ) notice quelque chose;
	end
	$$language plpgsql

create or replace function listeServeurs(une_date timestamp) returns setof serveur.nom%type as $$
	declare
		serveur.nom%type;
	begin
		return query select nom from serveur join connexion on serveur.ids=connexion.ids
		where datec>une_date;
	end
	$$language plpgsql
