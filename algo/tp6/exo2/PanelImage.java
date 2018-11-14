import javax.swing.*;
import java.awt.*;

public class PanelImage extends JPanel
{
	private Controleur ctrl;
	private JLabel image;

	public PanelImage( Controleur ctrl, String nomImage )
	{
		this.ctrl = ctrl;
		this.image = new JLabel(new ImageIcon(nomImage));

		this.add(this.image);
	}

	@Override
	public void paintChildren(Graphics g)
	{
		super.paintChildren(g);

		g.setColor(Color.RED);

		

		
	}
}
