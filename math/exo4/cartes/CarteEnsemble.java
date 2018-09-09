import java.util.ArrayList;

public class CarteEnsemble
{
	private ArrayList<Carte> ensCarte;

	/**
	* @constructor: créé un ensemble vide de carte
	*/
	public CarteEnsemble()
	{
		this.ensCarte = new ArrayList<>();
	}

	/**
	* @constructor: créé un ensemble de 32 cartes (n=32) peut importe le nombre passé en paramètre
	* 				il ne sert qu'à différencier les constructeurs
	*/
	public CarteEnsemble(int n )
	{
		// le 32 en paramètre ne fige pas la taille de la liste mais permet de lui attribuer une taille
		// de base
		this.ensCarte = new ArrayList<>(32);

		for(CarteEnseigne couleur: CarteEnseigne.values())
			for(CarteValeur valeur: CarteValeur.values  ())
				 this.ensCarte.add(new Carte(couleur,valeur));
	}

	/* ACTIONS SUR L'ENSEMBLE DE CARTE */

	/**
	* @param: ajoute à l'ensemble la carte passée en paramètre
	*/
	public void ajouter(Carte nvlCarte)
	{
		if(nvlCarte == null) return; // on vérifie que le paramètre n'est pas null
		this.ensCarte.add(nvlCarte);
	}

	/**
	* @param: ajoute toutes les cartes du tableau passé en paramètres à l'ensemble
	*/
	public void ajouter(Carte[] ensCarte)
	{
		// on vérifie que le tableau n'est pas null et qu'il n'est pas vide
		if(ensCarte == null || ensCarte.length == 0) return;

		for(int cpt=0; cpt<ensCarte.length; cpt++)
			this.ensCarte.add(ensCarte[cpt]);
	}

	/**
	* @param: ajoute toutes les cartes d'un objet CarteEnsemble à cet ensemble
	*/
	public void ajouter(CarteEnsemble ensCarte)
	{
		
	}

	/* ACCESSEURS */
	/**
	* @return: retourne le nombre de Carte présentes dans l'ensemble
	*/
	public int getNombre(){ return this.ensCarte.size(); }

	/**
	* @return: tire avec remise une carte de l'ensemble
	*/
	public Carte tirerAvecRemise()
	{
		if(this.ensCarte.size() == 0) return null;

		int indiceTirage = (int)(Math.random() * (this.ensCarte.size() - 1));
		return this.ensCarte.get(indiceTirage);
	}

	/**
	* @return: tire sans remise une carte de l'ensemble
	*/
	public Carte tirerSansRemise()
	{
		if(this.ensCarte.size() == 0) return null;

		int indiceTirage = (int)(Math.random() * (this.ensCarte.size() - 1));

		Carte carteRet = this.ensCarte.get(indiceTirage);
		this.ensCarte.remove(indiceTirage);

		return carteRet;
	}

	/**
	* @return: retourne vrai si la carte passée en paramètre fait partie de l'ensemble
	*/
	public boolean contient(Carte carte)
	{
		for(Carte carteComp: this.ensCarte)
			if(carteComp.equals(carte)) return true;

		return false;
	}

	/* METHODES JAVA */
	/**
	* @return: retourne le toString de chaque Carte par ligne
	*/
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for(Carte carte: this.ensCarte)
			sb.append(carte.toString() +"\n");

		return sb.toString();
	}

	public static void main(String[] args)
	{
		CarteEnsemble ce = new CarteEnsemble(32);
		System.out.println(ce);
	}
}