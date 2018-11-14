import java.awt.*;

import java.awt.image.*;
import javax.imageio.*;

import java.io.*;

public class Controleur
{
	private Gui ihm;

	private BufferedImage bi;
	private Graphics2D g2;

	private int[][] tabCoord = new int[][]{ {60 	,150 	,16764006 },
	  						{38 	,190 	,16764006}, 	
							{110 	,100 	,16764006}, 	
							{110 	,120 	,16764006 },	
							{100 	,140 	,16764006 },	
							{130 	,160 	,16764006 },
							{140 	,185 	,16764006 },	
							 { 90 	,120 	,15394878 	},
							{120 	,120 	,15394878 },	
							  {70 	,120 	,15394878} ,	
							{110 	  ,87 	,15394878 }	,
							 { 60 	,210 	,15139122 }	,
							  {40 	,230 	,13341955 }	,
							  {75 	,240 	,13341955 	},
							 { 30 	,250 	  ,9916943 	},
							{100 	,250 	  ,9916943 },	
						};


	public Controleur()
	{
		this.ihm = new Gui(this);

		try{
			bi = ImageIO.read(new File("Asterix_NB.gif"));

			g2 = bi.createGraphics();

			// peindre
			ImageIO.write(bi, "gif", new File("Asterix_NB.gif")); // générer l'image
		}
		catch(Exception evt){}

		
	}

	public int getNbLig(){return this.tabCoord.length; }
	public int getNbCol(){return this.tabCoord[0].length;}

	public int get(int lig, int col){return tabCoord[lig][col];}

	public void peindre(int x, int y, int codeCoul)
	{
		Color couleur = new Color(codeCoul);
		// getRGB(x,y) code blanc = &0XFFFFFF 16777215 ne peindre que si c'est blanc
	}

	public static void main(String[] argv)
	{
		new Controleur();
	}

/*	  X 	  Y 	couleur 	
	  60 	150 	16764006 	bras droit
	  38 	190 	16764006 	index droit
	110 	100 	16764006 	haut du visage
	110 	120 	16764006 	bas du visage
	100 	140 	16764006 	cou
	130 	160 	16764006 	bras gauche
	140 	185 	16764006 	doigt gauche
	  90 	120 	15394878 	moustache gauche
	120 	120 	15394878 	moustache droite
	  70 	120 	15394878 	cheveux
	110 	  87 	15394878 	cheveux
	  60 	210 	15139122 	pantalon
	  40 	230 	13341955 	bas pantalon droit
	  75 	240 	13341955 	bas pantalon gauche
	  30 	250 	  9916943 	chaussure droite
	100 	250 	  9916943 	chaussure gauche */
}