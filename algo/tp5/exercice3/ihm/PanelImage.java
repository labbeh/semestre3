package exercice3.ihm;

import exercice3.Controleur;
import superclasses.ihm.SuperPanelImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelImage extends SuperPanelImage
{
	private Controleur ctrl;


	public PanelImage( Controleur ctrl, String nomImage )
	{
		super(nomImage);
		this.ctrl = ctrl;

		this.addMouseListener(new GestionSouris());
	}

	private class GestionSouris extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent evt)
		{
			ctrl.verifZone(evt.getX(), evt.getY());
		}
	}

}
