<!DOCTYPE html>
<head>
  <meta charset="utf-8">
  <title>Exemple de formulaire avec des listes</title>
</head>

<body>
<form action="echo.php" method="get">

<p>
Jour de r&eacute;ception demand&eacute;e :
<select name="jourOK">
<option value="lundi">Lundi</option>
<option value="mardi">Mardi</option>
<option value="mercredi">Mercredi</option>
<option value="jeudi">Jeudi</option>
<option value="vendredi">Vendredi</option>
<option value="samedi">Samedi</option>
<option value="dimanche">Dimanche</option>
<option calue="jours_iconnnu">autre jours</option>
</select>
</p>

<p>Jours impossibles : <br/>
<select name="jourImpossible[]"  multiple>
<option value="lundi">Lundi</option>
<option value="mardi">Mardi</option>
<option value="mercredi">Mercredi</option>
<option value="jeudi">Jeudi</option>
<option value="vendredi">Vendredi</option>
<option value="samedi">Samedi</option>
<option value="dimanche">Dimanche</option>
</select>
</p>

<input type="submit" value="Envoyer">
</form>
</body>

</html>