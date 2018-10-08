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
				
				<xsl:apply-templates select="stock/auteur" />
				<xsl:apply-templates select="stock/auteur/jeu" />

			</body>
		</html>
	</xsl:template>

	<xsl:template match="jeu">
		<xsl:value-of select="titre" /> <br />
		<br />
	</xsl:template>

	<xsl:template match="auteur">
		<xsl:value-of select="@nom" /> <br />
	</xsl:template>

</xsl:stylesheet>