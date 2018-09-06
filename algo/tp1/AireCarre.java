import java.math.BigInteger;

public class AireCarre
{
	public static void main(String[] args)
	{
		//System.out.print("n= ");
		//int n = new java.util.Scanner(System.in).nextInt();

		/*for(int cpt=1; cpt<56; cpt++)
		{
			BigInteger bi = new BigInteger(""+cpt);

			System.out.println("n" +cpt+ ": " +bi.pow(2));
		}*/

		//int aire = n*n;
		//System.out.println("aire: " +aire);

		// question 2
		System.out.println(cote(8 ) * cote(8 ));
		System.out.println(cote(15) * cote(15));
		System.out.println(cote(25) * cote(25));

		// question 3
		System.out.println();
		System.out.println("question 3");

		for(int n=-1; n<=25; n++)
		{
			int cote = AireCarre.cote(n);
			System.out.println( "n: " 	+String.format("%3d", n)+
								" ===> "+String.format("%10d", cote)+
								" x "	+String.format("%10d", cote)+
								" = "	+String.format("%20d", cote*cote)
							  );
		}
	}

	// question 1
	public static int cote(int n)
	{
		int a=1;
		int b=1;

		int som;

		for(int cpt=2; cpt<n; cpt++)
		{
			som = a+b;

			a = b;
			b = som;
		}
		return b;
	}
}

/*
	1 	Ecrire dans une classe AireCarre, un programme permettant de calculer l'aire du nème carré.
	2 	Calculer l'aire pour n =8 n=15 et n=25
	3 	Adapter le programme pour qu'il affiche ∀ n ∈ [1;55], les aires de chaque carré
	4 	Ecrire une version qui utliserait la classe BigInteger
	5 	Essayer de formuler autrement l'algortihme qui calcule la valeur du côté
	6 	Ecrire dans une classe AireCarreRec, une nouvelle version à partir de la solution à la question précédente.
*/