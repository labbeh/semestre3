package exercice2.metier;

import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class GestionFichier
{
	public static List<Territoire> getListTerritoire(String nomFich)
	{
		Document document;
		Element  racine	 ;

		List<Territoire> lRet = new ArrayList<Territoire>();
		SAXBuilder 		 sxb  = new SAXBuilder			 ();

		try
		{
			document = sxb.build(new File(nomFich));
			racine 	 = document.getRootElement();

			List<Element> lCont = racine.getChildren("continent");

			for(Element cont: lCont)
			{
				List<Element> lTerr = cont.getChildren("territoire");

				for(Element terr: lTerr)
					lRet.add( new Territoire(terr.getText()) );
			}
		}
		catch(JDOMException evt){evt.printStackTrace(); return null;}
		catch(IOException 	evt){evt.printStackTrace(); return null;}

		return lRet;
	}

	public static boolean sauvegarderTerritoires(String nomFich, List<Territoire> aSauve)
	{
		try
		{
			ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(nomFich));

			objOut.writeObject(aSauve);
			objOut.close();

			return true;
		}
		catch(FileNotFoundException evt){System.out.println("erreur de fichier");}
		catch(IOException evt){System.out.println("erreur d'e/s");}

		return false;
	}

	public List<Territoire> lireTerritoires(String nomFich)
	{
		try
		{
			ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(nomFich));
			List<Territoire> territoires = (List<Territoire>) objIn.readObject();

			objIn.close();

			return territoires;
		}
		catch(FileNotFoundException  evt) {System.out.println("erreur de fichier");}
		catch(IOException 			 evt) {System.out.println("erreur d'e/s"	 );}
		catch(ClassNotFoundException evt) {System.out.println("erreur de class"	 );}

		return null;
	}
}
