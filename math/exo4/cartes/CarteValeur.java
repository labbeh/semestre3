/**
 * Classe pour gérer des cartes
 * @author  bruno LEGRIX
 * @version 2015-09-10
 */
public enum CarteValeur
{
	// OBJETS
	AS     ("as"	,"valeur" ),
	DEUX   ("deux"  ,"valeur" ),
	TROIS  ("trois" ,"valeur" ),
	QUATRE ("quatre","valeur" ),
	CINQ   ("cinq"  ,"valeur" ),
	SIX    ("six"   ,"valeur" ),
	SEPT   ("7" 	,"valeur" ),
	HUIT   ("8" 	,"valeur" ),
	NEUF   ("9" 	,"valeur" ),
	DIX    ("10"	,"valeur" ),
	VALET  ("V" 	,"figure" ),
	DAME   ("D" 	,"figure" ),
	ROI    ("R" 	,"figure" );

	// ATTRIBUTS
	private String nom ;
	private String type;
	
	// CONSTRUCTEUR
	CarteValeur(String nom , String type)
	{
		this.nom  = nom ;
		this.type = type;
	}
	
	// ACCESSEURS
	public String getNom () {return this.nom ;}
	public String getType() {return this.type;}
	
	// TO STRING
	public String toString()
	{
		return this.nom + " (" + this.type + ") ";
	}
}

