import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelImage extends JPanel implements MouseListener, MouseMotionListener
{
	private Controleur ctrl;

	private String nomImage;
	private JLabel image;

	private Integer x1;
	private Integer y1;
	private Integer x2;
	private Integer y2;

	private boolean deuxiemeClick; //passe à true au deuxieme click

	public PanelImage( Controleur ctrl, String nomImage )
	{
		this.ctrl = ctrl;
		this.nomImage = nomImage;
		this.deuxiemeClick = false;

		this.x1 = null;
		this.y1 = null;
		this.x2 = null;
		this.y2 = null;

		//this.image = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(this.nomImage))));
		this.image = new JLabel(new ImageIcon(this.nomImage));

		this.image.addMouseListener(this);
		this.image.addMouseMotionListener(this);

		this.add(this.image);
	}

	public void paintChildren(Graphics g)
	{
		super.paintChildren(g);

		g.setColor(Color.RED);

		if(this.x1 != null && this.y1 !=null)
			g.fillOval(this.x1, this.y1, 10,10);
	}

	// gestion de la souris
	@Override
	public void mouseClicked(MouseEvent evt) {
		if(!deuxiemeClick)
		{
			this.x1 = evt.getX();
			this.y1 = evt.getY();
		}

		else
		{
			this.x2 = evt.getX();
			this.y2 = evt.getY();
		}
		this.repaint();
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		System.out.println("entre");
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		System.out.println("x= " +evt.getX()+ " y= " +evt.getY());
		
	}
}