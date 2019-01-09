package algopars.ihm;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Classe abstraite permettant de concevoir des IHM console pour windows et unix/linux
 * @author Hugo Labbe, Titouan Cornilleau, Clement Baron, Sebastien Mande,Loan Cadorel 
 * @version 1.0, 2018-12-18
 * */

public abstract class IHMCui {
	
	/**
	 * Instance de Scanner pour lire sur l'entree standard
	 * */
	private Scanner stdin;
	
	/**
	 * Objet printstream pour la sortie standard
	 * */
	protected PrintStream stdout;
	/**
	 * Constructeur initialise le necessaire pour lire et ecrire sur l'entree 
	 * et la sortie standard 
	 * */
	public IHMCui() {
		this.stdin 	= new Scanner	 (System.in );
		this.stdout = new PrintStream(System.out);
	}
	
	/*-------------------------*/
	/* LECTURE ENTREE STANDARD */
	/*-------------------------*/
	/**
	 * Fonction lecture sur l'entree standard
	 * @return flux de l'entree standard en String
	 * */
	public String lireClavier(){
		return stdin.nextLine();
	}
	
	/**
	 * Fonction pour lire un entier au clavier qui redemande la saisie
	 * en cas de saisie invalide
	 * @return l'entier saisi au clavier
	 * */
	public int lireEntier()
    {
        int     iRet = 0    ;
        boolean bOk  = false;
        
        while(!bOk)
        {
            try{
                iRet = Integer.parseInt(lireClavier());
                bOk = true;
            }
            catch(NumberFormatException evt){
                System.out.println("Erreur: saisir un nombre entier");
            }
        }
        return iRet;
    }
	
	/**
	 * Fonction pour lire un reel au clavier qui redemande la saisie
	 * en cas de saisie invalide
	 * @return le reel saisi au clavier
	 * */
	public double lireReel()
    {
        double  dRet = 0    ;
        boolean bOk  = false;
        
        while(!bOk)
        {
            try{
                dRet = Double.parseDouble(lireClavier());
                bOk = true;
            }
            catch(NumberFormatException evt){
                System.out.println("Erreur: saisir un réel");
            }
        }
        return dRet;
    }
	
	/**
	 * Permet de lire un booleen au clavier
	 * Tourne jusqu'a ce que T/F ou O/N soit saisi
	 * @return valeur booleenne saisie au clavier
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
	* Methode a definir dans les classes filles permettant l'affichage sans retour a la ligne
	* Le but etant de definir une classe fille pour les sytemes UNIX/Linux et une autre pour Windows
	* @param aAfficher chaine a afficher
	* */
	public abstract void print(String aAfficher);
	
	/**
	* @param aAfficher chaine a afficher
	* utilise la methode print pour afficher la chaine et ajoute un retour ligne
	* */
	public void println(String aAfficher){
		print(aAfficher);
		print("\n");
	}
	
	/**
	* @param obj un objet
	* Permet d'afficher le toString() d'un objet sans l'appeler explicitement
	* */
	public void print(Object obj){
		print(obj.toString());
	}
	
	/**
	* @param obj un objet
	* Permet d'afficher le toString() d'un objet sans l'appeler explicitement en ajoutant un retour
	* ligne a la fin
	* */
	public void println(Object obj){
		println(obj.toString());
	}
	
	/**
	* @param carac un caractere
	* Affiche le caractere passe en parametre sans retour ligne
	*/
	public void print(char carac){
		print(Character.toString(carac));
	}
	
	/**
	* @param i un entier
	* Affiche l'entier passe en parametre sans retour ligne
	*/
	public void print(int i){
		print(Integer.toString(i));
	}
	
	/**
	* @param d un double
	* Affiche le double passe en parametre sans retour ligne
	*/
	public void print(double d){
		print(Double.toString(d));
	}
	
	/**
	* @param b un booleen
	* Affiche la valeur du booleen passee en parametre
	*/
	public void print(boolean b){
		Boolean.toString(b);
	}
	
	/**
	* @param f un float
	* Affiche le float passe en parametre sans retour ligne
	*/
	public void print(float f){
		print(Float.toString(f));
	}
	
	/**
	* @param l un long
	* Affiche le long passe en parametre sans retour ligne
	*/
	public void print(long l){
		print(Long.toString(l));
	}
	
	/**
	* @param sht un short
	* Affiche le short passe en parametre sans retour ligne
	*/
	public void print(short sht){
		Short.toString(sht);
	}
	
	/**
	* Efface le contenu de l'ecran
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