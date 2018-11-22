import iut.algo.CouleurConsole;

public class Berger
{
	private char     posL;
	private char     posM;
	private char     posC;
	private char     posB;

	public Berger()
	{
		this.posL = 'N';
		this.posM = 'N';
		this.posC = 'N';
		this.posB = 'N';
	}


	public char traverse(char c)
	{
		if ( c == 'N' ) return 'S';
		return 'N';
	}


	public boolean perdu()
	{
		return this.posB!=this.posM && ( this.posL==this.posM ||  this.posM==this.posC );
	}


	public boolean gagne ()
	{
		return this.posL == 'S' && this.posM == 'S' && this.posC == 'S' && this.posB == 'S';
	}


	public boolean deplacer(char p)
	{
		if ( p == 'B'                           ){ this.posB = traverse (this.posB);               return true; }
		if ( p == 'L' && this.posB == this.posL ){ this.posB = this.posL = traverse(this.posB);    return true; }
		if ( p == 'M' && this.posB == this.posM ){ this.posB = this.posM = traverse(this.posB);    return true; }
		if ( p == 'C' && this.posB == this.posC ){ this.posB = this.posC = traverse(this.posB);    return true; }

		return false;
	}


	public String toString()
	{
		return  "Rive Nord\n\n"                    +

		        ( this.posB == 'N' ? "B " : "  " ) +
		        ( this.posL == 'N' ? "L " : "  " ) +
		        ( this.posM == 'N' ? "M " : "  " ) +
		        ( this.posC == 'N' ? "C " : "  " ) + "\n" +

		        "-------------------\n"            + CouleurConsole.BLEU.getFond() +
		        "~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n"            + CouleurConsole.NOIR.getFond() +
		        "-------------------\n"            +

		        ( this.posB == 'S' ? "B " : "  " ) +
			   ( this.posL == 'S' ? "L " : "  " ) +
			   ( this.posM == 'S' ? "M " : "  " ) +
			   ( this.posC == 'S' ? "C " : "  " ) +

			   "\n\nRive Sud\n";
	}
}
