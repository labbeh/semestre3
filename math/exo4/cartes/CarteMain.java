/**
 * Classe pour gérer des cartes
 * @author  bruno LEGRIX
 * @version 2017-06-20
 */
public class CarteMain
{
	public static void main( String[] arg )
	{
		// création
		Carte[] cartes = new Carte[  CarteEnseigne.values().length
		                           * CarteValeur  .values().length];
		int i = 0;
		for(CarteEnseigne couleur : CarteEnseigne.values())
			for(CarteValeur valeur : CarteValeur.values  ())
				cartes[i++] = new Carte(couleur,valeur);

		// affichage
		CarteEnseigne enseigne = cartes[0].getEnseigne();
		String r = enseigne.toString() + " : ";
		for(i=0; i<cartes.length; i++)
		{
			if (enseigne != cartes[i].getEnseigne())
			{
				enseigne = cartes[i].getEnseigne();
				r += "\n" + enseigne.toString() + " : ";
			}
				
			r += cartes[i].getValeur().getNom() + ", ";
		}
		System.out.println(r);
		
		Carte carte = new Carte(CarteEnseigne.PIQUE,CarteValeur.VALET);
		for(i=0; i<cartes.length; i++)
		{
			if (carte.equals(cartes[i])) System.out.println(i+" "+carte);
		}
		
		/*
		CarteEnsemble jeu = new CarteEnsemble(32);
		CarteEnsemble main;
		Carte         asCoeur = new Carte(CarteEnseigne.COEUR,CarteValeur.AS);
		int k = 0;
		int n = 10;
		for(int j=0; j<7; j++)
		{
			n *= 10;
			k  =  0;
			for(int i=0; i<n; i++)
			{
				main = jeu.DonnerKCombinaison(5);
				if (main.contient(asCoeur)) k++;
			}
			System.out.println(n+"\t"+k+"\t"+((double)k/n));
		}
		*/
	}
}

