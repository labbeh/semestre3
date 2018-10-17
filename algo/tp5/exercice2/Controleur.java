import java.util.List;

public class Controleur
{
	private Gui gui;

	public Controleur()
	{
		this.gui = new Gui(this);
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
}