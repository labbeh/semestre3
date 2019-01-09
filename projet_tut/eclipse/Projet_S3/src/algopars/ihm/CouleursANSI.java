package algopars.ihm;

/**
 * @author Hugo Labbe, Titouan Cornilleau, Clement Baron, Sebastien Mande,Loan Cadorel 
 * @version 2019-01-08, 2.0
 * */

public enum CouleursANSI {
	
	NOIR("\033[30m", "\033[40m"), 
	ROUGE("\033[31m", "\033[41m"), 
	VERT("\033[32m", "\033[42m"), 
	JAUNE("\033[33m", "\033[43m"), 
	BLEU("\033[34m", "\033[44m"), 
	MAUVE("\033[35m", "\033[45m"), 
	CYAN("\033[36m", "\033[46m"), 
	BLANC("\033[37m", "\033[47m"),
	NORMAL("\033[0m", "\033[0m");
	  
	String couleurTexte;
	String couleurFond;
	 
	/**
	 * Constructeur pour initialiser la sequence ANSI du texte et du fond
	 * @param couleurTexte sequence ANSI pour la couleur du texte
	 * @param couleurFond sequence ANSI pour la couleur de fond
	 * */
	private CouleursANSI(String couleurTexte, String couleurFond) {
		this.couleurTexte = couleurTexte;
	    this.couleurFond  = couleurFond;
	}
  
	/**
	 * permet d'obtenir la couleur du texte
	 * @return String couleurTexte
	 * */
	public String getCouleurTexte() {
		return couleurTexte;
	}
	  /**
	   * permet d'obtenir la couleur du fond
	   * @return String couleurFond
	   * */
	public String getCouleurFond(){
		return couleurFond;
	}
}
