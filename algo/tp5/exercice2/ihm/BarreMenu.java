package exercice2.ihm;

import exercice2.Controleur;
import superclasses.ihm.SuperBarreMenu;

import javax.swing.*;
import java.awt.event.*;

public class BarreMenu extends SuperBarreMenu implements ActionListener
{
	private Controleur ctrl;

	public BarreMenu( Controleur ctrl )
	{
		super();
		this.ctrl = ctrl;

		this.ouvrir.setEnabled(false);

		this.enreg.addActionListener(this);
		this.enregSous.addActionListener(this);
	}

	public void sauv()
	{
		if(!this.ctrl.estEnregistre()) this.selectFile.showSaveDialog(null);

		try{
			this.ctrl.sauv(this.selectFile.getSelectedFile().getAbsolutePath());
		}
		catch(Exception evt){}
	}

	@Override
	public void actionPerformed(ActionEvent evt)
	{
		if(evt.getSource() == this.enregSous || evt.getSource() == this.enreg)
			this.sauv();
	}
}
