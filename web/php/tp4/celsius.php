<?php
  include "fctAux.inc.php";

// appel des fonctions pour creer une page html

  enTete();   // fonction declaree dans fctAux
  contenu();  // fonction a definir ci-dessous
  pied();     // fonction declaree dans fctAux 

function contenu()
{
  // reprendre l'exercice sur Celsius
	$cels= -50;
	$far = -58;

	print("<h1>" .'Correspondance Celsius	Fahrenheit'."</h1>");
	print("\n");

	print("\t". "<table>" ."\n");
	print("\t\t". "<thead>" ."\n");

	print("\t\t" ."<tr>". "\n");
		print("\t\t". "<th>" ."Celsius". "</th>" ."\n");
		print("\t\t". "<th>" ."Fahrenheit". "</th>" ."\n");
	print("\t\t" ."</tr>". "\n");

	print("\t\t". "</thead>" ."\n");

	for($cpt=0; $cpt<21; $cpt++)
	{
		print("\t\t". "<tr class=\"bleu_fonc\">" ."\n");

		print("\t\t\t". "<td>");
		print($cels);
		print("\t\t". "</td>" ."\n");
		//print("\t");
		print("\t\t\t". "<td>");
		print($far);
		print("\t\t". "</td>" ."\n");
		//print("<br />" ."\n");
		print("\t\t". "</tr>" ."\n");

		$cels += 5;
		$far  += 9;
	}

	print("\t". "</table>" ."\n");
}
?>