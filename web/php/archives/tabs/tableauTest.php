<?php

	$tab1 = array(12, 17, 21);

	$tab2[0] = 12;
	$tab2[1] = 17;
	$tab2[2] = 21;

	$tab3[] = 12;
	$tab3[] = 17;
	$tab3[] = 21;
	
	echo "<h3>Affichage avec print_r</h3>";

	echo "tab1 : ";
	print_r ($tab1);

	echo "<p>tab2 : ";
	print_r ($tab2);

	echo "<p>tab3 : ";
	print_r ($tab3);
?>