// question 4
// question 5

public class AireCarre6
{
	public static long cote(int n)
	{
		if(n<3) return 1;

		return AireCarre6.cote(n-1) + AireCarre6.cote(n-2);
	}

	public static void main(String[] args)
	{
		long cote = AireCarre6.cote(3);
		System.out.println(cote*cote);

		for(int n=-1; n<=20; n++)
		{
			cote = AireCarre6.cote(n);
			System.out.println( "n: " 	+String.format("%2d", n)+
								" ===> "+String.format("%10d", cote)+
								" x "	+String.format("%10d", cote)+
								" = "	+String.format("%20d", cote*cote)
							  );
		}
	}
}