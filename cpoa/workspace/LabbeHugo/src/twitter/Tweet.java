package twitter;

public class Tweet
{
	private String dateT;
	private String compte;		
	private String langue;		
	private int nbAbonnes;		
	private String texteTweet; 		
	private int nbRetweet;		
	private int nbFavoris;
	
	/**
	 * Constructeur à partir des champs d'un tweet
	 * */
	public Tweet(String dateT, String compte, String langue, int nbAbonnes,
			String texteTweet, int nbRetweet, int nbFavoris)
	{
		this.dateT = dateT;
		this.compte = compte;
		this.langue = langue;
		this.nbAbonnes = nbAbonnes;
		this.texteTweet = texteTweet;
		this.nbRetweet = nbRetweet;
		this.nbFavoris = nbFavoris;
	}

	public String getCompte()
	{
		return compte;
	}

	public String getDateT()
	{
		return dateT;
	}

	public String getLangue()
	{
		return langue;
	}

	public int getNbAbonnes()
	{
		return nbAbonnes;
	}

	public String getTexteTweet()
	{
		return texteTweet;
	}

	public int getNbRetweet()
	{
		return nbRetweet;
	}

	public int getNbFavoris()
	{
		return nbFavoris;
	}

	public void setCompte(String compte)
	{
		this.compte = compte;
	}

	public void setDateT(String dateT)
	{
		this.dateT = dateT;
	}

	public void setLangue(String langue)
	{
		this.langue = langue;
	}

	public void setNbAbonnes(int nbAbonnes)
	{
		this.nbAbonnes = nbAbonnes;
	}

	public void setTexteTweet(String texteTweet)
	{
		this.texteTweet = texteTweet;
	}

	public void setNbRetweet(int nbRetweet)
	{
		this.nbRetweet = nbRetweet;
	}

	public void setNbFavoris(int nbFavoris)
	{
		this.nbFavoris = nbFavoris;
	}

	@Override
	public String toString()
	{
		return "Tweet [compte=" + compte + ", dateT=" + dateT + ", langue="
				+ langue + ", nbAbonnes=" + nbAbonnes + ", texteTweet="
				+ texteTweet + ", nbRetweet=" + nbRetweet + ", nbFavoris="
				+ nbFavoris + "]";
	}
	
	
	
	
}
