/**
 * Classe pour gérer des cartes
 * enseigne = couleur
 * @author  bruno LEGRIX
 * @version 2015-09-10
 */
public enum CarteEnseigne
{
	// OBJETS
	COEUR   ("coeur"  ,'\u2665'),
	PIQUE   ("pique"  ,'\u2660'),
	CARREAU ("carreau",'\u2666'),
	TREFLE  ("trêfle" ,'\u2663');	

	// ATTRIBUTS
	private String nom    ;
	private char   symbole;
	
	// CONSTRUCTEUR
	CarteEnseigne(String nom , char symbole)
	{
		this.nom     = nom    ;
		this.symbole = symbole;
	}
	
	// ACCESSEURS
	public String getNom    () {return this.nom    ;}
	public char   getSymbole() {return this.symbole;}
	
	// TO STRING
	public String toString()
	{
		return this.nom + " (" + this.symbole + ") ";
	}
}
