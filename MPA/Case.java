package geometrie;

/**
	* Cette classe connait ses 4 voisins
	* @author : Hugo Labbé
	* @version: 1.0
	* @date   : 2018-09-07
*/

public class Case
{	
	private Case[] voisins	  ;
	private char   orientation;

	/**
		* @constructor: constructeur vide d'une case, initialise un tableau de 4 voisins selon l'ordre N O S E de 0 à 3
	*/
	public Case()
	{
		this.voisins = new Case[4];
	}

	/* ACCESSEURS */

	/**
	*	@param: position du voisin N O S ou E
	*/
	public Case getVoisin(char pos)
	{
		switch(pos)
		{
			case 'N': return this.voisins[0];
			case 'O': return this.voisins[1];
			case 'S': return this.voisins[2];
			case 'E': return this.voisins[3];
		}
	}

	/* MODIFICATEURS */

	/**
	*	@param: voisin à ajouté, position N O S E
	*/
	public void setVoisin(Case voisin, char orientation)
	{
		switch(orientation)
		{
			case 'N': this.voisins[0] = orientation; break;
			case 'O': this.voisins[1] = orientation; break;
			case 'S': this.voisins[2] = orientation; break;
			case 'E': this.voisins[3] = orientation; break;
		}
	}

	/* Méhodes java */

	/**
	 *	@return: toString renvoyant la position de cette case ainsi que le nombre de voisins
	*/
	@Override
	public String toString()
	{
		String sRet = new String("Orientation " +this.orientation);

		int nbVoisins;

		for(int cpt=0; cpt<this.voisins.length; cpt++)
			if(this.voisins[cpt] != null) nbVoisins++;

		sRet += (" " +nbVoisins+ " voisins");
		return sRet;
	}
}