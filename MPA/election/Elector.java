package vote;

/**
 * Electeur apprtenant à un corps électroal
 * @author Hugo Labbé, INFO2, Groupe D
 * @version 1.0, 2018-10-19
*/
class Elector
{
	/**
	 * Identifiant des lecteurs
	*/
	private String name;

	/**
	 * indique si l'électeur à voté ou non
	*/
	private boolean hasVoted;

	/**
	 * créé un nouvel électeur pour le système
	 * @param identifiant de l'électeur
	*/
	Elector( String name )
	{
		this.name = name;
		this.hasVoted = false;
	}

	/**
	 * vote
	*/
	void vote(Choice[] choices)
	{
		if(!this.hasVoted) // avoir créé le bulletin
		{

		}
	}

	/**
	 * retourne si l'électeur à voté ou non
	*/
	boolean hasVoted(){ return this.hasVoted; }

	/**
	 * @return identifiant de l'électeur
	*/
	String getName(){ return this.name; }
}