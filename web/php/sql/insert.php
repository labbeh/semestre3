<?php

// Connexion base de données
$connStr = 'pgsql:host=localhost port=5432 dbname=lh150094'; 
try {
$pdo = new PDO($connStr, 'lh150094', 'phppasswd'); //Instancie la connexion
// Configuration facultative de la connexion
$pdo->setAttribute(PDO::ATTR_CASE, PDO::CASE_LOWER); // les noms de champs seront en caractères minuscules
$pdo->setAttribute(PDO::ATTR_ERRMODE , PDO::ERRMODE_EXCEPTION); // les erreurs lanceront des exceptions

$requete = "insert into pac_client values(?,?,?)";
$stmt = $pdo->prepare($requete);
//$res = $stmt->execute(array(201,"Badinter","Paris")); //insertion d'un tuple
$res = $stmt->execute(array(203,"Dumas",NULL)); //insertion d'un tuple avec des valeurs NULL
if ($res) {
   echo "insertion reussie<br/>\n";
}
else {
     echo "erreur : pas d'insertion!<br/>\n";
}

}
catch (PDOException $e) {
      echo "probleme de connexion :".$e->getMessage();    
}

$pdo = null; //fermeture de la connexion

?>
