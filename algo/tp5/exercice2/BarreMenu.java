import javax.swing.*;
import java.awt.event.*;

public class BarreMenu extends JMenuBar implements ActionListener
{
	private Controleur ctrl;

	private JMenu fichier;

	private JMenuItem nv;
	private JMenuItem enreg;
	private JMenuItem enregSous;

	public BarreMenu( Controleur ctrl )
	{
		super();
		this.ctrl = ctrl;
		
		this.fichier = new JMenu("Fichier");

		this.add(fichier);

		this.nv 	   = new JMenuItem("Nouvelle saisie de territoires ...");
		this.enreg 	   = new JMenuItem("Enregistrer ...");
		this.enregSous = new JMenuItem("Enregistrer Sous ...");

		this.fichier.add(this.nv);
		this.fichier.add(this.enreg);
		this.fichier.add(this.enregSous);

		this.nv.addActionListener(this);
		this.enreg.addActionListener(this);
		this.enregSous.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent evt)
	{
		if(evt.getSource() == this.enregSous) this.ctrl.sauv();
	}
}