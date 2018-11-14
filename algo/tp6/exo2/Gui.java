import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame
{
	private Controleur ctrl;

	private PanelImage panelImage;

	public Gui( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.panelImage = new PanelImage(ctrl, "Asterix_NB.gif");

		this.setTitle("Coloriage");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.add(this.panelImage);

		this.pack();
		this.setVisible(true);
	}
}