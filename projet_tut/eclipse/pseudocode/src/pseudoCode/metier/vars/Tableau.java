package pseudoCode.metier.vars;

public class Tableau extends Variable
{
	private Variable[] elts;
	private int nbElts;
	
	public Tableau(TypeVariable type, int nbElts)
	{
		super(TypeVariable.TABLEAU, "");
		
		this.elts = new Variable[nbElts];
		this.nbElts = 0;
	}
	
	public Variable getVar(int i)
	{
		return this.elts[i];
	}
	
	public boolean ajouterElt(Variable v)
	{
		if(v == null) 				   return false;
		if(this.elts.length == nbElts) return false;
		
		this.elts[this.nbElts++] = v;
		return true;
	}
	
	public int getNbElts() { return this.nbElts; }
	
	@Override
	public String toString()
	{
		StringBuilder sbRet = new StringBuilder("[ ");
		
		for(int cpt=0; cpt<this.nbElts; cpt++)
			sbRet.append(this.elts[cpt] +" ");
		
		sbRet.append("]");
		
		return sbRet.toString();
	}

}
