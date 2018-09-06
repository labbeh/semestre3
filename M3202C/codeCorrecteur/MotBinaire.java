public class MotBinaire
{
	// FACTORY
	public static MotBinaire fabrique(int[] mot)
	{
		for(int cpt=0; cpt<mot.length; cpt++)
			if(mot[cpt] != 0 && mot[cpt] != 1)
				return null;

		return new MotBinaire(mot);
	}

	public static MotBinaire fabrique(String mot)
	{
		for(int cpt=0; cpt<mot.length(); cpt++)
			if(mot.charAt(cpt) != '0' && mot.charAt(cpt) != '1')
				return null;

		return new MotBinaire(mot);
	}

	// METHODES STATIC
	public static MotBinaire addition(MotBinaire a, MotBinaire b)
	{
		return null;
	}

	public static MotBinaire[] genererMots(int n)
	{
		return null;
	}

	// ATTRIBUTS
	private int[] mot  ;
	private int   poids;

	// CONSTRUCTEURs
	private MotBinaire(int[] mot)
	{
		this.mot = mot;

		for(int cpt=0; cpt<mot.length; cpt++)
			if(mot[cpt] == 1) this.poids++;
	}

	private MotBinaire(String mot)
	{
		this.mot = new int[mot.length()];

		for(int cpt=0; cpt<mot.length(); cpt++)
			this.mot[cpt] = Integer.parseInt(mot.substring(cpt, cpt+1));

		for(int cpt=0; cpt<this.mot.length; cpt++)
			if(this.mot[cpt] == 1) this.poids++;
	}

	// CONSTRUCTEUR PAR RECOPIE
	public MotBinaire(MotBinaire autreMot)
	{
		this.mot = autreMot.mot;
	}

	// ACCESSEURS
	public int poids   (				){return this.poids		;}
	public int distance(MotBinaire autre){return 0				;}
	public int nbBits  (				){return this.mot.length;}

	public MotBinaire sousMot(int debut, int fin)
	{
		if(debut < 0 || fin > this.mot.length+1) return null;

		int[] temp 	  = new int[fin - debut];

		for(int cpt=0; cpt<temp.length; cpt++) temp[cpt] = this.mot[debut+cpt];

		return new MotBinaire(temp);
	}

	public int get(int i)
	{
		return this.mot[i];
	}


	// MODIFICATEURS
	public void set(int i, int bit)
	{
		this.mot[i] = bit;
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
			}
			return true;
		}
		return false;
	}
}