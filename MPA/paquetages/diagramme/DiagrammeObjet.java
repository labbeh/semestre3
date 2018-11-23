package diagramme;

import java.util.LinkedList;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
import fichiers.Fichier;
import fichiers.SVGFabrique;
import fichiers.Fusion;

/**
 * Les diagrammes d'objet sont une subclasse des diagrammes de structure.
 * Une grande partie du comportement est inspirée de celui de la confection des
 * diagrammes de classe en beaucoup plus simple.
 * On peut aussi représenter des graphes simples avec ce modèle.
 * Deux classes hébergées : Objet et Lien.
 * la classe dispose d'un main pour lire un fichier de description
 * d'un diagramme d'objet UML.
 * Les objets sont déclarées par la syntaxe
 * <A> instance ou instance
 * Et les liens par
 * A  ----- B
 * A ----> B pour les liens orientés
 * @author Frédéric Serin, Université Le Havre Normandie, LITIS
 * @version 2.0, 2018-05-28
 */
public class DiagrammeObjet extends DiagrammeStructure {

/**
 * Dictionnaire des objets créés, permet de les identifier.
 * et utilisée dans la méthode générer
 */
TreeMap<String,Objet> objets;

/**
 * Cette classe permet aussi bien les diagrammes objet
 * que les graphes simples;
 * Une des différences est le soulignement des identifiants pour les objets.
 */
boolean diagobjet;

/**
 * Permet, si on précise un diagramme simple, d'éviter le soulignement
 */
String soulignement = "style=\"text-decoration: underline;\" ";

/**
 * Méthode admettant le fichier descriptif en entrée
 * permet la génération autonome de fichiers SVG et analyse.txt
 * @param args nom du fichier texte
 */
public static void main(String[] args) {
// lecture du fichier
try {
// les lignes du fichier lu
LinkedList<String> lignes = Fichier.getLignes(args[0]);
// identité du fichier décomposée en chemin+nom+suffixe
String[] partiesNomFichier = Fusion.decompositionNomFichier(args[0]);
// création du diagramme d'objet UML
new DiagrammeObjet(partiesNomFichier[0],partiesNomFichier[1],lignes,true);
} // fin try, le diagramme est créé
catch (Exception e) {
System.out.println("problème pour la création d'un diagramme objet UML");
System.out.println(e);
} // fin du catch
} // fin méthode main

/**
 * Pour générer un diagramme d'objet.
 * @param chemin_fichier chemin pour placement des fichiers svg et -analyse.txt
 * @param identifiant_fichier nom du fichier sans suffixe
 * @param lignes code à analyser servant à la génération
 * @param diagobjet vrai si diagramme objet, si faux alors graphe simple
 */
public DiagrammeObjet(String chemin_fichier, String identifiant_fichier, LinkedList<String> lignes,boolean diagobjet) 
throws Exception {
this(chemin_fichier + identifiant_fichier,lignes,diagobjet);
} // fin constructeur avec nom décomposé et sans suffixe

/**
 * Pour générer un diagramme d'objet.
 * @param nomFichierSortie nom complet avec chemin du fichier de sortie (non suffixé)
 * @param lignes code à analyser servant à la génération
 * @param diagobjet vrai si diagramme objet, si faux alors graphe simple
 */
public DiagrammeObjet(String nomFichierSortie, LinkedList<String> lignes, boolean diagobjet) 
throws Exception {
// initialisation des attributs de Graphique
super( nomFichierSortie, lignes);
this.diagobjet = diagobjet;
// sert pour numéroter les descriptions de liens
int indice = 0;
String chaineCaractères;
// coordonnées dans le diagramme
int x, y;
// dictionnaire des objets créés
objets = new TreeMap<String,Objet>();
// variables tampon pour les instances
Objet instanceA, instanceB;
// un lien entre instances
Lien lien;
// le nom de l'objet peut être de la forme nom ou nom(x,y) avec des coordonnées fixées
String nom; // nom de l'objet
String commentaireObjet; // description supplémentaire d'un objet
// pour décomposition des lignes
String[] parties;
String[] extremes;
// parcours de toutes les lignes descriptives du diagramme objet
for (String ligne : lignes) {
// nettoyage des blancs extrêmes, si ligne est vide elle ne sera pas traitée
ligne = ligne.trim();
if (ligne.equals("@simple")) {
soulignement = "";
ligne = "";
}
// si la ligne n'est pas vide
if (ligne.length()!=0) {
// les liens contiennent des tirets (au moins 2) dans leur représentation textuelle
if (ligne.contains("--")) {
// ligne décrivant un lien entre deux instances qui doivent exister au préalable
parties = ligne.split("-{2,}");
// pour chaque partie on retire les blancs extrêmes
for (int i=0; i<parties.length; i++) parties[i] = parties[i].trim(); // fin nettoyage blancs extrêmes
// extraction nom de l'objet
extremes = parties[0].split("[ ]{1,}"); // blancs séparateurs
nom = extremes[0];
if (!objets.containsKey(nom)) {
 System.out.println("Pas d'identifiant objet pour le lien "+ligne);
throw new Exception("Il faut définir un identifiant d'objet pour l'association "+ligne);
}
// le nom identifie bien un objet
// retrait de l'identifiant dans la description de l'association
parties[0] = "";
// reconstitution de la partie gauche sans identifiant d'objet
for (int i=1; i<extremes.length; i++) parties[0] += extremes[i];
// retrouver l'objet identifié
instanceA = objets.get(nom);
// traitement partie droite du lien
extremes = parties[parties.length-1].split("[ ]{1,}"); // blancs comme séparateurs
nom = extremes[extremes.length-1]; // nom de l'objet
if (!objets.containsKey(nom)) {
 System.out.println("Pas d'identifiant second objet pour le lien "+ligne);
throw new Exception("Il faut définir un identifiant d'objet pour le lien "+ligne);
}
instanceB = objets.get(nom);
parties[parties.length-1] = "";
for (int i=0; i<(extremes.length-1); i++) parties[parties.length-1] += extremes[i];
ligne = "";
for (String p : parties) {
// on place quatre tirets entre les deux objets liés
ligne += p + "----";
}
// on retire un ---- surnuméraire mis dans la boucle précédente
ligne = ligne.substring(0,ligne.length()-4);
lien = new Lien(instanceA, instanceB, ligne);
System.out.println("Création du lien entre "+instanceA+" et "+instanceB+" avec "+ligne);
} // fin traitement d'un lien entre objets
else {
// Analyse syntaxique déclaration d'un objet
// initialement, pas de nom à l'instance
nom = "";
commentaireObjet = "";
// blanc séparateur : 3 parties maximum considérées
parties = ligne.split(" ",3);
// si alias, partie 0  encadré par des chevrons
if ((parties[0].startsWith("<")) && (parties[0].endsWith(">"))) {
 // retrait des chevrons
 parties[0] = parties[0].substring(1,parties[0].length()-1);
 if (parties[0].length()==0) {
   System.out.println("Pas d'alias défini entre chevrons : "+ligne);
  throw new Exception("Il faut définir un alias si chevrons.");
 }
 // la partie alias peut contenir des coordonnées forcées entre parenthèses
 x = refX;
 y = refY;
 if (parties[0].indexOf("(")>0) {
  // ici, on peut retrouver les coordonnées forcée de la classe
  chaineCaractères = parties[0].substring(parties[0].indexOf("(")+1, parties[0].indexOf(")", parties[0].indexOf("(")));
  try {
   x = new Integer(chaineCaractères.split(",")[0]);
   y = new Integer(chaineCaractères.split(",")[1]);
   // on retire ces coordonnées du nom de la classe
   parties[0] = parties[0].substring(0,parties[0].indexOf("("));
  } // fin du try
  catch (Exception e) {
   System.out.println("Pas de vraies coordonnées !");
  } // fin du catch
 } // fin récupération coordonnées
 // vérification que la clé n'est pas déjà affectée
 if (objets.containsKey(parties[0])) {
  // la clé identifie déjà une classe !
  System.out.println("Problème1 : l'objet "+parties[0]+" est déjà défini");
 } else {
  // la clé est nouvelle, il faut donc créer un objet
  refX = x;
  refY = y;
  // affectation nom et description de l'objet
  nom = parties[0]; // si pas de seconde partie, le nom est celui de la clé
  commentaireObjet = ""; // en cas d'absence de troisième partie
  if (parties.length>=2) {
   nom = parties[1];
   if (parties.length==3) commentaireObjet = parties[2];
  }
 } // fin partie valide clé et objet identifié et possible
} else {
 // pas d'alias, c.-à-d. pas de chevrons
 // la partie 0 peut contenir des coordonnées forcées entre parenthèses
 x = refX;
 y = refY;
 if (parties[0].indexOf("(")>0) {
  // ici, on peut retrouver les coordonnées forcée de la classe
  chaineCaractères = parties[0].substring(parties[0].indexOf("(")+1, parties[0].indexOf(")", parties[0].indexOf("(")));
  try {
   x = new Integer(chaineCaractères.split(",")[0]);
   y = new Integer(chaineCaractères.split(",")[1]);
   // on retire ces coordonnées du nom de la classe
   parties[0] = parties[0].substring(0,parties[0].indexOf("("));
  } // fin du try
  catch (Exception e) {
   System.out.println("Pas de vraies coordonnées !");
  } // fin du catch
 } // fin récupération coordonnées
 // vérification que la clé n'est pas déjà affectée
 if (objets.containsKey(parties[0])) {
  // la partie 0  est déjà définie comme objet
  System.out.println("Problème2 : l'objet "+parties[0]+" est déjà défini");
 } else {
  refX = x;
  refY = y;
  // affectation nom et description de la classe
  nom = parties[0]; // si pas de seconde partie, le nom est celui de la clé
  commentaireObjet = ""; // en cas d'absence de troisième partie
  if (parties.length==2) commentaireObjet = parties[1];
 } // fin un objet est possible
}
// on doit faire correspondre un objet à la clé
instanceA = new Objet(nom, refX, refY, commentaireObjet);
// incrément en dur, 60 de large + 50 de longueur association potentielle
refX += 110;
objets.put(parties[0],instanceA);
// tracé de l'objet
code_SVG.addAll(instanceA.generer(parties[0]));
} // fin (else) lecture de la déclaration d'un objet
} // fin cas ligne non vide (sinon rien)
} // fin parcours des lignes
// code SVG généré - partie à placer dans Graphique
code_SVG.addAll(generer());
SVGFabrique.setLignes(nomFichierSortie+".svg", code_SVG,largeurGraphique,hauteurGraphique);
Fichier.setLignes(nomFichierSortie+"-description.txt",description);
} // fin du constructeur, tous les objets et les liens sont identifiés

/**
 * Génère le code SVG
 * 31-01
 * @return ensemble du code SVG des objets et de leurs liens
 */
LinkedList<String> generer() {
// lignes contenant le code SVG
LinkedList<String> lignes = new LinkedList<String>();
// ensemble des liens déjà placés (pour éviter la redondance)
// entendu que deux objets peuvent se partager le même
Set<Lien> liens = new HashSet<Lien>();
// ligne tampon, fort utile
String ligne = "";
// coordonnées après translation
int abscisse;
int ordonnee;
// chaque objet connait déjà ses coordonnées donc il suffit de les parcourir
Objet objet;
// récolte de tous les liens
for (String cle : objets.keySet()) {
objet = objets.get(cle);
// ajout des liens de l'objet o pour traitement ci-après boucle
liens.addAll(objet.liens);
} // fin parcours des objets pour récupérer les liens
// on va chercher les dimensions maximales en parcourant tous les objets
largeurGraphique = 0;
hauteurGraphique = 0;
for (String cle : objets.keySet()) {
objet = objets.get(cle);
largeurGraphique = Math.max(largeurGraphique, objet.getXMax());
hauteurGraphique = Math.max(hauteurGraphique, objet.getYMax());
} // fin parcours de tous les objets
// augmentation pour placement avec marge
largeurGraphique += 20;
hauteurGraphique += 20;

// placement des liens
for (Lien segment : liens) {
lignes.addAll(segment.generer());
lignes.add("");
} // fin boucle sur tous les liens générés
return lignes;
} // fin méthode générer le SVG

// Classes hébergées Objet et Lien

/**
 * Un objet est un composant du diagramme d'objet.
* Il est lié à un autre objet.
 * Ses dimensions sont 60 sur 25 d'après l'alias prédéfini dans l'entête SVG
 */
class Objet {

/**
 * Nom de l'instance
 */
String nom;

/**
 * Description de l'objet qui pourra être affichée dans desc du SVG
 * et contenir un stéréotype
 */
String description;

/**
 * Abscisse pour le document SVG
 */
int x;

/**
 * Ordonnée pour le document SVG
 */
int y;

/**
 * Ensemble des liens
 */
LinkedList<Lien> liens;

/**
 * Initialisation des attributs de chaque objet.
 * @param nom de l'objet
 * @param description éléments commentaires ajoutés à l'objet
 */
Objet(String nom, String description) {
this.nom = nom;
this.description = description;
x=0;
y = 0;
liens = new LinkedList<Lien>();
} // fin constructeur par défaut

/**
 * Constructeur appelé, le précédent étant par défaut.
 * Initialisation des attributs de chaque objet.
 * @param nom de l'objet
 * @param x abscisse
 * @param y ordonnée
 * @param description ajout pour stéréotype et complément d'information
 */
Objet(String nom, int x, int y, String description) {
this(nom,description);
this.x = x;
this.y = y;
System.out.println("Création de l'objet "+nom+" ("+x+","+y+")");
} // fin objet avec coordonnées

/**
 * Ajout d'un lien lié à l'objet
 * @param lien dont l'objet est un des élément
 */
void addLien(Lien lien) {
liens.add(lien);
} // fin ajout d'un lien

/**
 * Identification comme objet
 * @return le nom de l'objet
 */
public String toString() { // description objet
return ("Objet "+nom+ "("+x+","+y+")");
} // fin méthode toString

/**
 * Génération du code SVG pour un objet
 * ou un nœud pour un graphe simple.
 * @param cle identifiant de l'objet dans le document
 * @return les lignes SVG d'un objet
 */
LinkedList<String> generer(String cle) {
LinkedList<String> lignes = new LinkedList<String>();
lignes.add("<!-- Objet "+nom+" ("+x+","+y+") -->");
lignes.add("<g aria-labelledby=\""+cle+"\">");
lignes.add("<use xlink:href=\"#objet\" x=\""+x+"\" y=\""+y+"\" />");
String ligne = "<text x=\""+(x+30)+"\" y=\""+(y+12)+"\" text-anchor=\"middle\" font-size=\""+getDimNom()+"\" fill=\"black\" dominant-baseline=\"central\" ";
if (diagobjet) ligne+= soulignement + " ";
ligne += ">";
lignes.add(ligne);
lignes.add(nom+"</text>");
ligne = "<desc id=\""+cle+"\">";
if (diagobjet) ligne += "Objet ";
else ligne += "Sommet/concept ";
ligne += nom+"</desc>";
lignes.add(ligne);
lignes.add("</g>");
lignes.add("");
return lignes;
} // fin méthode générer pour un objet

/**
 * Retourne la partie verticale (abscisse) maximum
 * pour la zone de dessin
 * @return maximum d'espace en abscisse
 */
int getXMax() {
return (x + 60);
} // fin get X Max

/**
 * Retourne la partie verticale (ordonnée) maximum
 * pour la zone de dessin
 * @return maximum d'espace en ordonnée
 */
int getYMax() {
return (y + 25);
} // fin getYMax

/**
 * Méthode permettant de dimensionner la taille
 * de la police pour le nom de l'objet
 * @return taille en points (10 par défaut)
 */
int getDimNom() {
 int taille = nom.length();
 if (taille<=12) taille = 10; // taille maximale
 else taille = 120/taille; // division entière
if (taille<8) System.out.println("Taille de "+nom+" = "+taille+" trop petite pour la visibilité");
 return taille;
} // fin méthode getDimNom retournant la taille de la police du nom
} // fin classe Objet pour un diagramme de classe UML 

/**
 * Un lien relie forcément deux objets différents.
 */
class Lien {

/**
 * Un lien relie deux objets.
 */
Objet[] objets;

/**
 * Le type de lien indique s'il s'agit 
 * simplement ordinaire : 0
 * d'une orientation côté droit 1
 * d'une orientation côté gauche 2
 * et 3 si les deux côtés
 * 2 --- 1
 */
int stereotypeLien;

/**
 * Possible étiquette placée sur le lien.
 * Ecriture A --- étiquette ---> B par exemple
 */
String etiquette;

/**
 * Angle, en radian, du lien
 * Permet de placer l'étiquette si besoin
 */
double inclinaison;

/**
 * Ensemble des types d'associations
 * 0: rien et 1: et 2; transition
 */
String[] type = {"","marker-end:url(#transition);","marker-start:url(#antitransition);","marker-end:url(#transition);marker-start:url(#antitransition);"};

/**
 * Initialisation
 */
Lien() {
// on sait qu'il y aura nécessairement deux objets liés
objets = new Objet[2];
// pas de flèche par défaut
stereotypeLien = 0;
// initialisations
inclinaison = 0;
etiquette = "";
} // fin constructeur par défaut

/**
 * Construit le lien à partir
 * des objets et de sa description
 * @param o1 un des objets liés
 * @param o2 l'autre objet relié
 * @param description chaine avec multi rôles,
 */
Lien(Objet o1, Objet o2, String description) {
this(); // constructeur par défaut
// les deux objets liés sont mémorisés
objets[0] = o1;
objets[1] = o2;
analyseDescription(description);
// on signale aux objets le lien
o1.addLien(this);
o2.addLien(this);
} // fin constructeur lien entre deux objets

/**
 * la représentation du lien est un ensemble de tirets (au moins 2)
 * avec une étiquette précisée si besoin
 * et des flèches sous forme de chevrons
 * @param description chaine de caractères décrivant le lien hors ses objets
 */
void analyseDescription(String description) {
// pour obliger les deux parties séparées
description = " "+description+" ";
// séparation en deux parties en retirant les tirets (2 au moins) intermédiaires
String[] parties = description.split("-{2,}");
// deux solutions : avec ou sans étiquette au centre
if (parties.length==3) etiquette = parties[1];
// retrait des blancs aux extrémités
parties[0] = parties[0].trim();
parties[parties.length-1] = parties[parties.length-1].trim();
// détermination type de lien (orienté ou non)
// c'est une flèche côté gauche
if (parties[0].endsWith("<")) stereotypeLien += 2;
// c'est une flèche côté droit
if (parties[parties.length-1].startsWith(">")) stereotypeLien += 1;
} // fin analyse description

/**
 * Description normalisée du lien entre deux objets
 * @return description du lien
 */
public String toString() { // description lien
String ligne = "Lien ";
ligne += "\n"+objets[0]+" ";
ligne = ligne + " --- ";
ligne+=" "+objets[1];
return ligne;
} // fin to string

/**
 * Génération des lignes SVG
 * d'un lien
 * @return les lignes SVG dédiées au lien
 */
LinkedList<String> generer() {
// ensemble des lignes retournées pour représenter le lien en SVG
LinkedList<String> lignes = new LinkedList<String>();
// une ligne créée
String ligne = "";
// abscisses et ordonnées des extrémités et du centre du lien
int[] abscisses = new int[3];
int[] ordonnees = new int[3];
// initialisation des coordonnées des objets liés
for (int i=0; i<2; i++) {
abscisses[i] = objets[i].x;
ordonnees[i] = objets[i].y;
} // fin initialisation des extrémités
// compteur unique, écriture du lien ARIA pour la description voir classe DiagrammeStructure
numAssociation++;
lignes.add("<g aria-labelledby=\""+numAssociation+"\">");
// lien entre deux objets
// attention au cas de deux abscisses égales !
double tangente = 2;
System.out.println("Traitement "+this);
double angle = 0; // angle exprimé en degrés, utile pour l'orientation du texte de l'étiquette
if (abscisses[0]!=abscisses[1]) {
tangente = 1.0 * (ordonnees[1]-ordonnees[0])/(abscisses[1]-abscisses[0]);
System.out.println("Tangente = "+tangente);
inclinaison = Math.atan(tangente);
angle = Math.round(100.0 * Math.toDegrees(inclinaison))/100.0;
System.out.println("Angle : "+angle);
// mise en regard avec la forme du rectangle 60 sur 25
tangente = 2.4 * tangente;
} // fin calcul de la tangente et de l'angle correspondant
// entre -1 et 1 on va sur les côtés gauche ou droite
if ((tangente>-1) && (tangente<1)) {
if (abscisses[0]<abscisses[1]) {
// on va vers la droite
abscisses[0] += 60;
ordonnees[0] += Math.round(12.5 + 12.5*tangente);
ordonnees[1] += Math.round(12.5 - 12.5*tangente);
} else {
// on va vers la gauche
abscisses[1] += 60;
ordonnees[0] += Math.round(12.5 - 12.5*tangente);
ordonnees[1] += Math.round(12.5 + 12.5*tangente);
}
// fin lien horizontal
} else {
// sinon on va vers le haut ou le bas et on prend l'inverse la cotangente
tangente = 1.0 * (abscisses[1]-abscisses[0]) / (ordonnees[1]-ordonnees[0]);
System.out.println("Cotangente = "+tangente);
// attention, la variable appelée tangente est une cotangente
inclinaison = Math.PI/2-Math.atan(tangente);
if (inclinaison > (Math.PI/2)) inclinaison -= Math.PI;
angle = Math.round(100.0 * Math.toDegrees(inclinaison)) / 100.0;
System.out.println("Angle complémentaire cause cotangente : "+angle);
// mise en regard de la variable tangente avec la forme du rectangle 25 sur 60
tangente = 0.41667 * tangente;
if (ordonnees[0]<ordonnees[1]) {
// on va vers le bas
ordonnees[0] += 25;
abscisses[0] += 30 + Math.round(30.0*tangente);
abscisses[1] += 30 - Math.round(30.0*tangente);
} else {
// on va vers le haut
ordonnees[1] += 25;
abscisses[0] += 30 - Math.round(30.0*tangente);
abscisses[1] += 30 + Math.round(30.0*tangente);
}
} // fin second cas, lien vertical

// on calcul le milieu du segment pour placer l'étiquette
abscisses[2] = (abscisses[0]+abscisses[1])/2;
ordonnees[2] = (ordonnees[0]+ordonnees[1])/2;
// décalage du lien de 10
abscisses[2] += 15 * Math.cos(inclinaison - Math.PI/2);
ordonnees[2] += 15 * Math.sin(inclinaison - Math.PI/2);
ligne = "<line x1=\"";
ligne += abscisses[0]+"\" y1=\""+ordonnees[0];
ligne += "\" x2=\""+abscisses[1]+"\" y2=\""+ordonnees[1];
ligne += "\" style=\"stroke: black; stroke-width: 0.6;"+type[stereotypeLien]+" \" />";
lignes.add(ligne);
// affichage de l'étiquette
// l'ordre des attributs est important, coordonnées relatives à la transformation précédente
lignes.add("<text x=\""+abscisses[2]+"\" y=\""+ordonnees[2]+"\" font-size=\"8\" dominant-baseline=\"central\" text-anchor=\"middle\" transform=\"rotate("+angle+" "+abscisses[2]+" "+ordonnees[2]+")\" fill=\"black\" >");
lignes.add(etiquette+"</text>");
lignes.add("<desc id=\""+numAssociation+"\">"+getDescription()+"</desc>");
lignes.add("</g>");
lignes.add("");
return lignes;
} // fin méthode génération lignes SVG du lien

/**
 * Génère le commentaire nécessaire pour la description
 * du lien.
 * @return description utile pour le desc du SVG
 */
String getDescription() {
String desc = "Lien ";
switch (stereotypeLien) {
case 1 : // lien orienté vers la droite
desc += "dirigé de "+objets[0]+" vers "+objets[1];
break;
case 2 : // lien orienté vers la gauche
desc += "dirigé de "+objets[1]+" vers "+objets[0];
break;
case 3 : // lien bidirectionnel
desc += "bidirectionnel créant une interdépendance entre "+objets[0]+" et "+objets[1];
break;
default : // pas d'orientation
desc += "entre "+objets[0]+" et "+objets[1];
}
System.out.println(desc);
return desc;
} // fin description du lien

} // fin classe Lien

} // fin classe Diagramme d'objet