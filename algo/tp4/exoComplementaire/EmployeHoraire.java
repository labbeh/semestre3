/**
 * Employé payé au nombre d'heures travaillées.
 */
public class EmployeHoraire extends Employe {
  private static final double heuresDues = 35.0; // nombre heures de base
  
  private double nbHeures = 0.0;	        // nombre total des heures travaillées
  private double tarifHoraire = 0.0;	        // tarif horaire 
  private double pourcentageHeuresSup = 0.0;	// pourcentage des heures suplémentaires
  

  public EmployeHoraire(String nom, String prenom) 
  {
    super(nom, prenom);    
  }

  public EmployeHoraire(String nom, String prenom, double nbHeures, double tarifHoraire, double pourcentageHeuresSup)
  {
   super(nom, prenom);

   this.nbHeures = nbHeures;
   this.tarifHoraire = tarifHoraire;
   this.pourcentageHeuresSup = pourcentageHeuresSup;
  }
  
  public double getNbHeures()
  {
      return this.nbHeures;
  }

  public double getTarifHoraire()
  {
      return this.tarifHoraire;  
  }

  public double getPourcentageHeuresSup()
  {
    return this.pourcentageHeuresSup;
  }
  
  public void setNbHeures(double nbHeures)
  {  
      this.nbHeures = nbHeures; 
  }

  public void setTarifHoraire(double tarifHoraire)
  {
      this.tarifHoraire = tarifHoraire;  
  }

  public void setPourcentageHeuresSup(double pourcentageHeuresSup)
  {
      this.pourcentageHeuresSup = pourcentageHeuresSup;
  }

  public void setInfosSalaire(double nbHeures, double tarifHoraire, double pourcentageHeuresSup)
  {
      this.nbHeures = nbHeures;
      this.tarifHoraire = tarifHoraire;
      this.pourcentageHeuresSup = pourcentageHeuresSup;
  }
    
  
  public double getSalaire()
  {
      double salaireBase = EmployeHoraire.heuresDues * this.tarifHoraire;

      double hSup = this.nbHeures - EmployeHoraire.heuresDues;
      double tarifHsup = ( (this.pourcentageHeuresSup * this.tarifHoraire) / 100 ) +this.tarifHoraire;

      return salaireBase + (hSup * tarifHsup);
  }

 
  public String toString()
  {
    return super.toString(); 
  }

}

