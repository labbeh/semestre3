<?xml version="1.0" encoding="iso-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- Modèle pour la Racine -->
	<xsl:template match="/">

		<html>
			<head>

				<meta http-equiv="content-type" content="text/html;charset=ISO-8859-15"/>
			</head>

			<body bgcolor="#EEEEEE">
				
				<!-- <xsl:value-of select="stock/auteur/jeu/titre" /> ne récupère que la première balise titre du premier jue du premier auteur -->
				<xsl:for-each select="stock/auteur/jeu">
						<xsl:value-of select="titre" />
						<xsl:value-of select="../@nom" /> <br />

				</xsl:for-each>

			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>