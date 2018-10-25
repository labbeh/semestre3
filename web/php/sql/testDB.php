<html>
<?php
  require ("DB.inc.php");

  $db = DB::getInstance();
  if ($db == null) {
   echo "Impossible de se connecter &agrave; la base de donn&eacute;es !";
  }
  else {
        try {
	    $nb = $db->deleteClient(300);
	    echo "nombre de tuples supprim&eacute;s = ".$nb."<br/>\n";  
	    //test de requÃªte ne renvoyant aucun rÃ©sultat
	    $t = $db->getClient(300);
	    if (count($t) == 0) { echo "Il n'y a plus de client d'identifiant 300 ! <br/>\n"; }
	} //fin try
	catch (Exception $e) {
	      echo $e->getMessage();
	}  
	$db->close();
  } //fin du else connexion reussie
?>

</html>