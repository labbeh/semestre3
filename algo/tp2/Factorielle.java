public class Factorielle
{
	// ou Integer qui retourne null
	public static int factorielle(int n)
	{
		if(n == 0 ) return  1;
		if(n <  0 ) return -1; // code erreur

		int fact = n;

		for(int cpt=1; cpt<n; cpt++) fact *= cpt; // cpt=2 et cpt<=n
		return fact;
	}

	public static int factorielleRec(int n)
	{
		//if(n == 0 ) return  1; //fonctionne aussi
		if(n <  0 ) return -1; // code erreur
		if(n<2) return 1;

		//int fact = n*n-1;
		return n*factorielleRec(n-1);
	}

	public static void main(String[] args)
	{
		System.out.println(factorielle(4));
		System.out.println(factorielle(8));

		System.out.println(factorielleRec(4));
		System.out.println(factorielleRec(8));
	}
}