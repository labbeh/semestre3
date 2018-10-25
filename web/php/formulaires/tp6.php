<?php   // formEtAffiche.php

	//verif();
  //if (empty($_REQUEST['login']) || empty($_REQUEST['passwd']))
  //{
	$valider = false;

	 if($_SERVER['REQUEST_METHOD']=='POST')
	{
	   	verif();
	}
	else
	{
		afficherFormulaire();
	}

	function afficherFormulaire()
	{
	    echo "<form action=\"tp6.php\" method=\"post\">\n";
	    echo "Login: <input name=\"login\">\n";
	    echo "Mot de passe: <input name=\"passwd\">\n";
	    echo "<input type=\"submit\" name=\"btnValider\" value=\"Envoyer\">";
	    echo "</form>\n";
	}
  //} 

   function verif()
   {
   		$login  = $_REQUEST['login' ];
   		$passwd = $_REQUEST['passwd'];

   		if(empty($_REQUEST['login'] && $_REQUEST['passwd'] ))
   		{
   			echo "pas de login ou mdp";
   		}

   		else
   		{
   			$valider = true;

   			if($login  == 'admin')
   				echo "connecter en tant qu'admin";

   			else
   				echo "bonjour $login !";
   		}
   }
?>