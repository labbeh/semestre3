public class Hanoi
{
	public static void hanoi ( int nbDisque, int tourDeb, int tourFin )
	{
		if(nbDisque>0) hanoi(nbDisque-1, tourDeb, 6-tourFin-tourDeb);
		

		hanoi(nbDisque-1, 6-tourFin-tourDeb, tourFin);
	}

	public static void main(String[] args)
	{
		hanoi(3, 1, 6);
	}
}