public class Denombrement
{
	public static int factorielle(int n)
	{
		if(n == 0 ) return  1; //fonctionne aussi
		//if(n <  0 ) return -1; // code erreur
		if(n<2) return 1;

		//int fact = n*n-1;
		return n*factorielle(n-1);
	}

	public static int cnkFacorielle(int n, int k)
	{
		return 0;
	}

	public static int cnkPascal(int n, int k)
	{
		return 0;
	}

	public static int cnkRecursif(int n, int k)
	{
		return 0;
	}

	public static void main(String[] args)
	{
		System.out.println(Denombrement.factorielle(5));
	}
}