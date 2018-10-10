import java.io.*;
import org.jdom.*;
import org.jdom.input.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class LectureXML
{
	//private static Document document;
	//private static Element 	racine	;

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

	/*public static String afficher()
	{
		StringBuilder sRet = new StringBuilder();
		List<Element> listContients = racine.getChildren("continent");

		for(Element cont: listContients)
		{
			sRet.append(cont.getAttributeValue("nom") +": \n");
			List<Element> listTerritoires = cont.getChildren("territoire");

			for(Element terr: listTerritoires) sRet.append("\t" +terr.getText() +"\n");
		}

		return sRet.toString(); 
	}*/
}