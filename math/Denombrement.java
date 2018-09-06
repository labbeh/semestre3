public class Denombrement
{
	public static int factorielle(int n)
	{
		int iRet = n;

		if(n > 0) factorielle(n*(n-1));

		return n;
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