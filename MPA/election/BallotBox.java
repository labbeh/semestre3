package vote;

import java.util.Set;
import java.util.HashSet;

/**
 * Urne de notre système de vote
 * @author Hugo Labbé, INFO2, Groupe D
 * @version 1.0, 2018-10-19
*/

class BallotBox
{
	/**
	 * contient les bulletins de l'urne
	*/
	private Set<Ballot> ballotList;

	/**
	 * créé une nouvelle urne
	*/
	BallotBox()
	{
		this.ballotList = new HashSet<>();
	}

	/**
	 * insérer un bulletin dans l'urne
	*/
	void addBallot(Ballot b)
	{
		this.ballotList.add(b);
	}
}