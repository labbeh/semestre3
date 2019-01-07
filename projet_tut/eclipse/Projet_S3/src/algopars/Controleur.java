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
	 * Couleur du surlignage de la ligne en cours
	 * change en cas d'expression booléenne
	 * */
	private CouleursANSI couleurLigne;
	
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
		
		this.couleurLigne = CouleursANSI.BLEU;
	}
	
	
	
	public void lancer(){
		ihm.normal();
		afficher(this.afficherPseudoCode());
		
		//this.inter.lireCode();
		
		choixTracageVariables();
		
		afficher(code.afficherDonnees());
		
		// mode pas a pas
		while(inter.getIndex() < code.getNbLig()){
			code.setNumLig(inter.getIndex());
			
			inter.faireLigne();
			
			afficher(this.afficherPseudoCode());
			afficher(code.afficherDonnees()   );
			
			inter.incIndex()  ;
			ihm.lireClavier() ;
			ihm.nettoyer()    ;
		}
		
		code.setNumLig(inter.getIndex()-1);
		afficher(this.afficherPseudoCode());
		afficher(code.afficherDonnees()   );
		
		ihm.normal();
	}
	
	/**
	 * Lance la possibilité a l'utilisateur de choisir les variables
	 * à tracer
	 * */
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
		ihm.println(CouleursANSI.BLANC.getCouleurTexte()+ str);
	}
	
	/**
	 * Retourne la saisie au clavier depuis l'ihm
	 * @return un String
	 * */
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
	
	/**
	 * Génère sous forme de String le code à afficher
	 * @return un String
	 * */
	public String afficherPseudoCode() {
		String r = new String();
		modifierCouleur();
		for (int i = 0; i < code.contenuFichier.length; i++) {
			//r += CouleursANSI.NORMAL.getCouleurTexte();
			if(i == code.getNumLig()) r += couleurLigne.getCouleurFond();
			else r += CouleursANSI.NOIR.getCouleurFond();
			
			
			//if(i == code.getNumLig()) r += couleurLigne.getCouleurFond();
			r += String.format("|%2s %-50s|\n", Integer.toString(i), code.contenuFichier[i]);
			//r = r.replaceAll("ftq", CouleursANSI.BLEU.getCouleurTexte() + "ftq" + CouleursANSI.NORMAL.getCouleurTexte());
			//if(i == code.getNumLig()) r += couleurLigne.getCouleurFond();
			//r = r.replaceAll("tq", CouleursANSI.BLEU.getCouleurTexte() + "tq" +CouleursANSI.NORMAL.getCouleurTexte());
			
			//if(i == code.getNumLig()) r += CouleursANSI.NORMAL.getCouleurFond() + couleurLigne.getCouleurFond();
			r = r.replaceAll("ftq", CouleursANSI.BLEU.getCouleurTexte()    + "ftq" + CouleursANSI.BLANC.getCouleurTexte() );
			r = r.replaceAll("sinon", CouleursANSI.BLEU.getCouleurTexte()  + "sinon" + CouleursANSI.BLANC.getCouleurTexte() );
			r = r.replaceAll("fsi", CouleursANSI.BLEU.getCouleurTexte()    + "fsi" + CouleursANSI.BLANC.getCouleurTexte() );
			r = r.replaceAll("si ", CouleursANSI.BLEU.getCouleurTexte()    + "si " + CouleursANSI.BLANC.getCouleurTexte() );
			r = r.replaceAll("tq", CouleursANSI.BLEU.getCouleurTexte() 	   + "tq" + CouleursANSI.BLANC.getCouleurTexte() );
			r = r.replaceAll("ecrire", CouleursANSI.BLEU.getCouleurTexte() + "ecrire" + CouleursANSI.BLANC.getCouleurTexte() );
			r = r.replaceAll("lire", CouleursANSI.BLEU.getCouleurTexte()   + "lire" + CouleursANSI.BLANC.getCouleurTexte() );
			r = r.replaceAll("faire", CouleursANSI.BLEU.getCouleurTexte()  + "faire" + CouleursANSI.BLANC.getCouleurTexte() );
			r = r.replaceAll("alors", CouleursANSI.BLEU.getCouleurTexte()  + "alors" + CouleursANSI.BLANC.getCouleurTexte() );
			
			r = r.replaceAll("entier", CouleursANSI.VERT.getCouleurTexte() 	  + "entier" + CouleursANSI.BLANC.getCouleurTexte() );
			r = r.replaceAll("reel", CouleursANSI.VERT.getCouleurTexte()   	  + "reel" + CouleursANSI.BLANC.getCouleurTexte() );
			r = r.replaceAll("chaine", CouleursANSI.VERT.getCouleurTexte() 	  + "chaine" + CouleursANSI.BLANC.getCouleurTexte() );
			r = r.replaceAll("booleen", CouleursANSI.VERT.getCouleurTexte()   + "booleen" + CouleursANSI.BLANC.getCouleurTexte() );
			r = r.replaceAll("caractere", CouleursANSI.VERT.getCouleurTexte() + "caractere" + CouleursANSI.BLANC.getCouleurTexte() );
			//if(i == code.getNumLig()) r += couleurLigne.getCouleurFond();
			//r += String.format("|%2s %-50s|\n", Integer.toString(i), code.contenuFichier[i]);
			
			//r += CouleursANSI.NORMAL.getCouleurTexte();
			r += CouleursANSI.NOIR.getCouleurFond();
			
		}

		return r;
	}
	
	private void modifierCouleur(){
		if	   (inter.getEtatLigne() == null ) couleurLigne = CouleursANSI.JAUNE;
		else if(inter.getEtatLigne() == true ) couleurLigne = CouleursANSI.VERT;
		else if(inter.getEtatLigne() == false) couleurLigne = CouleursANSI.ROUGE;
	}

}