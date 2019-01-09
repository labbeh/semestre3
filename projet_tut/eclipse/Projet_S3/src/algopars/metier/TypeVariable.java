package algopars.metier;

/**
 * @author lh150094
 * Enum énumérant tout les types de bases possibles dans le pseudo-code
 * */

enum TypeVariable{
	ENTIER,
	REEL,
	CHAINE,
	CARACTERE,
	BOOLEEN,
	
	TABLEAU;
	
	public static TypeVariable trouverType(String type){
		if	   (type.equalsIgnoreCase("entier")) return ENTIER;
		else if(type.equalsIgnoreCase("réel"  )) return REEL;
		else if(type.equalsIgnoreCase("chaine")) return CHAINE;
		else if(type.equalsIgnoreCase("caractère")) return CARACTERE;
		else if(type.equalsIgnoreCase("booléen")) return BOOLEEN;
		else if(type.equalsIgnoreCase("tableau")) return TABLEAU;
		
		return null;
	}
}
