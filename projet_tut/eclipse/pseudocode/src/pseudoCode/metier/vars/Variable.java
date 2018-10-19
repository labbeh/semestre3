package pseudoCode.metier.vars;

public class Variable
{
	private TypeVarible type;
	private String nom;
	private String valeur;
	
	public Variable(TypeVarible type, String nom,String valeur)
	{
		this.type = type;
		this.nom = nom;
		this.valeur = valeur;
	}
	
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public String toString()
	{
		return this.valeur.toString();
	}
	
	
	
	public TypeVarible getType() {
		return type;
	}

	public void setType(TypeVarible type) {
		this.type = type;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
