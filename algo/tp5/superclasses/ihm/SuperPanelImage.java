package superclasses.ihm;

import javax.swing.*;
import java.awt.*;

public class SuperPanelImage extends JPanel
{
	protected JLabel image;

	public SuperPanelImage( String nomImage )
	{
		//this.image = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(this.nomImage))));
		this.image = new JLabel(new ImageIcon(nomImage));

		this.add(this.image);
	}
}
