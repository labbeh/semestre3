package fichiers;

import java.io.*;
import java.util.LinkedList;

/**
 * À partir de plusieurs fichiers, en crée un seul.
 * Possibilité de décaler les titres pour l'intégration
 * @author Frederic Serin, LITIS, université du Havre
 * @version 3.0, 2017-12-19
 */
public abstract class Fusion {

/**
 * Collection de l'ensemble des lignes fusionnées
 */
static LinkedList<String> toutesLignes;

/**
 * Méthode principale qui permet, à l'instar d'une fabrique,
 * d'appeler les méthodes lecture et Ecriture
 * afin de générer directement d'un terminal un document fusionné.
 * @param args fichiers à fusionner (booléen précise la nature de la fusion)
 */
public static void main (String[] args) {
// ensemble des lignes fusionnées
toutesLignes = new LinkedList<String>();
// la fusion intégrée décale les titres pour insérer le grand titre au niveau 5
boolean fusionIntegree = false;
int indice = 0;
try {
switch (args[0]) {
case "true" :
case "True" :
case "false" :
case "False" :
case "0" :
fusionIntegree = new Boolean(args[0]);
indice = 1;
break;
case "1" :
fusionIntegree = true;
indice = 1;
break;
default : indice=0;
}
} catch (Exception e) {
System.out.println(e);
}
// à partir d'ici, on lit les fichiers pour les fusionner
try {
LinkedList<String> lesLignes;
for (int i=indice; i<args.length; i++) {
lesLignes = Lecture.getLignes(args[i]);
if (fusionIntegree) {
for (String uneLigne : lesLignes) {
uneLigne = decalage(uneLigne);
toutesLignes.add(uneLigne);
} // fin parcours de toute); les lignes pour décaler les titres
} else {
toutesLignes.addAll(lesLignes);
}
// on s'assure qu'une ligne blanche sépare chaque fichier fusionné
toutesLignes.add("");
} // fin parcours de tous les fichiers
// on prend comme référence le premier fichier texte
String[] nom = decompositionNomFichier(args[indice]);
Ecriture.setLignes(nom[0]+"Fusion-"+nom[1]+nom[2],toutesLignes);
} catch (Exception e) {
System.out.println(e);
}
} // fin méthode main permettant un fonctionnement autonime

/**
 * Décale les niveaux de titres : un niveau 5 devient 4
 * transformation grand titre en titre de niveau 5
 * @param ligne la ligne traitée
 * @return le résultat de la transformation
 */
public static String decalage(String ligne) {
// on peut rencontrer un soucis s'il existe déjà des titres de niveau ==
if (ligne.startsWith("===")) 
return ligne.substring(1,ligne.length());
// pas de traitement si ligne commence par ces mots dans le document
if ((ligne.startsWith("Chapitre")) || (ligne.startsWith("Partie")))
return ("====="+ligne); 
// idem, si plusieurs apparitions de ***
if ((ligne.startsWith("***")) && (ligne.endsWith("***")))
return ("====="+ligne.substring(3,ligne.length()-3));
return ligne;
} // fin méthode décalage

/**
 * Décompose le nom du fichier en trois parties : chemin+nom+suffixe
 * @param fichierComplet nom du fichier entier
 * @return chemin, nom et suffixe (avec point)
 */
public static String[] decompositionNomFichier(String fichierComplet) {
String[] parties = new String[3];
String tampon; // nom+suffixe
// position du séparateur (slash) entre le dernier répertoire et le nom du fichier
int position = fichierComplet.lastIndexOf("/");
if (position>=0) {
// il existe un chemin non vide
parties[0] = fichierComplet.substring(0,position+1);
// reste après retrait du chemin
tampon = fichierComplet.substring(position+1,fichierComplet.length());
} else {
// il n'y a pas de chemin précisé
parties[0] = ""; // chemin vide
tampon = fichierComplet;
}
// le point sépare le nom du suffixe
position = tampon.lastIndexOf(".");
if (position>=0) { // there is a suffix
// il y a un suffixe
parties[1] = tampon.substring(0,position);
parties[2] = tampon.substring(position,tampon.length());
} else {
// il n'y a pas de suffixe
parties[1] = tampon; // nom du fichier
parties[2] = ""; // pas de suffixe
}
return parties;
} // fin de la méthode de décomposition du nom complet d'un fichier

} // fin classe Fusion