package irb;

public class Pays
{
	private String nomPays;
	private String anneeClassement;
	private String codePays; // en 3 lettres
	private int rang;
	private double nbPoint;

	public Pays(String nomPays, String anneeClassement, String codePays, int rang, double nbPoint)
	{
		this.nomPays = nomPays;
		this.anneeClassement = anneeClassement;
		this.codePays = codePays;
		this.rang = rang;
		this.nbPoint = nbPoint;
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
		return "Pays [nomPays=" + nomPays + ", anneeClassement="
				+ anneeClassement + ", codePays=" + codePays + ", rang=" + rang
				+ ", nbPoint=" + nbPoint + "]";
	}

	@Override
	public boolean equals(Object obj)
	{
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

}
