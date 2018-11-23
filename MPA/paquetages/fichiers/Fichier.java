package fichiers;

import java.util.LinkedList;
import java.io.*;

/**
 * La classe fichier sert d'interface entre les lignes au format LinkedList et des fichiers texte.
 * Elle permet de lire et d'écrire, elle remplace les classes Ecriture et Lecture
 * développées le 2016-03-21
 * Elle ne déclenche plus d'exception.
 * Elle propose l'ajout de lignes en fin de fichier.
 * @author Frédéric Serin, université Le Havre Normandie
 * @version 2018-02-17
 */
public abstract class Fichier {

/**
 * Ecriture dans un fichier de lignes de texte.
 * @param nomFichier nom complet du fichier
 * @param lignes de texte
 * @return true si écriture réussie, false sinon
 */
public static boolean setLignes(String nomFichier, LinkedList<String> lignes) {
boolean reussite = false;
try {
FileWriter fichier = new FileWriter(nomFichier);
for (String ligne : lignes) {
fichier.write(ligne+"\n");
}
fichier.close(); // héritée de OutputStreamWriter
reussite = true;
} catch (Exception e) {
}
return reussite;
} // fin méthode écriture de lignes

/**
 * Ajoute des lignes dans un fichier précisé en argument.
 * Ecriture à la fin du fichier.
 * @param nomFichier nom complet du fichier
 * @param lignes de texte ajoutées à la fin
 * @return true si écriture réussie, false sinon
 */
public static boolean addLignes(String nomFichier, LinkedList<String> lignes) {
boolean reussite = false;
try {
FileWriter fichier = new FileWriter(nomFichier,true);
for (String ligne : lignes) {
fichier.write(ligne+"\n");
}
fichier.close(); // héritée de OutputStreamWriter
reussite = true;
} catch (Exception e) {
}
return reussite;
} // fin méthode complète par des lignes

/**
 * Lecture des lignes à partir d'un fichier texte.
 * @param nomFichier nom complet du fichier
 * @return ensemble des lignes lues
 */
public static LinkedList<String> getLignes(String nomFichier) {
LinkedList<String> lignes = new LinkedList<String>();
String ligne;
try {
// lecture des lignes et placement dans une liste chainée
// les trois lignes qui suivent permettent d'extraire la date du fichier
// File leFichier = new File(fichierTexte);
// System.out.println("Date modif = "+leFichier.lastModified());
// lastModified retourne un long, nombre de ms depuis 1er janvier 1970

InputStream fichierEntree=new FileInputStream(nomFichier);
InputStreamReader fichierEnLecture=new InputStreamReader(fichierEntree);
BufferedReader br=new BufferedReader(fichierEnLecture);
while ((ligne=br.readLine())!=null){
lignes.add(ligne); // depose la ligne dans la liste
} // fin de la boucle while
br.close(); // fermeture du fichier ouvert en lecture
} catch (Exception e) {
}
return lignes;
} // fin méthode get Lignes


} // fin classe Fichier