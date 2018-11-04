package exercice2.ihm;

import exercice2.Controleur;

import javax.swing.*;
import java.awt.event.*;

public class BarreMenu extends JMenuBar implements ActionListener
{
	private Controleur ctrl;

	private JMenu fichier;

	private JMenuItem enreg;
	private JMenuItem enregSous;

	private JFileChooser selectFile;

	public BarreMenu( Controleur ctrl )
	{
		super();
		this.ctrl = ctrl;

		this.fichier = new JMenu("Fichier");

		this.add(fichier);

		this.enreg 	   = new JMenuItem("Enregistrer ...");
		this.enregSous = new JMenuItem("Enregistrer Sous ...");

		this.selectFile = new JFileChooser();

		this.fichier.add(this.enreg);
		this.fichier.add(this.enregSous);

		this.enreg.addActionListener(this);
		this.enregSous.addActionListener(this);
	}

	public void sauv()
	{
		if(!this.ctrl.estEnregistre()) this.selectFile.showSaveDialog(null);

		this.ctrl.sauv(this.selectFile.getSelectedFile().getAbsolutePath());
	}

	@Override
	public void actionPerformed(ActionEvent evt)
	{
		if(evt.getSource() == this.enregSous || evt.getSource() == this.enreg)
			this.sauv();
	}
}
