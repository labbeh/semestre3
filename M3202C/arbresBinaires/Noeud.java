public class Noeud
{
	static  int nbNoeud;

	int     numNoeud;
	char    val;

	Noeud[] tabNoeud;
	int     nbFils;

	Noeud pere;

	public Noeud ( char val, Noeud pere )
	{
		this.numNoeud = Noeud.nbNoeud++;
		this.val      = val;

		this.tabNoeud = new Noeud[4];
		this.nbFils   = 0;

		this.pere = pere;
	}

	/**
	* Permet d'ajouter un fils au Noeud courant
	*/
	public void ajouterNoeud ( Noeud n )
	{
		if(n 	  == null			) return;
		if(nbFils == tabNoeud.length) return;

		this.tabNoeud[nbFils++] = n;
	}

	/**
	* @return le caractère valeur du Noeud courant
	*/
	public char  getVal(){
		return this.val;
	}

	/**
	* @param  indice du fils
	* @return fils à l'indice en paramètre, null en cas d'indice invalide
	*/
	public Noeud getFils  (int indice)
	{
		if(indice < 0 || indice > tabNoeud.length-1)
			return null;

		return tabNoeud[indice];
	}

	/**
	* @return Noeud père du Noeud courant (null si il n'en a pas)
	*/
	public Noeud getPere(){
		return this.pere;
	}

	/**
	* @return nombre de fils que possède le Noeud courant
	*/
	public int   getNbFils(){
		return this.nbFils;
	}

	/**
	* @return chemin à parcourir depuis la racine pour arriver au Noeud
	*/
	public String getChemin(){

		if(this.pere == null) return this.val +" ";
		
		return this.pere.getChemin() + this.val +" ";
	}

	/**
	* @return Noeud courant sous forme textuelle
	*/
	public String toString(){
		return this.val + "(" + this.numNoeud + ")";
	}

	public static void main(String[] args)
	{
		Noeud n1 = new Noeud('A', null);
		Noeud n2 = new Noeud('B', n1);
		Noeud n3 = new Noeud('C', n2);
		Noeud n4 = new Noeud('D', n3);
		Noeud n5 = new Noeud('E', n4);
		Noeud n6 = new Noeud('F', n5);

		System.out.println(n6.getChemin());
	}


}
