public class TestAgence
{
	public static void main(String[] args)
	{
		Agence agence = new Agence("ag1");

		agence.ajouter(new EmployeHoraire("azerty", "jean-gary"));
		agence.ajouter(new Commercial("hussenvrac", "anne"));
		agence.ajouter(new EmployeHoraire("legland", "jean"));

		for(Employe e: agence)
			System.out.println(e);
	}
}