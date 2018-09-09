/**
 * Classe pour gérer des cartes
 * @author  bruno LEGRIX
 * @version 2015-09-21
 */
public class Carte
{
	// ATTRIBUTS
	private CarteEnseigne enseigne;
	private CarteValeur   valeur  ;

	// CONSTRUCTEUR
	Carte(CarteEnseigne enseigne , CarteValeur valeur)
	{
		this.enseigne = enseigne;
		this.valeur   = valeur  ;
	}
	
	// ACCESSEURS
	public CarteEnseigne getEnseigne() {return this.enseigne;}
	public CarteValeur   getValeur  () {return this.valeur  ;}
	
	// EQUALS
	public boolean equals(Object obj)
	{
		// Vérification de l'égalité des références
		if (obj==this) return true;

		// Vérification du type du paramètre
		if (obj instanceof Carte)
		{
			// Vérification des valeurs des attributs
			Carte autre = (Carte) obj;

			if (   this.enseigne == autre.enseigne
			    && this.valeur   == autre.valeur  )
			{
				return true;
			}
		}

		return false;
	}
	
	// TO STRING
	public String toString()
	{
		return this.valeur + " de " + this.enseigne;
	}
}
