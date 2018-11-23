<?php
//template avec une variable de type tableau d'objets

ini_set('display_errors', 1);
error_reporting(E_ALL);

require_once( "../../Twig/lib/Twig/Autoloader.php" );
include "./Beans/client.inc.php";

Twig_Autoloader::register();
$twig = new Twig_Environment( new Twig_Loader_Filesystem("./tpl"));

$tpl = $twig->loadTemplate( "template3.tpl" );

$client1 = new Client(5,'Dupont1','Le Havre');
$client2 = new Client(6,'Dupont2','Le Havre');
$client3 = new Client(7,'Dupont3','Le Havre');

$items = array($client1,$client2,$client3);
echo $tpl->render( array("items"=>$items) );
?>
