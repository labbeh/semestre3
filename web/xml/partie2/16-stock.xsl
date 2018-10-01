<?xml version="1.0" encoding="iso-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- Modèle pour la Racine -->
	<xsl:template match="/">

		<html>
			<head>
				<meta http-equiv="content-type" content="text/html;charset=ISO-8859-15"/>
				<link rel="stylesheet" type="text/css" href="style.css" />
			</head>

			<body>
				
				<!-- <xsl:value-of select="stock/auteur/jeu/titre" /> ne récupère que la première balise titre du premier jue du premier auteur -->
				<table>
					<xsl:for-each select="stock/auteur">
						<tr>
							<td>
								<xsl:value-of select="@prenom" /> &#160;
								<xsl:value-of select="@nom" />
							</td>
						</tr>

						<xsl:for-each select="jeu">
							<tr>
								<td><xsl:value-of select="titre" /></td>
								<td><xsl:value-of select="annee" /></td>
							</tr>
						</xsl:for-each>

					</xsl:for-each>
				</table>

			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>