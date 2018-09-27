<html>

 <head>

  <title>Formulaire serveur echo</title>

 </head>

 <body>
 	<h1>Avec la méthode get: </h1>
 	<form id="avec_get" action="echo.php" method="GET">
 		<input type="text" name="contenu_formulaire" value="">
 		<input type="submit" name="submit" value="Avec GET" form="avec_get">
 	</form>

 	<h1>Avec la méthode post: </h1>
 	<form id="avec_post" action="echo.php" method="POST">
 		<input type="text" name="contenu_formulaire" placeholder="texte par défaut">
 		<input type="submit" name="submit" value="Avec POST" form="avec_post">
 	</form>

 	<form action="echo.php">

    <fieldset>
        <legend>Nom</legend>
        <input name="test_par_defaut">
    </fieldset>

</form>

<form action="echo.php" method="post">

    <fieldset>
        <legend>Nom</legend>
        <input name="test_post">
    </fieldset>

    <fieldset>
		<legend>Mot de passe</legend>
		<input type="password">
	</fieldsset>

	<input type="submit" name="submit" value="Valider">

</form>

<form action="echo.php">

	<fieldset>
		<input name="nom" placeholder="Nom"> /
		<input name="prenom"  placeholder="Prénom">
	</fieldset>

</form>


<form action="echo.php" method="get">
Prénom <input name="prenom" ><br>
Nom <input name="nom" ><br>
Pays <input name="test" value="France"><br>
Mot de passe <input type="password" name="test" ><br>
<input type="submit" value="Envoyer">
<input type="submit" value="Bouton2" name="Bouton2">
<input type="reset" value="Reinit">
</form>

 </body>

</html>