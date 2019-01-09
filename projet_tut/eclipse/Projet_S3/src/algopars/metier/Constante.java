package algopars.metier;

/**
 * Classe heriant de Variable mais empechant la modification
 * de sa valeur une fois affectée
 * @author Baron Clement, Cornilleau Titouan, Cadorel Loan, Labbe Hugo, Mande Sebastien
 * @version 1.0, 2019-01-08
 * */

public class Constante extends Variable {
	
	/**
	 * Est vrai lorsqu'une valeur a ete affectee a la constante que ce soit
	 * dans le constructeur ou via l'accesseur
	 * */
	private boolean valeurDefini;

	public Constante(TypeVariable type, String valeur) {
		super(type, valeur);
		this.valeurDefini = true;
	}

	public Constante(TypeVariable type) {
		super(type);
		this.valeurDefini = false;
	}
	
	/*---------------*/
	/* MODIFICATEURS */
	/*---------------*/
	
	@Override
	/**
	 * Permet de definir la valeur de la constante
	 * Declenche une erreur en cas de tentative de
	 * modification de la valeur
	 * @param valeur String
	 * */
	public void setValeur(String valeur){
		if(valeurDefini){
			System.err.println("ERREUR: vous essayez de modifier la valeur " +
							   "d'une constante");
			System.exit(1);
		}
		
		super.setValeur(valeur);
		this.valeurDefini = true;
	}

}
