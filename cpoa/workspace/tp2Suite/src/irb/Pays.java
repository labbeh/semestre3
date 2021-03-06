package irb;

public class Pays implements Comparable<Pays>
{
	private String nomPays;
	private String anneeClassement;
	private String codePays; // en 3 lettres
	private int rang;
	private double nbPoint;
	
	/**
	 * Constructeur princpal de Pays
	 * */
	public Pays(String nomPays, String anneeClassement, String codePays, int rang, double nbPoint)
	{
		this.nomPays 		 = nomPays;
		this.anneeClassement = anneeClassement;
		this.codePays 		 = codePays;
		this.rang 			 = rang;
		this.nbPoint 		 = nbPoint;
	}
	
	/**
	 * Constructeur par recopie d'un Pays
	 * */
	public Pays(Pays pays)
	{
		this(pays.nomPays, pays.anneeClassement, pays.codePays, pays.rang, pays.nbPoint);
	}

	public String getNomPays()
	{
		return nomPays;
	}

	public void setNomPays(String nomPays)
	{
		this.nomPays = nomPays;
	}

	public String getAnneeClassement()
	{
		return anneeClassement;
	}

	public void setAnneeClassement(String anneeClassement)
	{
		this.anneeClassement = anneeClassement;
	}

	public String getCodePays()
	{
		return codePays;
	}

	public void setCodePays(String codePays)
	{
		this.codePays = codePays;
	}

	public int getRang()
	{
		return rang;
	}

	public void setRang(int rang)
	{
		this.rang = rang;
	}

	public double getNbPoint()
	{
		return nbPoint;
	}

	public void setNbPoint(double nbPoint)
	{
		this.nbPoint = nbPoint;
	}

	@Override
	public String toString()
	{
		// généré automatiquement par Eclipse
		return "Pays [nomPays=" + nomPays + ", anneeClassement="
				+ anneeClassement + ", codePays=" + codePays + ", rang=" + rang
				+ ", nbPoint=" + nbPoint + "]";
	}

	@Override
	public boolean equals(Object obj)
	{
		// généré automatiquement par Eclipse
		if (this == obj) return true;
		if (obj == null) return false;
		
		if (getClass() != obj.getClass()) return false;
		Pays other = (Pays) obj;
		if (anneeClassement == null)
		{
			if (other.anneeClassement != null) return false;
		}
		else if (!anneeClassement.equals(other.anneeClassement)) return false;
		if (codePays == null)
		{
			if (other.codePays != null) return false;
		}
		else if (!codePays.equals(other.codePays)) return false;
		if (Double.doubleToLongBits(nbPoint) != Double
				.doubleToLongBits(other.nbPoint)) return false;
		if (nomPays == null)
		{
			if (other.nomPays != null) return false;
		}
		else if (!nomPays.equals(other.nomPays)) return false;
		if (rang != other.rang) return false;
		return true;
	}
	
	/**
	 * compare le pays courant avec un autre pays passer en paramètre: la comparaison se fait sur le nombre de points
	 * @param pays sur lequel effectuer la comparaison
	 * @return -1, 0 ou 1 
	 * */
	@Override
	public int compareTo(Pays p)
	{
		return Double.compare(p.nbPoint, this.nbPoint);
	}

}
