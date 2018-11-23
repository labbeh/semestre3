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
$tpl = $twig->loadTemplate( "templateConsultProduits.tpl" );
$tplErr = $twig->loadTemplate("templateErreur.tpl");

$db = DB::getInstance();

try{
	$tabProduits = $db->getProduits();
	// génération d'une vue à partir du template
	if($tabProduits == array()) echo $tplErr->render(array("msgerr" => "EREEUR: Il n'y a aucun tuple dans la base !"));
	else 				   echo $tpl   ->render(array("titrecentre" => "Liste des achats", "items" => $tabProduits ));
}
catch(PDOException $evt){
	echo $tplErr->render(array("msgerr" => "EREEUR: Table introuvable dans la base !"));
}

?>