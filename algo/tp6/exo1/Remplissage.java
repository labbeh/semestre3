public class Remplissage
{
	public static void main(String[] a)
	{

		char[][] tab= new char[][] { {' ', ' ', ' ', ' ', 'X', ' ', ' ', ' ', ' ', ' ' },
		                             {' ', ' ', ' ', 'X', ' ', 'X', ' ', ' ', ' ', ' ' },
		                             {' ', ' ', 'X', ' ', ' ', ' ', 'X', ' ', ' ', ' ' },
		                             {' ', 'X', ' ', ' ', ' ', ' ', 'X', ' ', ' ', ' ' },
		                             {' ', 'X', ' ', ' ', ' ', ' ', 'X', 'X', ' ', ' ' },
		                             {' ', 'X', ' ', ' ', ' ', ' ', ' ', 'X', ' ', ' ' },
		                             {' ', 'X', 'X', ' ', ' ', ' ', ' ', 'X', ' ', ' ' },
		                             {' ', ' ', 'X', ' ', ' ', ' ', ' ', 'X', ' ', ' ' },
		                             {' ', ' ', 'X', 'X', 'X', 'X', 'X', ' ', ' ', ' ' },
		                             {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }  };


		System.out.println ( Remplissage.tabEnChaine(tab) );

		Remplissage.remplir ( tab, 1, 1, '.' );

		System.out.println ( Remplissage.tabEnChaine(tab) );

	}

	private static void remplir2 ( char[][] tab, int lig, int col, char cara)
	{
		if(col >= tab[0].length)
		{
			col = 0;
			lig++;
		}

		if(lig >= tab.length) return;
		
		if(tab[lig][col] == ' ') tab[lig][col] = cara;

		col++;
		remplir(tab, lig, col, cara);
	}

	private static void remplir ( char[][] tab, int lig, int col, char cara)
	{
		if(lig >= 0 && lig < tab.length && col >= 0 && col < tab[0].length && tab[lig][col] == ' ')
		{
			tab[lig][col] = cara;
			remplir(tab, lig-1, col  , cara);
			remplir(tab, lig+1, col  , cara);
			remplir(tab, lig  , col-1, cara);
			remplir(tab, lig  , col+1, cara);
		}
	}


	private static String tabEnChaine(char[][] tab)
	{

		String sRet = "";

		sRet += "/" + String.format("%"+tab[0].length+"s","").replace (' ', '-') + "\\\n";

		for (int lig=0;lig<tab.length;lig++)
		{
			sRet += "|" ;

			for (int col=0;col<tab[lig].length;col++)
				sRet += tab[lig][col];

			sRet += "|\n";
		}

		sRet += "\\" + String.format("%"+tab[0].length+"s","").replace (" ", "-") + "/\n";

		return sRet;
	}


}