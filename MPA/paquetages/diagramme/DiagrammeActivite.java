package diagramme;

import java.util.LinkedList;
import java.util.TreeMap;
import fichiers.Fichier;
import fichiers.Fusion;
import fichiers.SVGFabrique;

/**
 * Un diagramme d'activité est composé, principalement d'activités présentées sous forme de processus.
 * Les autres éléments étant les transitions et l'activité de début (unique).
 * @author Frédéric Serin, Université Le Havre Normandie, LITIS
 * @version 1.0, 2018-03-01
 */
public class DiagrammeActivite extends Graphique {

/**
 * Le processus principal est un processus avec
 * une activité de début qui le commence.
 */
Processus processusPrincipal;

/**
 * Ensemble des lignes générées pour le code SVG
 */
LinkedList<String> code_SVG;

/**
 * Ce compteur permet d'éviter tout risque avec les noms des processus et activités
 * pour les liens aria-labelledby
 */
int compteurDescrip;

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
// création du diagramme d'activité UML
new DiagrammeActivite(partiesNomFichier[0],partiesNomFichier[1],lignes);
} // fin try, le diagramme est créé
catch (Exception e) {
System.out.println("Erreur dans la méthode main");
System.out.println(e);
} // fin du catch
} // fin méthode main

/**
 * Initialisation des attributs
 */
public DiagrammeActivite() {
super();
code_SVG = new LinkedList<String>();
// initialisation compteur pour liens des descriptions 
compteurDescrip = 0;
} // fin constructeur initialisation

/**
 * Pour générer un diagramme d'activité.
 * @param chemin_fichier chemin pour placement des fichiers svg et -analyse.txt
 * @param identifiant_fichier nom du fichier sans suffixe
 * @param lignes descriptives à analyser servant à la génération
 */
public DiagrammeActivite(String chemin_fichier, String identifiant_fichier, LinkedList<String> lignes) 
throws Exception {
this(chemin_fichier + identifiant_fichier, lignes);
} // fin constructeur avec décomposition du nom du fichier

/**
 * Pour générer un diagramme d'activité.
 * @param nomFichierSortie nom du fichier sans suffixe
 * @param lignes descriptives à analyser servant à la génération
 */
public DiagrammeActivite(String nomFichierSortie, LinkedList<String> lignes) 
throws Exception {
// initialisation des attributs de Graphique
super( nomFichierSortie, lignes);
code_SVG = new LinkedList<String>();
// dictionnaire des processus
TreeMap<String,Sommet> dicoProcessus = new TreeMap<String,Sommet>();
// variables nécessaires pour repérer les processus
int type = 0;
String nomProcessus;
String tampon;
String[] parties;
// les lignes nettoyées des blancs extrèmes
LinkedList<String> nouvellesLignes = new LinkedList<String>();
// servira à retrouver la racine de l'arbre, processus principal
Sommet racine = new Sommet("processus vide",0); // pour éviter l'erreur de variable vide
System.out.println("phase 1 : première boucle");
for (String ligne : lignes) {
// retrait de blancs extrêmes potentiels et inutiles
ligne = ligne.trim();
System.out.println("Lecture de la ligne "+ligne);
if (ligne.startsWith("<")) {
// extraction nom du processus
nomProcessus = ligne.substring(1,ligne.indexOf(">"));
System.out.println("Extraction nom du processus "+nomProcessus);
// partie à droite de l'alias <e>
tampon = ligne.substring(ligne.indexOf(">")+1,ligne.length()).trim();
System.out.println("Partie à droite du processus : "+tampon);
parties = tampon.split(" ",2);
System.out.println("Le mot clé est : "+parties[0]);
// le mot clé est transformé en lettres minuscules (si besoin)
parties[0] = parties[0].toLowerCase();
// affecte son code type au processus
switch (parties[0]) {
case "embranchement" :
type = 2;
break;
case "test" :
type = 3;
break;
case "boucle" :
type = 4;
break;
default :
type = 1; // linéaire par défaut, ce qui évite le soucis d'accent
} // fin switch qui affecte le type de processus
if (!dicoProcessus.containsKey(nomProcessus)) {
if (parties.length<2) tampon = "";
else tampon = parties[1];
racine = new Sommet(nomProcessus,type,tampon);
dicoProcessus.put(nomProcessus,racine);
} else {
System.out.println("Ne devrait pas se produire, processus "+nomProcessus+" doublement identifié");
}
} // fin cas étudié des lignes
nouvellesLignes.add(ligne);
} // fin boucle sur toutes les lignes
System.out.println("phase 2 : sortie de la première boucle");
for (String ligne : dicoProcessus.keySet()) {
System.out.println("Clé: "+ligne+" valeur: "+dicoProcessus.get(ligne));
}
// à partir de là, un dictionnaire de tous les processus composites existe
// seules les activités ne sont pas encore des sommets
Sommet processusComposite = new Sommet("",5); // processus courant composé
Sommet processusInterne; // processus composite compris dans un processus composite
int compteur = 2; // compte le nombre de processus internes possibles
for (String ligne : nouvellesLignes) {
if (ligne.startsWith("<")) {
// nous commençons un processus composite, connu depuis le parcours précédent
nomProcessus = ligne.substring(1,ligne.indexOf(">"));
processusComposite = dicoProcessus.get(nomProcessus);
compteur = 2;
} else {
// on s'assure que nous lisons un processus interne, compteur en cours
if (compteur!=0) {
// la ligne est interne ou signale la fin du processus courant
if (ligne.length()==0) {
// cas d'une ligne vide porteuse de sens
if (processusComposite.type>=3) {
compteur--;
processusComposite.addEnfant(null);
} else compteur = 0;
if (compteur==0) processusComposite=null;
} // fin cas ligne vide
else {
// cas ou la ligne n'est pas vide, elle est soit un processus composite soit une activité
if (dicoProcessus.containsKey(ligne)) {
processusInterne = dicoProcessus.get(ligne);
processusInterne.setParent(processusComposite);
} else {
// cas d'une activité
new Sommet(ligne,processusComposite);
}
} // fin analyse de la ligne
} // fin cas ligne utile
else {
System.out.println("Compteur = "+compteur+" avec ligne "+ligne);
} // fin else compteur =0
} // fin else ligne ne commençant pas par chevron, ligne interne au processus courant ou ligne vide
} // fin boucle sur toutes les lignes
// tous les processus sont des sommets dans un arbre unique
// il faut trouver la racine qui sera le processus principal
racine = racine.getRacine();
// chaque sommet doit être traduit en processus
processusPrincipal = racine.getProcessus();
calculDimensions();
System.out.println("Largeur = "+processusPrincipal.getLargeur());
System.out.println("Hauteur = "+processusPrincipal.getHauteur());
// après le calcul des dimensions on peut connaitre
// le point d'entrée du processus principal
// et ajouter l'activité de début
int point_x = processusPrincipal.x + processusPrincipal.dimensions[0];
int point_y = processusPrincipal.y + 50;
processusPrincipal.calculPosition(point_x,point_y);
String ligne ="<!-- Activité Entrée -->";
code_SVG.add(ligne);
ligne ="<use xlink:href=\"#activitedebut\" x=\""+(point_x-5)+"\" y=\"12\" />";
code_SVG.add(ligne);
ligne = "<line x1=\""+point_x+"\" y1=\"22\" x2=\""+point_x+"\" y2=\"50\" style=\"stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />";
code_SVG.add(ligne);
code_SVG.add("");
// génération de tous les processus en SVG
generer();
// ajout du processus final (activité de fin)
point_x = point_x - processusPrincipal.dimensions[0] + processusPrincipal.dimensions[2];
point_y = point_y + processusPrincipal.getHauteur();
code_SVG.add("");
ligne = "<line x1=\""+point_x+"\" y1=\""+point_y+"\" x2=\""+point_x+"\" y2=\""+(point_y+25)+"\" style=\"stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />";
code_SVG.add(ligne);
ligne = "<use xlink:href=\"#activitefin\" x=\""+(point_x-7)+"\" y=\""+(point_y+25)+"\" />";
code_SVG.add(ligne);
code_SVG.add("");

// code SVG généré - partie à placer dans Graphique
hauteurGraphique += 99; // début et fin ajoutés
SVGFabrique.setLignes(getNomFichierSVG(),code_SVG,largeurGraphique,hauteurGraphique);

} // fin constructeur à partir de lignes descriptives

