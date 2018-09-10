public class Denombrement
{
	public static int factorielle(int n)
	{
		if(n == 0 ) return  1;
		if(n <  0 ) return -1; // code erreur

		return n*factorielle(n-1);
	}

	public static int cnkFactorielle(int n, int k)
	{
		if(k >  n) return 0;
		if(k <  0) return 0;
		if(k == 0) return 0;
		if(n <  0) return 0;

		return Denombrement.factorielle(n) / (Denombrement.factorielle(k) * (Denombrement.factorielle(n-k)));
	}

	public static int cnkPascal(int n, int k)
	{
		if(k <  0 || n <  k) return 0;
		if(k == 0 || n == k) return 1;

		return Denombrement.cnkPascal(n-1, k-1) + Denombrement.cnkPascal(n-1, k);
	}

	public static int cnkRecursif(int n, int k)
	{
		if(k <  0 || n < k) return 0;
		if(k == 0		  ) return 1;

		return n * Denombrement.cnkRecursif(n-1, k-1) / k;
	}

	public static void main(String[] args)
	{
		System.out.println(Denombrement.cnkFactorielle(42, 23));
	}
}