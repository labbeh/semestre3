public class BitParite extends CodeDetecteur
{
	public BitParite(int n, int k)
	{
		super(n, k);
	}

	@Override
	public boolean aProbleme(MotBinaire bits)
	{
		int b1;
		int b2;
		int ctrl;

		for(int cpt=0; cpt<bits.nbBits(); cpt+=3)
		{
			b1 = bits.get(cpt);
			b2 = bits.get(cpt+1);
			ctrl = bits.get(b1 != b2 ? 1:0);

			if(b1 != b2 && b1+b2 != ctrl) return false;
			else if((b1 == b2) && (ctrl != (b1+b2))) return false;
		}

		return true;
	}

	@Override
	public MotBinaire securiser(MotBinaire bits)
	{
		return null;
	}
}