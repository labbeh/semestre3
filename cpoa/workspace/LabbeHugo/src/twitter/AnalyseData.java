package twitter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class AnalyseData
{
	public ArrayList<Tweet> listeTweet;	
	public HashSet<String> listeComptes;
	public HashMap<String,ArrayList<Tweet>> hashTweet;
	private HashMap<Integer, HashSet<String>> listeMaxRetweet;
	
	public AnalyseData(String nomFichier)
	{
		// String compte, String dateT, String langue, int nbAbonnes,
		// String texteTweet, int nbRetweet, int nbFavoris
		this.listeTweet   	 = new ArrayList<>();
		this.listeComptes 	 = new HashSet<>();
		this.hashTweet    	 = new HashMap<>();
		this.listeMaxRetweet = new HashMap<>();
		
		InputStream ips = this.getClass().getResourceAsStream("/" +nomFichier);
		InputStreamReader ipsr = new InputStreamReader(ips);
		Scanner sc = new Scanner(ipsr);
		
		// construction de la liste de tweet et de la liste de compte
		while(sc.hasNextLine())
		{
			Scanner scLig = new Scanner(sc.nextLine());
			scLig.useDelimiter(";");
			
			Tweet temp = new Tweet(scLig.next(), scLig.next(), scLig.next(), scLig.nextInt(),
					  			   scLig.next(), scLig.nextInt(), scLig.nextInt());
			
			// pour enlever le "@" devant le nom du compte
			String compte = temp.getCompte().substring(1, temp.getCompte().length());
			temp.setCompte(compte);
			
			this.listeTweet.add(temp);
			this.listeComptes.add(compte);
		}
		sc.close();
		
		// construction du tableau associatif entre un compte tweeter et les tweet qu'il a publié
		this.hashTweet = AnalyseData.groupeTweet(this.listeTweet);
		
		// tableau associatif du max de retweets par compte
		this.listeMaxRetweet = this.listerMaxRetweet();
	}
	
	/**
	 * Retourne un tableau associatif HashMap qui prend comme clef un nom de compte twitter
	 * auquel on associe une ArrayList de ses tweets
	 * @return HashMap de tweets associés à un compte twitter
	 * */
	private static HashMap<String, ArrayList<Tweet>> groupeTweet(ArrayList<Tweet> alT)
	{
		HashMap<String, ArrayList<Tweet>> hashTweet = new HashMap<>();
		for(Tweet t: alT)
		{
			if(!hashTweet.containsKey(t.getCompte()))
				hashTweet.put(t.getCompte(), new ArrayList<Tweet>());
			
			hashTweet.get(t.getCompte()).add(t);
		}
		
		return hashTweet;
	}
	
	/**
	 * Affiche sur la sortie standard les tweets du compte en paramètre
	 * @param nom du compte twitter
	 * */
	public void afficheTweet(String nomCompte)
	{
		ArrayList<Tweet> alT = this.hashTweet.get(nomCompte);
		
		if(alT == null){
			System.out.println("Erreur: nom de compte inexistant");
			return;
		}
		
		System.out.println("Compte " +nomCompte+ ": ");
		
		for(Tweet t: alT)
			System.out.println(t);
			
	}
	
	
	/**
	 * Accesseur nécessaire pour vérifier le contenue de la List de Tweet dans le main
	 * @return ArrayList de Tweet
	 * */
	public ArrayList<Tweet> getListeTweet()
	{
		return listeTweet;
	}
	
	/**
	 * @param nom d'un compte (String)
	 * @return nombre de tweets publiés pas le compte, retourne -1 si le compte n'existe pas
	 * */
	public int nbTweet(String compte)
	{
		ArrayList<Tweet> alT = this.hashTweet.get(compte);
		
		if(alT == null) return -1;
		
		return alT.size();
	}
	
	/**
	 * Renvoie le nombre max de retweet obtenu par un compte
	 * @param nom du compte
	 * @return nbr max de retweet, -1 si compte inexistant
	 * */
	public int maxRetweet(String compte)
	{
		ArrayList<Tweet> alT = this.hashTweet.get(compte);
		int nbRt = 0;
		
		if(alT == null) return -1;
		
		for(Tweet t: alT)
			if(nbRt < t.getNbRetweet())nbRt = t.getNbRetweet();
		
		return nbRt;
	}
	
	/**
	 * @return HashMap qui associe un nbr max de retweet à un Set de nom de compte
	 * */
	private HashMap<Integer, HashSet<String>> listerMaxRetweet()
	{
		HashMap<Integer, HashSet<String>> map = new HashMap<>();
		
		for(String compte: this.listeComptes)
		{
			int nbRtMax = this.maxRetweet(compte);
			
			if(!map.containsKey(nbRtMax))
				map.put(new Integer(nbRtMax), new HashSet<String>());
			
			map.get(nbRtMax).add(compte);
		}
		
		return map;
	}
	
	/**
	 * Affiche sur la sortie standard le nombre de tweets que chaque compte a publié
	 * */
	public void afficheNbTweet()
	{
		for(String compte: this.listeComptes)
			System.out.println(compte +" : " +nbTweet(compte));
	}
	
	/**
	 * Affiche sur la sortie standard le nombre max de retweets obentues par chaques comptes
	 * */
	public void afficheMaxRetweet()
	{
		Set<Integer> clefs = this.listeMaxRetweet.keySet();
		
		for(Integer i: clefs)
		{
			System.out.print(i +": ");
			HashSet<String> hs = this.listeMaxRetweet.get(i);
			
			for(String compte: hs)
				System.out.print(" " +compte);
			
			System.out.println();
		}
	}

	public void setListeTweet(ArrayList<Tweet> listeTweet)
	{
		this.listeTweet = listeTweet;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		AnalyseData ad = new AnalyseData("TWEXLIST_Home_Timeline_fr.csv");
		
		// affiche les 10 premiers tweets
		for(int i=0; i<10; i++) System.out.println(ad.getListeTweet().get(i));
		System.out.println("------------------");
		
		// affichage tweets associés à un compte
		System.out.println("Nombre de tweets du compte @CompteInfosNews: ");
		ad.afficheTweet("CompteInfosNews");
		System.out.println("------------------");
		
		// nombre de tweets d'un compte
		System.out.println("Nombre de tweets du compte @CompteInfosNews: " 
							+ad.nbTweet("CompteInfosNews"));
		System.out.println("------------------");
		// nombre de tweets par compte
		ad.afficheNbTweet();
		
		System.out.println("------------------");
		
		// max retweets par comptes:
		System.out.println("Max retweets par comptes: ");
		ad.afficheMaxRetweet();
	}

}
