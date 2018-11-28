public class Arbre
{
	private Noeud racine;

	public Arbre()
	{
		char[] tabAct = new char[] { 'L', 'M', 'C', 'B' };

		this.racine = new Noeud ( 'o', null );

		this.construire ( this.racine, 3, 1, tabAct );

	}

	// Construction r?cursive de l'Arbre
	private void construire ( Noeud n, int levelMax, int level, char[] tabAct )
	{
		char act=' ';
		Noeud tmp;

		if ( level <= levelMax )
			for ( int cpt=0; cpt < tabAct.length; cpt++ )
				if ( n.getVal() != tabAct[cpt] )
				{
					tmp = new Noeud ( tabAct[cpt], n );
					n.ajouterNoeud( tmp );

					construire ( tmp, levelMax, level+1, tabAct );
				}
	}

	// retourne sous forme de chaine le parcours de l'arbre selon la m?thode racine-enfants
	public String toString()
	{
		return this.afficher (this.racine);
	}

	// M?thode r?cursive permettant de parcourir l'arbre
	private String afficher (Noeud n)
	{
		String sRet = "" + n.getVal();

		for (int cpt=0; cpt<n.getNbFils(); cpt++)
			sRet += afficher ( n.getFils ( cpt ) );

		return sRet;
	}

	/**
	* @return l'ensemble des branches de l'arbre sous forme textuelle 
	*/
	public String afficherBranches ()
	{
		return parcourirBranches ( this.racine );
	}

	// Méthode récursive permettant de parcourir l'ensemble de l'arbre,
	//d'identifier chaque feuille, pour afficher l'ensemble des chemins
	private String parcourirBranches (Noeud n )
	{
	   String sRet = "";

	   if ( n.getNbFils() == 0 )                  // si feuille
	   		sRet += n.getVal();
	      
	   else{
	      for (int cpt=0; cpt<n.getNbFils(); cpt++)        // Parcours des fils
	         sRet += parcourirBranches ( n.getFils ( cpt ) );
	      sRet += "\n";
	     }

	     //sRet += "\n";
	   return sRet;
	}


	public static void main(String[] a)
	{
		Arbre arbre = new Arbre();

		//System.out.println ( arbre );
		System.out.println ( arbre.afficherBranches() );

	}
}
