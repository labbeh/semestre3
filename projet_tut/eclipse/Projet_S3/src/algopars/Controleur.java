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
	 * @param os caractère ayant pour valeur 'w' ou 'u' permettant au controleur d'instancier la bonne version de l'ihmcui
	 * */
	public Controleur( Code code, char os ) {
		if(os == 'w')
			this.ihm = new IHMCuiWindows();
		else
			this.ihm = new IHMCuiUnix();
		
		this.code  = code ;
		this.inter = new Interpreteur(code, this);
		
		this.couleurLigne = CouleursANSI.BLEU;
	}
	
	
	/**
	 * Lancer le programme en mode pas à pas
	 * */
	public void lancerPasAPas(){
		String saisi;
		boolean saisiReussi = false;
		
		ihm.nettoyer();
		ihm.normal();
		afficher(this.afficherPseudoCode());
		
		choixTracageVariables();
		
		afficher(this.afficherDonnees());
		afficherTrace();
		
		ihm.nettoyer();
		// mode pas a pas
		while(inter.getIndex() < code.getNbLig()){
			saisiReussi = false;
			
			code.setNumLig(inter.getIndex());
			
			inter.faireLigne();
			
			afficher(this.afficherPseudoCode());
			afficher(this.afficherDonnees()   );
			afficherTrace();
			
			inter.incIndex()  ;
		
			inter.incCptIndex();
		
			saisi = ihm.lireClavier().toLowerCase();
			
			if(saisi.startsWith("l")){
				
				inter.getTrace().clear();
				saisiReussi = inter.allerLigne(saisi);
				
				while(!saisiReussi){
					saisi 		= ihm.lireClavier();
					saisiReussi = inter.allerLigne(saisi);
				}
			}
			else if(saisi.startsWith("b")) {
				inter.getTrace().clear();
				code.reinitVars();
				inter.retourArriere();
			}
			
			ihm.nettoyer();
		}
		
		code.setNumLig(inter.getIndex()-1);
		
		afficher(this.afficherPseudoCode());
		afficher(this.afficherDonnees()   );
		afficherTrace();
		
		ihm.normal();
	}
	
	/*public void lancerPasAPas(){
		ihm.nettoyer();
		ihm.normal();
		afficher(this.afficherPseudoCode());
		
		choixTracageVariables();
		
		afficher(this.afficherDonnees());
		afficherTrace();
		
		ihm.nettoyer();
		// mode pas a pas
		while(inter.getIndex() < code.getNbLig()){
			code.setNumLig(inter.getIndex());
			
			inter.faireLigne();
			
			afficher(this.afficherPseudoCode());
			afficher(this.afficherDonnees()   );
			afficherTrace();
			
			inter.incIndex()  ;
			ihm.lireClavier() ;
			ihm.nettoyer()    ;
		}
		
		code.setNumLig(inter.getIndex()-1);
		
		afficher(this.afficherPseudoCode());
		afficher(this.afficherDonnees()   );
		afficherTrace();
		
		ihm.normal();
	}*/
	
	/**
	 * Lance le programme en mode automatique
	 * @param delai temps en secondes entre chaque lignes
	 * */
	public void lancerAuto(long delai){
		ihm.nettoyer();
		ihm.normal();
		
		afficher(this.afficherPseudoCode());
		
		choixTracageVariables();
		
		afficher(this.afficherDonnees());
		afficherTrace();
		
		ihm.nettoyer();
		
		// mode pas a pas
		while(inter.getIndex() < code.getNbLig()){
			code.setNumLig(inter.getIndex());
			
			inter.faireLigne();
			
			afficher(this.afficherPseudoCode());
			afficher(this.afficherDonnees()   );
			afficherTrace();
			
			inter.incIndex();
			pause(delai);
			ihm.nettoyer();
		}
		
		code.setNumLig(inter.getIndex()-1);
		
		afficher(this.afficherPseudoCode());
		afficher(this.afficherDonnees()   );
		afficherTrace();
		
		ihm.normal();
	}
	
	private void pause(long delai){
		try{
			Thread.sleep(delai * 1000);
		}
		catch(InterruptedException evt){
			System.err.println(evt);
		}
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
		ihm.println(CouleursANSI.NOIR.getCouleurFond() +
					CouleursANSI.BLANC.getCouleurTexte()+
					str + CouleursANSI.NORMAL.getCouleurFond());
	}
	
	/**
	 * Permet d'afficher la trace d'execution
	 * Affiche les 5 dernières lignes
	 * */
	public void afficherTrace(){
		if(inter.getTrace().size() > 5 ) inter.getTrace().remove(0);
		
		afficher("TRACE:");
		for(String s: inter.getTrace()) afficher(s);
	}
	
	/**
	 * Retourne la saisie au clavier depuis l'ihm
	 * @return un String
	 * */
	public String lireClavier(){
		return ihm.lireClavier();
	}
	
	/**
	 * Retourne l'entier saisi au clavier depuis l'ihm
	 * @return un entier
	 * */
	public int lireEntier(){
		return ihm.lireEntier();
	}
	
	/**
	 * Retourne le réel saisi au clavier depuis l'ihm
	 * @return un réel
	 * */
	public double lireReel(){
		return ihm.lireReel();
	}
	
	/**
	 * Permet de lancer le nettoyage de la console
	 * */
	public void netoyer(){
		ihm.nettoyer();
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
	 * Génère le block "données" à afficher et ne met que les variables a tracer
	 * @return un String 
	 * */
	public String afficherDonnees() {
		String r = new String();
		r += CouleursANSI.NOIR.getCouleurFond();
		r += CouleursANSI.BLANC.getCouleurTexte();
		r += "|    NOM    |   TYPE    |  VALEUR   |\n";
		for (String v : code.variables.keySet()) {
			if(code.variables.get(v).estAtracer())
				r += String.format("| %-10s| %-10s| %-10s|\n", v, 
									code.variables.get(v).getType(),
									code.variables.get(v));
		}
		
		return r;
	}
	
	/**
	 * Génère sous forme de String le code à afficher
	 * @return un String
	 * */
	public String afficherPseudoCode() {
		String r = new String();
		modifierCouleur();
		
		int i = 0 ;
		int max = 0 ;
		if (code.getCode().size() > 30 && code.getNumLig() > 15 && code.getNumLig() < code.contenuFichier.length - 15 ){
			i = code.getNumLig()-16 ;
			max = code.getNumLig() + 15 ;
		}
		if (code.getCode().size() > 30 && code.getNumLig() <= 15){
			i = 0 ;
			max = 31 ;
		}
		if (code.getCode().size() > 30 && code.getNumLig() >= code.contenuFichier.length - 15 ){
			i =  code.contenuFichier.length  - 31 ;
			max = code.contenuFichier.length;
		}
		if (code.getCode().size() <= 30)
			max = code.contenuFichier.length ;
		
		
		while ( i < max)  {
			//r += CouleursANSI.NORMAL.getCouleurTexte();
			if(i == code.getNumLig()) r += couleurLigne.getCouleurFond();
			else 					  r += CouleursANSI.NOIR.getCouleurFond();
			
			r += CouleursANSI.BLANC.getCouleurTexte();
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

			r += CouleursANSI.NOIR.getCouleurFond();
			i++ ;
		}
		return r;
	}
	
	/**
	 * Changer l'état d'une ligne pour sa coloration dans la console.
	 * Vert si c'est une expression boléenne vrai, rouge si c'est faux
	 * Jaune si c'est une ligne quelconque
	 * */
	private void modifierCouleur(){
		if	   (inter.getEtatLigne() == null ) couleurLigne = CouleursANSI.JAUNE;
		else if(inter.getEtatLigne() == true ) couleurLigne = CouleursANSI.VERT;
		else if(inter.getEtatLigne() == false) couleurLigne = CouleursANSI.ROUGE;
	}

}