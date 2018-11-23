package fichiers;

import java.io.*;
import java.util.LinkedList;

/**
 * Classe permettant d'écrire des lignes texte dans un fichier.
 * @author Frederic Serin, LITIS, université du Havre Normandie
 * @version 2.0.1, 2017-04-15
 * @since 2.0, 2016-03-21
 */
abstract public class Ecriture {

/**
 * Ecriture dans un fichier d'une série de lignes.
 * @param fichier nom du fichier
 * @param lignes ensemble du texte à inscrire dans le fichier
 * @return réussite de l'écriture
 */
static public boolean setLignes(String fichier, LinkedList<String> lignes) {
try {
FileWriter fw = new FileWriter (fichier);
BufferedWriter bw = new BufferedWriter (fw);
PrintWriter fichierSortie = new PrintWriter (bw);
// ecriture des lignes
for (String s : lignes){
fichierSortie.println (s);
} // fin de l'écriture des lignes
// fermeture du fichier généré
fichierSortie.close();
return true;
} // fin du try, création réussie
catch (Exception e){
System.out.println(e.toString());
System.out.println("Erreur dans fichier "+fichier+" passé en argument pour écriture dans la classe Ecriture.");
return false;
} // fin du catch
} // fin méthode d'écriture

} // fin de la classe écriture dans un fichier