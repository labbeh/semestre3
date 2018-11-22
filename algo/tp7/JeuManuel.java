import iut.algo.Console;
import iut.algo.CouleurConsole;


public class JeuManuel
{
	public static void main(String[] a)
	{
		char action;

		Berger partie = new Berger();


		Console.println ( "\n\n" + partie );


		while ( !partie.gagne() && ! partie.perdu() )
		{

			System.out.print ( "           Action : ");
			Console.couleurFont ( CouleurConsole.VERT );
			action = Console.lireChar();

			if ( ! partie.deplacer(action) )
			{
				Console.couleurFont ( CouleurConsole.ROUGE );
				Console.println ( "           Action invalide" );
			}

			Console.normal();
			Console.println ( "\n\n" + partie );

		}





		if ( partie.gagne() )
		{
			Console.gras();
			Console.couleurFont ( CouleurConsole.VERT );
			Console.println ( "           GAGNE" );
		}
		else
		{
			Console.gras();
			Console.couleurFont ( CouleurConsole.ROUGE );
			Console.println ( "           PERDU" );
		}

		Console.normal();

	}
}
