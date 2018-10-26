package statistique;

public class Binomiale
{
	private int n;
	private double p;

	public Binomiale(int n, double p)
	{
		// faire un factory n positif et p compris entre 0 et 1
		this.n = n;
		this.p = p;
	}

	public double cumul(double k)
	{
		double valeur  = Math.pow(1-p, n);
		double total = 0.0;

		for(int i=0; i<=k; i++)
		{
			total += valeur;
			valeur = (valeur * p) / (1-p ) * (p-i / n-p+i);
		}

		return total;
	}


}