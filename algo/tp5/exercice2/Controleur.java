import java.util.List;

public class Controleur
{
	private Gui gui;

	public Controleur()
	{
		this.gui = new Gui(this);
	}

	public void selectTerritoire()
	{
		this.gui.selectTerritoire();
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
}