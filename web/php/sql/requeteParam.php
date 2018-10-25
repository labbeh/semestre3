<?php

/*
 * Execution d'une requete parametree, test du nombre de tuples retournes et recuperation
   du resultat en unefois avec fetchAll. 
   NB : fetchAll charge en une seule fois en memoire le resultat de la requete ce qui peut poser probleme
   si la requete renvoie beaucoup de tuples.
 */

// Connexion base de donnees
$connStr = 'pgsql:host=woody port=5432 dbname=lh150094';

try {
      $pdo = new PDO($connStr, 'lh150094', 'phppasswd'); //Instancie la connexion
      // Configuration facultative de la connexion
      $pdo->setAttribute(PDO::ATTR_CASE, PDO::CASE_LOWER); // les noms de champs seront en caracteres minuscules
      $pdo->setAttribute(PDO::ATTR_ERRMODE , PDO::ERRMODE_EXCEPTION); // les erreurs lanceront des exceptions

      //on prepare la requete
      $requete = "select * from pac_client where nom = ? or adr = ?"; //un parametre dans la requete
      $stmt = $pdo->prepare($requete);

      //on execute la requete
      $stmt->execute(array('HUSSENVRAC', 'PARIS')); //le tableau passe en parametre de execute permet de donner une valeur
            			  	    //a chaque parametre de la requete
      //$stmt->execute(array('Marseille')); //pour tester une requete qui ne renvoie aucun tuple

      //on recupere en une fois l'ensemble des tuples renvoyes par la requete
      $result = $stmt->fetchAll(PDO::FETCH_ASSOC); //les index du tableau retourne vont avoir le meme nom que
      	  				    //les attributs SQL

      //calcul du nombre tuples retournes
      $nbtuples = count($result);
      echo "nombre de tuples retournes = ".$nbtuples."<br/>\n"; //calcul du nombre de tuples retournes
      echo "nombre de tuples retournes avec rowcount = ".$stmt->rowCount()."<br/>\n";

      //test pour savoir si au moins un tuple a ete retourne
      if (! empty($result)) { echo "Au moins un tuple retourn&eacute; ! <br>\n"; }
      else { echo "Aucun tuple retourn&eacute; ! <br>\n"; }

      //on parcourt le tableau contenant les tuples renvoyes par la requete
      echo "------- Resultat --------<br/>\n";
      foreach ($result as $tuple) {
          foreach ($tuple as $key => $value) {
          	echo "$key:$value ";
          }
          echo "<br/>\n";
      }

}
catch (PDOException $e) {
      echo "probleme de connexion :".$e->getMessage();    
}

$pdo = null; //fermeture de la connexion

?>