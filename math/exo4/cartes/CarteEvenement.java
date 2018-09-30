/**
* @author 	   : Hugo Labbé, groupe D2
* @version	   : 1.1
* @description : Exercice 7 à rendre, classe CarteEvenement 2ème étape de l'exercice
*/

public class CarteEvenement
{
	public static boolean aucunAs(CarteEnsemble main)
	{
		return CarteEvenement.nbAs(main) == 0;
	}

	public static boolean auMoinsUnAs(CarteEnsemble main)
	{
		return CarteEvenement.nbAs(main) >= 1;
	}

	public static boolean deuxAs(CarteEnsemble main)
	{
		return CarteEvenement.nbAs(main) == 2;
	}

	public static boolean auPlusUnAs(CarteEnsemble main)
	{
		return CarteEvenement.nbAs(main) <= 1;
	}

	public static boolean unAs(CarteEnsemble main)
	{
		return CarteEvenement.nbAs(main) == 1;
	}

	private static int nbAs(CarteEnsemble main)
	{
		int nbAs = 0;

		for(int cpt=0; cpt<main.getNombre(); cpt++)
			if(main.get(cpt).getValeur() == CarteValeur.AS)
				nbAs++;

		return nbAs;
	}
}