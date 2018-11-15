<?php
	$cels= -50;
	$far = -58;

	print("<h1>" .'Celsius	Fahrenheit'."</h1>");
	print("\n");

	for($cpt=0; $cpt<21; $cpt++)
	{
		print($cels);
		print("\t");
		print($far);
		print("<br />" ."\n");

		$cels += 5;
		$far  += 9;
	}
?>