/**
 * Calcul automatique et récursif des dimensions
 * à partir du processus principal
 */
void calculDimensions() {
processusPrincipal.calculDimensions();
} // fin calcul dimensions du diagramme d'activité

/**
 * Génère le code SVG
 * et le place progressivement dans code_SVG
 */
void generer() {
processusPrincipal.generer();
} // fin méthode générer du diagramme

/**
 * La hauteur du diagramme est celle du processus principal.
 * @return hauteur en pixels
 */
int getHauteur() {
return processusPrincipal.getHauteur();
} // fin retour de la hauteur

/**
 * La largeur du diagramme est celle du processus principal.
 * @return largeur en pixels
 */
int getLargeur() {
return processusPrincipal.getLargeur();
} // fin retour de la largeur

/****** Classes hébergées ******/

/**
 * Les sommets sont dans l'arborescence, les éléments constitutifs
 * des processus.
 * Chaque parent possède des enfants, sauf les activités, feuilles de l'arbre.
 * Chaque enfant possède un seul parent, sauf le processus principal, racine de l'arbre.
 * Chaque sommet représente un processus et possède ainsi un nom,
 * un type (numéro).
 */
class Sommet {

/**
 * Parent, unique, du sommet courant.
 */
Sommet parent;

/**
 * Enfants du sommet, 0..*
 */
LinkedList<Sommet> enfants;

/**
 * Nom du processus, appelé alias pour les composites
 * et identifiant d'une activité.
 */
String nom;

/**
 * Type de processus contenu dans le sommet, 0: activité
 * 1: linéaire, 2: embranchement, 3: test et 4: boucle
 */
int type;

/**
 * La description intègre les si-sinon des boucles et tests.
 */
String description;

/**
 * Constructeur qui initialise les attributs.
 * @param nom identifiant ou alias du processus
 * @param type du processus (0:ativité, 1:linéaire, 2:embranchement, 3:test, 4:boucle)
 */
Sommet(String nom, int type) {
this.type = type;
this.nom = nom;
this.description = "";
enfants = new LinkedList<Sommet>();
} // fin constructeur sans description

/**
 * Constructeur qui initialise les attributs
 * sans placer le sommet dans l'arborescence.
 * @param nom identifiant ou alias du processus
 * @param type du processus (0: ativité, 1: linéaire, 2: embranchement, 3: test, 4: boucle)
 * @param complementaire utile pour les tests et boucles, complète la description potentiellement
 */
Sommet(String nom, int type, String complementaire) {
this(nom, type);
this.description = complementaire;
} // fin constructeur de processus composite

/**
 * Constructeur d'une activité nécessairement inscrite dans un processus composite (son parent).
 * Une activité (type 0) connait son parent dès la construction.
 * @param nom identifiant de l'activité
 * @param parent processus composite contenant ce processus
 */
Sommet(String nom, Sommet parent) {
this(nom, 0);
this.parent = parent;
parent.addEnfant(this);
} // fin constructeur d'une activité

/**
 * Affecte son parent à un processus et
 * indique que le sommet est enfant du parent.
 * @param parent
 */
void setParent(Sommet parent) {
this.parent = parent;
parent.addEnfant(this);
} // setParent et donc aussi enfant du parent
/**
 * Donne un nouvel enfant à un parent.
 * @param enfant nouvel enfant
 */
void addEnfant(Sommet enfant) {
enfants.add(enfant);
} // fin méthode addEnfant

/**
 * A partir de n'importe quel sommet, retourne la racine de l'arbre
 * en remontant par les parents.
 * @return racine de l'arbre
 */
Sommet getRacine() {
if (parent==null) return this;
return (parent.getRacine());
} // fin méthode getRacine

/**
 * Permet de créer les processus de façon récursive
 * pour constituer un ensemble cohérent.
 * @return processus complet et composite
 */
Processus getProcessus() {
Processus nouveauProcessus; // valeur retournée
// doit d'abord connaitre les processus inclus - récursivité
if (type==0) return (new Activite(nom));
// ensemble des sous-processus inclus dans le processus à créer
Processus[] subprocessus = new Processus[enfants.size()];
int indice = 0;
for (Sommet s : enfants) {
if (s==null) subprocessus[indice] = null;
else subprocessus[indice] = s.getProcessus();
indice++;
} // fin boucle sur tous les enfants
// il n'y a plus qu'à créer le processus composite en fonction de son type
switch (type) {
case 0 : // jamais, voir test premier de la méthode
nouveauProcessus = new Activite(nom);
break;
case 1 :
nouveauProcessus = new Lineaire(subprocessus);
break;
case 2 :
nouveauProcessus = new Embranchement(subprocessus);
break;
case 3 :
nouveauProcessus = new Test(subprocessus,description);
break;
default : // cas 4
nouveauProcessus = new Boucle(subprocessus,description);
} // fin du switch
System.out.println("Création processus "+type);
return nouveauProcessus;
} // fin méthode getPorcessus

/**
 * Retourne une information succincte sur le processus contenu dans le sommet.
 * @return nom suivi du type puis de la description
 */
public String toString() {
return ("Sommet "+nom+" de type "+type+" ("+description+")");
} // fin toString présentant un sommet

} // fin classe Sommet de l'arbre structurant le processus - diagramme d'activité

