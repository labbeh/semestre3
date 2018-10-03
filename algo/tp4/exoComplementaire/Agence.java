import java.util.*;

/**
 * la classe Agence
 */
public class Agence implements Iterable<Employe>
{
  private String nom;			// le nom de l'Agence
  private ArrayList<Employe> employes;	// employés de l'Agence

  public Agence(String nom)
  {
      this.employes = new ArrayList<>();
      this.nom = nom;
  }
  
  public String getNom()
  {
      return this.nom;
  }

  public void ajouter(Employe emp)
  {
      this.employes.add(emp);
  }

  public Iterator<Employe> iterEmployes()
  {
      return new IterEmp();
  }

  @Override
  public Iterator<Employe> iterator()
  {
      return iterEmployes();
  }
  
  public String toString()
  {
      return "Agence " +this.nom+ " employés: " +this.employes;
  }

  private class IterEmp implements Iterator<Employe>
  {
      private int indCourant;

      IterEmp(){this.indCourant = 0;}

      @Override
      public boolean hasNext(){ return indCourant < employes.size(); }

      @Override
      public Employe next(){ return employes.get(this.indCourant++); }

      @Override
      public void remove() throws UnsupportedOperationException
      {
        new UnsupportedOperationException("suppresion impossible");
      }
  }
}
