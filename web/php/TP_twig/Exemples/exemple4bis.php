<?php
//template4 : template de base avec des blocs
//template4bis : template4bis hérite de template4 et re-définit certains blocs
//NB : template4bis ne contient pas de variable

ini_set('display_errors', 1);
error_reporting(E_ALL);

//pour pouvoir utiliser le loader de twig
require_once( "../../Twig/lib/Twig/Autoloader.php" );


Twig_Autoloader::register();
//On indique que les templates seront chargés depuis ./tpl/ 
$twig = new Twig_Environment( new Twig_Loader_Filesystem("./tpl"));

// Chargement du template template.tpl
$tpl = $twig->loadTemplate( "template4bis.tpl" );
//génération d'une vue à partir du template
echo $tpl->render(array("Bienvenu"));
?>
