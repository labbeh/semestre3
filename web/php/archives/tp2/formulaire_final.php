<!DOCTYPE html>
<html>
	<head>
  		<meta charset="utf-8">
  		<title>Formulaire exercice 4</title>
	</head>

	<body>

		<form action="echo.php" method="post">
		
		<fieldset>

			<table>
				<tr>
					<td align="center"><input type="radio" name="genre" value="Mme"> Mme</td>
					<td align="center"><input type="radio" name="genre" value="Mme"> Mme</td>
					<td align="center"><input type="radio" name="genre" value="M"> M</td>
				</tr>

				<tr>
					<td align="right">Nom: </td>
					<td> <input type="text" name="nom" value=""></td>
				</tr>
				<tr>
					<td align="right">Mot de passe: </td>
					<td> <input type="password" name="mdp"> </td>
				</tr>

				<tr>
					<td align="center"><input type="checkbox" name="document" value="Doc1"> Document 1</td>
					<td align="center"><input type="checkbox" name="document" value="Doc2"> Document 2</td>
					<td align="center"><input type="checkbox" name="document" value="Doc3"> Document 3</td>
				</tr>

				<tr>
					<td align="right">Pays: </td>
					<td>
						<select name="pays">
							<option value="suisse">Suisse</option>
							<option value="autre">Autre</option>
						</select>
					</td>
				</tr>
			
				<tr>
					<td align="right"><input type="submit" name="envoyer" value="Envoyer"></td>
					<td><input type="reset"  name="effacer" value="Effacer"></td>
				</tr>
			</table>

		</fieldset>


		</form>

	</body>

</html>