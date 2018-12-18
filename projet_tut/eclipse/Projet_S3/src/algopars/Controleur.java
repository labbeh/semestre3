package algopars;
import algopars.ihm.*;
import algopars.metier.*;

/**
 * Classe controleur faisant la passerelle enre la partie métier et l'ihm
 * @author hugo labbé
 * @version 1.0, 2018-12-18
 * */

public class Controleur {
	
	/**
	 * Instance d'une IHMCui
	 * */
	private IHMCui ihm;
	
	/**
	 * Insantance de Code
	 * */
	private Code code;
	
	/**
	 * Instance de Interpreteur
	 * */
	private Interpreteur inter;
	
	public Controleur( Code code, Interpreteur inter ) {
		if(System.getProperty("os.name").equalsIgnoreCase("Windows"))
			this.ihm = new IHMCuiWindows(this);
		else
			this.ihm = new IHMCuiUnix(this);
		
		this.code  = code ;
		this.inter = inter;
	}
	
	
	
	public void lancer(){
		this.inter.lireCode();
		afficher(code.afficherPseudoCode());
		afficher(code.afficherDonnees());
	}
	
	public void afficher(String str){
		ihm.println(str);
	}

}
