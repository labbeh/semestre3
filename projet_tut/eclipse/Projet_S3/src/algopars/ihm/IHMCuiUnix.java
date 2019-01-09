package algopars.ihm;

/**
 * Classe fille de IHMCui pour les sytemes UNiX/Linux
 * @author Hugo Labbe, Titouan Cornilleau, Clement Baron, Sebastien Mande,Loan Cadorel 
 * @version 1.0, 2018-12-18
 * */

public class IHMCuiUnix extends IHMCui {
	
	/**
	 * Constructeur par defaut, appel celui de la classe mere
	 * */
	public IHMCuiUnix() {
		super();
	}
	
	/**
	 * Définit la methode abstraite print pour l'affichage d'un String
	 * sur la sortie standart
	 * */
	@Override
	public void print(String str) {
		stdout.print(str);
	}

}
