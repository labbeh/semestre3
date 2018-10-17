package zoneTerritoire.metier;
import java.util.List;

public class GestionTerritoire
{
	private List<Territoire> listTerritoire;

	public GestionTerritoire()
	{
		this.listTerritoire = LectureXML.getListTerritoire("./risk.xml");
	}

	public List<Territoire> getTerritoires(){return this.listTerritoire;}

	/* TESTS	 */
	public static void main(String[] args)
	{
		GestionTerritoire g = new GestionTerritoire();
		System.out.println(g.getTerritoires());
	}
}