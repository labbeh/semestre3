package exercice3.ihm;

import exercice3.Controleur;
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

		ouvrir.addActionListener(this);

		this.enreg.setEnabled(false);
		this.enregSous.setEnabled(false);
	}

	public void ouvrir()
	{
		try{
			selectFile.showOpenDialog(null);
			this.ctrl.ouvrir(selectFile.getSelectedFile().getAbsolutePath());
		}
		catch(Exception evt){}
	}


	@Override
	public void actionPerformed(ActionEvent evt)
	{
		this.ouvrir();
	}
}
