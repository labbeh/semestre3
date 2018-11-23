package diagramme;

/**
 * Énumération de toutes les couleurs.
 * Chacune ayant son identifiant HTML et sa suivante.
 * Huit couleurs ont été choisies, soit un peu plus que l'empan mnésique
 * orange";
 * BLEU = deepskyblue, 00BFFF
 * JAUNEVERT = greenyellow, ADFF2F
 * Anciennes couleurs non conservées :
 * Bleu royal	royalblue	#4169E1
 * Rose brumeux	mistyrose	#FFE4E1
 * Amande	blanchedalmond	#FFEBCD
 * @author Frédéric Serin, Université Le Havre Normandie
 * @version 2017-12-29, évolution de 2017-06-17
 */
public enum Couleur {

/**
 * Suite des huit couleurs définies.
 */
MAGENTA("darkmagenta"), // Magenta foncé, 8B008B
JAUNE("lightyellow",MAGENTA), // jaune clair
VERT("forestgreen",JAUNE), // vert forêt, foncé
GRIS("lightgrey",VERT), // gris clair
ROUGE("darkred",GRIS), // rouge foncé
JAUNEVERT("greenyellow",ROUGE),
BLEU("deepskyblue",JAUNEVERT),
ORANGE("orange",BLEU);

/**
 * Nom correspondant au code HTMl utilisé
 */
	String nom;

/**
 * Chaque couleur possède une suivante sauf MAGENTA
 */
	Couleur suivante;

/**
 * Construit une couleur avec son nom
 * @param nomCouleur nom générique HTML
 */
	Couleur(String nomCouleur) {
		nom = nomCouleur;
	} // fin constructeur de chaque élément couleur

/**
 * Construit une couleur avec son nom
 * @param nomCouleur nom générique HTML
 * @param suivante couleur suivante dans l'ordre de parcours
 */
	Couleur(String nomCouleur, Couleur suivante) {
		this(nomCouleur);
		this.suivante = suivante;
	} // fin constructeur de chaque élément couleur sauf jaune

/**
 * Retourne la couleur suivante
 * @return couleur suivante
 */
	public Couleur getSuivante() {
		if (suivante!=null)
			return suivante;
		else // cycle retour à la 1ère couleur
			return ORANGE;
	}

/**
 * Fourni le nom standard de la couleur en HTML
 * @return nom identifiant HTML
 */
	public String toString() {
		return nom;
	}

} // fin énumération des couleurs