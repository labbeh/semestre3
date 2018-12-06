// pour le traitement de L'image
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

// Pour le presse-Papier
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;

import java.util.ArrayList;


public class Test
{
	public static void main(String[] a)
	{
		/*-------------------*/
		/* Question 2.1      */
		/*-------------------*/

		System.out.println( "Question 2.1" );

		ArcEnCiel     arc = new ArcEnCiel();

		System.out.println ( arc );


		/*-------------------*/
		/* Question 2.2/2.3  */
		/*-------------------*/

		System.out.println( "Question 2.2/2.3" );

		System.out.println ( Test.afficher ( arc ) );


		/*-------------------*/
		/* Question 2.4      */
		/*-------------------*/

		System.out.println( "Question 2.4" );

		arc.triNaturel();

		System.out.println ( Test.afficher ( arc ) );


		/*-------------------*/
		/* Question 2.5      */
		/*-------------------*/

		System.out.println( "Question 2.5" );

		arc.triSecondaire();

		System.out.println ( Test.afficher ( arc ) );



		/*-------------------*/
		/* Question 2.6      */
		/*-------------------*/

		System.out.println( "Question 2.6" );

		// parcourir l'ensemble de image.gif pour mettre à jour le nombre d'occurences
		// à chaque couleur de notre arc

		BufferedImage bi  =  null;
		try
		{
			bi = ImageIO.read(new File ("image.gif"));

			for(int x=0; x<bi.getWidth()*bi.getWidth(); x++)
				for(Couleur coul: arc)
					if((bi.getColorModel().getRGB(x) & 0xFFFFFF) == coul.getRVB())
						coul.ajouterOccurence();
		}	
		catch(Exception e){e.printStackTrace();}

		// Ajout des instructions permettant de répondre à la question 2.6

		

		System.out.println ( Test.afficher( arc ) );


		/*-------------------*/
		/* Question 2.7      */
		/*-------------------*/

		System.out.println( "Question 2.7" );

		System.out.println ( Test.afficher2( arc ) );


		/*-------------------*/
		/* Question 2.8      */
		/*-------------------*/
		// Placer le résultat final dans le Presse-Papier

		// Ajout des instructions permettant de répondre à la question 2.8

		// ...
		// ...
		// ...
		// ...

	}



	/*-------------------*/
	/* Question 2.3      */
	/*-------------------*/
	private static String afficher(ArcEnCiel arc)
	{
		String motif = "+----------+-----+----------+-------+";
		String sRet = motif +"\n";

		sRet += "|" + String.format("%10s", "nom   ");
		sRet += "|" + String.format("%5s" , "long"  );
		sRet += "|" + String.format("%10s", "RVB   ");
		sRet += "|" + String.format("%7s" , "nbOcc" );
		sRet += "|\n";

		sRet += motif +"\n";

		for(Couleur coul: arc){
			sRet += "|" + String.format("%10s", coul.getNom()		  );
			sRet += "|" + String.format("%5s" , coul.getLongueurOnde());
			sRet += "|" + String.format("%10s", coul.getRVB()		  );
			sRet += "|" + String.format("%7s" , coul.getNbOccurence() );

			sRet += "|\n";
			sRet += motif + "\n";
		}

		//sRet += motif;

		return sRet;
	}

	// Méthode utilisable uniquement si vous n'arrrivez pas à faire la question 2.3
	public static String afficherDegrade(ArcEnCiel arc)
	{
		String sRet="";
		for (int cpt=0; cpt<arc.getNbCouleur(); cpt++)
			sRet+= arc.getCouleur(cpt).getNom()          + "|" +
			       arc.getCouleur(cpt).getLongueurOnde() + "|" +
			       arc.getCouleur(cpt).getNbOccurence () + "|" +
			       arc.getCouleur(cpt).getRVB()          +"\n";

		return sRet;
	}



	/*-------------------*/
	/* Question 2.7      */
	/*-------------------*/
	private static String afficher2(ArcEnCiel arc)
	{
		String sRet = "";

		// Ajout des instructions permettant de répondre à la question 2.7

		// ...
		// ...
		// ...
		// ...

		return sRet;
	}


}