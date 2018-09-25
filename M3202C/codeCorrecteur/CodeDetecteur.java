public abstract class CodeDetecteur extends CodeCorrecteurDetecteur
{
	public CodeDetecteur(int n, int k)
	{
		super(n, k);
	}

	public abstract boolean aProbleme(MotBinaire bits);
}