/**
 * Les processus sont les éléments constituants un diagramme d'activité.
 * Il existe cinq types de processus : activité qui est le processus élémentaire, 
 * Linéaire, test, boucle et embranchement qui sont des processus composites.
 */
abstract class Processus {

/**
 * Les 4 dimensions absolues : largeurs et hauteur.
 * largeur haut gauche, haut droite, et bas gauche
 * l'indice 3 est celui de la hauteur
 */
int[] dimensions;

/**
 * Abscisse pour le document SVG
 * Point central d'entrée dans le processus.
 */
int x;

/**
 *Ordonnée pour le document SVG
 * Point central d'entrée du processus.
 */
int y;

/**
 * Initialisation des variables,
 * dimensionnement du tableau des dimensions
 */
Processus() {
dimensions = new int[4];
} // fin constructeur d'initialisation

/**
 * Hauteur totale du processus
 * @return l'élément 3 de dimensions
 */
int getHauteur() {
return dimensions[3];
}

/**
 * Largeur totale du processus
 * @return somme de 0 et 1 de dimensions
 */
int getLargeur() {
return (dimensions[0]+dimensions[1]);
} // largeur totale du processus

/**
 * Calcul automatique (y compris récursif pour les processus composites)
 des dimensions
 */
abstract void calculDimensions();

/**
 * Calcul automatique (y compris récursif pour les processus composites)
 des positions des éléments.
 * @param Px abscisse point d'entrée
 * @param Py ordonnée point d'entrée
 */
abstract void calculPosition(int Px, int Py);

/**
 * Génération du code SVG
 * de chaque type de processus.
 */
abstract void generer();

} // fin classe abstraite Processus

/**
 * L'activité est le processus élémentaire, ces dimensions absolues sont fixées
 * dans le fichier de définitions (voir fichiers.SVGdefs.txt
 */
class Activite extends Processus {

/**
 * Nom de l'activité
 */
String nom;

/**
 * les dimensions d'une activité sont établies et fixées
 * dans la méthode calculDimensions
 * largeur de 70 et hauteur de 25
 */
Activite(String nom) {
super();
this.nom = nom;
} // fin constructeur d'une activité

/**
 * Dimensions d'une activité élémentaire
 * Une activité faisant 70 sur 25 (cf. defs)
 */
void calculDimensions() {
dimensions[0] = 35; // largeur haut gauche
dimensions[1] = 35; // largeur haut droite
dimensions[2] = 35; // largeur bas gauche
dimensions[3] = 25; // hauteur activité
} // fin méthode initialisation dimensions d'une activité

/**
 * Positionnement de l'activité.
 * @param Px abscisse point d'entrée
 * @param Py ordonnée point d'entrée
 */
void calculPosition(int Px, int Py) {
x = Px;
y = Py;
} // fin méthode calculPosition

/**
 * Génération du code SVG d'une activité, fait référence à l'activité définie comme classe
 * dans l'entête du  SVG et de dimension 70 sur 25
 */
void generer() {
String ligne = "";
// <!-- Activité Consultation mels -->
// incrémentation compteur identification des liens
compteurDescrip++;
ligne = "<g aria-labelledby=\"Activite"+compteurDescrip+"\">";
code_SVG.add(ligne);
ligne = "<use xlink:href=\"#activite\" x=\""+(x-35)+"\" y=\""+y+"\" />";
code_SVG.add(ligne);
ligne = "<text x=\""+x+"\" y=\""+(y+12)+"\" text-anchor=\"middle\" font-size=\""+getDimNom()+"\" fill=\"black\" dominant-baseline=\"central\" >";
code_SVG.add(ligne);
ligne = nom + "</text>";
code_SVG.add(ligne);
// description de l'activité
ligne = "<desc id=\"Activite"+compteurDescrip+"\">";
ligne += "Activité " + nom + ".";
ligne += "</desc>"; // fin description embranchement
code_SVG.add(ligne);
code_SVG.add("</g>");
code_SVG.add("");
} // fin méthode générer une activité

/**
 * Méthode permettant de dimensionner la taille
 * de la police pour le nom de l'activité
 * @return taille en points (10 par défaut)
 */
int getDimNom() {
 int taille = nom.length();
 if (taille<=14) taille = 10; // taille maximale
 else taille = 140/taille; // division entière
if (taille<8) System.out.println("Taille de "+nom+" = "+taille+" trop petite pour la visibilité");
 return taille;
} // fin méthode getDimNom retournant la taille de la police du nom


} // fin classe Activité qui hérite de Processus

