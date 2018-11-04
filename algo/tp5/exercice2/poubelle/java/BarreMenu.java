import javax.swing.*;
import java.awt.event.*;

public class BarreMenu extends JMenuBar implements ActionListener
{
	private Controleur ctrl;

	private JMenu fichier;

	private JMenuItem nv;
	private JMenuItem enreg;
	private JMenuItem enregSous;

	private JFileChooser selectFile;

	public BarreMenu( Controleur ctrl )
	{
		super();
		this.ctrl = ctrl;

		this.fichier = new JMenu("Fichier");

		this.add(fichier);

		this.nv 	   = new JMenuItem("Nouvelle saisie de territoires ...");
		this.enreg 	   = new JMenuItem("Enregistrer ...");
		this.enregSous = new JMenuItem("Enregistrer Sous ...");

		this.selectFile = new JFileChooser();

		this.fichier.add(this.nv);
		this.fichier.add(this.enreg);
		this.fichier.add(this.enregSous);

		this.nv.addActionListener(this);
		this.enreg.addActionListener(this);
		this.enregSous.addActionListener(this);
	}

	public void sauv()
	{
		//this.selectFile = new JFileChooser();
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
