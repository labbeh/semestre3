public class CarteTirage
{
	/*******************************************************/
	/* n = nombre de fois que l'on renouvelle l'expérience */
	/*******************************************************/

	public static double[] successivementUneCarteDansDeuxJeux(int n)
	{
		double[] tab 		= new double[5];
		int	  [] nbReussite = new int   [5];

		CarteEnsemble jeu1 = new CarteEnsemble(52);
		CarteEnsemble jeu2 = new CarteEnsemble(52);

		for(int cpt=0; cpt<n; cpt++)
		{
			if(CarteEvenement.aucunAs(jeu1) && CarteEvenement.aucunAs(jeu2))
				nbReussite[0]++;
			if(CarteEvenement.auMoinsUnAs(jeu1) && CarteEvenement.auMoinsUnAs(jeu2))
				nbReussite[1]++;
			if(CarteEvenement.deuxAs(jeu1) && CarteEvenement.deuxAs(jeu2))
				nbReussite[2]++;
			if(CarteEvenement.auPlusUnAs(jeu1) && CarteEvenement.auPlusUnAs(jeu2))
				nbReussite[3]++;
			if(CarteEvenement.unAs(jeu1) && CarteEvenement.unAs(jeu2))
				nbReussite[4]++;
		}

		for(int cpt=0; cpt<n; cpt++)
			tab[cpt] = nbReussite[cpt] / n;

		return tab;
	}

	public static double[] successivementDeuxCartesDansUnJeu(int n)
	{
		CarteEnsemble jeu1 = new CarteEnsemble(52);

		return null;
	}

	public static double[] successivementSansRemiseDansPaquetDeuxJeux(int n)
	{
		CarteEnsemble jeu1 = new CarteEnsemble(52);
		CarteEnsemble jeu2 = new CarteEnsemble(52);

		return null;
	}
}