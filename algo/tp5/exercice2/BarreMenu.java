import javax.swing.*;

public class BarreMenu extends JMenuBar
{
	private JMenu fichier;

	public BarreMenu()
	{
		super();
		this.fichier = new JMenu("Fichier");

		this.add(fichier);
	}
}