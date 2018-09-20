public abstract class CodeCorrecteurDetecteur
{
	/**
	* @author : Labbé Hugo D2
	* @version: 1.1, 2018-09-13
	*/

	// ATTRIBUTS
	private int n;
	private int k;

	/**
	* @constructor: prend en paramètre le nombre de bits de mots de code et le nombre de bits d'info du MotBinaire
	*/
	public CodeCorrecteurDetecteur(int n, int k)
	{
		this.n = n;
		this.k = k;
	}

	public MotBinaire encoder(String 	 message )
	{
		String sRet = new String();

		for(int cpt=0; cpt<message.length(); cpt++) sRet += encoder(message.charAt(cpt));

		return MotBinaire.fabrique(sRet);
	}

	public String encoder(char c)
	{
		return String.format("8%s", Integer.toBinaryString(c).replace(' ', '0'));
	}

	public String 	  decoder(MotBinaire bits	 )
	{
		String sRet = new String();

		for(int cpt=0; cpt<=bits.nbBits()-8; cpt+=8)
			sRet += (char)Integer.parseInt(bits.sousMot(cpt, cpt+8).toString(), 2);

		return sRet;
	}

	public MotBinaire transmettre(MotBinaire bits, int n)
	{
		MotBinaire ret = new MotBinaire(bits);

		for(int cpt=0; cpt<ret.nbBits(); cpt++)
			if(Math.random()*n<1) ret.set(cpt, (ret.get(cpt) == 0 ? 1 : 0));
		return ret;
	}

	public MotBinaire desecuriser(MotBinaire bits)
	{
		String sRet = new String();

		for(int cpt=0; cpt<bits.nbBits(); cpt+=this.n)
			sRet += bits.sousMot(cpt, cpt+this.k);

		return MotBinaire.fabrique(sRet);
	}

	public abstract MotBinaire securiser(MotBinaire bits);
}