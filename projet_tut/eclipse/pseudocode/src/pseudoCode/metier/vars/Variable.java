package pseudoCode.metier.vars;

public class Variable
{
	private TypeVarible type;
	private Object valeur;
	
	public Variable(Object valeur)
	{
		this.valeur = valeur;
	}
	
	@Override
	public String toString()
	{
		return this.valeur.toString();
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