/**
 * Un processus composite est un processus contenant d'autres processus.
 * Il existe quatre processus composites, tous subclasses de la classe Composite.
 * Cette dernière regroupe tous les processus inclus.
 */
abstract class Composite extends Processus {

/**
 * Ensemble des processus qui constituent le processus
 */
Processus[] processus;

/**
 * Construit un processus avec ses processus inclus s'ils existent.
 * Ces derniers doivent être fournis à la création, il n'est pas possible d'en ajouter ensuite.
 * @param processus ensemble des processus contenus
 */
Composite(Processus[] processus) {
super();
if (processus.length==0)
this.processus = null;
else {
this.processus = new Processus[processus.length];
for (int i=0; i<processus.length; i++) this.processus[i] = processus[i];
}
} // fin constructeur initialisation des attributs

/**
 * Calcul automatique et récursif des dimensions
 * du processus composite.
 */
void calculDimensions() {
for (Processus p : processus) {
// il est possible que certains processus soient null
if (p!=null)
p.calculDimensions();
} // fin boucle calcul des dimensions des processus inclus
} // fin calcul des dimensions des processus inclus

} // fin classe Composite, hérite de Processus

/**
 * Un processus linéaire est un processus constitué de processus inclus
 * qui se succèdent linéairement (sans test ni embranchement.
 */
class Lineaire extends Composite {

/**
 * Processus linéaire constitué linéairement de processus
 * @param processus ensemble ordonné des processus qui se succèdent
 */
Lineaire(Processus[] processus) {
super(processus);
} // fin constructeur créant le processus linéaire

/**
 * Dimensions d'un processus linéaire
 * Les dimensions sont les extrémités des processus contenus
 * La hauteur est augmentée des transitions internes (hauteur fixée à 25)
*/
void calculDimensions() {
super.calculDimensions();
// on part du point d'entrée centré en 0
int largeurGauche = 0;
int largeurDroite = 0;
int hauteur = 0;
int decalage = 0;
for (Processus p : processus) {
largeurGauche = Math.max(largeurGauche,p.dimensions[0]-decalage);
largeurDroite = Math.max(largeurDroite,p.dimensions[1]+decalage);
// 25 est la longueur d'une transition entre processus
hauteur += (p.getHauteur() + 25);
decalage += p.dimensions[2] - p.dimensions[0];
} // fin parcours de tous les processus
dimensions[0] = largeurGauche;
dimensions[1] = largeurDroite;
dimensions[2] = largeurGauche+decalage;
// on a une transition en trop dans la boucle qu'on retranche ici
dimensions[3] = hauteur - 25;
} // fin méthode calcul dimensions d'un processus linéaire

/**
 * Positionnement des processus inclus par
 * rapport au point d'entrée (quasi central)
 * @param Px abscisse point d'entrée
 * @param Py ordonnée point d'entrée
 */
void calculPosition(int Px, int Py) {
x = Px;
y = Py;
// on part du point d'entrée centré en 0
int point_x = x;
int point_y = y;
for (Processus p : processus) {
p.calculPosition(point_x,point_y);
// décalage entre point d'entrée et point de sortie
point_x = point_x - p.dimensions[0] + p.dimensions[2];
point_y += p.getHauteur() + 25; // 25 est l'écart entre deux processus consécutifs
} // fin parcours de tous les processus
} // fin méthode calculPosition

/**
 * Génération du code SVG des processus linéaires.
 * Ces processus sont verticaux, en ligne.
 */
void generer() {
String ligne = "";
Processus precedent = null;
Processus suivant = null;
int xcentral;
int ypremier;
int ydernier;
for (Processus p : processus) {
precedent = suivant;
suivant = p;
if (precedent != null) {
xcentral = precedent.x-precedent.dimensions[0]+precedent.dimensions[2];
ypremier = precedent.y+precedent.getHauteur();
ydernier = suivant.y;
code_SVG.add("<line x1=\""+xcentral+"\" y1=\""+ypremier+"\" x2=\""+xcentral+"\" y2=\""+ydernier+"\" style=\"stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />");
} // génération entre deux processus de la transition
// tracé du processus courant
p.generer();
} // fin boucle sur l'ensemble des processus contenus
} // fin génération processus linéaire

} // fin classe processus linéaire qui hérite de processus

/**
 * Une boucle est un processus constitué de un ou deux processus
 * avec diverses configurations de forme.
 */
