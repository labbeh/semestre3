<?php   // formEtAffiche.php

  if (empty($_REQUEST['nom'])) {
    echo "<form action=\"formEtAffiche.php\" method=\"get\">\n";
    echo "Saisir un nom <input name=\"nom\">\n";
    echo "</form>\n";
  } else  {
    $nom=$_REQUEST['nom'];	
    echo "<h2>AFFICHE</h2>";
    echo "Le nom est $nom";
  }
?>