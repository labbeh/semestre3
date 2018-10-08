public class DeSimulation
{
	public static int lancer()
	{
		return (int)(Math.random() * 6) + 1;
	}

	public static double sommeSuperieurA6(int n)
	{
		int de1, de2, sommeDe;
		double nbReussite;

		de1 = 0;
		de2 = 0;
		sommeDe = 0;

		nbReussite = 0;

		for (int cpt=0; cpt<n; cpt++) 
		{
			de1 = lancer();
			de2 = lancer();

			sommeDe = de1 + de2;

			if(sommeDe > 6) nbReussite++;
		}

		return nbReussite / n;
	}

	public static void main(String[] args)
	{
		System.out.println(sommeSuperieurA6(5));
	}
}