class Boucle extends Composite  {

/**
 * Texte placé pour rester dans la boucle
 */
String tantque;

/**
 * Test de sortie de la boucle
 */
String sinon;

/**
 * Une boucle possède un ou deux processus
 * @param processus 0: avant débranchement, 1: après débranchement
 * @param complementaire dont le tant que sinon
 */
Boucle(Processus[] processus, String complementaire) {
super(processus);
sinon = "";
String[] parties = complementaire.split(" ",3);
tantque =parties[0];
if (parties.length>1) sinon =parties[1];
} // fin initialisation d'une boucle

/**
 * Dimensions d'une boucle, sachant qu'il y a un ou deux processus
 */
void calculDimensions() {
super.calculDimensions();
// on part du point relatif d'entrée centré en 0
int largeurGauche = 0; // à gauche du point d'entrée
int largeurDroite = 0; // à droite du point d'entrée
int hauteur = 14; // un losange au moins (14)
// variable par rapport au centre initial
int decalage = 0;
// s'il y a un processus faire avant, indice 0
if (processus[0]!=null) {
hauteur += 34; // forcément un second losange et une transition
largeurGauche = processus[0].dimensions[0]+10;
largeurDroite = processus[0].dimensions[1];
hauteur += processus[0].getHauteur() + 20; // une transition
decalage += processus[0].dimensions[2] - processus[0].dimensions[0];
dimensions[2] = largeurGauche+decalage;
} // fin si processus 0 existe
if (processus[1]!=null) {
// il y a un processus 1
largeurGauche = Math.max(largeurGauche,processus[1].dimensions[0]-decalage+10);
largeurDroite = Math.max(largeurDroite,processus[1].dimensions[1]+decalage+10);
hauteur += processus[1].getHauteur() + 30; // 20 + hauteur processus + 10
dimensions[2] = largeurGauche + decalage + processus[1].getLargeur() - processus[1].dimensions[2] + 10;
} // fin si processus 1 existe
dimensions[0] = largeurGauche;
dimensions[1] = largeurDroite;
dimensions[3] = hauteur;
} // fin calcul des dimensions d'une boucle

/**
 * Positionnement des processus dans une boucle
 * par rapport au point d'entrée passé en argument.
 * @param Px abscisse point d'entrée
 * @param Py ordonnée point d'entrée
 */
void calculPosition(int Px, int Py) {
x = Px;
y = Py;
// le premier processus est forcément aligné sur l'entrée
int point_x = x;
// 14+20 espace entre losange et premier processus
int point_y = y + 34;
// s'il y a un processus faire avant
if (processus[0]!=null) {
processus[0].calculPosition(point_x,point_y);
// décalage de l'abscisse
point_x = point_x -processus[0].dimensions[0] + processus[0].dimensions[2];
point_y += processus[0].getHauteur() + 54; // 20+14+20
} // fin si processus 0 existe
if (processus[1]!=null) {
processus[1].calculPosition(point_x,point_y);
} // fin si processus 1 existe
} // fin méthode calcul Position boucle

/**
 * Génération code SVG pour une boucle
 */
void generer() {
String ligne = "";
int abscisse = x;
int ypremier = y;
int ydernier;
// incrémentation compteur identification des liens
compteurDescrip++;
ligne = "<g aria-labelledby=\"Boucle"+compteurDescrip+"\">";
code_SVG.add(ligne);
// s'il y a un processus 0
if (processus[0]!=null) {
// création du premier losange
ligne = "<polygon points=\""+x+","+y+" "+(x+10)+","+(y+7)+" "+x+","+(y+14)+" "+(x-10)+","+(y+7)+"\" style=\"fill:white; stroke:black; stroke-width:0.6;\" />";
code_SVG.add(ligne);
// une transition partant du losange (systématique)
ypremier = ypremier + 14;
ydernier = ypremier + 20;
code_SVG.add("<line x1=\""+abscisse+"\" y1=\""+ypremier+"\" x2=\""+abscisse+"\" y2=\""+ydernier+"\" style=\"stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />");
processus[0].generer();
abscisse += processus[0].dimensions[2] - processus[0].dimensions[0];
ypremier += 20 + processus[0].getHauteur(); // 20+25
ydernier = ypremier + 20;
code_SVG.add("<line x1=\""+abscisse+"\" y1=\""+ypremier+"\" x2=\""+abscisse+"\" y2=\""+ydernier+"\" style=\"stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />");
ypremier = ydernier;
} // fin si premier processus existe
// affichage du premier ou second losange selon cas
ligne = "<polygon points=\""+abscisse+","+ypremier+" "+(abscisse+10)+","+(ypremier+7)+" "+abscisse+","+(ypremier+14)+" "+(abscisse-10)+","+(ypremier+7)+"\" style=\"fill:white; stroke:black; stroke-width:0.6;\" />";
code_SVG.add(ligne);
ypremier += 14; // hauteur du losange
ydernier = ypremier + 20;
if (processus[1]!=null) {
// frod sinon
// sinon à droite
ligne = "<text x=\""+(abscisse+10)+"\" y=\""+(ypremier-12)+"\" text-anchor=\"start\" font-size=\"9\" fill=\"black\" dominant-baseline=\"central\" >";
code_SVG.add(ligne);
ligne = sinon + "</text>";
code_SVG.add(ligne);

// ligne sans transition à la droite du losange
// modifié à CenterPark
ligne = "<polyline points=\""+(abscisse+10)+","+(ypremier-7)+" "+(x-dimensions[0]+dimensions[2])+","+(ypremier-7)+" "+(x-dimensions[0]+dimensions[2])+","+(y+getHauteur())+" \" style=\"fill:none; stroke: black; stroke-width: 0.6; \" />";
code_SVG.add(ligne);

// tant que vers le bas (à gauche)
ligne = "<text x=\""+(abscisse-5)+"\" y=\""+(ypremier+7)+"\" text-anchor=\"end\" font-size=\"9\" fill=\"black\" dominant-baseline=\"central\" >";
code_SVG.add(ligne);
ligne = tantque + "</text>";
code_SVG.add(ligne);

// transition après le second losange, vers le bas
code_SVG.add("<line x1=\""+abscisse+"\" y1=\""+ypremier+"\" x2=\""+abscisse+"\" y2=\""+ydernier+"\" style=\"stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />");
processus[1].generer();
abscisse += processus[1].dimensions[2] - processus[1].dimensions[0];
ypremier += 20 + processus[1].getHauteur(); // 20+25
ydernier = ypremier + 10; // ici on va ensuite partir vers la gauche pour remonter
ligne = "<polyline points=\""+abscisse+","+ypremier+" "+abscisse+","+ydernier+" "+(x-dimensions[0])+","+ydernier+" "+(x-dimensions[0])+","+(y+7)+" "+(x-10)+","+(y+7)+"\" style=\"fill:none; stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />";
code_SVG.add(ligne);

} else {
// retour du losange vers le premier car pas de second processus
// extrémité gauche du losange
abscisse = abscisse - 10;
ypremier = ypremier - 7;
// tantque à gauche
ligne = "<text x=\""+abscisse+"\" y=\""+(ypremier+5)+"\" text-anchor=\"end\" font-size=\"9\" fill=\"black\" dominant-baseline=\"central\" >";
code_SVG.add(ligne);
ligne = tantque + "</text>";
code_SVG.add(ligne);
// sinon en bas (à droite)
ligne = "<text x=\""+(abscisse+15)+"\" y=\""+(ypremier+14)+"\" text-anchor=\"start\" font-size=\"9\" fill=\"black\" dominant-baseline=\"central\" >";
code_SVG.add(ligne);
ligne = sinon + "</text>";
code_SVG.add(ligne);

// ordonnée du haut avant retour sur losange
ydernier = y + 7;
// on suppose que processus 0 existe dans ce cas
ligne = "<polyline points=\""+abscisse+","+ypremier+" "+(x-dimensions[0])+","+ypremier+" "+(x-dimensions[0])+","+ydernier+" "+abscisse+","+ydernier+"\" style=\"fill:none; stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />";
code_SVG.add(ligne);
} // fin cas pas de processus second
ligne = "<desc id=\"Boucle"+compteurDescrip+"\">";
if (processus[0]==null) {
ligne += "Boucle de type tant que alors. ";
} else {
if (processus[1]==null) {
ligne += "Boucle de type faire processus tant que. ";
} else {
ligne += "Boucle complète avec processus avant et après test. ";
}}
ligne += "Continue si "+tantque+" et sinon sort avec "+sinon + ". ";
ligne += "</desc>"; // fin descroption
code_SVG.add(ligne);
// fermeture groupe correspondant à l'activité boucle décrite
code_SVG.add("</g>");
code_SVG.add("");
} // fin méthode génération SVG d'une boucle
} // fin classe boucle héritant de processus

