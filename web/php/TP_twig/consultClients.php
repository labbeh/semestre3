<?php
require ("../sql/DB.inc.php");
// template avec une variable simple

ini_set('display_errors', 1);
error_reporting(E_ALL);

// pour pouvoir utiliser le loader de twig
require_once( "../Twig/lib/Twig/Autoloader.php" );


Twig_Autoloader::register();
// On indique que les templates seront chargés depuis ./tpl/ 
$twig = new Twig_Environment( new Twig_Loader_Filesystem("./tpl"));

// Chargement du template template.tpl
$tpl = $twig->loadTemplate( "templateConsultClients.tpl" );
$tplErr = $twig->loadTemplate("templateErreur.tpl");

// création du tableau de client
$db = DB::getInstance();
//$connStr = 'pgsql:host=localhost port=5432 dbname=lh150094'; 
//$pdo = new PDO($connStr, 'lh150094', 'phppasswd');

try{
	$tabCli = $db->getClients();
	// génération d'une vue à partir du template
	if($tabCli == array()) echo $tplErr->render(array("msgerr" => "EREEUR: Il n'y a aucun tuple dans la base !"));
	else 				   echo $tpl   ->render(array("titrecentre" => "Liste des clients", "items" => $tabCli ));
	//echo $tp1->render(array("items" => $tabCli));
}
catch(PDOException $evt){
	echo $tplErr->render(array("msgerr" => "EREEUR: Table introuvable dans la base !"));
}

?>