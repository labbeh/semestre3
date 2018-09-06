// question 4
import java.math.BigInteger;

public class AireCarre4
{
	public static BigInteger cote(int n)
	{
		BigInteger a = BigInteger.ONE;
		BigInteger b = BigInteger.ONE;

		BigInteger som;

		for(int cpt=2; cpt<n; cpt++)
		{
			som = a.add(b);
			a = b;
			b = som;
		}

		return b;
	}

	public static void main(String[] args)
	{
		BigInteger cote;

		for(int n=-1; n<=150; n++)
		{
			cote = AireCarre4.cote(n);
			System.out.println( "n: " 	+String.format("%3d", n)+
								" ===> "+String.format("%35d", cote)+
								" x "	+String.format("%35d", cote)+
								" = "	+String.format("%65s", cote.pow(2))
							  );
		}
	}
}