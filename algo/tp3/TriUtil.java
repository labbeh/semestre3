// A rediriger dans le dossier paquetage_class
package utilitaire;

public class TriUtil
{
	public static void triSelection( int[] tab )
	{
		int max;
		int taille = tab.length;

		for(int cpt=0; cpt<tab.length; cpt++)
		{
			max = TriUtil.max(tab, taille);
			taille--;
			TriUtil.echanger(tab, max, taille);
		}
	}

	private static int max( int[] tab, int taille )
	{
		int max = 0;

		for(int cpt=0; cpt<taille; cpt++)
			if(tab[cpt] > tab[max]) max = cpt;

		return max;
	}

	private static void echanger( int[] tab, int posOrg, int posDest )
	{
		int tmp = tab[posDest];

		tab[posDest] = tab[posOrg];
		tab[posOrg ] = tmp;
	}

   	public static void triBulle( int[] tab )
   	{
   		for(int cpt=0; cpt<tab.length-1; cpt++)
   		{
   			if(tab[cpt] < tab[cpt+1]) echanger(tab, cpt, cpt+1);
   		}
   	}

   	public static void triInsertion( int[] tab )
   	{

   	}

   	public static void triRapide( int[] tab, int deb, int fin )
   	{
   		int pivot;
   		if(deb < fin)
   		{
   			pivot = partitionner(tab, deb, fin);

   			triRapide(tab, deb, pivot-1);
   			triRapide(tab, pivot+1, fin);
   		}
   	}

   	private static int partitionner( int[] tab, int deb, int fin )
   	{
   		return 0;
   	}

   	public static void main(String[] args)
   	{
   		int[] tab = new int[]{9,5,2,3,5,7,8,2,1,4};
   		triSelection(tab);
   		//triBulle(tab);

   		for(int cpt=0; cpt<tab.length; cpt++)
   			System.out.print(tab[cpt] +" ");

   		System.out.println();
   	}

   	/**
   	* @constructor: privé et ne fait rien car classe utilitaire non instnciable
   	*/
	private TriUtil(){}
}