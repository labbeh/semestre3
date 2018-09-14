package gameOfLife;

public class JeuDeLaVie
{
	public Cell[][] situInit;

	public JeuDeLaVie(int nbLig, int nbCol)
	{
		this.situInit = new Cell[nbLig][nbCol];
	}

	public void remplirAleatoire()
	{
		boolean etat = false;


		/*for(int cptLig=0; cptLig<this.situInit.length; cptLig++)
			for(int cptCol=0; cptCol<this.situInit[0].length; cptCol++)*/

	}

	public static void main(String[] args)
	{
		JeuDeLaVie jeu = new JeuDeLaVie(15, 15);
	}
}