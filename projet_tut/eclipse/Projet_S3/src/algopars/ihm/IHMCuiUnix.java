package algopars.ihm;

/**
 * Classe fille de IHMCui pour les sytèmes UNiX/Linux
 * @author hugo labbé
 * @version 1.0, 2018-12-18
 * */

public class IHMCuiUnix extends IHMCui {
	
	/**
	 * Constructeur par défaut, appel celui de la classe mère
	 * */
	public IHMCuiUnix() {
		super();
	}
	
	/**
	 * Définit la méthode abstraite print pour l'affichage d'un String
	 * sur la sortie standart
	 * */
	@Override
	public void print(String str) {
		stdout.print(str);
	}

}
