public class Noeud
{
	static  int nbNoeud;

	int     numNoeud;
	char    val;

	Noeud[] tabNoeud;
	int     nbFils;

	public Noeud ( char val )
	{
		this.numNoeud = Noeud.nbNoeud++;
		this.val      = val;

		this.tabNoeud = new Noeud[4];
		this.nbFils   = 0;
	}

	public void ajouterNoeud ( Noeud n )
	{
		if(n 	  == null			) return;
		if(nbFils == tabNoeud.length) return;

		this.tabNoeud[nbFils++] = n;
	}

	public char  getVal(){
		return this.val;
	}

	public Noeud getFils  (int indice)
	{
		if(indice < 0 || indice > tabNoeud.length-1)
			return null;

		return tabNoeud[indice];
	}

	public int   getNbFils(){
		return this.nbFils;
	}

	public String toString(){
		return this.val + "(" + this.numNoeud + ")";
	}


}
