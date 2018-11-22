public class Exo2 // exo1 = la même chose sans le if
{
	public static void main(String[] argv)
	{
		char[] tab = new char[]{'B', 'L', 'M', 'C'};

		for(int i=0; i<tab.length; i++)
			for(int i2=0; i2<tab.length; i2++)
				if(tab[i] != tab[i2])
					System.out.println(tab[i] +" "+ tab[i2]);

	}
}