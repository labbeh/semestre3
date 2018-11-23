<?php
//template avec une variable de type tableau

ini_set('display_errors', 1);
error_reporting(E_ALL);

require_once( "../../Twig/lib/Twig/Autoloader.php" );

Twig_Autoloader::register();
$twig = new Twig_Environment( new Twig_Loader_Filesystem("./tpl"));

$tpl = $twig->loadTemplate( "template2.tpl" );

$items = array("ch1","ch2","ch3","ch4");
echo $tpl->render( array("items"=>$items) );
?>
