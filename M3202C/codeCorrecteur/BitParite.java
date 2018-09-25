public class BitParite extends CodeDetecteur
{
	public BitParite(int n, int k)
	{
		super(n, k);
	}

	@Override
	public boolean aProbleme(MotBinaire bits)
	{
		return false;
	}

	@Override
	public MotBinaire securiser(MotBinaire bits)
	{
		return null;
	}
}