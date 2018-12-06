public class Util{

	/*
	* Constructeur vide sans paramètre privé pour rendre la classe non instanciable
	* puisqu'il s'agit d'une classe utilitaire
	**/
	private Util(){}

	/**
	* @param expression chaine de caractère d'une expression mathématique
	* @return résultat de l'expression
	* calcul une expression passée en paramètre sous forme récursive
	*/
	public static int evaluer(String expression){
		expression = expression.replaceAll(" ", "");

		String[] elts = expression.split("+");
		int res = Integer.parseInt(elts[0]) * Integer.parseInt(elts[1]);

		if(elts.length == 2) return res;


		return ;
	}

	public static void main(String[] argv){
		evaluer("2 * 3 + 4 * 2 + 6");
	}
}