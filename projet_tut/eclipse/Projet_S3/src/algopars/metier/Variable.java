package algopars.metier;
//package pseudoCode.metier.vars;
/**
 * Crée une classe Variable qui permet de stocker 
 * une variable à partir de son type et sa valeur
 * @author Baron Clement, Cornilleau Titouan, Cadorel Loan, Labbé Hugo, Mandé Sébastien
 * @version 1.0, 2018-12-17
 * */
public class Variable{
	/**
	 * Type de la variable courante
	 * */
	private TypeVariable type;
	
	/**
	 * Valeur de la variable courante
	 * */
	private String valeur;
	
	/**
	 * Vrai si l'utilisateur choisi de tracer cette variable
	 * */
	private boolean aTracer;
	
	/**
	 * Permet de créer une variable avec un type et une valeur
	 * @param type un TypeVariable
	 * @param valeur un String
	 * */
	public Variable(TypeVariable type, String valeur){
		this.type 	= type	;
		this.valeur = valeur;
		
		this.aTracer = false;
	}
	
	/**
	 * Permet de créer une variable d'un certains type
	 * en initialisant sa valeur à null
	 * @param type un TypeVariable
	 * */
	public Variable(TypeVariable type){
		this(type, null);
	}

	@Override
	public String toString(){
		if(valeur == null) return "pas de valeur";
		return valeur;
	}
	
	/* MODIFICATEURS */
	
	/**
	 * Permet de définir le type de la variable
	 * @param type TypeVariable
	 * */
	public void setType(TypeVariable type){
		this.type = type;
	}
	
	/**
	 * Permet de définir la valeur de la variable
	 * @param valeur String
	 * */
	public void setValeur(String valeur){
		this.valeur = valeur;
	}
	
	/**
	 * Permet de définir si la variable est à tracer ou non.
	 * @param aTracer un booléeen
	 * */
	public void setTracage(boolean aTracer){
		this.aTracer = aTracer;
	}
	
	/**
	 * Retourne la valeur du type
	 * @return un String
	 * */
	public String getValeur(){
		return valeur;
	}
	
	/**
	 * Retourne le type de la Variable
	 * @return un TypeVariable
	 * */
	public TypeVariable getType(){
		return type;
	}
	
	/**
	 * Retourne si la variable est à tracer ou non
	 * */
	public boolean estAtracer(){
		return aTracer;
	}
	
	
}