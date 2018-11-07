package superclasses.ihm;

import javax.swing.*;

public class SuperBarreMenu extends JMenuBar
{
	protected JMenu fichier;

	protected JMenuItem ouvrir;
	protected JMenuItem enreg;
	protected JMenuItem enregSous;

	protected JFileChooser selectFile;

	public SuperBarreMenu()
	{
		super();

		this.fichier = new JMenu("Fichier");

		this.add(fichier);

		this.ouvrir	   = new JMenuItem("Ouvrir");
		this.enreg 	   = new JMenuItem("Enregistrer ...");
		this.enregSous = new JMenuItem("Enregistrer Sous ...");

		this.selectFile = new JFileChooser();

		this.fichier.add(this.ouvrir);
		this.fichier.add(this.enreg);
		this.fichier.add(this.enregSous);

	}
}