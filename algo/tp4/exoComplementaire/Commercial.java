/**
 * Un Employé Commercial.
 */
public class Commercial extends Employe {
  private static final double pourcentageCa = 2.0;	// pourcentage du CA pour le calcul du salaire
  
  private double ca  ;  				// chiffre d'affaire par semaine
  private double fixe; 				// salaire fixe
  
  

  public Commercial(String nom, String prenom, double fixe, double ca)
  {
      super(nom, prenom);
      this.fixe = fixe;
      this.ca = ca;    
  }

  public Commercial(String nom, String prenom)
  {
    this(nom, prenom, 0.0, 0.0);
  }

   public double getCA()
  {
     return this.ca;
  }

  public double getFixe()
  {
      return this.fixe;
  }

  public void setFixe(double fixe)
  {
    this.fixe = fixe;
  }
  
  public void setInfosSalaire(double fixe, double ca)
  {
      this.fixe = fixe;
      this.ca = ca;
  }

  public double getSalaire()
  {
      return this.fixe + (this.ca * Commercial.pourcentageCa / 100);
  }

  
  public String toString()
  {
      return super.toString();
  }
}

    
