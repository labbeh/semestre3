public class Hanoi
{
	private static int nbCoup = 0;

	public static void hanoi ( int nbDisque, int tourDeb, int tourFin )
	{
		if(nbDisque>0)
		{
			hanoi(nbDisque-1, tourDeb, 6-tourFin-tourDeb);

			System.out.println(String.format("3%d", ++nbCoup) +" : "+ tourDeb+ " --> " +tourFin);

			hanoi(nbDisque-1, 6-tourFin-tourDeb, tourFin);
		}
		

		//hanoi(nbDisque-1, 6-tourFin-tourDeb, tourFin);
	}

	public static void hanoiDebug ( String sep, int nbDisque, int tourDeb, int tourFin )
	{
		if(nbDisque>0) System.out.println(); else System.out.println(" stop");

		if(nbDisque>0)
		{
			hanoiDebug(sep +" |   ", nbDisque-1, tourDeb, 6-tourFin-tourDeb);

			System.out.println(sep + " |   |");
			System.out.println(sep + " |   affiche " +tourDeb + " --> " +tourFin);
			System.out.println(sep + " |   |");

			hanoiDebug( sep + " |   ", nbDisque-1, 6-tourFin-tourDeb, tourFin);
		}
		

		//hanoi(nbDisque-1, 6-tourFin-tourDeb, tourFin);
	}

	public static void main(String[] args)
	{
		//System.out.println()
		//hanoi(3,1,3);
		hanoiDebug(" ", 3,1,3);
	}
}