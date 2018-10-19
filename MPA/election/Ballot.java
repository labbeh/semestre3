package vote;

import java.util.Set;
import java.util.HashSet;
/**
 * Bulletin de vote appartenant à un corps électroal et permettant de faire plusieurs choix
 * @author Hugo Labbé, INFO2, Groupe D
 * @version 1.0, 2018-10-19
*/

class Ballot
{
	/**
	 * Liste ordonée de choix, peut être vide (vote blanc)
	*/
	private Set<Choice> choiceList;

	/**
	 * initialise le bulletin
	*/
	Ballot()
	{
		this.choiceList = new HashSet<>();
	}

}