/**
 * Un embranchement permet de placer en parallèle autant de processus que voulu
 */
class Embranchement extends Composite {

/**
 * Place les processus parallèles dans l'attribut ad hoc
 */
Embranchement(Processus[] processus) {
super(processus);
} // fin initialisation embranchement

/**
 * Dimensions d'un embranchement
 * La largeur est la somme, la hauteur le maximum
 */
void calculDimensions() {
super.calculDimensions();
int largeur = 0;
int hauteur = 0;
for (Processus p : processus) {
largeur += p.getLargeur();
hauteur = Math.max(hauteur,p.getHauteur());
} // fin boucle sur tous les processus parallèles
// espace de 20 entre deux processus parallèles
largeur += (processus.length-1)*20;
dimensions[0] = largeur/2;
dimensions[1] = largeur/2;
dimensions[2] = largeur/2;
// 20 après  embranchement synchro + 20 avant embranchement fusion
dimensions[3] = hauteur + 40;
} // fin méthode calcul dimensions

/**
 * Positionnement des processus compris dans l'embranchement.
 * @param Px abscisse du point d'entrée
 * @param Py ordonnée du point d'entrée
 */
void calculPosition(int Px, int Py) {
x = Px;
y = Py;
// on part du point le plus à gauche
int point_x = x - dimensions[0];
// transition de 20 après barre de synchronisation
int point_y = y + 20;
for (Processus p : processus) {
// décalage vers la droite
point_x = point_x + p.dimensions[0];
p.calculPosition(point_x,point_y);
// décalage vers la droite au prochain processus
point_x += p.dimensions[1] + 20;
} // fin boucle sur tous les processus parallèles
} // fin méthode calcul position embranchement

/**
 * Génération SVG d'un embranchement avec ses processus parallèles
 */
void generer() {
String ligne;
// incrémentation compteur identification des liens
compteurDescrip++;
ligne = "<g aria-labelledby=\"Embranchement"+compteurDescrip+"\">";
code_SVG.add(ligne);
code_SVG.add("<line x1=\""+(x-dimensions[0])+"\" y1=\""+y+"\" x2=\""+(x+dimensions[1])+"\" y2=\""+y+"\" style=\"stroke: black; stroke-width: 1.2; \" />");
int abscisse;
int y1;
int y2 = y+getHauteur();
for (Processus p : processus) {
abscisse = p.x;
code_SVG.add("<line x1=\""+abscisse+"\" y1=\""+y+"\" x2=\""+abscisse+"\" y2=\""+(y+20)+"\" style=\"stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />");
p.generer();
abscisse = abscisse - p.dimensions[0] + p.dimensions[2];
y1 = y + p.getHauteur() + 20;
code_SVG.add("<line x1=\""+abscisse+"\" y1=\""+y1+"\" x2=\""+abscisse+"\" y2=\""+y2+"\" style=\"stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />");
} // fin parcours des processus de l'embranchement
// tracé de la barre finale de synchro
code_SVG.add("<line x1=\""+(x-dimensions[0])+"\" y1=\""+y2+"\" x2=\""+(x+dimensions[1])+"\" y2=\""+y2+"\" style=\"stroke: black; stroke-width: 1.2; \" />");
// introduction de la description de l'embranchement
ligne = "<desc id=\"Embranchement"+compteurDescrip+"\">";
ligne += "" + (processus.length) + " processus en parallèle.";
ligne += "</desc>"; // fin description embranchement
code_SVG.add(ligne);
code_SVG.add("</g>");
code_SVG.add("");
} // fin méthode génération SVG d'un embranchement

} // fin de la classe  Embranchement, type de processus composite

