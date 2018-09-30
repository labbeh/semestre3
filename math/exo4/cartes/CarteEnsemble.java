import java.util.ArrayList;

/**
* @author 	   : Hugo Labbé, groupe D2
* @version	   : 1.2
* @description : Exercice 7 à rendre, classe modifiée depuis l'exercice 4 pour avoir 52 cartes
*			   : au lieux de 32
*/

// pas de tabulation: 4
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
	public CarteEnsemble(int n)
	{
		// le 52 en paramètre ne fige pas la taille de la liste mais permet de lui attribuer une taille
		// de base
		this.ensCarte = new ArrayList<>(52);

		for(CarteEnseigne couleur: CarteEnseigne.values())
			for(CarteValeur valeur: CarteValeur.values  ())
				 this.ensCarte.add(new Carte(couleur,valeur));
	}

	/***********************************/
	/* ACTIONS SUR L'ENSEMBLE DE CARTE */
	/***********************************/

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
		if(ensCarte == null || ensCarte.getNombre() == 0) return;

		for(int cpt=0; cpt<ensCarte.getNombre(); cpt++)
			this.ensCarte.add(ensCarte.get(cpt));
	}

	/**************/
	/* ACCESSEURS */
	/**************/

	/**
	* @return: retourne le nombre de Carte présentes dans l'ensemble
	*/
	public int getNombre(){ return this.ensCarte.size(); }

	/**
	* @param : indice dans l'ensemble
	* @return: retourne la carte de l'ensemble à l'indice passé en paramètre
	*/
	public Carte get(int i){ return this.ensCarte.get(i); }

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
		return this.ensCarte.remove(indiceTirage);
	}

	/**
	* @param : Carte à comparer
	* @return: retourne vrai si la carte passée en paramètre fait partie de l'ensemble
	*/
	public boolean contient(Carte carte)
	{
		if(carte == null) return false;

		for(Carte carteComp: this.ensCarte)
			if(carteComp.equals(carte)) return true;

		return false;
	}

	/**
	* @param : CarteEnseigne soit l'enseigne d'une carte
	* @return: retourne vrai si au moins une carte de l'ensemble à l'enseigne en paramètre,
	*		   comparaison sur les adresses mémoires
	*/
	public boolean contient(CarteEnseigne enseigne)
	{
		if(enseigne == null) return false;

		for(Carte carteComp: this.ensCarte)
			if(carteComp.getEnseigne() == enseigne) return true;

		return false;
	}

	/**
	* @param : CarteValeur soit la valeur d'une carte
	* @return: retourne vrai si au moins une carte de l'ensemble à la valeur en paramètre,
	*		   comparaison sur les adresses mémoires
	*/
	public boolean contient(CarteValeur valeur)
	{
		if(valeur == null) return false;

		for(Carte carteComp: this.ensCarte)
			if(carteComp.getValeur() == valeur) return true;

		return false;
	}

	/**
	* @param : nombre d'éléments de la liste retournée
	* @return: retourne une k-liste en fonction du nombre en paramètre,
	*		   retourne null en cas d'ensemble vide de paramètre invalide
	*/
	public Carte[] donnerKListe(int k)
	{
		if(this.ensCarte.size() == 0) return null;

		Carte[] tabRet = new Carte[k];

		for(int cpt=0; cpt<k; cpt++)
			tabRet[cpt] = this.tirerAvecRemise();

		return tabRet;
	}

	/**
	* @param : nombre d'éléments de la liste retournée
	* @return: retourne un k-arrangement en fonction du nombre en paramètre,
	*		   retourne null en cas d'ensemble vide ou de paramètre invalide
	*/
	public Carte[] donnerKArrangement(int k)
	{
		if(this.ensCarte.size() == 0		) return null;
		if(k < 1 || k > this.ensCarte.size()) return null;

		Carte[] tabRet = new Carte[k];

		for(int cpt=0; cpt<k; cpt++)
			tabRet[cpt] = this.ensCarte.get(cpt);

		return tabRet;
	}

	/**
	* @param : nombre d'éléments de la liste retournée
	* @return: retourne une k-combinaison en fonction du nombre en paramètre,
	*		   retourne null en cas d'ensemble vide ou de paramètre invalide
	*/
	public Carte[] donnerKCombinaison(int k)
	{
		if(this.ensCarte.size() == 0		) return null;
		if(k < 1 || k > this.ensCarte.size()) return null;

		Carte[] tabRet = new Carte[k];

		for(int cpt=0; cpt<k; cpt++)
			tabRet[cpt] = this.tirerSansRemise();

		return tabRet;
	}

	/*****************/
	/* METHODES JAVA */
	/*****************/
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
}