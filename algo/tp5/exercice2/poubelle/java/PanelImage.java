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

	public Integer getX1() { return this.x1; }
	public Integer getX2() { return this.x2; }
	public Integer getY1() { return this.y1; }
	public Integer getY2() { return this.y2; }

	public void paintChildren(Graphics g)
	{
		super.paintChildren(g);

		g.setColor(Color.RED);

		if(this.x1 != null && this.y1 !=null)
			g.fillOval(this.x1, this.y1, 10,10);

		if(this.x2 != null && this.y2 !=null)
			g.fillOval(this.x2, this.y2, 10,10);

		if(this.x1 != null && this.y1 !=null && this.x2 != null && this.y2 !=null)
		{
			g.setColor(Color.BLUE);
			System.out.println("ok");

			if(this.x1 < this.x2 && this.y1 < this.y2)
			{
				System.out.println("rectangle 1");
				g.drawRect(this.x1+5, this.y1+5, this.x2-this.x1, this.y2-this.y1);
			}

			if(this.x1 > this.x2 && this.y1 > this.y2)
				g.drawRect(this.x2+5, this.y2+5, this.x1-this.x2, this.y1-this.y2);

			/*else if(this.x1 > this.x2 && this.y1 > this.y2)
			{
				System.out.println("rectangle 2");
				g.drawRect(this.x1, this.y1, this.y1-this.y2, this.x1+this.x2);
			}*/
		}
	}

	public void reset()
	{
		this.x1 = null;
		this.y1 = null;
		this.x2 = null;
		this.y2 = null;

		this.deuxiemeClick = false;

		this.repaint();
	}

	// gestion de la souris
	@Override
	public void mouseClicked(MouseEvent evt) {
		/*if(!deuxiemeClick)
		{
			this.x1 = evt.getX();
			this.y1 = evt.getY();
			this.deuxiemeClick = true;
			//this.repaint();
		}

		else
		{
			this.x2 = evt.getX();
			this.y2 = evt.getY();
			this.deuxiemeClick = false;

			System.out.println("x1: " +this.x1+ " y1: " +this.y1+ " x2: " +this.x2+ " y2: " +this.y2);

			this.traiterValeurs();
			this.ctrl.selectTerritoire();
		}
		this.repaint();*/
		//this.reset();

	}

	private void traiterValeurs()
	{
		int temp = 0;

		//if(this.y1 > this.x1 && this.y2 > this.x2) return;

		/*if(this.x1 >= this.x2 && this.y1 >= this.y2)
		{
			System.out.println("sup");
			temp = this.x1;
			this.x1 = this.x2;
			this.x2 = temp;

			temp = this.y1;
			this.y1 = this.y2;
			this.y2 = temp;
		}*/

		if(x1 > x2)
		{
			temp = x2;
			x2 = x1;
			x1 = temp;
		}

		if(y1 > y2)
		{
			temp = y2;
			y2 = y1;
			y1 = temp;
		}


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
		this.x1 = arg0.getX();
		this.y1 = arg0.getY();

	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		//this.x2 = evt.getX();
		//this.y2 = evt.getY();
		//this.deuxiemeClick = false;

		//System.out.println("x1: " +this.x1+ " y1: " +this.y1+ " x2: " +this.x2+ " y2: " +this.y2);

		this.traiterValeurs();
		System.out.println("x1: " +this.x1+ " y1: " +this.y1+ " x2: " +this.x2+ " y2: " +this.y2);
		this.ctrl.selectTerritoire();

		this.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		this.x2 = arg0.getX();
		this.y2 = arg0.getY();

		//this.traiterValeurs();
		repaint();
		//this.reset();
		//this.repaint();

	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		this.ctrl.appartientA(evt.getX(), evt.getY());

	}
}
