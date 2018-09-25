public abstract class CodeCorrecteur extends CodeCorrecteurDetecteur
{
	public CodeCorrecteur(int n, int k)
	{
		super(n, k);
	}

	public abstract MotBinaire corriger(MotBinaire bits);
}