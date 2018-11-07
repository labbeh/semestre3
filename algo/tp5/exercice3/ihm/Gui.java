package exercice3.ihm;

import exercice3.Controleur;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame
{
	private Controleur ctrl;

	//private JFrame choixTerr;

	private BarreMenu  barreMenu ;
	private PanelImage panelImage;
	private JLabel 	   labelZone ;

	public Gui( Controleur ctrl )
	{
		this.ctrl = ctrl;
		//this.choixTerr = new FrameChoixTerritoire(ctrl);

		this.barreMenu  = new BarreMenu(ctrl);
		this.panelImage = new PanelImage(this.ctrl, "carte_risk.gif");
		this.labelZone  = new JLabel("Pas de fichier ouvert");

		this.setTitle("Carte zonee");
		//this.setSize(1024, 768);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setJMenuBar(this.barreMenu);
		this.add(this.panelImage, BorderLayout.NORTH);
		this.add(this.labelZone, BorderLayout.SOUTH);

		this.pack();
		this.setVisible(true);
	}

	public void setSelectZone(String nomTerr)
	{
		this.labelZone.setText(nomTerr);
	}
}