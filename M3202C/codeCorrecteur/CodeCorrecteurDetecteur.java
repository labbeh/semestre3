public abstract class CodeCorrecteurDetecteur
{
	/**
	* @author : Labbé Hugo D2
	* @version: 1.1, 2018-09-13
	*/

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
		StringBuilder mot = new StringBuilder();

		for(int cpt=0; cpt<bits.nbBits(); cpt++)
		{
			mot.append(bits.get(cpt));
			if(cpt % n == 0) mot.append((int)(Math.random() * 2));
		}
		
		return MotBinaire.fabrique(mot.toString());
	}

	public MotBinaire desecuriser(MotBinaire bits)
	{
		int[] motRet  = new int[this.nbBitsCode];
		int cptTabMot = 0;

		for(int cpt=0; cpt<bits.nbBits(); cpt++)
		{
			if(cpt % (this.nbBitsCode-this.nbBitsInfo) != 0)
				motRet[cptTabMot++] = bits.get(cpt);
		}

		return MotBinaire.fabrique(motRet);
	}

	public abstract MotBinaire securiser(MotBinaire bits);
}