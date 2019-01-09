package algopars;
import java.util.Set;

import algopars.ihm.*;
import algopars.metier.*;

/**
 * Classe controleur faisant la passerelle enre la partie metier et l'ihm
 * @author Hugo Labbe, Titouan Cornilleau, Clement Baron, Sebastien Mande,Loan Cadorel 
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
	 * change en cas d'expression booleenne
	 * */
	private CouleursANSI couleurLigne;
	
	/**
	 * Constructeur du controleur passerelle avec l'ihm
	 * @param code instance de Code
	 * @param os caractere ayant pour valeur 'w' ou 'u' permettant au controleur d'instancier la bonne version de l'ihmcui
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
	 * Lancer le programme en mode pas a pas
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
			else if(saisi.startsWith("+ var")){
				saisiReussi = changerTracage(saisi, true);
				
				while(!saisiReussi){
					saisi 		= ihm.lireClavier();
					saisiReussi = changerTracage(saisi, true);
				}
			}
			else if(saisi.startsWith("- var")){
				saisiReussi = changerTracage(saisi, false);
				
				while(!saisiReussi){
					saisi 		= ihm.lireClavier();
					saisiReussi = changerTracage(saisi, false);
				}
			}
			
			ihm.nettoyer();
		}
		
		code.setNumLig(inter.getIndex()-1);
		
		afficher(this.afficherPseudoCode());
		afficher(this.afficherDonnees()   );
		afficherTrace();
		
		ihm.normal();
	}
	
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
	
	/**
	 * Permet de changer les variables a tracer pendant l'utilisation
	 * en mode manuel
	 * @param saisi la ligne saisie au clavier
	 * @param aTracer vrai si la variable est a tracer
	 * */
	public boolean changerTracage(String saisi, boolean aTracer){
		String[] splitSaisi = saisi.split(" ");
		
		if(splitSaisi.length != 3){
			afficher("Erreur de saisie");
			return false;
		}
		
		return code.tracerVariable(splitSaisi[2], aTracer);
	}
	
	/**
	 * Permet de choisir le temp de temporistation pour passer d'une ligne a l'autre
	 * en mode auto
	 * @param delai temporisation en secondes
	 * */
	private void pause(long delai){
		try{
			Thread.sleep(delai * 1000);
		}
		catch(InterruptedException evt){
			System.err.println(evt);
		}
	}
	
	/**
	 * Lance la possibilite a l'utilisateur de choisir les variables
	 * a tracer
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
	 * Envoie sur l'ihm console une chaine de caractere a afficher
	 * avec retour ligne
	 * */
	public void afficher(String str){
		ihm.println(CouleursANSI.NOIR.getCouleurFond() +
					CouleursANSI.BLANC.getCouleurTexte()+
					str + CouleursANSI.NORMAL.getCouleurFond());
	}
	
	/**
	 * Permet d'afficher la trace d'execution
	 * Affiche les 5 dernieres lignes
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
	 * Retourne le reel saisi au clavier depuis l'ihm
	 * @return un reel
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
	 * Incremente le numero de ligne en cours dans l'instance de Code
	 * */
	public void incNumLig(){
		code.incNumLig();
	}
	
	/**
	 * Defini le numero de la ligne courante dans l'instance de Code
	 * */
	public void setNumLig(int numLig){
		code.setNumLig(numLig);
	}
	
	/**
	 * Retourne la valeur de la ligne utile a l'indice i dans la liste
	 * de numeros de lignes utiles
	 * @param index indice dans la liste entier
	 * @return entier numero de ligne utile a l'indice i
	 * */
	public int getNumLigUtilAt(int index){
		return code.getNumLigUtilAt(index);
	}
	
	/**
	 * Genere le block "donnees" a afficher et ne met que les variables a tracer
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
	 * Genere sous forme de String le code a afficher
	 * @return un String
	 * */
	public String afficherPseudoCode() {
		String r = new String();
		modifierCouleur();
		
		int i = 0 ;
		int max = 0 ;
		if (code.contenuFichier.length > 30 && code.getNumLig() > 15 && code.getNumLig() < code.contenuFichier.length - 15 ){
			i = code.getNumLig()-16 ;
			max = code.getNumLig() + 15 ;
		}
		if (code.contenuFichier.length > 30 && code.getNumLig() <= 15){
			i = 0 ;
			max = 31 ;
		}
		if (code.contenuFichier.length > 30 && code.getNumLig() >= code.contenuFichier.length - 15 ){
			i =  code.contenuFichier.length  - 31 ;
			max = code.contenuFichier.length;
		}
		if (code.contenuFichier.length <= 30)
			max = code.contenuFichier.length ;
		
		
		while ( i < max)  {
			if(i == code.getNumLig()) r += couleurLigne.getCouleurFond();
			else 					  r += CouleursANSI.NOIR.getCouleurFond();
			
			r += CouleursANSI.BLANC.getCouleurTexte();
			r += String.format("|%2s %-50s|\n", Integer.toString(i), code.contenuFichier[i]);
		
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
	 * Changer l'etat d'une ligne pour sa coloration dans la console.
	 * Vert si c'est une expression boleenne vrai, rouge si c'est faux
	 * Jaune si c'est une ligne quelconque
	 * */
	private void modifierCouleur(){
		if	   (inter.getEtatLigne() == null ) couleurLigne = CouleursANSI.JAUNE;
		else if(inter.getEtatLigne() == true ) couleurLigne = CouleursANSI.VERT;
		else if(inter.getEtatLigne() == false) couleurLigne = CouleursANSI.ROUGE;
	}
}