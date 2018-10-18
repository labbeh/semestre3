import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame
{
	private Controleur ctrl;
	private JFrame choixTerr;

	private JPanel panelImage;

	public Gui( Controleur ctrl )
	{
		this.ctrl = ctrl;
		this.choixTerr = new FrameChoixTerritoire(ctrl);

		this.panelImage = new PanelImage(this.ctrl, "carte_zonee_risk.gif");

		this.setTitle("Carte zonee");
		//this.setSize(1024, 768);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.add(this.panelImage);

		this.pack();
		this.setVisible(true);
	}

	public void selectTerritoire()
	{
		this.choixTerr.setVisible(true);
	}
}