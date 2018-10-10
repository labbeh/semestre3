import javax.swing.*;
import java.awt.*;

public class PanelImage extends JPanel
{
	private String nomImage;

	public PanelImage( String nomImage )
	{
		this.nomImage = nomImage;
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Image img = Toolkit.getDefaultToolkit().getImage(this.nomImage);
		g.drawImage(img, 1024, 768, null);
	}
}