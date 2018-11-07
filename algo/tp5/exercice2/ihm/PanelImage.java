package exercice2.ihm;

import exercice2.Controleur;
import superclasses.ihm.SuperPanelImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelImage extends SuperPanelImage implements MouseListener, MouseMotionListener
{
	private Controleur ctrl;

	//private String nomImage;
	//private JLabel image;

	private Integer x1;
	private Integer y1;
	private Integer x2;
	private Integer y2;

	public PanelImage( Controleur ctrl, String nomImage )
	{
		super(nomImage);
		this.ctrl = ctrl;
		//this.nomImage = nomImage;

		this.x1 = null;
		this.y1 = null;
		this.x2 = null;
		this.y2 = null;

		//this.image = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(this.nomImage))));
		//this.image = new JLabel(new ImageIcon(nomImage));

		this.image.addMouseListener(this);
		this.image.addMouseMotionListener(this);

		//this.add(this.image);
	}

	/* ACESSEURS */
	public Integer getX1() { return this.x1; }
	public Integer getX2() { return this.x2; }
	public Integer getY1() { return this.y1; }
	public Integer getY2() { return this.y2; }

	@Override
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

			if(this.x1 < this.x2 && this.y1 < this.y2)
				g.drawRect(this.x1+5, this.y1+5, this.x2-this.x1, this.y2-this.y1);

			else if(this.x1 > this.x2 && this.y1 > this.y2)
				g.drawRect(this.x2+5, this.y2+5, this.x1-this.x2, this.y1-this.y2);

			else if(this.x1 < this.x2 && this.y1 > this.y2)
				g.drawRect(this.x1+5, this.y2+5, this.x2-this.x1, this.y1-this.y2);

			else if(this.x1 > this.x2 && this.y1 < this.y2)
				g.drawRect(this.x2+5, this.y1+5, this.x1-this.x2, this.y2-this.y1);
		}
	}

	public void reset()
	{
		this.x1 = null;
		this.y1 = null;
		this.x2 = null;
		this.y2 = null;

		this.repaint();
	}

	// gestion de la souris
	@Override
	public void mouseClicked(MouseEvent evt){

	}

	private void traiterValeurs()
	{
		int temp = 0;

		if(this.x1 > this.x2)
		{
			temp = this.x2;
			this.x2 = this.x1;
			this.x1 = temp;
		}

		if(this.y1 > this.y2)
		{
			temp = this.y2;
			this.y2 = this.y1;
			this.y1 = temp;
		}


	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		this.x1 = arg0.getX();
		this.y1 = arg0.getY();

	}

	@Override
	public void mouseReleased(MouseEvent evt) {

		this.traiterValeurs();
		System.out.println("x1: " +this.x1+ " y1: " +this.y1+ " x2: " +this.x2+ " y2: " +this.y2);
		this.ctrl.selectTerritoire();

		//this.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		this.x2 = arg0.getX();
		this.y2 = arg0.getY();

		repaint();

	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		//this.ctrl.appartientA(evt.getX(), evt.getY());
	}
}
