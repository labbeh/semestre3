public abstract class Employe {

  private String nom;
  private String prenom;

  protected Employe(String nom,String prenom)
  {
    this.nom = nom;
    this.prenom = prenom;
  }

  public String getNom() {
    return nom;
  }

  public String getPrenom() {
    return prenom;
  }
  public abstract double getSalaire();
  
  public String toString() {
    return "(" + getClass().getName() + ")" + " Nom:" + nom + " ; Prénom:" + prenom
      + " ; Salaire=" + getSalaire() +"\u20AC";
  }

}
