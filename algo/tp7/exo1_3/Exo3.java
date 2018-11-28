public class Exo3
{
	public static void main(String[] argv)
	{
		char[] tab = new char[]{'L', 'M', 'C', 'B'};

		for(int i=0; i<tab.length; i++)
			for(int i2=0; i2<tab.length; i2++)
				for(int i3=0; i3<tab.length; i3++)
					if(tab[i] != tab[i2])
						if(tab[i2] != tab[i3])
							System.out.println(tab[i] +" "+ tab[i2] +" "+ tab[i3]);

	}
}