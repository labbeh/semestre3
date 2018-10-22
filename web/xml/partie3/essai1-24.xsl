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
				
				

			</body>
		</html>
	</xsl:template>

	<xsl:template match="jeu">
		<b><xsl:value-of select="titre" /></b>
		<br /><br />
		édité par:<br />
			<wsl:apply-templates select="edite_par" />
		<br /><hr /><br />

	</sxl:template>

	<xsl:template match="edite_par">
		&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
		<xsl:apply-templates select ="key('clef_editeur', @lien_editeur)" /> <br />
	</xsl:template>

	<xsl:key name="clef_editeur" match="editeur" use="@id" />

	<xsl:template match="editeur">
		</xsl:value-of select="." />
	</xsl:template>

	<xsl:template match="jeu" >
		<img src="./images_data/couvertures/{@couv}.gif" /> <!-- entre acolades = ce sui doit être interprété par xsl -->
	</xsl:template>

</xsl:stylesheet>