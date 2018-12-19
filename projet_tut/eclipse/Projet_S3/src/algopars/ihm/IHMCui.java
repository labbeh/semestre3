package algopars.ihm;

import java.io.PrintStream;
import java.util.Scanner;

import algopars.Controleur;

/**
 * Classe abstraite permettant de concevoir des IHM console pour windows et unix/linux
 * @author hugo labbé
 * @version 1.0, 2018-12-18
 * */

public abstract class IHMCui {
	
	/**
	 * Controleur pour l'intercation entre ihm et métier
	 * */
	private Controleur ctrl;
	
	/**
	 * Instance de Scanner pour lire sur l'entrée standard
	 * */
	private Scanner stdin;
	
	/**
	 * Objet printstream pour la sortie standard
	 * */
	protected PrintStream stdout;
	
	public IHMCui(Controleur ctrl) {
		this.ctrl  = ctrl;
		
		this.stdin 	= new Scanner	 (System.in );
		this.stdout = new PrintStream(System.out);
	}
	
	/*-------------------------*/
	/* LECTURE ENTREE STANDARD */
	/*-------------------------*/
	/**
	 * Fonction lecture sur l'entrée standard
	 * @return flux de l'entrée standard en String
	 * */
	public String lireClavier(){
		return stdin.nextLine();
	}
	
	/**
	 * Permet de lire un booléen au clavier
	 * Tourne jusqu'à ce que T/F ou O/N soit saisi
	 * @return valeur booléenne saisie au clavier
	 * */
	public boolean lireBoolean(){
		boolean bRet = false;
		char saisie = ' ';
		
		while(saisie != 't' && saisie != 'f' && saisie != 'o' && saisie != 'n'){
			try{
				saisie = lireClavier().toLowerCase().charAt(0);
			
				if(saisie != 't' && saisie != 'f' && saisie != 'o' && saisie != 'n')
					stdout.println("Erreur: saisir[T/F] [O/N]");
			}catch(Exception e){stdout.println("Erreur: saisir[T/F] [O/N]");}
		}
		
		if(saisie == 't' || saisie == 'o') bRet = true ;
		else							   bRet = false;
		
		return bRet;
	}
	
	/*----------------------------------*/
	/* AFFICHAGE SUR LA SORTIE STANDARD */
	/*----------------------------------*/
	/**
	* @param aAfficher chaine à afficher
	* Méthode a définir dans les classes filles permettant l'affichage sans retour a la ligne
	* Le but étant de définir une classe fille pour les sytèmes UNIX/Linux et une autre pour Windows
	* */
	public abstract void print(String aAfficher);
	
	/**
	* @param aAfficher chaine à afficher
	* utilise la méthode print pour afficher la chaine et ajoute un retour ligne
	* */
	public void println(String aAfficher){
		print(aAfficher);
		print("\n");
	}
	
	/**
	* @param obj un objet
	* Permet d'afficher le toString() d'un objet sans l'appelé explicitement
	* */
	public void print(Object obj){
		print(obj.toString());
	}
	
	/**
	* @param obj un objet
	* Permet d'afficher le toString() d'un objet sans l'appelé explicitement en ajoutant un retour
	* ligne à la fin
	* */
	public void println(Object obj){
		println(obj.toString());
	}
	
	/**
	* @param carac un caractère
	* Affiche le caractère passé en paramètre sans retour ligne
	*/
	public void print(char carac){
		print(Character.toString(carac));
	}
	
	/**
	* @param i un entier
	* Affiche l'entier' passé en paramètre sans retour ligne
	*/
	public void print(int i){
		print(Integer.toString(i));
	}
	
	/**
	* @param d un double
	* Affiche le double passé en paramètre sans retour ligne
	*/
	public void print(double d){
		print(Double.toString(d));
	}
	
	/**
	* @param b un booléen
	* Affiche la valeur du booléen passé en paramètre
	*/
	public void print(boolean b){
		Boolean.toString(b);
	}
	
	/**
	* @param f un float
	* Affiche le float passé en paramètre sans retour ligne
	*/
	public void print(float f){
		print(Float.toString(f));
	}
	
	/**
	* @param l un long
	* Affiche le long passé en paramètre sans retour ligne
	*/
	public void print(long l){
		print(Long.toString(l));
	}
	
	/**
	* @param sht un short
	* Affiche le short passé en paramètre sans retour ligne
	*/
	public void print(short sht){
		Short.toString(sht);
	}
	
	/**
	* Efface le contenu de l'écran
	*/
	public void nettoyer(){
		print("\033[H\033[2J");
	}
	
	/**
	 * Remet le jeu de couleurs de base de la console
	 * */
	public void normal(){
		print("\033[0m");
	}

}
