<?php
//Attention : si on utilise renderBlock au lieu de render, seul le bloc
//va figurer dans la page HTML générée

ini_set('display_errors', 1);
error_reporting(E_ALL);

//pour pouvoir utiliser le loader de twig
require_once( "../../Twig/lib/Twig/Autoloader.php" );


Twig_Autoloader::register();
//On indique que les templates seront chargés depuis ./tpl/ 
$twig = new Twig_Environment( new Twig_Loader_Filesystem("./tpl"));

// Chargement du template template.tpl
$tpl = $twig->loadTemplate( "template3+.tpl" );
//génération d'une vue à partir du template
//echo $tpl->renderBlock('title', array("titlepart"=>"Blocs Twig"));
echo $tpl->renderBlock('content', array("contenu" => "Contenu de ma page Twig"));
//echo $tpl->renderBlock('footer', array("nom"=>"G. Simon", "annee" => "2018"));

?>
