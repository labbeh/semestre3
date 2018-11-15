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

// création du tableau de client
$db = DB::getInstance();


// génération d'une vue à partir du template
echo $tpl->render(array("titrecentre" => "Liste des achats"));
//echo $tp1->render(array("items" => $tabCli));

?>