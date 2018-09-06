-- 1. Ecrire   une   requête   qui   renvoie   le   nombre   total   de   connexions   effectuées   sur   le   serveur
-- «corton.iut.univ­lehavre.fr».
select count(*) from connexion join serveur on connexion.ids=serveur.ids where nom='corton.iut.univ-lehavre.fr';

--2. Ecrire   une   requête   renvoyant   l'identifiant   de   l'utilisateur   ayant   fait   la   connexion   la   pluslongue le 01/09/2017 (ie entre le 01/09/2017 00:00:00 et le 01/09/2017 23:59:59).
select idu from connexion where datec <'2017/09/01 00:00:00' and datec < '2017/09/01 23:59:59'
															 and duree = (select max(duree) from connexion where datec <'2017/09/01 																		  00:00:00' and datec < '2017/09/01 23:59:59'
															 			 );

-- 3. Ecrire une requête renvoyant pour chaque utilisateur le nombre de connexions effectuées sur le serveur corton  le 01/09/2017. 
select idu, count(*) from connexion join serveur on connexion.ids=serveur.ids where nom='corton.iut.univ-lehavre.fr'
	and datec <'2017/09/01 00:00:00' and datec < '2017/09/01 23:59:59'
	group by connexion.idu;
