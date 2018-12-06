/**
 * @author hugo labbé D2
 * @version 2018-12-06
 * */

public class ArbreBinaire{

	/**
	* Arbre droit de l'arbre courant
	* */
	private ArbreBinaire droit;

	/**
	* Arbre gauche de l'arbre courant
	* */
	private ArbreBinaire gauche;

	/**
	* Entier hauteur de l'arbre
	* */
	private int hauteur;

	/**
	* String étiquette de l'arbre
	* */
	private String etiquette;

	/**
	* fréquence de l'abre sous forme réelle
	* */
	private double frequence;

	public ArbreBinaire( String etiquette ){
		this.etiquette = etiquette;
	}

	/* ACCESSEURS */

	/**
	* Retourne le fils gauche
	* @return un ArbreBinaire ou null si l'abre courant n'a pas de fils gauche
	* */
	public ArbreBinaire getSag(){
		return gauche;
	}

	/**
	* Retourne le fils droit
	* @return un ArbreBinaire ou null si l'abre courant n'a pas de fils droit
	* */
	public ArbreBinaire getSad(){
		return droit;
	}

	/**
	* Retourne l'étiquette de l'arbre
	* @return chaine de caractère
	* */
	public String getEtiquette(){
		return etiquette;
	}

	/**
	* Retourne la fréquence de l'arbre
	* @return un réel
	* */
	public double getFrequence(){
		return frequence;
	}

	/**
	* Permet de savoir si l'arbre est vide ou non
	* @return true si l'arbre est vide
	* */
	public boolean estVide(){
		return false;
	}

	/**
	* Permet de savoir si l'abre est une feuille, c'est à dire si l'arbre n'a aucun fils
	* @return true si l'abre est une feuille
	* */
	public boolean estFeuille(){
		return droit == null && gauche == null;
	}

	/**
	* Permet de connaitre le nombre de feuilles de l'arbre
	* @return entier nombre de feuilles
	* */
	public int nbFeuille(){
		int nbFeuilles = 0;

		if(estFeuille()) return 1;

		if(droit  != null) nbFeuilles += droit.nbFeuille();
		if(gauche != null) nbFeuilles += gauche.nbFeuille();

		return nbFeuilles;
	}

	/**
	* Permet de connaitre la hauteur de l'arbre
	* @return entier hauteur de l'arbre
	* */
	public int hauteur(){
		return hauteur;
	}

	/*****************/
	/* MODIFICATEURS */
	/*****************/

	/**
	* Permet de définir l'abre gauche de l'arbre courant
	* @param gauche un autre ArbreBinaire
	* */
	public void setSag(ArbreBinaire gauche){
		this.gauche = gauche;
	}

	/**
	* Permet de définir l'abre droit de l'arbre courant
	* @param droit un autre ArbreBinaire
	* */
	public void setSad(ArbreBinaire droit){
		this.droit = droit;
	}

	/**
	* Permet de définir l'étiquette
	* @param etiquette chaine de caractère représentant l'étiquette
	* */
	public void setEtiquette(String etiquette){
		this.etiquette = etiquette;
	}

	/**
	* Permet de définir la fréquence
	* @param frequence réel représentant la fréquence
	* */
	public void setFrequence(double frequence){
		this.frequence = frequence;
	}

	/*****************************/
	/* CONSTRUCTION DES PARCOURS */
	/*****************************/

	/**
	* Construit une chaine représentative d'un parcours préfixe de l'arbre
	* @return chaine du parcours préfixe
	* */
	public String parcoursPrefixe(){
		return null;
	}

	/**
	* Construit une chaine représentative d'un parcours infixe de l'arbre
	* @return chaine du parcours infixe
	* */
	public String parcoursInfixe(){
		return null;
	}

	/**
	* Construit une chaine représentative d'un parcours postfixe de l'arbre
	* @return chaine du parcours postfixe
	* */
	public String parcoursPostfixe(){
		return null;
	}

	/********************/
	/* MÉTHODES PRIVEES */
	/********************/
	private void calculerHauteur(){

	}

	public static void main(String[] args){
		ArbreBinaire a = new ArbreBinaire("+");

		a.setSad(new ArbreBinaire("5"));
		a.setSag(new ArbreBinaire("2"));

		a.getSad().setSad(new ArbreBinaire("3"));
		a.getSad().setSag(new ArbreBinaire("4"));

		a.getSag().setSad(new ArbreBinaire("3"));
		a.getSag().setSag(new ArbreBinaire("4"));

		System.out.println(a.nbFeuille());
	}
}