/**
 * Un test est un processus constitué de un ou deux processus avec un débranchement
 * appelé test binaire.
 * S'il n'y a qu'un seul processus, l'autre branche rejoint directement la jonction finale du processus.
 * S'il y a deux processus, chacun occupe une des deux branches qui se rejoignent ensuite sur la jonction.
 */
class Test extends Composite {

/**
 * Texte placé dans la transition de gauche, le si
 */
String si;

/**
 * Texte placé dans la transition de droite, le sinon
 */
String sinon;

/**
 * Initialisation de l'attribut processus
 * mais surtout analyse des informations complémentaires contenant potentiellement le si sinon
 * @param processus les deux processus constituant les deux branches
 * @param complementaire;
 */
Test(Processus[] processus, String complementaire) {
super(processus);
sinon = "";
String[] parties = complementaire.split(" ",3);
si =parties[0];
if (parties.length>1) sinon =parties[1];
} // fin constructeur du test

/**
 * Dimensions d'un test
 * la hauteur est le maximum entre les deux possibles
 * et la largeur est la somme +20 entre deux
 * ou +10 si transition contournante
 */
void calculDimensions() {
super.calculDimensions();
// il y a deux processus dont l'un peut être null
int hauteur = 0;
int largeur = 0;
if (processus[0]==null) {
hauteur = processus[1].getHauteur();
dimensions[0] = processus[1].dimensions[0]+10;
dimensions[1] = processus[1].dimensions[1];
dimensions[2] = processus[1].dimensions[2]+10;
}
if (processus[1]==null) {
hauteur = processus[0].getHauteur();
dimensions[0] = processus[0].dimensions[0];
dimensions[1] = processus[0].dimensions[1]+10;
dimensions[2] = processus[0].dimensions[2];
}
if ((processus[0]!=null) && (processus[1]!=null)) {
hauteur = Math.max(processus[0].getHauteur(),processus[1].getHauteur());
largeur = processus[0].getLargeur() + processus[1].getLargeur() + 20;
dimensions[0] = largeur/2;
dimensions[1] = largeur/2;
dimensions[2] = largeur/2;
} // hauteur maximum entre les deux possibles
dimensions[3] = hauteur + 64; //32 = 7+25 encadrant les processus internes
} // fin méthode calcul dimensions d'un test

/**
 * Positionnement du ou des deux  processus inclus dans le test.
 * @param Px abscisse du point d'entrée
 * @param Py ordonnée du point d'entrée
 */
void calculPosition(int Px, int Py) {
x = Px;
y = Py;
// position abscisse la plus à gauche
int point_x = x - dimensions[0];
// décalage vers le bas après losange et transition
int point_y = y + 32;
// sur les deux processus, l'un peut être null
if (processus[0]==null) {
processus[1].calculPosition(point_x+10+processus[1].dimensions[0],point_y);
}
if (processus[1]==null) {
processus[0].calculPosition(point_x+processus[0].dimensions[0],point_y);
}
if ((processus[0]!=null) && (processus[1]!=null)) {
processus[0].calculPosition(point_x+processus[0].dimensions[0],point_y);
processus[1].calculPosition(point_x+processus[0].getLargeur()+20+processus[1].dimensions[0],point_y);
}
} // fin méthode calcul position dans le test

/**
 * Génération du code SVG d'un test
 */
void generer() {
String ligne = ""; // sert pour faciliter la lecture
// incrémentation compteur identification des liens
compteurDescrip++;
ligne = "<g aria-labelledby=\"Test"+compteurDescrip+"\">";
code_SVG.add(ligne);
// création du premier losange
ligne = "<polygon points=\""+x+","+y+" "+(x+10)+","+(y+7)+" "+x+","+(y+14)+" "+(x-10)+","+(y+7)+"\" style=\"fill:white; stroke:black; stroke-width:0.6;\" />";
code_SVG.add(ligne);
int abscisse;
int ypremier;
int ydernier;
if ((processus[0]!=null) && (processus[1]!=null)) {
// si les deux processus existent
abscisse = x-10;
ypremier = y+7;
ydernier = ypremier+ 25;
// si à gauche
ligne = "<text x=\""+abscisse+"\" y=\""+(ypremier-5)+"\" text-anchor=\"end\" font-size=\"9\" fill=\"black\" dominant-baseline=\"central\" >";
code_SVG.add(ligne);
ligne = si + "</text>";
code_SVG.add(ligne);
ligne = "<polyline points=\""+abscisse+","+ypremier+" "+processus[0].x+","+ypremier+" "+processus[0].x+","+ydernier+" \" style=\"fill:none; stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />";
code_SVG.add(ligne);
processus[0].generer();
// génération branche droite
abscisse += 20;
// sinon à droite
ligne = "<text x=\""+abscisse+"\" y=\""+(ypremier-5)+"\" text-anchor=\"start\" font-size=\"9\" fill=\"black\" dominant-baseline=\"central\" >";
code_SVG.add(ligne);
ligne = sinon + "</text>";
code_SVG.add(ligne);
ligne = "<polyline points=\""+abscisse+","+ypremier+" "+processus[1].x+","+ypremier+" "+processus[1].x+","+ydernier+" \" style=\"fill:none; stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />";
code_SVG.add(ligne);
processus[1].generer();
// génération branches du bas gauche puis droite
abscisse = processus[0].x-processus[0].dimensions[0]+processus[0].dimensions[2];
ypremier = ydernier+processus[0].getHauteur();
ligne = "<polyline points=\""+abscisse+","+ypremier+" "+abscisse+","+(y+getHauteur()-7)+" "+(x+dimensions[2]-dimensions[0]-10)+","+(y+getHauteur()-7)+" \" style=\"fill:none; stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />";
code_SVG.add(ligne);
abscisse = processus[1].x-processus[1].dimensions[0]+processus[1].dimensions[2];
ypremier = ydernier+processus[1].getHauteur();
ligne = "<polyline points=\""+abscisse+","+ypremier+" "+abscisse+","+(y+getHauteur()-7)+" "+(x+dimensions[2]-dimensions[0]+10)+","+(y+getHauteur()-7)+" \" style=\"fill:none; stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />";
code_SVG.add(ligne);
} else {
// sinon, un et un seul processus existe
if (processus[0]!=null) {
// processus de gauche existe
abscisse = x;
ypremier = y+14;
ydernier = ypremier+18;
// si vers le bas (à gauche)
ligne = "<text x=\""+(abscisse-5)+"\" y=\""+(ypremier+6)+"\" text-anchor=\"end\" font-size=\"9\" fill=\"black\" dominant-baseline=\"central\" >";
code_SVG.add(ligne);
ligne = si + "</text>";
code_SVG.add(ligne);
// une transition descendant du losange
ligne = "<line x1=\""+abscisse+"\" y1=\""+ypremier+"\" x2=\""+abscisse+"\" y2=\""+ydernier+"\" style=\"stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />";
code_SVG.add(ligne);
processus[0].generer();
abscisse = abscisse - processus[0].dimensions[0] + processus[0].dimensions[2];
ypremier = ydernier + processus[0].getHauteur();
ydernier = ypremier + 18;
// une transition partant du processus vers le second losange
ligne = "<line x1=\""+abscisse+"\" y1=\""+ypremier+"\" x2=\""+abscisse+"\" y2=\""+ydernier+"\" style=\"stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />";
code_SVG.add(ligne);
// contournement par la droite
abscisse = x + dimensions[1];
ypremier = y + 7;
ydernier = y + getHauteur() - 7;
// sinon à droite
ligne = "<text x=\""+(x+10)+"\" y=\""+(ypremier-5)+"\" text-anchor=\"start\" font-size=\"9\" fill=\"black\" dominant-baseline=\"central\" >";
code_SVG.add(ligne);
ligne = sinon + "</text>";
code_SVG.add(ligne);

ligne = "<polyline points=\""+(x+10)+","+ypremier+" "+abscisse+","+ypremier+" "+abscisse+","+ydernier+" "+(x + 10 - dimensions[0] + dimensions[2])+","+ydernier+" \" style=\"fill:none; stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />";
code_SVG.add(ligne);
} else {
// seul processus de droite existe
abscisse = x;
ypremier = y+14;
ydernier = ypremier+18;
// sinon vers le bas (à droite)
ligne = "<text x=\""+(abscisse+5)+"\" y=\""+(ypremier+6)+"\" text-anchor=\"start\" font-size=\"9\" fill=\"black\" dominant-baseline=\"central\" >";
code_SVG.add(ligne);
ligne = sinon + "</text>";
code_SVG.add(ligne);

// une transition descendant du losange
ligne = "<line x1=\""+abscisse+"\" y1=\""+ypremier+"\" x2=\""+abscisse+"\" y2=\""+ydernier+"\" style=\"stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />";
code_SVG.add(ligne);
processus[1].generer();
abscisse = abscisse - processus[1].dimensions[0] + processus[1].dimensions[2];
ypremier = ydernier + processus[1].getHauteur();
ydernier = ypremier + 18;
// une transition partant du processus vers le second losange
ligne = "<line x1=\""+abscisse+"\" y1=\""+ypremier+"\" x2=\""+abscisse+"\" y2=\""+ydernier+"\" style=\"stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />";
code_SVG.add(ligne);
// contournement par la gauche
abscisse = x - dimensions[0];
ypremier = y + 7;
ydernier = y + getHauteur() - 7;
// si à gauche
ligne = "<text x=\""+(x-10)+"\" y=\""+(ypremier-5)+"\" text-anchor=\"end\" font-size=\"9\" fill=\"black\" dominant-baseline=\"central\" >";
code_SVG.add(ligne);
ligne = si + "</text>";
code_SVG.add(ligne);
ligne = "<polyline points=\""+(x-10)+","+ypremier+" "+abscisse+","+ypremier+" "+abscisse+","+ydernier+" "+(x - 10 - dimensions[0] + dimensions[2])+","+ydernier+" \" style=\"fill:none; stroke: black; stroke-width: 0.6;marker-end:url(#transition); \" />";
code_SVG.add(ligne);
} // fin traitement processus seul
} // fin de tous les cas avec 1 ou 2 processus
// affichage du second losange - fin du processus de test
abscisse = x - dimensions[0] + dimensions[2];
ydernier = y - 14 + getHauteur();
ligne = "<polygon points=\""+abscisse+","+ydernier+" "+(abscisse+10)+","+(ydernier+7)+" "+abscisse+","+(ydernier+14)+" "+(abscisse-10)+","+(ydernier+7)+"\" style=\"fill:white; stroke:black; stroke-width:0.6;\" />";
code_SVG.add(ligne);
// description du test avec desc
ligne = "<desc id=\"Test"+compteurDescrip+"\">";
if (processus[0]==null) {
ligne += "Si "+si+" alors pas d'action et sinon "+sinon+" un processus à gauche exécuté";
} else {
if (processus[1]==null) {
ligne += "Si "+si+" alors exécution du processus à gauche sinon "+sinon+" on ne fait rien. ";
} else {
ligne += "Test avec si "+si+" ou alors "+sinon+" choix. ";
}}
ligne += "</desc>";
code_SVG.add(ligne);
code_SVG.add("</g>");
code_SVG.add("");
} // fin de la génération du code SVG d'un test binaire

} // fin classe Test hérite de Processus

/****** fin de la liste des classes hébergées ******/
} // fin classe hôte DiagrammeActivite