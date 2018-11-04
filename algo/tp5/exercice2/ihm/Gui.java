package exercice2.ihm;

import exercice2.Controleur;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame
{
	private Controleur ctrl;

	private JFileChooser selectFile;

	private JFrame choixTerr;

	private BarreMenu  barreMenu ;
	private PanelImage panelImage;
	private JLabel 	   labelZone ;

	public Gui( Controleur ctrl )
	{
		this.ctrl = ctrl;
		this.choixTerr = new FrameChoixTerritoire(ctrl);

		this.barreMenu  = new BarreMenu(ctrl);
		this.panelImage = new PanelImage(this.ctrl, "carte_zonee_risk.gif");
		this.labelZone  = new JLabel("Sélectionnez les territoires pour les affecter à un nom");

		this.setTitle("Carte zonee");
		//this.setSize(1024, 768);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setJMenuBar(this.barreMenu);
		this.add(this.panelImage, BorderLayout.NORTH);
		this.add(this.labelZone, BorderLayout.SOUTH);

		this.pack();
		this.setVisible(true);
	}

	public void setZoneCurseur(String zone)
	{
		this.labelZone.setText(zone);
	}

	public void selectTerritoire()
	{
		this.choixTerr.setVisible(true);
	}

	public void reInitIhm()
	{
		this.panelImage.reset();
	}

	/* ACCESSEURS */
	public Integer getX1() { return this.panelImage.getX1(); }
	public Integer getX2() { return this.panelImage.getX2(); }
	public Integer getY1() { return this.panelImage.getY1(); }
	public Integer getY2() { return this.panelImage.getY2(); }

	/*public void sauv()
	{
		this.selectFile = new JFileChooser();
		this.selectFile.showSaveDialog(null);

		this.ctrl.sauv(this.selectFile.getSelectedFile().getAbsolutePath());
	}*/
}
