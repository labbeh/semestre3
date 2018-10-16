begin transaction;

insert into pac_achat values(105, 50, 400);
update pac_produit set qs = qs-50 where np = 50;

commit transaction;
