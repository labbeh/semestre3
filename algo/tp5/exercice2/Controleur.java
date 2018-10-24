import java.util.*;

public class Controleur
{
	private List<Territoire> listTerritoires;

	private Gui gui;

	// si un pixel appartient a cette zone
	// if x >= valminX && x <= valmaxx && y >= valminy && y<= valmaxy
	public Controleur()
	{
		this.listTerritoires = new ArrayList<>();

		this.gui = new Gui(this);
	}

	public void selectTerritoire()
	{
		this.gui.selectTerritoire();
	}

	public void setZone(String nomTerritoire)
	{
		Integer x1 = this.gui.getX1();
		Integer x2 = this.gui.getX2();
		Integer y1 = this.gui.getY1();
		Integer y2 = this.gui.getY2();

		this.listTerritoires.add(new Territoire(nomTerritoire, getMin(x1, x2),
															   getMax(x1, x2),
															   getMin(y1, y2),
															   getMax(y1, y2)));

		System.out.println(this.listTerritoires);
	}

	public void reInitIhm()
	{
		this.gui.reInitIhm();
	}

	public void setZoneCurseur(String zone)
	{
		this.gui.setZoneCurseur(zone);
	}

	public List<String> getListeNomTerritoires()
	{
		List<Territoire> terrs = LectureXML.getListTerritoire("risk.xml");
		List<String> lRet = new ArrayList<>();

		for(Territoire terr: terrs)
			lRet.add(new String( terr.getNom() ));

		return lRet;
	}

	public void appartientA(int x, int y)
	{
		if(this.listTerritoires.isEmpty()) return;

		for(Territoire terr: this.listTerritoires)
		{
			if(x >= terr.getMinX() && x <= terr.getMaxX() && y >= terr.getMinY() && y<= terr.getMaxY())
			{
				this.setZoneCurseur(terr.getNom());
				break;
			}

			else this.setZoneCurseur("");
			try{Thread.sleep(50);}
			catch(InterruptedException evt){}
		}
					
	}

	public static void main(String[] args)
	{
		new Controleur();
	}

	private static Integer getMax(Integer nb1, Integer nb2)
	{
		if(nb1 > nb2) return nb1;
					  return nb2;
	}

	private static Integer getMin(Integer nb1, Integer nb2)
	{
		if(nb1 < nb2) return nb1;
					  return nb2;
	}
}