public class RechercheTab
{
	public static int rechercheMinRec ( int[] tab, int indDeb, int indFin )
	{
		if(indDeb == indFin) return tab[indDeb];

		int milieu = (indDeb + indFin) / 2;
		int min1   = rechercheMinRec(tab, indDeb, milieu);
		int min2   = rechercheMinRec(tab, milieu+1, indFin);

		return (min1<min2 ? min1:min2);
	}

	public static void main(String[] args)
	{
		int[] tab = new int[]{1, 2, 9, 2, 5, 7, 0, 3, -15};
		System.out.println(RechercheTab.rechercheMinRec(tab, 0, tab.length-1));
	}
}