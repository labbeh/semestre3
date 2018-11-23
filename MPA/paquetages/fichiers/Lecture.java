package fichiers;

import java.io.*;
import java.util.LinkedList;

/**
 * Classe qui lit les lignes d'un fichier texte.
 * Cette classe est appelée par latitude.
 * @author Frederic Serin, LITIS, université du Havre
 * @version 2.0, 2016-03-21
 * @since 2016-03-21
 */
abstract public class Lecture {

/**
 * Extrait les lignes d'un fichier texte passé en argument.
 * Méthode appelée par Latitude.
 * @thrown génère une exception si le fichier n'existe pas ou n'est pas ouvrable
 * @param fichierTexte nom du fichier (incluant son chemin d'accès)
 * @return une liste de strings, chaque item correspond à une ligne du texte
 */
static public LinkedList<String> getLignes(String fichierTexte) 
throws Exception {
String ligne;
LinkedList<String> lignes = new LinkedList<String>();

// lecture des lignes et placement dans une liste chainée

// les trois lignes qui suivent permettent d'extraire la date du fichier
// File leFichier = new File(fichierTexte);
// System.out.println("Date modif = "+leFichier.lastModified());
// lastModified retourne un long, nombre de ms depuis 1er janvier 1970
// InputStream fichierEntree=new FileInputStream(leFichier);

InputStream fichierEntree=new FileInputStream(fichierTexte);
InputStreamReader fichierEnLecture=new InputStreamReader(fichierEntree);
BufferedReader br=new BufferedReader(fichierEnLecture);
while ((ligne=br.readLine())!=null){
lignes.add(ligne); // depose la ligne dans la liste
} // fin de la boucle while
br.close(); // fermeture du fichier ouvert en lecture
return lignes;
} // fin méthode get Lignes


} // fin classe Lecture