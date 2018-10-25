<?php
/*
 * Exécution d'un update + récupération du nombre de lignes modifiées
 */

// Connexion base de données
$connStr = 'pgsql:host=woody port=5432 dbname=lh150094'; 
try {
$pdo = new PDO($connStr, 'lh150094', 'phppasswd'); //Instancie la connexion
echo "connexion reussie\n";
// Configuration facultative de la connexion
$pdo->setAttribute(PDO::ATTR_CASE, PDO::CASE_LOWER); // les noms de champs seront en caractères minuscules
$pdo->setAttribute(PDO::ATTR_ERRMODE , PDO::ERRMODE_EXCEPTION); // les erreurs lanceront des exceptions

$requete = "update pac_client set adr='Idf' where adr = 'PARIS'";
$stmt = $pdo->prepare($requete);
$res = $stmt->execute(); //execution du update
if ($res) {
   echo "maj reussie<br/>\n";
}
else {
     echo "erreur : pas de maj! <br/>\n";
}

echo "Nombre de ligne mises &agrave; jour = ".$stmt->rowCount();
}
catch (PDOException $e) {
      echo "probleme de connexion :".$e->getMessage();    
}

$pdo = null; //fermeture de la connexion

?>
