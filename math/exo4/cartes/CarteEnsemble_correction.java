
import java.util.ArrayList;
import java.util.List;
/**
* Classe pour gérer un ensemble de cartes
* Basé sur la correction envoyée par mail
*/
public class CarteEnsemble
{
	/**
	* Attributs
	*/
	private List<Carte> sac; // l'ensemble de cartes

	/**
	* Constructeurs*/

	public CarteEnsemble()
	{
		this.sac= new ArrayList<Carte>();
	}
	public CarteEnsemble(int n)
	{
		 this();
		  if (n==52)
		  for(CarteEnseigne couleur : CarteEnseigne.values())
		   for(CarteValeur valeur : CarteValeur.values ())
		    this.sac.add(new Carte(couleur,valeur));
	}

	/**
	* ajouter une carte
	* @param nouvelle nouvelle carte dans le sac
	*/
	public void ajouter(Carte nouvelle)
	{
		this.sac.add(nouvelle);
	}

	/**
	* ajouter un ensemble de cartes
	* @param nouvelles ajouter un autre ensemble dans le sac courant
	*/
	public void ajouter(Carte[] nouvelles)
	{
		for(Carte carte : nouvelles) this.sac.add(carte);
	}

	/**
	* ajouter un ensemble de cartes
	* @param nouvelles ajouter un autre ensemble dans le sac courant
	*/
	public void ajouter(CarteEnsemble nouvelles)
	{
		for(Carte carte : nouvelles.sac) this.sac.add(carte);
	}

	/**
	* contient une carte
	*@param carte est-ce que carte est dans le sac
	*/
	public boolean contient(Carte carte)
	{
		return this.sac.contains(carte);
	}

	/**
	* contient une carte avec enseigne
	* @param enseigne est-ce qu'il y a une carte de cette enseigne dans le sac
	*/
	public boolean contient(CarteEnseigne enseigne)
	{
		for(Carte carte : this.sac)
		if (carte.getEnseigne()==enseigne) return true;
		return false;
	}

	/**
	* contient une carte avec valeur
	* @param valeur est-ce qu'il y a une carte de cette valeur dans le sa
	*/
	public boolean contient(CarteValeur valeur)
	{
		for(Carte carte : this.sac)
		 if (carte.getValeur()==valeur) return true;
		return false;
	}

	/**
	* nombre de cartes
	* @return le nombre de cartes dans le sac
	*/
	public int getNombre() {return this.sac.size();}

	/**
	* tirer une carte avec remise
	* @return une carte du sac sans la retirer
	*/
	public Carte tirerAvecRemise()
	{
		return this.sac.get((int)(Math.random()*this.sac.size()));
	}

	/**
	* tirer une carte sans remise
	* @return un carte du sac qui est retirée de celui-ci
	*/
	public Carte tirerSansRemise()
	{
		return this.sac.remove((int) (Math.random()*this.sac.size()));
	}
	/**
	* retourner une k-liste
	* @return une k-liste du sac
	* @param k le nombre d'éléments dans la k-liste
	*/
	public Carte[] DonnerKListe(int k)
	{
		Carte[] r = new Carte[k];
		for(int i=0;i<k;i++) r[i] = this.tirerAvecRemise();
		return r;
	}

	/**
	* retourner une k-arrangement
	* return une k-arrangement du sac
	* param k le nombre d'éléments dans la k-liste
	*/
	public Carte[] donnerKArrangement(int k)
	{
		 Carte[] r = new Carte[k];
		 for(int i=0;i<k;i++) r[i] = this.tirerSansRemise();
		 return r;
	}

	/**
	* retourner une k-combinaison
	* @return une k-combinaison du sac
	*/
	 public CarteEnsemble donnerKCombinaison(int k)
	 {
		CarteEnsemble ens = new CarteEnsemble();
		ens.ajouter(this.DonnerKArrangement(k));
		return ens;
	 }
}





