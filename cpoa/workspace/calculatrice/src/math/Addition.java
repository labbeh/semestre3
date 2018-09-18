package math;

public class Addition
{
	public Addition() {
		super();
	}

	public Long calculer(Long a,Long b){
		return a+b;
	}
		
	public Character lireSymbole()
	{
		return '-';
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Addition op = new Addition();	
		Long resultat = op.calculer(new Long(6),new Long(3));
		
		if(resultat.equals(new Long(9)))
				System.out.println("Réussite du test");
			else
				System.out.println("Echec du test");
		
		Character c = op.lireSymbole();
		if (c.equals('+'))
			System.out.println("Réussite du test");
		else
			System.out.println("Echec du test");	

	}

}
