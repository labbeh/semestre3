package algopars;
import java.util.Set;

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
	
	/**
	 * Constructeur du controleur passerel avec l'ihm
	 * @param code instance de Code
	 * */
	public Controleur( Code code ) {
		if(System.getProperty("os.name").equalsIgnoreCase("Windows"))
			this.ihm = new IHMCuiWindows(this);
		else
			this.ihm = new IHMCuiUnix(this);
		
		this.code  = code ;
		this.inter = new Interpreteur(code, this);
	}
	
	
	
	public void lancer(){
		ihm.normal();
		afficher(code.afficherPseudoCode());
		
		//this.inter.lireCode();
		
		choixTracageVariables();
		
		afficher(code.afficherDonnees());
		
		// mode pas a pas
		for(int i=0; i<code.getNbLig(); i++){
			afficher(code.afficherPseudoCode());
			afficher(code.afficherDonnees());
			
			
			
			inter.faireLigne();
			code.getNumSvt();
			
			
			lireClavier();
			
			ihm.nettoyer();
			inter.incIndex();
			
			
			
			//i = code.getIligUtil();
		}
		
		ihm.normal();
		
		
	}
	
	private void choixTracageVariables() {
		afficher("Quelles variables souhaitez vous tracer ?");
		
		Set<String> vars = code.variables.keySet();
		boolean saisie = false;
		
		for(String nomVar: vars){
			ihm.print(nomVar +": " );
			saisie = ihm.lireBoolean();
			
			code.variables.get(nomVar).setTracage(saisie);
		}
	}

	/**
	 * Envoie sur l'ihm console une chaine de caractère à afficher
	 * avec retour ligne
	 * */
	public void afficher(String str){
		ihm.println(str);
	}
	
	public String lireClavier(){
		return ihm.lireClavier();
	}
	
	/**
	 * Incrémente le numéro de ligne en cours dans l'instance de Code
	 * */
	public void incNumLig(){
		code.incNumLig();
	}
	
	/**
	 * Défini le numéro de ligne courante dans l'instance de Code
	 * */
	public void setNumLig(int numLig){
		code.setNumLig(numLig);
	}
	
	/**
	 * Retourne la valeur de la ligne utile à l'indice i dans la liste
	 * de numéros de lignes utiles
	 * @param index indice dans la liste entier
	 * @return entier numéro de ligne utile à l'indice i
	 * */
	public int getNumLigUtilAt(int index){
		return code.getNumLigUtilAt(index);
	}

}
