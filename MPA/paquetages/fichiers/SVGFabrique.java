package fichiers;

import java.io.*;
import java.util.LinkedList;

/**
 * Fabrique un fichier SVG en incorporant automatiquement
 * l'entête et les figures prédéfinies.
 * Une méthode setLignes par défaut de paramètres est proposée
 * pour optimisé au dimensions d'une page avec les unités employées sous Latitude.
 * @author Frederic Serin, LITIS, université le Havre Normandie
 * @version 1.1, 2018-02-02
 * @since 1.0, 2018-01-06
 */
public abstract class SVGFabrique {

/**
 * Ecriture dans un fichier d'une série de lignes SVG.
 * La largeur réelle par défaut du schéma
 * utilise l'échelle 1/30
 * Donc, pour 450px de largeur, le schéma aura une largeur de 15cm.
 * Une largeur max de 17cm est considérée, le schéma étant alors redimensionné.
 * @param fichier nom du fichier
 * @param lignes ensemble des lignes à inclure dans le fichier
 * @param largeur dimension absolue en pixels
 * @param hauteur dimension absolue en pixels
 * @return réussite de l'écriture
 */
public static boolean setLignes(String fichier, LinkedList<String> lignes, int largeur, int hauteur) {
double largeurCm = (100.0+largeur)/30.0;
if (largeurCm>17) largeurCm = 17.0;
else largeurCm = Math.round(largeurCm*100.0)/100.0;
return setLignes(fichier, lignes, largeur, hauteur, largeurCm);
} // fin méthode avec optimisation largeur réelle

/**
 * Ecriture dans un fichier d'une série de lignes SVG.
 * @param fichier nom du fichier
 * @param lignes ensemble des lignes à inclure dans le fichier
 * @param largeur dimension absolue en pixels
 * @param hauteur dimension absolue en pixels
 * @param largeurReelle largeur apparente en cm
 * @return réussite de l'écriture
 */
public static boolean setLignes(String fichier, LinkedList<String> lignes, int largeur, int hauteur, double largeurReelle) {
// création d'une marge de 50
largeur += 50;
hauteur += 50;
// il y aura échec si impossibilité d'écriture (ou de lecture) de fichiers
try {
// ensemble de toutes les lignes du code SVG
LinkedList<String> ensemble = new LinkedList<String>();
// lecture de la première entête
for (String s : Lecture.getLignes("fichiers/SVGheader1.txt")) {
ensemble.add(s);
} // fin boucle ajout des lignes 1ère entête
// écriture de la dimension de la zone absolue et relative
// hauteur et largeur sont augmentées de 50 en négatif
// la largeur réelle est de 15.0 cm par défaut
double proportion = largeurReelle / (largeur+50.0);
double h = Math.round((hauteur+50.0)*proportion*10.0)/10.0;
ensemble.add(" width=\""+largeurReelle+"cm\" height=\""+h+"cm\" viewBox=\"-50 -50 "+largeur+" "+hauteur+"\" zoomAndPan=\"disable\" ");
// lecture de la suite de l'entête
for (String s : Lecture.getLignes("fichiers/SVGheader2.txt")) {
ensemble.add(s);
} // fin boucle ajout des lignes 2e entête

// introduction des définitions
ensemble.add("<defs>");
for (String s : Lecture.getLignes("fichiers/SVGdefs.txt")) {
ensemble.add(s);
} // fin boucle ajout des lignes des définitions defs

ensemble.add("</defs>");

// ligne introduisant le graphique inclus
ensemble.add("<g>");
// inclusion des lignes passées en argument
ensemble.addAll(lignes);
// dernières lignes terminant un fichier SVG
ensemble.add("</g>");
ensemble.add("</svg>");
// écriture dans le fichier cible
FileWriter fw = new FileWriter (fichier);
BufferedWriter bw = new BufferedWriter (fw);
PrintWriter fichierSortie = new PrintWriter (bw);
// ecriture des lignes
for (String s : ensemble){
fichierSortie.println (s);
} // fin de l'écriture des lignes
// fermeture du fichier généré
fichierSortie.close();
return true;
} // fin du try, création réussie
catch (Exception e){
System.out.println(e.toString());
System.out.println("Erreur dans fichier "+fichier+" passé en argument pour écriture dans la classe SVGFabrique.");
return false;
} // fin du catch
} // fin méthode d'écriture

} // fin de la classe SVGFabrique