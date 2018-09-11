public class MotBinaire
{
	// FACTORY

	/**
	* @param : tableau d'entier contenant les bits du mot binaire
	* @return: objet MotBinaire si le tableau de int est correct, null sinon
	*/
	public static MotBinaire fabrique(int[] mot)
	{
		for(int cpt=0; cpt<mot.length; cpt++)
			if(mot[cpt] != 0 && mot[cpt] != 1)
				return null;

		return new MotBinaire(mot);
	}

	/**
	* @param : Chaine de caractère contenant les bits du mot binaire
	* @return: objet MotBinaire si la chaine est correct, null sinon
	*/
	public static MotBinaire fabrique(String mot)
	{
		for(int cpt=0; cpt<mot.length(); cpt++)
			if(mot.charAt(cpt) != '0' && mot.charAt(cpt) != '1')
				return null;

		return new MotBinaire(mot);
	}

	// METHODES STATIC
	/**
	* @description: fait l'addition de deux mots binaires
	* @param	  : Les 2 objets MotBinaire à additionner
	* @retrun	  : Somme des MotBinaire 
	*/
	public static MotBinaire addition(MotBinaire a, MotBinaire b)
	{
		int nb1 = Integer.parseInt(a.toString(), 2);
		int nb2 = Integer.parseInt(b.toString(), 2);

		int somme = nb1 + nb2;

		return new MotBinaire(Integer.toBinaryString(somme));
	}

	/**
	* @description: retourne un tableau de MotBinaire des mots de 0 à n
	* @param	  : entier n
	* @return 	  : tableau de MotBinaire contenant les n mots binaires de 0 à n
	*/
	public static MotBinaire[] genererMots(int n)
	{
		MotBinaire[] tabMots = new MotBinaire[n];

		for(int cpt=0; cpt<n; cpt++)
			tabMots[cpt] = MotBinaire.fabrique(Integer.toBinaryString(cpt));
		return tabMots;
	}

	// ATTRIBUTS
	private int[] mot  ;
	private int   poids;

	// CONSTRUCTEURs
	/**
	* @constructor: constructeur privé appelé par un factory créant un MotBinaire à partir
	*				d'un tableau de int valide et calcul le poids du mot
	*/
	private MotBinaire(int[] mot)
	{
		this.mot = mot;

		// on calcul le poids ici pour éviter d'avoir à le calculé à chaque appel de la méthode poids()
		// au prix d'un attribut de plus en mémoire
		for(int cpt=0; cpt<mot.length; cpt++)
			if(mot[cpt] == 1) this.poids++;
	}

	/**
	* @constructor: constructeur privé appelé par un factory créant un MotBinaire à partir
	*				d'une chaine de caractère valide et calcul le poids du mot
	*/
	private MotBinaire(String mot)
	{
		this.mot = new int[mot.length()];

		// on converti le String en un tableau d'entiers
		for(int cpt=0; cpt<mot.length(); cpt++)
			this.mot[cpt] = Integer.parseInt(mot.substring(cpt, cpt+1));

		// on calcul le poids ici pour éviter d'avoir à le calculé à chaque appel de la méthode poids()
		// au prix d'un attribut de plus en mémoire
		for(int cpt=0; cpt<this.mot.length; cpt++)
			if(this.mot[cpt] == 1) this.poids++;
	}

	// CONSTRUCTEUR PAR RECOPIE
	public MotBinaire(MotBinaire autreMot)
	{
		this.mot   = autreMot.mot  ;
		this.poids = autreMot.poids;
	}

	// ACCESSEURS
	public int poids   (){return this.poids		;}
	public int nbBits  (){return this.mot.length;}

	/**
	* @description: calcul la distance (nombre de bits de différence) entre deux MotBinaire
	*				retourne -1 comme code d'erreur en cas de paramètre invalide,
	*				paramètre null ou de nombre de bit différent de l'objet courant
	* @param 	  : MotBinaire à comparer avec l'objet courant
	* @return 	  : distance entre les deux mots, -1 en cas de paramètre invalide 
	*/
	public int distance(MotBinaire autre)
	{
		if(autre == null ||this.mot.length != autre.mot.length) return -1; // renvoie d'un code d'erreur si la taille est différente

		int distance = 0;
		for(int cpt=0; cpt<this.mot.length; cpt++)
			if(this.mot[cpt] != autre.mot[cpt]) distance++;

		return distance;
	}

	/**
	* @descprition: retourne un sous mot
	* @param 	  : borne de début, borne de fin (exclue)
	* @return 	  : nouveau MotBinaire
	*/
	public MotBinaire sousMot(int debut, int fin)
	{
		if(debut < 0 || fin > this.mot.length+1) return null;

		int[] temp 	  = new int[fin - debut];

		for(int cpt=0; cpt<temp.length; cpt++) temp[cpt] = this.mot[debut+cpt];

		return new MotBinaire(temp);
	}

	/**
	* @param : indice du bit dans le mot
	* @return: bit à la position passée en paramètre, retourne -1 en cas d'indice hors champ
	*/
	public int get(int i)
	{
		if(i < 0 || i > this.mot.length) return -1; // code d'erreur en cas d'indice invalide
		return this.mot[i];
	}


	// MODIFICATEURS
	/**
	* @descrpition: remplace un bit à une position donnée dans le mot
	* @param 	  : indice de la position du bit dans le mot, valeur du bit à changer
	* @return 	  : true si le bit à été changé, false si l'indice était incorrect
	*/
	public boolean set(int i, int bit)
	{
		try
		{
			// déclenchera une exception ArrayIndexOutOfBoundsException en cas de d'indice invalide
			this.mot[i] = bit;
			return true;
		}
		catch(ArrayIndexOutOfBoundsException evt){return false;}
	}

	// METHODES JAVA
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for(int cpt=0; cpt<this.mot.length; cpt++)
			sb.append(Integer.toString(this.mot[cpt]));

		return sb.toString();
	}

	@Override
	public boolean equals(Object autre)
	{
		if(autre instanceof MotBinaire)
		{
			MotBinaire autreMot = (MotBinaire)autre;

			if(this.poids == autreMot.poids && this.mot.length == autreMot.mot.length)
			{
				for(int cpt=0; cpt<this.mot.length; cpt++)
					if(this.mot[cpt] != autreMot.mot[cpt]) return false;
				return true;
			}
		}
		return false;
	}

	// MAIN JEU DE TESTS
	public static void main(String[] args)
	{
		int[] tabBon  = new int[]{0,1,1,0,1,0,0,0,1,0,1,1,0,1,1};
		int[] tabFaux = new int[]{1,1,0,0,0,1,4,0,1,2,1,0,1,1  };

		// tests sur la créations de MotBinaire
		MotBinaire mbStringFaux = MotBinaire.fabrique("01101gsfgfd"); // doit renvoyer null
		MotBinaire mbStringBon  = MotBinaire.fabrique("01101100101"); // doit fonctionner

		MotBinaire mbTabBon  = MotBinaire.fabrique(tabBon ); // doit fonctionner
		MotBinaire mbTabFaux = MotBinaire.fabrique(tabFaux); // doit renvoyer null

		MotBinaire mbRecopie = new MotBinaire(mbTabBon);

		// affichage des toString (ou null)
		System.out.println("string bon : " +mbStringBon );
		System.out.println("string faux: " +mbStringFaux);

		System.out.println("tab bon    : " +mbTabBon );
		System.out.println("tab faux   : " +mbTabFaux);

		System.out.println();

		// tests sur les accesseurs
		System.out.println("Bit de position 2 (tab)   :" +mbTabBon.get(2)   );
		System.out.println("Bit de position 3 (string):" +mbStringBon.get(3));

		System.out.println();
		System.out.println("Poids (tab)   :" +mbTabBon.poids()   );
		System.out.println("Poids (string):" +mbStringBon.poids());

		System.out.println();
		System.out.println("Nombre de bits (tab)   :" +mbTabBon.nbBits()   );
		System.out.println("Nombre de bits (string):" +mbStringBon.nbBits());

		MotBinaire mot1 = MotBinaire.fabrique("011010");
		MotBinaire mot2 = MotBinaire.fabrique("110011");
		MotBinaire mot3 = MotBinaire.fabrique("011"	  );

		System.out.println();
		System.out.println("mot1: " +mot1);
		System.out.println("mot2: " +mot2);
		System.out.println("mot3: " +mot3);

		System.out.println();
		System.out.println("Distance entre mot1 et mot2: " +mot1.distance(mot2));
		System.out.println("Distance entre mot2 et mot3: " +mot2.distance(mot3)); // doit renvoyer -1 car par la même taille

		// Tests sur les modificateurs et autres méthodes
		mot2.set(1, 0);
		System.out.println();
		System.out.println("Modification du bit en position 1 (en partant de 0) du mot2: " +mot2);

		MotBinaire sousMot1 = mot1.sousMot(1, 5);
		System.out.println();
		System.out.println("Sous mot de l'intervalle [1, 5[ du mot 1: " +sousMot1);

		System.out.println();
		System.out.println("Addition de " +mot1+ " + " +mot2+ ": " +MotBinaire.addition(mot1, mot2));

		MotBinaire[] tabMots = MotBinaire.genererMots(5);
		System.out.println();
		System.out.println("5 premiers mots de 0 à 5: ");

		for(int cpt=0; cpt<tabMots.length; cpt++)
			System.out.println(tabMots[cpt]);

	}
}