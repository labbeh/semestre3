import java.util.*;

import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;

public class ArcEnCiel implements Iterable<Couleur>
{
	private List<Couleur> ensCouleur;

	public static void main(String[] args){
		new ArcEnCiel();
	}

	public ArcEnCiel()
	{
		this.ensCouleur = this.chargerXml();

		/*
		this.ensCouleur =  // A compl?ter //

		this.ensCouleur.add ( new Couleur ( "violet",  400, 128,   0, 128 ) );
		this.ensCouleur.add ( new Couleur ( "cyan",    500,   0, 192, 192 ) );
		this.ensCouleur.add ( new Couleur ( "bleu",    450,   0,   0, 192 ) );
		this.ensCouleur.add ( new Couleur ( "rouge",   750, 192,   0,   0 ) );
		this.ensCouleur.add ( new Couleur ( "vert",    550,   0, 192,   0 ) );
		this.ensCouleur.add ( new Couleur ( "jaune",   600, 255, 242,   0 ) );
		this.ensCouleur.add ( new Couleur ( "orange",  700, 255, 128,   0 ) );
		*/
	}


	/*----------------------*/
	/* Question 2.1         */
	/*----------------------*/
	private List<Couleur> chargerXml()
	{
		List<Couleur> listRetour = new ArrayList<>();  // A corriger

		Document document;
		Element  racine	 ;

		// éléments à récupérer dans le fichier xml
		String nom;
		int longueurOnde;
		int r;
		int v;
		int b;

		SAXBuilder sxb = new SAXBuilder();

		try
		{
			document = sxb.build(new File("arc.xml"));
			racine 	 = document.getRootElement();

			List<Element> couleurs = racine.getChildren("couleur");

			for(Element coul: couleurs)
			{
				nom = coul.getAttributeValue("nom");
				
				longueurOnde = Integer.parseInt(coul.getChildText("longueur"));

				// le replaceAll ser à enlever les espaces mis dans l'xml pour son
				// alignement dans l'indentation
				r = Integer.parseInt(coul.getChildText("rouge").replaceAll(" ", ""));
				v = Integer.parseInt(coul.getChildText("vert").replaceAll(" ", ""));
				b = Integer.parseInt(coul.getChildText("bleu").replaceAll(" ", ""));

				listRetour.add(new Couleur(nom, longueurOnde, r, v, b));
			}
		}
		catch(JDOMException evt){evt.printStackTrace(); return null;}
		catch(IOException 	evt){evt.printStackTrace(); return null;}

		return listRetour;
	}

	public String toString()
	{
		String sRet = "|";

		for(Couleur coul: ensCouleur)
			sRet += coul.getNom() + "|";

		return sRet;
	}



	/*-------------------------------------------------------------------------------*/
	/* M?thodes ? utiliser uniquement si vous n'arrivez pas ? faire la question 2.2  */
	public int     getNbCouleur()          { return this.ensCouleur.size();      }
	public Couleur getCouleur  (int indice){ return this.ensCouleur.get(indice); }
	/*-------------------------------------------------------------------------------*/


	/*----------------------*/
	/* Question 2.2         */
	/*----------------------*/

	@Override
  	public Iterator<Couleur> iterator(){
     	return new IterArc();
  	}

  	/**
  	* classe interne pour l'iterateur
  	*/
  	private class IterArc implements Iterator<Couleur>{
  		private int indEltCourant;

  		IterArc(){
  			indEltCourant = 0;
  		}

  		@Override
  		public boolean hasNext(){
  			return indEltCourant < ensCouleur.size();
  		}

		@Override
		public Couleur next(){
			return ensCouleur.get(this.indEltCourant++);
		}

		@Override
		public void remove() throws UnsupportedOperationException{
			new UnsupportedOperationException("suppresion impossible");
		}
  	}

	/*----------------------*/
	/* Question 2.4         */
	/*----------------------*/
	public void triNaturel()
	{
		Collections.sort(ensCouleur);
	}


	/*----------------------*/
	/* Question 2.5         */
	/*----------------------*/
	public void triSecondaire()
	{
		Collections.sort(ensCouleur, new CompareNom());
	}

	/**
	* classe interne pour avoir une autre possibilité de comparaison pour les couleurs
	* comparaison sur le nom
	*/
	private class CompareNom implements Comparator<Couleur>{

		@Override
		public int compare(Couleur coul1, Couleur coul2){
			return coul1.getNom().compareTo(coul2.getNom());
		}
	}
}