import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame
{
	private JPanel panelImage;

	public Gui()
	{
		this.panelImage = new PanelImage("carte_zonee_risk.gif");

		this.setTitle("Carte zonee");
		this.setSize(1024, 768);

		this.add(this.panelImage);

		this.setVisible(true);
	}
}