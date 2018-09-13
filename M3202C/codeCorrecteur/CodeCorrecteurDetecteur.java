public abstract class CodeCorrecteurDetecteur
{
	/**
	* @author: labbeh
	* @date  : 2018-09-13
	*/

	// CONSTANTES BITS POSSIBLES
	private static final int BIT_ZERO = 0;
	private static final int BIT_UN	  = 1;

	// ATTRIBUTS
	private int nbBitsCode;
	private int nbBitsInfo;

	/**
	* @constructor: prend en paramètre le nombre de bits de mots de code et le nombre de bits d'info du MotBinaire
	*/
	public CodeCorrecteurDetecteur(int nbBitsCode, int nbBitsInfo)
	{
		this.nbBitsCode = nbBitsCode;
		this.nbBitsInfo = nbBitsInfo;
	}

	public MotBinaire encoder(String 	 message ) { return MotBinaire.fabrique(message); }
	public String 	  decoder(MotBinaire bits	 ) { return bits.toString()				; }

	public MotBinaire transmettre(MotBinaire bits, int n)
	{
		int bitAct;

		for(int cpt=0; cpt<bits.nbBits(); cpt++)
		{
			if(cpt % n == 0)
			{
				bitAct = bits.get(cpt);

				if(bitAct == CodeCorrecteurDetecteur.BIT_ZERO) bits.set(cpt, CodeCorrecteurDetecteur.BIT_UN	 );
				else 										   bits.set(cpt, CodeCorrecteurDetecteur.BIT_ZERO);
			}
		}
		
		return new MotBinaire(bits);
	}

	public MotBinaire desecuriser(MotBinaire bits)
	{
		int[] motRet = new int[this.nbBitsCode];
		int cptTabMot = 0;

		for(int cpt=0; cpt<bits.nbBits(); cpt++)
		{
			if(cpt % this.nbBitsCode-this.nbBitsInfo != 0)
				motRet[cptTabMot++] = bits.get(cpt);
		}

		return MotBinaire.fabrique(motRet);
	}

	public abstract MotBinaire securiser(MotBinaire bits);
}