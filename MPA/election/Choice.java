package vote;

/**
 * Choix apprtenant à un corps électroal
 * @author Hugo Labbé, INFO2, Groupe D
 * @version 1.0, 2018-10-19
*/

class Choice
{
	/**
	 * Identifiant des choix
	*/
	private String idChoix;

	/**
	 * créé un nouveau choix dans pour système
	 * @param identifiant du choix
	*/
	Choice( String idChoix )
	{
		this.idChoix = idChoix;
	}

	String getIdChoix(){ return this.idChoix; }
}