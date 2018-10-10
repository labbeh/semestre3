import java.io.*;
import org.jdom.*;
import org.jdom.input.*;

import java.util.List;
import java.util.Iterator;

public class LectureXML
{
	private static Document document;
	private static Element 	racine	;

	public static void main(String[] args)
	{
		SAXBuilder sxb = new SAXBuilder();

		try
		{
			document = sxb.build(new File("risk.xml"));
		}
		catch(JDOMException evt){evt.printStackTrace();}
		catch(IOException evt){evt.printStackTrace();}

		racine = document.getRootElement();

		System.out.println(afficher());
	}

	public static String afficher()
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
	}
}