package diagramme;

import java.util.LinkedList;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
import fichiers.Fichier;
import fichiers.SVGFabrique;
import fichiers.Fusion;

/**
 * la classe dispose d'un main pour lire un fichier de description
 * d'un diagramme de classe UML.
 * Toutes les classes utiles à la conception de diagrammes
 * sont encapsulées.
 * 19 février 2018
 * 31 janvier 2018
 * Les classes sont déclarées par la syntaxe
 * <A> MaClasse
 * Et les associations par
 * A 0..1 ----- * B
 * 5 février 2018
 * Ajout du caractère clé c ou u pour les associations contournantes.
 * @author Frédéric Serin, Université Le Havre Normandie, LITIS
 * @version 2.0, 2018-02-19
 * @since 1.2, 2018-02-12
 * @since 1.1, 2018-02-05
 * @since 1.0a, 2018-01-19
 */
public class DiagrammeClasse extends DiagrammeStructure {

/**
 * Dictionnaire des classes créées, permet de les identifier.
 * et utilisée dans la méthode générer
 * 31-01
 */
TreeMap<String,Classe> classes;

/**
 * Ordonnée des coordonnées par défaut
 * données aux classes dans leur ordre d'apparition.
 */
int refY;

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
// création du diagramme de classe UML
new DiagrammeClasse(partiesNomFichier[0],partiesNomFichier[1],lignes);
} // fin try, le diagramme est créé
catch (Exception e) {
System.out.println(e);
} // fin du catch
} // fin méthode main

/**
 * Pour générer un diagramme de classe.
 * @param chemin_fichier chemin pour placement des fichiers svg et -analyse.txt
 * @param identifiant_fichier nom du fichier sans suffixe
 * @param lignes code à analyser servant à la génération
 */
public DiagrammeClasse(String chemin_fichier, String identifiant_fichier, LinkedList<String> lignes) 
throws Exception {
this(chemin_fichier + identifiant_fichier,lignes);
} // fin constructeur avec nom décomposé et sans suffixe

/**
 * Pour générer un diagramme de classe.
 * @param nomFichierSortie nom complet avec chemin du fichier de sortie (non suffixé)
 * @param lignes code à analyser servant à la génération
 */
public DiagrammeClasse(String nomFichierSortie, LinkedList<String> lignes) 
throws Exception {
// initialisation des attributs de Graphique
super( nomFichierSortie, lignes);
int indice = 0;
// récupération des coordonnées forcées
String chaineCaractères;
int x, y;
// identification association contournante, trois valeurs null, false et true
Boolean c_association;
// dictionnaire des classes créées
classes = new TreeMap<String,Classe>();
// variables tampon pour les classes
Classe classeA, classeB;
// et les associations
Association association;
// le nom initial peut être de la forme nom ou nom(x,y) avec des coordonnées forcées
String nom; // nom de la classe
String commentaireClasse; // description supplémentaire d'une classe
// pour décomposition des lignes
String[] parties;
String[] extremes;
// réuni toutes les classes liées par un héritage
LinkedList<String> heritage = new LinkedList<String>();
// précise s'il s'agit d'un héritage avec surclasse ou interface
boolean estInterface = false;
for (String ligne : lignes) {
// vérification liste à puce pour héritage
chaineCaractères = ligne;
chaineCaractères = chaineCaractères.trim();
if (chaineCaractères.startsWith("*")) {
// enrichissement description héritage
heritage.add(ligne);
} // fin ajout subclasse
else {
if (heritage.size()!=0) {
// arbre d'héritage à générer
// et ajouté dans le code SVG créé
if (estInterface)
code_SVG.addAll(creerHeritageInterface(heritage));
else
code_SVG.addAll(creerHeritage(heritage));
// reprise des variables pour un usage ultérieur
estInterface = false;
heritage.clear();
} // fin si héritage non vide et donc à générer
// élimination du cas d'une ligne vide
if (chaineCaractères.length() != 0) {
// nettoyage blancs extrêmes
ligne = chaineCaractères;
// de 3 choses l'une : interface, association ou classe
if (ligne.startsWith("interface")) {
heritage.add(ligne);
estInterface = true;
// normalement, la ligne suivante doit commencer par un astérisque
} else {
// les associations contiennent des tirets dans leur représentation textuelle
if (ligne.contains("--")) {
// il y a 2 parties séparées par des tirets
parties = ligne.split("-{2,}");
// pour chaque partie on retire les blancs extrêmes
for (int i=0; i<parties.length; i++) parties[i] = parties[i].trim(); // fin nettoyage blancs extrêmes
// une association peut commencer par c ou u, puis possède une classe puis une multiplicité
extremes = parties[0].split("[ ]{1,}"); // blancs séparateurs
// commence par c ou u, C_Association
if ((extremes[0].equals("c")) || (extremes[0].equals("u"))) {
c_association = extremes[0].equals("c");
indice = 1;
} else {
c_association = null;
indice = 0;
}
// extraction nom de la classe
nom = extremes[indice];
if (!classes.containsKey(nom)) {
 System.out.println("Pas d'identifiant classe pour l'association "+ligne);
throw new Exception("Il faut définir un identifiant de classe pour l'association "+ligne);
}
// le nom identifie bien une classe
// retrait de l'identifiant dans la description de l'association
parties[0] = "";
// reconstitution de la partie gauche sans identifiant de classe
for (int i=(indice+1); i<extremes.length; i++) parties[0] += extremes[i];
// retrouver la classe identifiée
classeA = classes.get(nom);
// traitement partie droite de l'association
extremes = parties[parties.length-1].split("[ ]{1,}"); // blancs comme séparateurs
nom = extremes[extremes.length-1]; // nom de la classe
parties[parties.length-1] = "";
for (int i=0; i<(extremes.length-1); i++) parties[parties.length-1] += extremes[i];
if (!classes.containsKey(nom)) {
 System.out.println("Pas d'identifiant seconde classe pour l'association "+ligne);
throw new Exception("Il faut définir un identifiant de classe pour l'association "+ligne);
}
classeB = classes.get(nom);
ligne = "";
for (String p : parties) {
// on place quatre tirets entre les deux classes associées
ligne += p + "----";
}
// on retire un ---- surnuméraire mis dans la boucle précédente
ligne = ligne.substring(0,ligne.length()-4);
if (c_association==null) {
association = new Association(classeA, classeB, ligne);
System.out.println("Fred2 Création association entre "+classeA+" et "+classeB+" avec entre deux "+ligne);
} else {
association = new CAssociation(classeA, classeB, ligne,c_association);
}
} // fin traitement d'une association
else {
// Analyse syntaxique déclaration d'une classe
nom = "";
commentaireClasse = "";
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
 if (classes.containsKey(parties[0])) {
  // la clé identifie déjà une classe !
  System.out.println("Problème1 , la classe "+parties[0]+" est déjà définie");
 } else {
  // la clé est nouvelle, il faut donc créer une classe
  refX = x;
  refY = y;
  // affectation nom et description de la classe
  nom = parties[0]; // si pas de seconde partie, le nom est celui de la clé
  commentaireClasse = ""; // en cas d'absence de troisième partie
  if (parties.length>=2) {
   nom = parties[1];
   if (parties.length==3) commentaireClasse = parties[2];
  }
 } // fin partie valide clé et classe identifiée et possible
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
 if (classes.containsKey(parties[0])) {
  // la partie 0  est déjà définie comme classe
  System.out.println("Problème2, la classe "+parties[0]+" est déjà définie");
 } else {
  refX = x;
  refY = y;
  // affectation nom et description de la classe
  nom = parties[0]; // si pas de seconde partie, le nom est celui de la clé
  commentaireClasse = ""; // en cas d'absence de troisième partie
  if (parties.length==2) commentaireClasse = parties[1];
 } // fin une classe est possible
}
// on doit faire correspondre une classe à la clé
classeA = new Classe(nom, refX, refY, commentaireClasse);
// incrément en dur, 60 de large + 80 de longueur association potentielle
refX += 140;
classes.put(parties[0],classeA);
// tracé de la classe
code_SVG.addAll(classeA.generer(parties[0]));
} // fin traitement classe (else) et association préalablement (then)
} // fin else si autre que interface
} // fin cas ligne non vide (sinon rien)
} // fin cas hors lecture subclasse
} // fin parcours des lignes
// Si un arbre d'héritage est le dernier généré, il faut le traiter car aucune ligne suivante pour déclencher l'action
if (heritage.size()!=0) {
// arbre d'héritage à générer
// et ajouté dans le code SVG créé
if (estInterface)
code_SVG.addAll(creerHeritageInterface(heritage));
else
code_SVG.addAll(creerHeritage(heritage));
} // fin si héritage non vide et donc à générer

// code SVG généré - partie à placer dans Graphique
code_SVG.addAll(generer());
SVGFabrique.setLignes(nomFichierSortie+".svg", code_SVG,largeurGraphique,hauteurGraphique);
Fichier.setLignes(nomFichierSortie+"-description.txt",description);

} // fin du constructeur, toutes les classes et les associations sont identifiées

/**
 * Méthode dédiée à la création d'un héritage
 * constitué de surclasses (type de classes)
 * @param arbre liste à puces décrivant l'héritage simple
 * @return les liens d'héritages, les classes étant créées par ailleurs
 */
LinkedList<String> creerHeritage(LinkedList<String> arbre) 
throws Exception {
// une translation sera appliquée en fonction des valeurs refX et refY
// ainsi que la possibilité de fixer des coordonnées à la racine
// on peut accepter une translation vers la droite, pas vers la gauche
int translaHorizontal = 0;
int minix = refX;
int translaVertical = refY;
// variables tampon si précision de coordonnées
String coordonnees;
int x, y;
// variable pour définir la puce utilisée dans l'arbre décrit sous forme de liste
String puce = "*";
// gestion des niveaux de l'héritage
int niveauHeritage = 0;
int niveau = -1;
int niveauMax = -1;
// nom et description de la classe
String nom = "";
String commentaireClasse = "";
// décomposition des lignes
String[] parties;
// parcours de toutes les lignes pour établir le niveau maximum
for (String ligne : arbre) {
// position de la puce
niveau = ligne.indexOf(puce);
// vérifie la cohérence de profondeur
if (niveau>(niveauHeritage+1)) {
throw new Exception("Soucis dans les niveaux d'héritage, la classe "+ligne+" est trop en dessous");
} else niveauHeritage = niveau;
niveauMax = Math.max(niveauMax,niveau);
} // fin boucle calculant le niveau max de l'arbre
// à partir de là, l'arbre est considéré comme cohérent, reste à vérifier que chaque item est bien une classe
// tableau qui permet de retrouver facilement chaque surclasse supérieure pour les liens 
Surclasse[] classe = new Surclasse[niveauMax+1];
// parcours de la liste à puce
for (String ligne : arbre) {
niveau = ligne.indexOf(puce);
// ligne nettoyée de la puce
ligne = ligne.substring(niveau+1,ligne.length());
ligne = ligne.trim();
// analyse syntaxique des lignes déclarant une surclasse
// blanc séparateur : 3 parties maximum considérées
parties = ligne.split(" ",3);
if ((parties[0].startsWith("<")) && (parties[0].endsWith(">"))) {
 // retrait des chevrons
 parties[0] = parties[0].substring(1,parties[0].length()-1);
parties[0] = parties[0].trim();
if (parties[0].length()==0) {
 System.out.println("Pas de surclasse définie ! "+ligne);
throw new Exception("Il faut définir une classe");
}
// prise en compte des coordonnées par défaut si pas de précision après la classe racine
if (niveau==0) {
minix = refX;
translaVertical = refY;
} // fin précision position souhaitée
// la partie 0 peut contenir des coordonnées forcées
// on recherche d'éventuelles parenthèses
if(parties[0].indexOf("(")>0) {
// ici, on peut retrouver les coordonnées forcée de la surclasse
coordonnees = parties[0].substring(parties[0].indexOf("(")+1, parties[0].indexOf(")", parties[0].indexOf("(")));
try {
x = new Integer(coordonnees.split(",")[0]);
y = new Integer(coordonnees.split(",")[1]);
// on retire ces coordonnées du nom de la classe
parties[0] = parties[0].substring(0,parties[0].indexOf("("));
// uniquement si coordonnées précisées à la racine
if (niveau==0) {
minix = x;
translaVertical = y;
}} // fin du try
catch (Exception e) {
System.out.println("Pas de vraies coordonnées !");
} // fin du catch
} // fin récupération coordonnées
// si pas de seconde partie, le nom est celui de la clé
if (parties.length==1) nom = parties[0];
else {
nom = parties[1];
if (parties.length>2) commentaireClasse = parties[2];
} // le nom est différent de la clé, une description existe peut-être
} // fin si clé en première partie
else {
// cas ou pas de clé donc partie 0 est le nom (avec coordonnées ?)
if (parties.length>1) commentaireClasse = parties[1];
// on recherche d'éventuelles parenthèses
if(parties[0].indexOf("(")>0) {
// ici, on peut retrouver les coordonnées forcée de la surclasse
coordonnees = parties[0].substring(parties[0].indexOf("(")+1, parties[0].indexOf(")", parties[0].indexOf("(")));
try {
x = new Integer(coordonnees.split(",")[0]);
y = new Integer(coordonnees.split(",")[1]);
// on retire ces coordonnées du nom de la classe
parties[0] = parties[0].substring(0,parties[0].indexOf("("));
// uniquement si coordonnées précisées à la racine
if (niveau==0) {
minix = x;
translaVertical = y;
}} // fin du try
catch (Exception e) {
System.out.println("Pas de vraies coordonnées !");
} // fin du catch
} // fin récupération coordonnées
nom = parties[0];
} // fin cas ou le nom est l'identifiant
if (classes.containsKey(parties[0])) {
// la clé identifie déjà une classe !
System.out.println("Problème3, la classe "+parties[0]+" est déjà définie");
} else {
// si on se situe à la racine
if (niveau == 0) {
classe[0] = createRacine(nom,commentaireClasse);
} else {
classe[niveau] = classe[niveau-1].add(nom, commentaireClasse);
} // fin création d'une surclasse
// on doit faire correspondre la surclasse à la clé
classes.put(parties[0],classe[niveau]);
}} // fin création de l'arbre
// lancement de l'algorithme de création de l'arbre d'héritage
classe[0].lancerPlacement();
// à la suite de ce placement, il faut translater les coordonnées si besoin
if (minix>classe[0].x) translaHorizontal = minix-classe[0].x;
else translaHorizontal = 0;
if ((translaVertical>0) || (translaHorizontal>0))
classe[0].translation(translaHorizontal,translaVertical);
// mise à jour des coordonnées pour la suite des générations de classes dans le diagramme
refX = classe[0].x + 140;
refY = classe[0].y;
// ajout au fichier de la description de l'arbre
description.add("");
description.add("=====");
description.add("Structure de l'arborescence");
description.add("");
description.add("====");
description.add("Ensemble des classes");
// fiches d'identité de chaque classe présente
description.addAll(classe[0].affichage());
// fiches d'identité de chaque niveau dans l'arborescence
description.add("");
description.add("====");
description.add("Description des niveaux");
description.addAll(classe[0].affichageNiveaux());
// toutes les lignes SVG, classes et liens d'héritage
return (classe[0].generer());
} // fin de la méthode creerHeritage

/**
 * Fabrique de la racine de l'arborescence.
 * @param nom de la classe qui sera la racine
 * @param description associée à la classe
 * @return classe générée
 */
Surclasse createRacine(String nom, String description) {
Surclasse racine = new Surclasse(nom, description);
racine.niveau = new Niveau(0);
racine.niveau.groupes.add(racine);
racine.niveau.groupes.add(new Intervalle());
return racine;
} // fin fabrique de la racine


/**
 * Méthode dédiée à la création d'un héritage
 * dont la racine est une interface.
 * L'héritage a une profondeur de 1
 * @param arbre liste à puces décrivant l'héritage simple
 * @return l'interface et  l'héritage
 */
LinkedList<String> creerHeritageInterface(LinkedList<String> arbre) 
throws Exception {
// variables tampon si précision de coordonnées
int x = -1;
int y = 0;
String coordonnees = "";
// variable pour définir la puce utilisée dans l'arbre décrit sous forme de liste
String puce = "*";
// nom et description de l'interface
String nom = "";
String commentaireInterface = "";
// décomposition des lignes
String[] parties;
// la racine de l'arbre
Interface racineInterface = new Interface("Erreur","null");
// le premier élément commence par le mot clé interface
String racine = arbre.pollFirst();
racine = racine.substring(9,racine.length());
racine = racine.trim();
// analyse syntaxique de la racine (de type Interface)
// blanc séparateur : 3 parties maximum considérées
parties = racine.split(" ",3);
// cas d'existence d'une clé
if ((parties[0].startsWith("<")) && (parties[0].endsWith(">"))) {
 // retrait des chevrons
 parties[0] = parties[0].substring(1,parties[0].length()-1);
parties[0] = parties[0].trim();
if (parties[0].length()==0) {
 System.out.println("Pas d'interface définie ! "+racine);
throw new Exception("Il faut définir une interface");
}
// la partie 0 peut contenir des coordonnées forcées
// on recherche d'éventuelles parenthèses
if(parties[0].indexOf("(")>0) {
// ici, on peut retrouver les coordonnées forcée de l'interface
coordonnees = parties[0].substring(parties[0].indexOf("(")+1, parties[0].indexOf(")", parties[0].indexOf("(")));
try {
x = new Integer(coordonnees.split(",")[0]);
y = new Integer(coordonnees.split(",")[1]);
// on retire ces coordonnées du nom de la classe
parties[0] = parties[0].substring(0,parties[0].indexOf("("));
} // fin du try
catch (Exception e) {
System.out.println("Pas de vraies coordonnées !");
} // fin du catch
} // fin récupération coordonnées
// si pas de seconde partie, le nom est celui de la clé
if (parties.length==1) nom = parties[0];
else {
nom = parties[1];
if (parties.length>2) commentaireInterface = parties[2];
} // le nom est différent de la clé, une description existe peut-être
} // fin si clé en première partie
else {
// cas ou pas de clé donc partie 0 est le nom (avec coordonnées ?)
if (parties.length>1) commentaireInterface = parties[1];
// on recherche d'éventuelles parenthèses
if(parties[0].indexOf("(")>0) {
// ici, on peut retrouver les coordonnées forcée de l'interface
coordonnees = parties[0].substring(parties[0].indexOf("(")+1, parties[0].indexOf(")", parties[0].indexOf("(")));
try {
x = new Integer(coordonnees.split(",")[0]);
y = new Integer(coordonnees.split(",")[1]);
// on retire ces coordonnées du nom de l'interface
parties[0] = parties[0].substring(0,parties[0].indexOf("("));
} // fin du try
catch (Exception e) {
System.out.println("Pas de vraies coordonnées !");
} // fin du catch
} // fin récupération coordonnées
nom = parties[0];
} // fin cas ou le nom est l'identifiant
if (classes.containsKey(parties[0])) {
// la clé identifie déjà une classe !
System.out.println("Problème4, l'interface "+parties[0]+" est déjà définie");
} else {
if (x>=0)
racineInterface = new Interface(nom,x,y,commentaireInterface);
else
racineInterface = new Interface(nom,commentaireInterface);
classes.put(parties[0],racineInterface);
} // fin définition de l'interface
// parcours de la liste à puce
for (String ligne : arbre) {
// ligne nettoyée de la puce
ligne = ligne.substring(ligne.indexOf(puce)+1,ligne.length());
ligne = ligne.trim();
// chaque ligne correspond à la clé d'une classe
if (classes.containsKey(ligne))
racineInterface.add(classes.get(ligne));
else
System.out.println("Soucis héritage interface : "+ligne+" devrait être une classe déclarée avant");
} // fin boucle sur les sous-classes implémentant l'interface racine

// toutes les lignes SVG, classes et liens d'héritage
return (racineInterface.generer());
} // fin de la méthode creerHeritageInterface

/**
 * Génère le code SVG
 * 31-01
 * @return ensemble du code SVG des classes et de leurs associations
 */
LinkedList<String> generer() {
// lignes contenant le code SVG
LinkedList<String> lignes = new LinkedList<String>();
// ensemble des associations déjà placées (pour éviter la redondance)
// entendu que deux classes peuvent se partager la même
Set<Association> associations = new HashSet<Association>();
// ligne tampon, fort utile
String ligne = "";
// coordonnées après translation
int abscisse;
int ordonnee;
// chaque classe connait déjà ses coordonnées donc il suffit de les parcourir
Classe classe;

// récolte de toutes les associations
for (String cle : classes.keySet()) {
classe = classes.get(cle);
// ajout des associations de la classe c pour traitement ci-après boucle
associations.addAll(classe.associations);
} // fin parcours des classes pour récupérer les associations
// déterminer le nombre d'associations pour chaque côté de chaque classe
for (Association assoc : associations) {
assoc.etablirCote();
} // fin établissement des points de contacts

// on va chercher les dimensions maximales dans la boucle sur les classes
largeurGraphique = 0;
hauteurGraphique = 0;
for (String cle : classes.keySet()) {
classe = classes.get(cle);
largeurGraphique = Math.max(largeurGraphique, classe.getXMax());
hauteurGraphique = Math.max(hauteurGraphique, classe.getYMax());
} // fin parcours de toutes les classes
// augmentation pour placement avec marge
/*
largeurGraphique += 50;
hauteurGraphique += 50;
*/
// placement des associations
for (Association assoc : associations) {
lignes.addAll(assoc.generer());
lignes.add("");
} // fin boucle sur les associations
return lignes;
} // fin méthode générer le SVG

// Classes hébergées Classe, Surclasse, Intervalle, groupe et Niveau

/**
 * Un groupe est soit un ensemble de classes simples,
 * soit une surclasse possédant nécessairement des subclasses.
 * Cette interface est nécessaire pour dissocier dans les niveaux
 * les classes sans sous-classe et les classes avec subclasses.
 */
interface Groupe {

/**
 * Ajoute au groupe, soit dans l'intervalle
 * soit comme sous-classe d'une surclasse
 * @param classe classe ajoutée
 */
void add(Surclasse classe);

/**
 * permet de retourner une classe de type Surclasse
 * @return la première classe du groupe
 */
Surclasse firstClasse();

/**
 * Méthode permettant de calculer et recalculer 
 * les coordonnées de chaque élément
 */
void calculCoordonnees();

/**
 * Retourne le nombre d'éléments dans l'intervalle
 * soit nombre de classes simples, soit nombre de subclasses
 * @return nombre de classes dans le groupe
 */
int nombreClasses();

/**
 * Retourne les classes contenues
 * @return toutes les classes
 */
LinkedList<Surclasse> getClasses();


} // fin interface Groupe

/**
 * Un intervalle, de type Groupe,
 * regroupe uniquement des classes simples sans subclasse.
 */
class Intervalle implements  Groupe {

/**
 * Ensemble de classes simples
 * comprises dans l'intervalle
 */
LinkedList<Surclasse> classes;

/**
 * Initialisation des attributs.
 */
Intervalle() {
classes = new LinkedList<Surclasse>();
} // fin constructeur

/**
 * Méthode publique car implémentée de l'interface
 * @param classe présupposée classe simple
 */

public void add(Surclasse classe) {
classes.add(classe);
} // fin ajout d'une classe simple

/**
 * Surtout utilisée dans Intervalle 
 * @return première classe de cet intervalle
 */
public Surclasse firstClasse() {
if (classes.size()==0) return null;
return (classes.get(0));
} // fin méthode firstClasse

/**
 * Cette méthode ne sert à rien,
 * créée uniquement par héritage de l'interface
 * et parcours des groupes
 */
public void calculCoordonnees() {
// code vide - ne sert à rien sauf conformité héritage
} // fin méthode calculCoordonnees

/**
 * Nombre de classes simples comprises dans cet intervalle
 * @return nombre de classes simples de l'intervalle, supérieur ou égal à 0
 */
public int nombreClasses() {
return classes.size();
} // fin méthode nombreClasses

/**
 * Classes comprises dans l'intervalle
 * @return ensemble des classes simples de l'intervalle, peut être vide
 */
public LinkedList<Surclasse> getClasses() {
return classes;
} // fin méthode getClasses
} // fin classe Intervalle

/**
 * Une classe est un élément du diagramme de classe.
* Elle est reliée soit à elle-même
 * soit à une autre classe par
 * une association stéréotypée.
 * L'héritage est aussi une forme de relation.
 * Parmi les stéréotypes : agrégation  et composition
 * Aussi classe qualifiée et class-association.
 */
class Classe {

/**
 * Commun avec surclasse
 * Nom donné à la classe
 */
String nom;

/**
 * Description de la classe qui pourra être affichée dans desc du SVG
 * et contenir un stéréotype
 */
String description;

/**
 * Abscisse pour le document SVG
 * Commun avec surclasse
 */
int x;

/**
 * Ordonnée pour le document SVG
 * Commun avec Niveau dans l'héritage
 */
int y;

/**
 * Ensemble des associations liées à cette instance de classe
 */
LinkedList<Association> associations;

/**
 * Compteur des points de contact, associations liées,
 * pour chaque côté du rectangle représentant une classe.
 * Nombre compris entre 0 et 3 normalement, plus est un soucis.
 */
int[] pointsContacts;

/**
 * Drapeau signalant la présence d'un point central, association horizontale ou verticale.
 */
boolean[] pointsCentraux;

/**
 * Initialisation des attributs de chaque objet.
 * @param nom de la classe
 * @param description éléments commentaires ajoutés à la classe
 */
Classe(String nom, String description) {
this.nom = nom;
this.description = description;
x=0;
y = 0;
associations = new LinkedList<Association>();
pointsContacts = new int[4];
pointsCentraux = new boolean[4];
} // fin constructeur par défaut

/**
 * Initialisation des attributs de chaque objet.
 * @param nom de la classe
 * @param x abscisse
 * @param y ordonnée
 * @param description ajout pour stéréotype et complément d'information
 */
Classe(String nom, int x, int y, String description) {
this(nom,description);
this.x = x;
this.y = y;
System.out.println("Création de la classe "+nom+" ("+x+","+y+")");
} // fin classe avec coordonnées

/**
 * Ajout d'une asociation liée à la classe
 * @param association dont la classe est un des élément
 */
void addAssociation(Association association) {
// faut-il vérifier (précondition) que la classe est présente dans l'association ?
associations.add(association);
} // fin ajout d'une association


/**
 * Identification comme classe
 * @return le nom de la classe
 */
public String toString() {
return ("Classe "+nom+ "("+x+","+y+")");
} // fin méthode toString

/**
 * Retourne le décalage potentiel en fonction du côté
 * @param cote numéro du côté (de 0 à 3)
 * @return décalage potentiel (0 ou 20, 0 ou 10)
 */
int getDecalage(int cote) {
// normalement, pointsContacts ne doit pas être nul
if (pointsContacts[cote]<=1) return 0;
if (pointsContacts[cote] == 3) return 20;
if (pointsContacts[cote]==2) {
if (pointsCentraux[cote]) return 20;
else return 10;
}
return 0;
} // fin getDecalage

/**
 * Génération du code SVG pour une classe
 * @param cle identifiant de la classe dans le document
 * @return les lignes SVG d'une classe
 */
LinkedList<String> generer(String cle) {
LinkedList<String> lignes = new LinkedList<String>();
lignes.add("<!-- Classe "+nom+" ("+x+","+y+") -->");
lignes.add("<g aria-labelledby=\""+cle+"\">");
lignes.add("<use xlink:href=\"#classe\" x=\""+x+"\" y=\""+y+"\" />");
lignes.add("<text x=\""+(x+30)+"\" y=\""+(y+12)+"\" text-anchor=\"middle\" font-size=\""+getDimNom()+"\" fill=\"black\" dominant-baseline=\"central\">");
lignes.add(nom+"</text>");
lignes.add("<desc id=\""+cle+"\">Classe "+nom+"</desc>");
lignes.add("</g>");
lignes.add("");
return lignes;
} // fin méthode générer pour une classe


/**
 * Retourne la partie verticale (abscisse) maximum
 * pour la zone de dessin
 * @return maximum d'espace en abscisse
 */
int getXMax() {
int max = x + 60;
if (pointsContacts[2]>0) max += 20;
return max;
} // fin get X Max

/**
 * Retourne la partie verticale (ordonnée) maximum
 * pour la zone de dessin
 * @return maximum d'espace en ordonnée
 */
int getYMax() {
int max = y + 50;
if (pointsContacts[1]>0) max += 20;
return max;
} // fin getYMax

/**
 * Méthode permettant de dimensionner la taille
 * de la police pour le nom de la classe
 * inscrit dans la partie supérieure de la représentation d'une classe en UML.
 * @return taille en points (10 par défaut)
 */
int getDimNom() {
 int taille = nom.length();
 if (taille<=12) taille = 10; // taille maximale
 else taille = 120/taille; // division entière
if (taille<8) System.out.println("Taille de "+nom+" = "+taille+" trop petite pour la visibilité");
 return taille;
} // fin méthode getDimNom retournant la taille de la police du nom
} // fin classe Classe pour un diagramme de classe UML 

/**
 * Une interface est, dans le modèle, une Classe.
* Elle dispose de classes qui l'implémente.
 * Ses coordonnées sont soit forcées, soit centrées sur ses sous-classes.
 * L'héritage est en traits discontinus.
 */
class Interface extends Classe {

/**
 * Classes qui implémentent l'interface
 */
LinkedList<Classe> sousclasses;

/**
 * Initialisation des attributs de chaque objet.
 * @param nom de l'interface
 * @param description éléments commentaires ajoutés à l'interface
 */
Interface(String nom, String description) {
super(nom,description);
// pour vérifier si les coordonnées sont fixées ou doivent être calculées
x = -1;
if (!this.description.contains("«interface»")) this.description = "«interface« "+this.description;
sousclasses = new LinkedList<Classe>();
} // fin constructeur par défaut

/**
 * Initialisation des attributs de chaque objet.
 * @param nom de l'interface
 * @param x abscisse
 * @param y ordonnée
 * @param description ajout pour stéréotype et complément d'information
 */
Interface(String nom, int x, int y, String description) {
this(nom,description);
this.x = x;
this.y = y;
} // fin interface avec coordonnées

/**
 * Ajout d'une classe qui implémente l'interface
 * @param sousclasse implémente l'interface directement
 */
void add(Classe sousclasse) {
sousclasses.add(sousclasse);
} // fin méthode ajout d'une sous-classe

/**
 * Génère le code SVG de l'arborescence.
 * @return code SVG surclasses et héritages
 */
LinkedList<String> generer() {
// lignes contenant le code SVG
LinkedList<String> lignes = new LinkedList<String>();
String ligne = "";
// variables utiles pour le tracer des héritages
int abscisse = 0;
int ordonnee = 10000; // choisi très très grand pour recherche minimum
// détermination des ordonnées
if (x==-1) {
if (sousclasses.size()==0) {
System.out.println("Interface "+nom+" sans sous-classe, ne devrait pas exister");
x = 0;
y = 0;
} else {
for (Classe c : sousclasses) {
abscisse += c.x;
ordonnee = Math.min(ordonnee,c.y);
} // fin parcours des sous-classes
x = abscisse/sousclasses.size(); // interface centrée
y = ordonnee - 100; // placement interface au dessus des sous-classes
} // fin coordonnées centrées
} // fin détermination x et y
System.out.println("Création de "+this);
// dessin de l'interface
lignes.add("<!-- Interface "+nom+" ("+x+","+y+") -->");
lignes.add("<g aria-labelledby=\""+nom+"\">");
lignes.add("<use xlink:href=\"#classe\" x=\""+x+"\" y=\""+y+"\" />");
lignes.add("<text x=\""+(x+30)+"\" y=\""+(y+10)+"\" text-anchor=\"middle\" font-size=\""+getDimNom()+"\" fill=\"black\" dominant-baseline=\"central\">");
lignes.add(nom+"</text>");
lignes.add("<text x=\""+(x+30)+"\" y=\""+(y+20)+"\" text-anchor=\"middle\" font-size=\""+getDimNom()+"\" fill=\"black\" dominant-baseline=\"central\">");
lignes.add("«interface»</text>");
lignes.add("<desc id=\""+nom+"\">Interface "+nom+"</desc>");
lignes.add("</g>");
lignes.add("");
// si il existe des sous-classes à l'interface
if (sousclasses.size()>0) {
// normalement, il existe des sous-classes
Classe sousclasse;
// il faut tracer l'héritage
abscisse = x + 30; // milieu de l'interface
ordonnee = y + 50; // bas de l'interface
// recherche de la largeur de l'héritage
int minigauche = abscisse;
int maxidroite = abscisse;
for (Classe c : sousclasses) {
minigauche = Math.min(minigauche,c.x+35);
maxidroite = Math.max(maxidroite,c.x+25);
}
lignes.add("<g aria-labelledby=\"heritage-"+nom+"\">");
// si mini=maxi alors il n'y a qu'une seule sous-classe (ou elles sont alignées verticalement !
if (minigauche==maxidroite) {
sousclasse = sousclasses.get(0);
lignes.add("<line x1=\""+(sousclasse.x+30)+"\" y1=\""+sousclasse.y+"\" x2=\""+abscisse+"\" y2=\""+ordonnee+"\" style=\"stroke: black; stroke-width: 0.6; stroke-dasharray:7,4;marker-end:url(#heritage); \" />");
ligne = ""+sousclasse.nom+" hérite de "+nom;
// fin tracé une seule sous-classe alignée
} else {
// au moins deux sousclasses
lignes.add("<line x1=\""+abscisse+"\" y1=\""+(ordonnee+30)+"\" x2=\""+abscisse+"\" y2=\""+ordonnee+"\" style=\"stroke: black; stroke-width: 0.6; stroke-dasharray:7,4;marker-end:url(#heritage); \" />");
lignes.add("<line x1=\""+minigauche+"\" y1=\""+(ordonnee+30)+"\" x2=\""+maxidroite+"\" y2=\""+(ordonnee+30)+"\" style=\"stroke: black; stroke-width: 0.6; stroke-dasharray:7,4; \" />");
for (Classe c : sousclasses) {
if (c.x<abscisse) {
lignes.add("<line x1=\""+(c.x+35)+"\" y1=\""+(ordonnee+30)+"\" x2=\""+(c.x+35)+"\" y2=\""+c.y+"\" style=\"stroke: black; stroke-width: 0.6; stroke-dasharray:7,4; \" />");
} else {
lignes.add("<line x1=\""+(c.x+25)+"\" y1=\""+(ordonnee+30)+"\" x2=\""+(c.x+25)+"\" y2=\""+c.y+"\" style=\"stroke: black; stroke-width: 0.6; stroke-dasharray:7,4; \" />");
}
ligne = ligne+c.nom+", ";
} // fin boucle sur toutes les sous-classes
} // fin de l'existence de sous-classes
ligne = ligne+" héritent de "+nom;
} // fin cas ou il y a des sous-classes une ou plusieurs
// ligne nécessaire à cause d'un bogue dans ARIA
lignes.add("<text x=\"10\" y=\"10\" font-size=\"1\" fill=\"none\">.</text>");
lignes.add("<desc id=\"heritage-"+nom+"\">"+ligne+"</desc>");
lignes.add("</g>");
lignes.add("");
return lignes;
} // fin méthode générer le SVG

/**
 * Identification comme interface
 * @return le nom de l'interface
 */
public String toString() {
return ("Interface "+nom+" ("+x+","+y+")");
} // fin méthode toString
} // fin classe Interface

/**
 * Une surclasse est un nœud dans l'arborescence
 * Elle connait sa surclasse et ses subclasses.
 * Elle possède des coordonnées.
 * Elle implémente Groupe pour l'usage dans Niveau.
 */
class Surclasse extends Classe implements Groupe {

/**
 * Classe mère
 */
Surclasse surclasse;

/**
 * Ensemble des subclasses de la classe
 */
LinkedList<Surclasse> subclasses;

/**
 * Niveau d'appartenance dans l'arborescence
 */
Niveau niveau;

/**
 * Initialisation des attributs de chaque objet.
 * @param nom de la classe
 */
Surclasse(String nom, String description) {
super(nom, description);
surclasse = null;
subclasses = new LinkedList<Surclasse>();
} // fin constructeur par défaut

/**
 * le nom d'une classe n'est pas nécessairement un identifiant,
 * mais cela est fortement suggéré.
 * La surclasse est précisée, pour la racine il faut utiliser le constructeur par défaut.
 * @param surclasse classe mère
 * @param nom de la classe
 * @param description commentaire supplémentaire
 */
public Surclasse(Surclasse surclasse, String nom, String description) {
this(nom,description);
this.surclasse = surclasse;
// la nouvelle classe est indiquée comme subclasse de sa surclasse
surclasse.subclasses.add(this);
} // fin constructeur

/**
 * Fabrique une subclasse de type Surclasse
 * à l'instance (surclasse) courante.
 * @param nom subclasse à la classe courante
 * @return nouvelle subclasse fabriquée
 */
public Surclasse add(String nom, String description) {
Surclasse nouvelleClasse = new Surclasse(this,nom,description);
return nouvelleClasse;
} // fin ajout comme subclasse

/**
 * Ajout comme subclasse à la classe.
 * @param subclasse à la classe courante
 */
public void add(Surclasse subclasse) {
System.out.println("méthode add dans Surclasse : ne doit pas s'afficher");
subclasses.add(subclasse);
} // fin ajout comme subclasse


/**
 * La méthode affecte leur niveau
 * aux subclasses de la classe.
 * Cette méthode considère que la racine a son niveau en précondition.
 */
public void affecteNiveau() {
y = niveau.niveau * 100;
if (subclasses.size()>0) {
Niveau subniveau = niveau.giveSubniveau();
// à chaque subclasse on donne le sous niveau déterminé
for (Surclasse s : subclasses) {
s.setNiveau(subniveau);
// appel récursif
s.affecteNiveau();
} // fin parcours toute subclasse de la classe
} // fin quand il existe des sous classes
} // fin méthode affecteNiveau

/**
 * Cette méthode fait deux actions :
 * donne son niveau à la classe
 * donne la classe au niveau
 * @param niveau relation entre niveau et classe
 */
void setNiveau(Niveau niveau) {
this.niveau = niveau;
niveau.add(this);
} // fin méthode setNiveau

/**
 * Surtout utilisée dans Intervalle mais
 * introduite ici par héritage de Groupe
 * @return classe du groupe
 */
public Surclasse firstClasse() {
return this;
} // fin méthode firstClasse

/**
 * Calcul l'abscisse x en fonction des abscisses des subclasses
 */
public void calculCoordonnees() {
int premier_x = subclasses.getFirst().x;
int dernier_x = subclasses.getLast().x;
x = (premier_x + dernier_x) / 2;
} // fin méthode calculCoordonnees

/**
 * Méthode qui ne sert à rien
 * nécessaire à cause de l'interface Groupe
 * @return 1 (une seule classe concernée dans ce groupe
 */
public int nombreClasses() {
return 1;
} // fin méthode nombreClasses

/**
 * Décalage de l'abscisse ainsi que de celles des subclasses
 * @param decalage valeur du décalage
 */
public void decaler(int decalage) {
x = x + decalage;;
for (Surclasse s : subclasses) {
s.decaler(decalage);
} // fin parcours des subclasses
} // fin méthode décalage

/**
 * Lors de la révision des positions
 * on fourni une nouvelle abscisse
 * qui déclenche un décalage
 * @param abscisse nouvelle valeur pour x
 */
public void nouvelX(int abscisse) {
decaler(abscisse-x);
} // fin méthode nouvelX

/**
 * Cette méthode contribue à la concaténation
 * de toutes les classes d'un niveau
 * @return ensemble ne contenant que la classe présente
 */
public LinkedList<Surclasse> getClasses() {
LinkedList<Surclasse> allClasses = new LinkedList<Surclasse>();
allClasses.add(this);
return allClasses;
} // fin méthode getClasses

/**
 * Méthode qui lance la 2nde étape
 * placement spatial des classes dans l'arborescence.
 * fixe les coordonnées basiques (sans translation)
 */
public void lancerPlacement() {
// à chaque classe, on affecte son niveau et elle est placée dedans
this.affecteNiveau();
// lancement de l'initialisation
niveau.getLast().initialiseSocle();
} // fin méthode lancerPlacement

/**
 * Translate toute les classes
 * par réécursivité.
 * @param decalageDroite translation horizontale forcément vers la droite
 * @param decalageBas translation vertical forcément vers le bas
 */
void translation(int decalageDroite, int decalageBas) {
x = x + decalageDroite;
y = y + decalageBas;
for (Surclasse subclasse : subclasses) {
subclasse.translation(decalageDroite, decalageBas);
} // fin parcours toutes les subclasses
} // fin méthode translation

/**
 * Cette méthode permet d'avoir une vision de l'arborescence par niveaux.
 * @return commentaires au format Latitude
 */
LinkedList<String> affichageNiveaux() {
return (niveau.affichage());
} // fin méthode affichage

/**
 * Permet de disposer de la description de toutes les surclasses présentent dans l'arbre.
 * @return lignes des descriptions au format Latitude
 */
LinkedList<String> affichage() {
LinkedList<String> lignes = new LinkedList<String>();
lignes.add("");
lignes.add("====");
lignes.add("Surclasse "+nom);
lignes.add("");
lignes.add("Dans le niveau "+niveau.niveau+" avec pour abscisse "+x);
lignes.add("");
if (surclasse!=null) lignes.add("Superclasse : "+surclasse.nom);
switch (subclasses.size()) {
case 0 :
break;
case 1 :
lignes.add("Une subclasse :"+subclasses.get(0).nom);
break;
default :
lignes.add("Ensemble des subclasses :");
for (Surclasse s : subclasses) {
lignes.add("* "+s.nom);
} // fin boucle
} // fin switch
for (Surclasse s : subclasses) {
lignes.addAll(s.affichage());
} // fin boucle
return lignes;
} // fin méthode affichage

/**
 * méthode de retour d'identité
 * @return nom de la classe
 */
public String toString() {
return ("Surclasse "+nom+" ("+x+","+y+")");
} // fin méthode toString

/**
 * Génère le code SVG de l'arborescence.
 * @param transHor translation horizontale positive
 * @return code SVG surclasses et héritages
 */
LinkedList<String> generer() {
// lignes contenant le code SVG
LinkedList<String> lignes = new LinkedList<String>();
String ligne;
// variables utiles pour le tracer des héritages
int abscisse = 0;
int ordonnee = 0;
System.out.println("Générer surclasse "+nom+" ("+x+","+y+")");
// dessin de la surclasse
lignes.add("<!-- Surclasse "+nom+" ("+x+","+y+") -->");
lignes.add("<g aria-labelledby=\""+nom+"\">");
lignes.add("<use xlink:href=\"#classe\" x=\""+x+"\" y=\""+y+"\" />");
lignes.add("<text x=\""+(x+30)+"\" y=\""+(y+12)+"\" text-anchor=\"middle\" font-size=\""+getDimNom()+"\" fill=\"black\" dominant-baseline=\"central\">");
lignes.add(nom+"</text>");
ligne = "Classe "+nom;
lignes.add("<desc id=\""+nom+"\">"+ligne+"</desc>");
lignes.add("</g>");
lignes.add("");
// si il existe des subclasses à la surclasse courante
if (subclasses.size()>0) {
// il existe des subclasses
// il faut tracer l'héritage
abscisse = x + 30; // milieu de la surclasse
ordonnee = y + 50; // bas de la classe
Surclasse sousclasse;
lignes.add("<g aria-labelledby=\"heritage-"+nom+"\">");
if (subclasses.size() == 1) {
sousclasse = subclasses.get(0);
lignes.add("<line x1=\""+abscisse+"\" y1=\""+(ordonnee+50)+"\" x2=\""+abscisse+"\" y2=\""+ordonnee+"\" style=\"stroke: black; stroke-width: 0.6;marker-end:url(#heritage); \" />");
ligne = ""+sousclasse.nom+" hérite de "+nom;
} else {
// au moins deux subclasses
// les ordonnées sont déductibles de l'ordonnée de base
int abscisse2 = subclasses.get(0).x+30;
int abscisse3 = subclasses.getLast().x+30;
lignes.add("<line x1=\""+abscisse+"\" y1=\""+(ordonnee+25)+"\" x2=\""+abscisse+"\" y2=\""+ordonnee+"\" style=\"stroke: black; stroke-width: 0.6;marker-end:url(#heritage); \" />");
lignes.add("<line x1=\""+abscisse2+"\" y1=\""+(ordonnee+25)+"\" x2=\""+abscisse3+"\" y2=\""+(ordonnee+25)+"\" style=\"stroke: black; stroke-width: 0.6; \" />");
ligne = "";
for (Surclasse s : subclasses) {
lignes.add("<line x1=\""+(s.x+30)+"\" y1=\""+(ordonnee+25)+"\" x2=\""+(s.x+30)+"\" y2=\""+(ordonnee+50)+"\" style=\"stroke: black; stroke-width: 0.6; \" />");
ligne = ligne+s.nom+", ";
} // fin boucle

ligne = ligne+" héritent de "+nom;
} // fin cas ou il y a des sous-classes une ou plusieurs
// ligne nécessaire à cause d'un bogue dans ARIA
lignes.add("<text x=\"10\" y=\"10\" font-size=\"1\" > </text>");
lignes.add("<desc id=\"heritage-"+nom+"\">"+ligne+"</desc>");
lignes.add("</g>");
lignes.add("");
} // fin s'il y a des subclasses
for (Surclasse s : subclasses) {
lignes.addAll(s.generer());
} // fin boucle
return lignes;
} // fin méthode générer le SVG

/**
 * Retourne le décalage potentiel en fonction du côté
 * @param cote numéro du côté (de 0 à 3)
 * @return décalage potentiel (0 ou 20, 0 ou 10)
 */
int getDecalage(int cote) {
int decalage = super.getDecalage(cote);
if ((cote==3) && (surclasse!=null)) decalage=Math.max(decalage,20);
if ((cote==1) && (subclasses.size()>0)) decalage = Math.max(decalage,20);
return decalage;
} // fin getDecalage
} // fin classe Surclasse

/**
 * Niveau dans l'arborescence
 * Chaque classe d'un arbre possède un niveau, la racine est au premier niveau,
 * la profondeur de l'arbre est indiquée par le dernier niveau.
 */
public class Niveau {

/**
 * Permettra de déduire l'ordonnée y
 */
int niveau;

/**
 * Niveau juste inférieur
 */
Niveau subniveau;

/**
 * Ensemble des classes du même niveau, regroupées soit
 * comme classes simples, soit comme classe possédant des subclasses.
 * Pour cela il a fallu créer une interface Groupe
 */
LinkedList<Groupe> groupes;

/**
 * constructeur d'un niveau
 * @param numero indique le niveau (à partir de 0)
 */
Niveau(int numero) {
niveau = numero;
// pas de sous niveau au début
subniveau = null;
groupes = new LinkedList<Groupe>();
// toutes suite de classes placées dans des groupes commence
// par un intervalle, potentiellement vide
groupes.add(new Intervalle());
} // fin constructeur de niveau

/**
 * Fourni le sous niveau d'un niveau donné
 * Si le sous niveau n'existe pas encore, il est fabriqué.
 * @return subniveau unique d'un niveau
 */
public Niveau giveSubniveau() {
if (subniveau==null) subniveau = new Niveau(niveau+1);
return subniveau;
} // fin méthode giveSubniveau

/**
 * Introduit une classe dans le niveau ad hoc
 * @param classe placée dans le niveau
 */
void add(Surclasse classe) {
// de deux choses l'une : classe simple ou surclasse
if (classe.subclasses.size()==0) {
// pas de subclasse, donc classe simple
// groupes.getLast() est un intervalle
groupes.getLast().add(classe);
 } else {
// sinon, la surclasse possède au moins une subclasse
groupes.add(classe);
// garantie que tout ensemble se termine par un intervalle
groupes.add(new Intervalle());
} // fin tests
} // fin méthode add

/**
 * Permet de connaitre le plus bas niveau existant
 * @return plus bas niveau de l'arborescence
 */
Niveau getLast() {
if (subniveau==null) return this;
else return (subniveau.getLast());
} // fin méthode getLast

/**
 * Retourne le niveau juste supérieur
 * @return niveau supérieur
 */
Niveau getPrevious() {
Surclasse classe;
if (groupes.size()>1) classe = groupes.get(1).firstClasse();
else classe = groupes.get(0).firstClasse();
if (classe.surclasse == null) return null;
else return (classe.surclasse.niveau);
} // fin méthode getPrevious

/**
 * Cette méthode appartient à la vue
 * Elle donne les premières coordonnées du socle
 */
void initialiseSocle () {
// au début, l'abscisse est située le plus à gauche
int x = 0;
// au dernier niveau,  les classes sont toutes dans un intervalle unique
for (Surclasse s : groupes.get(0).getClasses()) {
s.x = x;
// 80 = 60 + 20
// largeur d'une classe + espacement minimum
x = x + 80;
} // fin boucle
// passage au niveau supérieur
try {
getPrevious().calculCoordonnees();
} catch (Exception e) {
}
} // fin méthode initialiseSocle

/**
 * Cette méthode est récursive
 */
public void calculCoordonnees() {
/* En entrant dans cette méthode, toutes les surclasses
 possédant des subclasses doivent avoir des coordonnées */
int nombreIntervalle = groupes.size();
// tous les intervalles impairs sont des surclasses
for (int i=1; i<nombreIntervalle; i=i+2) {
groupes.get(i).calculCoordonnees();
} // fin boucle sur les surclasses
/* Il faut étudier les classes simples intermédiaires
 maintenant que toutes les surclasses ont des coordonnées */
// Les classes comprises entre deux surclasses définissent une séparation minimale
// elles sont dans les groupes de 2 à 2n 2n+2 étant l'indice maximal
// le max est donc défini par les deux x et le nombre de classes entre deux
// distance minimum
int ecartMinimum = 20;
int ecart;
int premier = groupes.get(1).firstClasse().x + 60;
int dernier = 0;
int nombreInterne =0;
for (int i=2; i<(nombreIntervalle-1); i=i+2) {
dernier = groupes.get(i+1).firstClasse().x;
System.out.println("Niveau "+niveau+" dernier = "+dernier+" (et le premier = "+premier+")");
// nombre de classes dans l'intervalle
nombreInterne = groupes.get(i).nombreClasses();
// espace libre entre les deux surclasses qui encadrent l'intervalle
if (nombreInterne==0) {
// pas de classe interne
// il ne se passe rien entendu que les surclasses sont séparées par leurs subclasses
ecartMinimum = Math.max(ecartMinimum,dernier-premier);
} else {
// il existe au moins une classe dans l'intervalle
ecart = (dernier-premier-60*nombreInterne) / (nombreInterne+1);
if (ecart<20) {
// écart trop petit, il faut décaler toutes les surclasse à droite
for (int j = i+1; j<nombreIntervalle; j=j+2) {
groupes.get(j).firstClasse().decaler(20 * (nombreInterne+1) - (dernier-premier-60*nombreInterne));
} // fin boucle j sur décalage surclasses à partir de i+1
} else {
// écart grand, il faut changer le minimum possible
ecartMinimum = Math.max(ecartMinimum,ecart);
} // fin test sur les écarts
} // fin test sur le nombre de classes internes
premier = dernier+60;
} // fin boucle sur les intervalles
/* Quand on arrive là, il nous reste à gérer le décalage minimum demandé
ainsi que des classes simples à gauche qui pourraient avoir une abscisse négative */
// ici donner des coordonnées aux classes simples de gauche
int nombreClassesGauche = groupes.getFirst().nombreClasses();
premier = Math.max(0,groupes.get(1).firstClasse().x);
// s'il y a des classes à gauche
if (nombreClassesGauche > 0) {
// il faut donner des coordonnées aux classes de gauche
premier = Math.max(0,premier - (60+ecartMinimum)*nombreClassesGauche);
}
nouvelX(premier,ecartMinimum);
try {
getPrevious().calculCoordonnees();
} catch (Exception e) {
} // stop quand plus de niveau supérieur
} // fin méthode calculCoordonnees

/**
 * Cette méthode appartient à la vue
 * Elle donne les nouvelles coordonnées d'un niveau
 * @param premierX valeur abscisse de la classe la plus à gauche
 * @param decalage intervalle (espace) entre deux classes de ce niveau
 */
void nouvelX(int premierX, int decalage) {
int x = premierX;
// les classes sont toutes dans un ensemble unique
for (Surclasse s : getClasses()) {
s.nouvelX(x);
// largeur d'une classe + espacement minimum
x = x + decalage + 60;
} // fin boucle sur toutes les classes
// passage au niveau supérieur
getPrevious().calculCoordonnees();
} // fin méthode nouvel X

/**
 * Retourne toutes les classes du niveau
 * @return toutes les classes comprises dans le niveau
 */
public LinkedList<Surclasse> getClasses() {
LinkedList<Surclasse> allClasses = new LinkedList<Surclasse>();
for (Groupe g : groupes) {
allClasses.addAll(g.getClasses());
}
return allClasses;
} // fin méthode getClasses


/**
 * Prototype
 * à revoir
 * @return lignes décrivant le niveau, appel récursif
 */
LinkedList<String> affichage() {
LinkedList<String> lignes = new LinkedList<String>();
lignes.add("");
lignes.add("====");
lignes.add("Fiche du niveau "+niveau);
lignes.add("");
switch(getClasses().size()) {

case 0 :
lignes.add("Pas de classe dans ce niveau ! Erreur certaine !.");
break;
case 1 :
lignes.add("Contient la classe "+getClasses().get(0).nom);
break;
default :
lignes.add("Contient les classes suivantes :");
for (Surclasse s : getClasses()) {
lignes.add(" * "+s.nom);
} // fin boucle
} // fin switch
if (subniveau!=null) lignes.addAll(subniveau.affichage());
return lignes;
} // fin méthode affichage
} // fin classe Niveau

// Classes hébergées Association et C_Association

/**
 * Une association relie une ou deux classes.
 * Elle peut être qualifiée d'agrégation ou de composition
 * @author Frédéric Serin, Université Le Havre Normandie, LITIS
 * @version 1.2, 2018-02-12
 * @author Frédéric Serin
 * @since 1, 2018-02-03
 */
class Association {

/**
 * Une association relie une ou deux classes.
 */
Classe[] classes;

/**
 * Classe 1 et 2 reliées par l'association
 * Contient donc la classe, la multiplicité et le rôle
 */
String[] multiplicites;

/**
 * Le type d'association indique s'il s'agit 
 * d'une agrégation côté droit 1
 * d'une agrégation côté gauche  3
 * d'une composition côté droit 2
 * d'une composition côté gauche 4
 * simplement ordinaire : 0
 */
int stereotypeAssociation;

/**
 * Ensemble des types d'associations
 */
String[] type = {"","marker-end:url(#agregation);","marker-end:url(#composition);"};

/**
 * Le rôle est optionnel.
 */
String[] roles;

/**
 * Initialisation
 */
Association() {
multiplicites = new String[2];
roles = new String[2];
for (int i=0; i<2; i++) {
multiplicites[i] = "";
roles[i] = "";
} // fin initialisation avec chaine vide
// association ordinaire par défaut
stereotypeAssociation = 0;
} // fin constructeur par défaut

/**
 * Construit l'association à partir
 * des classes et de sa description
 * Inclus les associations réflexives.
 * @param c1 une des classes liée
 * @param c2 l'autre classe reliée
 * @param description chaine avec multi rôles,
 */
Association (Classe c1, Classe c2, String description) {
this(); // constructeur par défaut
// cas d'une association réflexive
if (c1.equals(c2)) {
classes = new Classe[1];
classes[0] = c1;
analyseDescription(description);
c1.addAssociation(this);
} else {
// sinon, association entre deux classes distinctes
classes = new Classe[2];
classes[0] = c1;
classes[1] = c2;
analyseDescription(description);
c1.addAssociation(this);
c2.addAssociation(this);
} // fin association entre deux classes distinctes
} // fin constructeur association entre deux classes

/**
 * L'analyse de la représentation
 * de l'association est une chaine séparée en son milieu par des tirets (au moins deux).
 * À gauche les éléments liés à la 1ère classe
 * À droite les éléments liés à la 2nde classe.
 * Elle met à jour les attributs multiplicites et roles
 * @param description chaine de caractères décrivant l'association hors ses classes
 */
void analyseDescription(String description) {
// séparation en deux parties en retirant les tirets intermédiaires
String[] parties = description.split("-{2,}");
String tampon;
int indice;
// Normalement il doit y avoir exactement 2 parties
if (parties.length!=2)  { System.out.println("Soucis avec "+description); return; }
// retrait des blancs aux extrémités
for (int i=0; i<2; i++) {
parties[i] = parties[i].trim();
// toute suite de blancs est remplacée par un seul blanc
parties[i] = parties[i].replaceAll("[ ]{1,}"," ");
} // fin parcours parties 0 et 1
// détermination type d'association (agrégation ou composition)
if (parties[0].endsWith("<>")) {
// c'est une agrégation côté gauche
stereotypeAssociation = 3;
parties[0] = parties[0].substring(0,parties[0].length()-2);
} else
if (parties[0].endsWith("<<>>")) {
// composition côté gauche
stereotypeAssociation = 4;
parties[0] = parties[0].substring(0,parties[0].length()-4);
}
if (parties[1].startsWith("<>")) {
// c'est une agrégation côté droit
stereotypeAssociation = 1;
parties[1] = parties[1].substring(2,parties[1].length());
} else
if (parties[1].startsWith("<<>>")) {
// composition côté droit
stereotypeAssociation = 2;
parties[1] = parties[1].substring(4,parties[1].length());
}

// chaque partie est composée de la multiplicité et du rôle (accessoirement)
// le rôle peut être agrégation, composition
// syntaxe role multiplicité ou le contraire
String[] decomposition;
// parcours des deux extrémités : chaque multiplicité et rôle étendu
for (int i=0; i<2; i++) {
decomposition = parties[i].split(" ");
/* dans la décomposition, un des éléments est la multiplicité
sauf si erreur d'écriture */
indice = 0;
do {
tampon = getMultiplicite(decomposition[indice]);
indice++;
} while((tampon==null) && (indice<decomposition.length));
multiplicites[i] = tampon;
} // fin boucle 0 et 1
} // fin analyse description

/**
 * Vérifie si une chaine représente une multiplicité
 * @param chaine pouvant représenter une multiplicité
 * @return la multiplicité bien écrite ou null sinon
 */
String getMultiplicite(String chaine) {
// une multiplicité est soit n..m soit m tout seul
String[] valeurs = chaine.split("[\056]{2}");
// 056 est le code octal pour le point
// cas possible d'une étoile pour la multiplicité
boolean etoile = false;
int entier = 0;
// on ne devrait jamais avoir ce cas !
if (valeurs.length>2) return null;
// si une seule valeur vérifier si elle est correcte
if (valeurs.length==1) {
entier = 0;
try {
entier = new Integer(valeurs[0]);
if (entier>0) return (""+entier);
else return null;
} catch (Exception e) {
if (valeurs[0].equals("*")) return "*";
else return null;
} // fin du catch
} // fin du if une seule valeur
// sinon, comme il y a deux valeurs, vérifier la conformité
// et le fait que la seconde soit plus grande que la première
if (valeurs.length==2) {
entier = 0;
try {
entier = new Integer(valeurs[0]);
} catch (Exception e) {
if (valeurs[0].equals("*")) etoile = true;
else return null;
} // fin du catch
// on a un entier ou une étoile
if (etoile) {
if (valeurs[1].equals("*")) return "*";
else return null;
} // fin cas étoile des deux côtés
// à partir de là, on a un entier en première valeur
int entier2 = 0;
try {
entier2 = new Integer(valeurs[1]);
// on fait le test uniquement si entier2 existe, pas d'exception
if ((entier2<entier) || (entier2==0)) return null;
if (entier==entier2) return (""+entier);

else return (""+entier+".."+entier2);
} catch (Exception e) {
if (valeurs[1].equals("*")) {
if (entier==0) return "*";
else return (""+entier+"..*");
}
else return null;
} // fin du catch
} // fin du if deux valeurs
return null;
} // fin méthode getMultiplicite


/**
 * Description normalisée de l'association
 * @return description association
 */
public String toString() {
String ligne = "Association ";
if (classes.length==1) ligne+=" réflexive";
ligne += "\n"+classes[0]+" ";
ligne = ligne + multiplicites[0] + " --- "+multiplicites[1];
if (classes.length>1) ligne+=" "+classes[1];
return ligne;
} // fin to string

/**
 * Génération des lignes SVG
 * d'une association
 * @return les lignes SVG dédiées à l'association
 */
LinkedList<String> generer() {
// ensemble des lignes retournées pour représenter l'association en SVG
LinkedList<String> lignes = new LinkedList<String>();
// une ligne créée
String ligne = "";
// abscisses et ordonnées des deux extrémités de l'association
int[] abscisses = new int[2];
int[] ordonnees = new int[2];
// type local afin de permettre la modification pour simplification du tracé
int typeAssociation = stereotypeAssociation;
// initialisation des coordonnées des classes liées avec translation éventuelle
for (int i=0; i<classes.length; i++) {
abscisses[i] = classes[i].x;
ordonnees[i] = classes[i].y;
} // fin initialisation des extrémités
// compteur unique, écriture du lien ARIA pour la description
numAssociation++;
lignes.add("<g aria-labelledby=\""+numAssociation+"\">");

// cas d'une association réflexive
if (classes.length==1) {
// x1=x2 et y1=y2
abscisses[0] += 60;
ordonnees[0] += 25;
abscisses[1] = abscisses[0]-30;
ordonnees[1] = ordonnees[0]-25;
// orientation du tracé de  l'association
if (typeAssociation>=3) {
// tracé inversé et typé
typeAssociation -= 2; // type 1 ou 2 finalement
ligne = "<polyline points=\""+abscisses[1]+","+ordonnees[1];
// on remonte de 20 en dur
ligne += " "+abscisses[1]+","+(ordonnees[1]-20);
// on va vers la droite
ligne += " "+(abscisses[0]+20)+","+(ordonnees[1]-20);
// on redescend
ligne += " "+(abscisses[0]+20)+","+ordonnees[0];
ligne += " "+abscisses[0]+","+ordonnees[0];
ligne += "\" style=\"fill:none; stroke: black; stroke-width: 0.6;"+type[typeAssociation]+" \" />";
lignes.add(ligne);
// placement des multiplicités avec 10x10 en dur
ligne = "<text x=\""+(abscisses[0]+10)+"\" y=\""+(ordonnees[0]+10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[1]+"</text>");
ligne = "<text x=\""+(abscisses[1]-10)+"\" y=\""+(ordonnees[1]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[0]+"</text>");
lignes.add("");
// fin écriture de l'association réflexive inversée
lignes.add("<desc id=\""+numAssociation+"\">"+getDescription()+"</desc>");
lignes.add("</g>");
lignes.add("");
return lignes;
} else {
// tracé normal association réflexive
ligne = "<polyline points=\""+abscisses[0]+","+ordonnees[0];
// on décale de 20 (en dur) vers la droite
ligne += " "+(abscisses[0]+20)+","+ordonnees[0];
// on remonte de 45 en dur vers le haut
ligne += " "+(abscisses[0]+20)+","+(ordonnees[1]-20);
ligne += " "+abscisses[1]+","+(ordonnees[1]-20);
ligne += " "+abscisses[1]+","+ordonnees[1];
ligne += "\" style=\"fill:none; stroke: black; stroke-width: 0.6;"+type[typeAssociation]+" \" />";
lignes.add(ligne);
// placement des multiplicités avec 10x10 en dur
ligne = "<text x=\""+(abscisses[0]+10)+"\" y=\""+(ordonnees[0]+10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[0]+"</text>");
ligne = "<text x=\""+(abscisses[1]-10)+"\" y=\""+(ordonnees[1]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[1]+"</text>");
lignes.add("<desc id=\""+numAssociation+"\">"+getDescription()+"</desc>");
lignes.add("</g>");
lignes.add("");
return lignes;
} // fin cas des associations réflexives normales
} // fin cas association réflexive

// début cas associations entre deux classes distinctes
// il nous reste donc 8 des 9 possibilités, après le traitement de l'association réflexive

// cas association horizontale (y1=y2)
if (ordonnees[0]==ordonnees[1]) {
ordonnees[0] += 25;
ordonnees[1] += 25;
// de deux choses l'une : une des deux classes est à gauche, l'autre à droite
// deux cas, selon la position à gauche ou à droite de la classe 0
if (abscisses[0]<abscisses[1]) {
abscisses[0] += 60;
if (typeAssociation>=3) {
// on va tracer de droite à gauche de 1 vers 0
typeAssociation -= 2;
ligne = "<line x1=\"";
ligne += abscisses[1]+"\" y1=\""+ordonnees[1];
ligne += "\" x2=\""+abscisses[0]+"\" y2=\""+ordonnees[0];
ligne += "\" style=\"stroke: black; stroke-width: 0.6;"+type[typeAssociation]+" \" />";
lignes.add(ligne);
// 0 est à gauche
ligne = "<text x=\""+(abscisses[0]+10)+"\" y=\""+(ordonnees[0]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[0]+"</text>");
// 1 est à droite
ligne = "<text x=\""+(abscisses[1]-10)+"\" y=\""+(ordonnees[1]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[1]+"</text>");
} else {
// on va tracer de gauche à droite de 0 vers 1
ligne = "<line x1=\"";
ligne += abscisses[0]+"\" y1=\""+ordonnees[0];
ligne += "\" x2=\""+abscisses[1]+"\" y2=\""+ordonnees[1];
ligne += "\" style=\"stroke: black; stroke-width: 0.6;"+type[typeAssociation]+" \" />";
lignes.add(ligne);
// 0 est à gauche
ligne = "<text x=\""+(abscisses[0]+10)+"\" y=\""+(ordonnees[0]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[0]+"</text>");
// 1 est à droite
ligne = "<text x=\""+(abscisses[1]-10)+"\" y=\""+(ordonnees[1]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[1]+"</text>");
} // fin association normale 0 vers 1 de gauche à droite
} else {
abscisses[1] += 60;
if (typeAssociation>=3) {
// on va tracer de gauche à droite de 1 vers 0
typeAssociation -= 2;
ligne = "<line x1=\"";
ligne += abscisses[1]+"\" y1=\""+ordonnees[1];
ligne += "\" x2=\""+abscisses[0]+"\" y2=\""+ordonnees[0];
ligne += "\" style=\"stroke: black; stroke-width: 0.6;"+type[typeAssociation]+" \" />";
lignes.add(ligne);
// 1 est à gauche
ligne = "<text x=\""+(abscisses[1]+10)+"\" y=\""+(ordonnees[1]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[1]+"</text>");
// 0 est à droite
ligne = "<text x=\""+(abscisses[0]-10)+"\" y=\""+(ordonnees[0]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[0]+"</text>");
} else {
// on va tracer de droite à gauche de 0 vers 1
ligne = "<line x1=\"";
ligne += abscisses[0]+"\" y1=\""+ordonnees[0];
ligne += "\" x2=\""+abscisses[1]+"\" y2=\""+ordonnees[1];
ligne += "\" style=\"stroke: black; stroke-width: 0.6;"+type[typeAssociation]+" \" />";
lignes.add(ligne);
// 1 est à gauche
ligne = "<text x=\""+(abscisses[1]+10)+"\" y=\""+(ordonnees[1]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[1]+"</text>");
// 0 est à droite
ligne = "<text x=\""+(abscisses[0]-10)+"\" y=\""+(ordonnees[0]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[0]+"</text>");
}} // fin association normale 0 vers 1 de droite à gauche
lignes.add("<desc id=\""+numAssociation+"\">"+getDescription()+"</desc>");
lignes.add("</g>");
lignes.add("");
return lignes;
} // fin totale des tracés horizontaux

// cas des classes verticalement alignées
if (abscisses[0]==abscisses[1]) {
abscisses[0] += 30; // association verticale centrée au milieu des deux classes
abscisses[1] += 30;
// de deux choses l'une : une des deux classes est au dessus de l'autre
// deux cas, selon la position en haut ou en bas de la classe 0
if (ordonnees[0]<ordonnees[1]) {
// la classe 0 est en haut
ordonnees[0] += 50;
// faut-il inverser le tracé ?
if (typeAssociation>=3) {
// on va tracer de bas en haut de 1 vers 0
typeAssociation -= 2;
ligne = "<line x1=\"";
ligne += abscisses[1]+"\" y1=\""+ordonnees[1];
ligne += "\" x2=\""+abscisses[0]+"\" y2=\""+ordonnees[0];
ligne += "\" style=\"stroke: black; stroke-width: 0.6;"+type[typeAssociation]+" \" />";
lignes.add(ligne);
// 0 est en haut
ligne = "<text x=\""+(abscisses[0]+10)+"\" y=\""+(ordonnees[0]+10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[0]+"</text>");
// 1 est en bas
ligne = "<text x=\""+(abscisses[1]+10)+"\" y=\""+(ordonnees[1]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[1]+"</text>");
} else {
// on va tracer de haut en bas de 0 vers 1
ligne = "<line x1=\"";
ligne += abscisses[0]+"\" y1=\""+ordonnees[0];
ligne += "\" x2=\""+abscisses[1]+"\" y2=\""+ordonnees[1];
ligne += "\" style=\"stroke: black; stroke-width: 0.6;"+type[typeAssociation]+" \" />";
lignes.add(ligne);
// 0 est en haut
ligne = "<text x=\""+(abscisses[0]+10)+"\" y=\""+(ordonnees[0]+10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[0]+"</text>");
// 1 est en bas
ligne = "<text x=\""+(abscisses[1]+10)+"\" y=\""+(ordonnees[1]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[1]+"</text>");
}
// fin cas classe 0 en haut
} else {
// la classe 1 est en haut
ordonnees[1] += 50;
// faut-il inverser le tracé ?
if (typeAssociation>=3) {
typeAssociation -= 2;
// on va tracer de bas en haut de 0 vers 1
ligne = "<line x1=\"";
ligne += abscisses[0]+"\" y1=\""+ordonnees[0];
ligne += "\" x2=\""+abscisses[1]+"\" y2=\""+ordonnees[1];
ligne += "\" style=\"stroke: black; stroke-width: 0.6;"+type[typeAssociation]+" \" />";
lignes.add(ligne);
// 1 est en haut
ligne = "<text x=\""+(abscisses[1]+10)+"\" y=\""+(ordonnees[1]+10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[1]+"</text>");
// 0 est en bas
ligne = "<text x=\""+(abscisses[0]+10)+"\" y=\""+(ordonnees[0]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[0]+"</text>");
} else {
// on va tracer de haut en bas de 1 vers 0
ligne = "<line x1=\"";
ligne += abscisses[1]+"\" y1=\""+ordonnees[1];
ligne += "\" x2=\""+abscisses[0]+"\" y2=\""+ordonnees[0];
ligne += "\" style=\"stroke: black; stroke-width: 0.6;"+type[typeAssociation]+" \" />";
lignes.add(ligne);
// 1 est en haut
ligne = "<text x=\""+(abscisses[1]+10)+"\" y=\""+(ordonnees[1]+10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[1]+"</text>");
// 0 est en bas
ligne = "<text x=\""+(abscisses[0]+10)+"\" y=\""+(ordonnees[0]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[0]+"</text>");
} // fin tracé normal de 1 vers 0 de haut en bas
} // fin cas classe 1 en haut
lignes.add("<desc id=\""+numAssociation+"\">"+getDescription()+"</desc>");
lignes.add("</g>");
lignes.add("");
return lignes;
} // fin du cas classes verticalement alignées

// 4 autres cas : x1≠x2 et y1≠y2
if (abscisses[0]<abscisses[1]) {
// x1<x2
if (ordonnees[0]<ordonnees[1]) {
// x1<x2 et y1<y2
abscisses[0] += 30+classes[0].getDecalage(1);
ordonnees[0] += 50;
ordonnees[1] += 25-classes[1].getDecalage(0);
if (typeAssociation>=3) {
typeAssociation -= 2;
lignes.add(getTraceL(abscisses[1],ordonnees[1],abscisses[0],ordonnees[0],type[typeAssociation],false));
} else {
lignes.add(getTraceL(abscisses[0],ordonnees[0],abscisses[1],ordonnees[1],type[typeAssociation],true));
}
// affichage des multiplicités
ligne = "<text x=\""+(abscisses[0]+10)+"\" y=\""+(ordonnees[0]+10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[0]+"</text>");
ligne = "<text x=\""+(abscisses[1]-10)+"\" y=\""+(ordonnees[1]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[1]+"</text>");
} else {
// x1<x2 et y1>y2
abscisses[0] += 60;
ordonnees[0] += 25-classes[0].getDecalage(2);
abscisses[1] += 30-classes[1].getDecalage(1);
ordonnees[1] += 50;
if (typeAssociation>=3) {
typeAssociation -= 2;
lignes.add(getTraceJ(abscisses[1],ordonnees[1],abscisses[0],ordonnees[0],type[typeAssociation],false));
} else {
lignes.add(getTraceJ(abscisses[0],ordonnees[0],abscisses[1],ordonnees[1],type[typeAssociation],true));
}
// affichage des multiplicités
ligne = "<text x=\""+(abscisses[0]+10)+"\" y=\""+(ordonnees[0]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[0]+"</text>");
ligne = "<text x=\""+(abscisses[1]-10)+"\" y=\""+(ordonnees[1]+10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[1]+"</text>");
}
} else {
if (ordonnees[0]<ordonnees[1]) {
// x1>x2 et y1<y2
ordonnees[0] += 25+classes[0].getDecalage(0);
abscisses[1] += 30+classes[1].getDecalage(3);
if (typeAssociation>=3) {
typeAssociation -= 2;
lignes.add(getTraceJ(abscisses[1],ordonnees[1],abscisses[0],ordonnees[0],type[typeAssociation],false));
} else {
lignes.add(getTraceJ(abscisses[0],ordonnees[0],abscisses[1],ordonnees[1],type[typeAssociation],true));
}
// affichage des multiplicités
ligne = "<text x=\""+(abscisses[0]-10)+"\" y=\""+(ordonnees[0]+10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[0]+"</text>");
ligne = "<text x=\""+(abscisses[1]+10)+"\" y=\""+(ordonnees[1]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[1]+"</text>");
} else {
// x1>x2 et y1>y2
abscisses[0] += 30-classes[0].getDecalage(3);
abscisses[1] += 60;
ordonnees[1] += 25+classes[1].getDecalage(2);
if (typeAssociation>=3) {
typeAssociation -= 2;
lignes.add(getTraceL(abscisses[1],ordonnees[1],abscisses[0],ordonnees[0],type[typeAssociation],false));
} else {
lignes.add(getTraceL(abscisses[0],ordonnees[0],abscisses[1],ordonnees[1],type[typeAssociation],true));
}
}
// affichage des multiplicités
ligne = "<text x=\""+(abscisses[0]-10)+"\" y=\""+(ordonnees[0]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[0]+"</text>");
ligne = "<text x=\""+(abscisses[1]+10)+"\" y=\""+(ordonnees[1]+10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[1]+"</text>");
}
lignes.add("<desc id=\""+numAssociation+"\">"+getDescription()+"</desc>");
lignes.add("</g>");
lignes.add("");
return lignes;
} // fin méthode génération lignes SVG de l'association

/**
 * Tracé en L (ou non logique inversement)
 * @param x1 abscisse premier point
 * @param y1 ordonnée premier point
 * @param x2 abscisse second point
 * @param y2 ordonnée second point
 * @param marqueur indique si besoin agrégation ou composition
 * @param sens true si sens trigonométrique
 * @return la ligne SVG du tracé en L
 */
String getTraceL(int x1, int y1, int x2, int y2, String marqueur, boolean sens) {
String ligne = "";
ligne = "<polyline points=\""+x1+","+y1;

if (sens)
ligne += " "+x1+","+y2;
else
ligne += " "+x2+","+y1;

ligne += " "+x2+","+y2;
ligne += "\" style=\"fill:none; stroke: black; stroke-width: 0.6;"+marqueur+" \" />";

return ligne;
} // fin méthode tracé en L

/**
 * Tracé en J (ou Gamma majuscule)
 * @param x1 abscisse premier point
 * @param y1 ordonnée premier point
 * @param x2 abscisse second point
 * @param y2 ordonnée second point
 * @param marqueur indique si besoin agrégation ou composition
 * @param sens true si sens trigonométrique
 * @return la ligne SVG du tracé en L
 */
String getTraceJ(int x1, int y1, int x2, int y2, String marqueur, boolean sens) {
String ligne = "";
ligne = "<polyline points=\""+x1+","+y1;

if (sens)
ligne += " "+x2+","+y1;
else
ligne += " "+x1+","+y2;

ligne += " "+x2+","+y2;
ligne += "\" style=\"fill:none; stroke: black; stroke-width: 0.6;"+marqueur+" \" />";

return ligne;
} // fin méthode tracé en J

/**
 * Etablit le côté de contact pour les deux classes.
 */
void etablirCote() {
int leQuadrant = getQuadrant();
switch (leQuadrant) {
case 0 : // quadrant haut gauche
classes[0].pointsContacts[3]++;
classes[1].pointsContacts[2]++;
break;
case 1 : // quadrant bas gauche
classes[0].pointsContacts[0]++;
classes[1].pointsContacts[3]++;
break;
case 2 : // quadrant haut droite
classes[0].pointsContacts[2]++;
classes[1].pointsContacts[1]++;
break;
case 3 : // quadrant bas droite
classes[0].pointsContacts[1]++;
classes[1].pointsContacts[0]++;
break;
default : // association réflexive, horizontale ou verticale
if (isDirecte()) {
// association verticale ou horizontale, il faut tester
if (classes[0].x==classes[1].x) {
// association verticale
if (classes[0].y<classes[1].y) {
// classe 0 au dessus de classe 1
classes[0].pointsContacts[1]++;
classes[0].pointsCentraux[1] = true;
classes[1].pointsContacts[3]++;
classes[1].pointsCentraux[3] = true;
} else {
// classe 1 au dessus de classe 0
classes[0].pointsContacts[3]++;
classes[0].pointsCentraux[3] = true;
classes[1].pointsContacts[1]++;
classes[1].pointsCentraux[1] = true;
}
} else {
// association horizontale
if (classes[0].x<classes[1].x) {
// classe 0 à gauche de classe 1
classes[0].pointsContacts[2]++;
classes[0].pointsCentraux[2] = true;
classes[1].pointsContacts[0]++;
classes[1].pointsCentraux[0] = true;
} else {
// classe 1 à gauche de classe 0
classes[0].pointsContacts[0]++;
classes[0].pointsCentraux[0] = true;
classes[1].pointsContacts[2]++;
classes[1].pointsCentraux[2] = true;
}
} // fin association horizontale
} else {
// association réflexive
classes[0].pointsContacts[2]++;
classes[0].pointsContacts[3]++;
}// fin association réflexive
} // fin du switch
} // fin méthode etablirCoté

/**
 * Méthode qui permet de déterminer dans quel quadrant se situe l'association
 * (2x+y+3)/2 qui donne
 * 0 quadrant haut gauche
 * 1 quadrant bas gauche
 * 2 quadrant haut droite
 * 3 quadrant bas droite
 * @return le quadrant (de 0 à 3) sauf si alignement (retourne -1)
*/
int getQuadrant() {
// si une seule classe, juxtaposition des coordonnées
if (classes.length==1) return -1;
int x = classes[1].x - classes[0].x;
int y = classes[1].y - classes[0].y;
// si alignement horizontal ou vertical
if ((x==0) || (y==0)) return -1;
return ((2*x/Math.abs(x) + y/Math.abs(y) +3)/2);
} // fin méthode getQuadrant

/**
 * Détermine si une association est directe, en ligne droite
 * @return true si ligne droite
 */
boolean isDirecte() {
if (classes.length==1) return false;
return (getQuadrant()==-1);
} // fin méthode isDirecte

/**
 * Génère le commentaire nécessaire pour la description
 * de l'association.
 * @return description utile pour le desc du SVG
 */
String getDescription() {
String desc = "";
// identité des classes associées
String c0, c1;
c0 = classes[0].nom;
// s'il existe une seconde classe (association non rélfexive)
if (classes.length>1) c1 = classes[1].nom;
else {
c1 = c0;
desc += "Association réflexive. ";
}
desc += "Chaque instance de "+c1;
// la multiplicité est constituée d'un nombre mineur et d'un majeur
Integer mineur = 1, majeur = 1;
// le code 056 en octal est le point
String[] parties = multiplicites[0].split("[\056]{2}");
if (parties[0].startsWith("*")) mineur = -1;
else mineur = new Integer(parties[0]);
// sil existe un nombre majeur (possible sauf si *)
if (parties.length>1) {
if (parties[1].startsWith("*")) majeur = -1;
else majeur = new Integer(parties[1]);
}
// si multiplicité mineur égale à astérisque
if ((parties.length == 1) && (mineur == -1)) {
switch(stereotypeAssociation) {
case 0 :
desc += " peut être liée à un nombre quelconque d'instances de "+c0;
break;
case 1 : // agrégation côté c1
desc += " peut agréger un nombre quelconque d'instances de "+c0;
break;
case 2 : // composition côté c1
desc += " peut être  composée d'un nombre quelconque d'instances de "+c0;
break;
case 3 : // agrégation côté c0
desc += " peut être agrégée par un nombre quelconque d'instances de "+c0;
break;
default : // case 4, composition côté c0
desc += " peut être composant (par erreur) d'un nombre quelconque d'instances de "+c0;
}
} else {
// une multiplicité composée seulement de la mineur qui ne peut être nulle
if ((parties.length==1) && (mineur==1)) {
// c1 liée à une seule instance de c0
switch(stereotypeAssociation) {
case 0 : // association normale
desc += " est liée à une seule instance de "+c0;
break;
case 1 : // agrégation côté c1
desc += " agrège une seule instance de "+c0;
break;
case 2 : // composition côté c1
desc += " est composée d'une instance unique de "+c0;
break;
case 3 : // agrégation côté c0
desc += " est agrégée par une instance de "+c0;
break;
default : // case 4, composition côté c0
desc += " est composant de "+c0;
} // fin du switch
} else {
if (parties.length==1) {
// en ce cas, mineur unique et supérieur ou égal à 2
switch(stereotypeAssociation) {
case 0 : // association normale
desc += " est liée à exactement "+mineur+" instances de "+c0;
break;
case 1 : // agrégation côté c1
desc += " agrège exactement "+mineur+" instances de "+c0;
break;
case 2 : // composition côté c1
desc += " est composée  d'exactement "+mineur+" instances de "+c0;
break;
case 3 : // agrégation côté c0
desc += " est agrégée par exactement "+mineur+" instances de "+c0;
break;
default : // case 4, composition côté c0
desc += " est composant (anormalement - erreur) de "+mineur+ "instances de "+c0;
} // fin du switch
} else {
// à partir de là, une majeur existe forfcément
if (mineur == 0) {
if (majeur==1) {
// la multiplicité est 0..1
switch(stereotypeAssociation) {
case 0 : // association normale
desc += " est ou non liée à une seule instance de "+c0;
break;
case 1 : // agrégation côté c1
desc += " peut agréger une seule instance de "+c0;
break;
case 2 : // composition côté c1
desc += " peut être  composée au plus d'une instance de "+c0;
break;
case 3 : // agrégation côté c0
desc += " peut être agrégée par au plus une instance de "+c0;
break;
default : // case 4, composition côté c0
desc += " peut être composant d'au plus une instance de "+c0;
} // fin du switch
} else {
// la multiplicité est du type 0..n avec n≥2
desc += " peut être liée jusqu'à "+majeur+" instances de "+c0;
}} else {
// à partir d'ici mineur est non nulle et majeur existe
switch(stereotypeAssociation) {
case 0 : // association normale
desc += " est liée à ";
break;
case 1 : // agrégation côté c1
desc += " agrège ";
break;
case 2 : // composition côté c1
desc += " est composée de ";
break;
case 3 : // agrégation côté c0
desc += " est agrégée par ";
break;
default : // case 4, // composition côté c0
desc += " est composant (anormalement) de ";
} // fin du switch qui commence la phrase
desc += mineur+" à "+majeur+" instances de "+c0;
}}}}
// deuxième partie lecture en inversant c0 qui devient 1 et c1 qui devient 0
desc += ". Chaque instance de ";
String prov = c1; // tampon pour inversion
c1 =c0;
c0 = prov;
desc += c1;
// multiplicité inscrite à droite, 056 est le code octal du point
parties = multiplicites[1].split("[\056]{2}");
if (parties[0].startsWith("*")) mineur = -1;
else mineur = new Integer(parties[0]);
if (parties.length>1) {
if (parties[1].startsWith("*")) majeur = -1;
else majeur = new Integer(parties[1]);
}
// mineur et majeur sont initialisés
if ((parties.length==1) && (mineur==-1)) {
// astérisque côté droit, c0 maintenant
switch(stereotypeAssociation) {
case 0 : // association normale
desc += " peut être liée à un nombre quelconque d'instances de "+c0;
break;
case 1 : // agrégation côté c0
desc += " peut être agrégée par un nombre quelconque d'instances de "+c0;
break;
case 2 : // composition côté c0
desc += " peut être composant (par erreur -2) d'un nombre quelconque  d'instances de  "+c0;
break;
case 3 : // agrégation côté c1
desc += " peut agréger un nombre quelconque d'instances de "+c0;
break;
default : // case 4, composition côté c1
desc += " peut être  composée d'un nombre quelconque d'instances de "+c0;
} // fin du switch
} else {
if ((parties.length==1) && (mineur == 1)) {
// 1 côté c0 qui se situe à gauche
switch(stereotypeAssociation) {
case 0 : // association normale
desc += " est liée à une seule instance de "+c0;
break;
case 1 : // agrégation côté c0
desc += " est agrégé par une seule instance de "+c0;
break;
case 2 : // composition côté c0
desc += " est composant d'une seule instance de  "+c0;
break;
case 3 : // agrégation côté c1
desc += " agrège une seule instance de "+c0;
break;
default : // case 4, composition côté c1
desc += " est composée d'une seule instance de "+c0;
} // fin du switch
} else {
if (parties.length==1) {
// la multiplicité côté c0 (à gauche) est unique et supérieure ou égale à 2
switch(stereotypeAssociation) {
case 0 : // association normale
desc += " est liée à exactement "+mineur+" instance de "+c0;
break;
case 1 : // agrégation côté c0
desc += " est agrégé par"+mineur+" instance de "+c0;
break;
case 2 : // composition côté c0
desc += " est composant (par erreur) de "+mineur+" instances de "+c0;
break;
case 3 : // agrégation côté c1
desc += " agrège exactement "+mineur+" instances de "+c0;
break;
default : // case 4, composition côté c1
desc += " est composée exactement de "+mineur+" instances de "+c0;
} // fin du switch
} else {
// à partir de là, il doit y avoir une majeur entendu que la mineur n'est ni astérisque ni unique
if (mineur==0) {
if (majeur==1) {
// multiplicité 0..1 côté c0
switch(stereotypeAssociation) {
case 0 : // association normale
desc += " est ou non liée à une seule instance de "+c0;
break;
case 1 : // agrégation côté c0
desc += " est agrégé au plus par une instance de "+c0;
break;
case 2 : // composition côté c0
desc += " est composant d'au plus une instance de "+c0;
break;
case 3 : // agrégation côté c1
desc += " peut agréger au plus une instance de "+c0;
break;
default : // case 4, composition côté c1
desc += " peut être composée d'au plus 'une instance de "+c0;
} // fin du switch
} else {
desc += " peut être liée jusqu'à "+majeur+" instances de "+c0;
}} else {
// ici on doit être dans une situation quelconque mineur..majeur
switch(stereotypeAssociation) {
case 0 : // association normale
desc += "est liée à ";
break;
case 1 : // agrégation côté c0
desc += " est agrégé par ";
break;
case 2 : // composition côté c0
desc += " est composant de ";
break;
case 3 : // agrégation côté c1
desc += " agrège ";
break;
default : // case 4, composition côté c1
desc += " est composée de ";
} // fin du switch
// la phrase est terminée pareillement par tous les cas
desc += mineur+" à "+majeur+" instances de "+c0;
}}}}
// fred trace provisoire à retirer à terme
System.out.println(desc);
return desc;
} // fin méthode getDescription

} // fin classe Association

/**
 * Une C-association relie une ou deux classes.
 * Elle est une association dont elle hérite, engendrant 
 * une ligne en forme de C ou de U.
 */
class CAssociation extends Association {

/**
 * Indice C-U qui précise si l'association est en forme de U (faux)
 * ou en forme de C (vrai).
 */
boolean indiceCU;

/**
 * Initialisation
 * Forme en U par défaut.
 */
CAssociation() {
super();
indiceCU = false;
}

/**
 * Construit l'association à partir
 * des classes et de sa description
 * Inclus les associations réflexives.
 * Forme en U par défaut
 * et en C si et seulement si l'une au dessus de l'autre exactement.
 * @param c1 une des classes liée
 * @param c0 l'autre classe reliée
 * @param description chaine avec multi rôles,
 */
CAssociation (Classe c1, Classe c0, String description) {
super(c1, c0, description);
if ((classes.length==2) && (classes[0].x==classes[1].x))
indiceCU = true;
} // fin constructeur sans spécification C ou U

/**
 * Construit l'association à partir
 * des classes et de sa description
 * Inclus les associations réflexives.
 * @param c1 une des classes liée
 * @param c0 l'autre classe reliée
 * @param description chaine avec multi rôles,
 * @param symetrieVeticale U si faux et C si vrai
 */
CAssociation (Classe c1, Classe c0, String description, Boolean symetrieVeticale) {
super(c1, c0, description);
// l'indice est pris en compte uniquement si les deux classes ne sont pas strictement identiques
indiceCU = symetrieVeticale;
if ((classes.length==2) && (classes[0].x==classes[1].x)) indiceCU = true;
if ((classes.length==2) && (classes[0].y==classes[1].y)) indiceCU = false;
} // fin constructeur complet

/**
 * Description normalisée de l'association
 * @return description association
 */
public String toString() {
String ligne = "C-Association ";
if (classes.length==1) ligne+=" réflexive";
ligne += "\n"+classes[0]+" ";
ligne = ligne + multiplicites[0] + " --- "+multiplicites[1];
if (classes.length>1) ligne+=" "+classes[1];
return ligne;
} // fin to string

/**
 * Génération des lignes SVG
 * d'une c-association
 * @param transHor translation horizontale
 * @param transVer translation verticale
 * @return les lignes SVG dédiées à la c-association
 */
LinkedList<String> generer() {
// cas d'une association réflexive, traitée comme toute association
if (classes.length==1) {
return (super.generer());
} // fin cas association réflexive
// ensemble des lignes retournées pour représenter l'association
LinkedList<String> lignes = new LinkedList<String>();
// une ligne créée
String ligne = "";
// abscisses et ordonnées des deux extrémités de l'association
int[] abscisses = new int[2];
int[] ordonnees = new int[2];
// usage local du type d'association
int typeAssociation = stereotypeAssociation;
// initialisation des coordonnées des deux classes liées
for (int i=0; i<classes.length; i++) {
abscisses[i] = classes[i].x;
ordonnees[i] = classes[i].y;
} // fin initialisation des extrémités
int decalage = 0;
// les deux classes sont nécessairement distinctes
// les cas U ou C sont définis sans ambiguïté
// U: indiceCU=false
// C: indiceCU=true
if (indiceCU) {
// C: indiceCU=true
// de deux choses l'une : une des deux classes est en haut, l'autre en bas
if (ordonnees[0]<ordonnees[1]) {
// classe 0 en haut
ordonnees[0] += 25+classes[0].getDecalage(0);
ordonnees[1] += 25-classes[0].getDecalage(1);
decalage = Math.min(abscisses[0],abscisses[1]) - 20;
largeurGraphique = Math.max(largeurGraphique, decalage);
if (typeAssociation>=3) {
typeAssociation -= 2;
// tracé inverse
ligne = "<polyline points=\""+abscisses[1]+","+ordonnees[1];
ligne += " "+decalage+","+ordonnees[1];
ligne += " "+decalage+","+ordonnees[0];
ligne += " "+abscisses[0]+","+ordonnees[0];
ligne += "\" style=\"fill:none; stroke: black; stroke-width: 0.6;"+type[typeAssociation]+" \" />";
lignes.add(ligne);
} else {
// typeAssociation<=2
ligne = "<polyline points=\""+abscisses[0]+","+ordonnees[0];
ligne += " "+decalage+","+ordonnees[0];
ligne += " "+decalage+","+ordonnees[1];
ligne += " "+abscisses[1]+","+ordonnees[1];
ligne += "\" style=\"fill:none; stroke: black; stroke-width: 0.6;"+type[typeAssociation]+" \" />";
lignes.add(ligne);
}
// placement des multiplicités
ligne = "<text x=\""+(abscisses[0]-10)+"\" y=\""+(ordonnees[0]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[0]+"</text>");
ligne = "<text x=\""+(abscisses[1]-10)+"\" y=\""+(ordonnees[1]+10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[1]+"</text>");

} else {
// classe 1 en haut
abscisses[0] += 60;
ordonnees[0] += 25-classes[0].getDecalage(2);
abscisses[1] += 60;
ordonnees[1] += 25+classes[1].getDecalage(2);
decalage = Math.max(abscisses[0],abscisses[1]) + 20;
largeurGraphique = Math.max(largeurGraphique, decalage);
if (typeAssociation>=3) {
typeAssociation -= 2;
// tracé inverse
ligne = "<polyline points=\""+abscisses[1]+","+ordonnees[1];
ligne += " "+decalage+","+ordonnees[1];
ligne += " "+decalage+","+ordonnees[0];
ligne += " "+abscisses[0]+","+ordonnees[0];
ligne += "\" style=\"fill:none; stroke: black; stroke-width: 0.6;"+type[typeAssociation]+" \" />";
lignes.add(ligne);
} else {
// typeAssociation<=2
ligne = "<polyline points=\""+abscisses[0]+","+ordonnees[0];
ligne += " "+decalage+","+ordonnees[0];
ligne += " "+decalage+","+ordonnees[1];
ligne += " "+abscisses[1]+","+ordonnees[1];
ligne += "\" style=\"fill:none; stroke: black; stroke-width: 0.6;"+type[typeAssociation]+" \" />";
lignes.add(ligne);
}
// placement des multiplicités
ligne = "<text x=\""+(abscisses[0]+10)+"\" y=\""+(ordonnees[0]+10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[0]+"</text>");
ligne = "<text x=\""+(abscisses[1]+10)+"\" y=\""+(ordonnees[1]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[1]+"</text>");
}
// fin de tous les cas C
} else {
// U: indiceCU=false
// de deux choses l'une : une des deux classes est à gauche, l'autre à droite
if (abscisses[0]<abscisses[1]) {
// classe 0 à gauche
ordonnees[0] += 50;
ordonnees[1] += 50;
abscisses[0] += 30+classes[0].getDecalage(1);
abscisses[1] += 30-classes[1].getDecalage(1);
decalage = Math.max(ordonnees[0],ordonnees[1]) + 20;
hauteurGraphique = Math.max(hauteurGraphique,decalage);
if (typeAssociation>=3) {
typeAssociation -= 2;
// tracé inverse
ligne = "<polyline points=\""+abscisses[1]+","+ordonnees[1];
ligne += " "+abscisses[1]+","+decalage;
ligne += " "+abscisses[0]+","+decalage;
ligne += " "+abscisses[0]+","+ordonnees[0];
ligne += "\" style=\"fill:none; stroke: black; stroke-width: 0.6;"+type[typeAssociation]+" \" />";
lignes.add(ligne);
} else {
// typeAssociation<=2
ligne = "<polyline points=\""+abscisses[0]+","+ordonnees[0];
ligne += " "+abscisses[0]+","+decalage;
ligne += " "+abscisses[1]+","+decalage;
ligne += " "+abscisses[1]+","+ordonnees[1];
ligne += "\" style=\"fill:none; stroke: black; stroke-width: 0.6;"+type[typeAssociation]+" \" />";
lignes.add(ligne);
}
// placement des multiplicités
ligne = "<text x=\""+(abscisses[0]-10)+"\" y=\""+(ordonnees[0]+10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[0]+"</text>");
ligne = "<text x=\""+(abscisses[1]+10)+"\" y=\""+(ordonnees[1]+10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[1]+"</text>");
} else {
// classe 1 à gauche
abscisses[0] += 30-classes[0].getDecalage(3);
abscisses[1] += 30+classes[1].getDecalage(3);
decalage = Math.min(ordonnees[0],ordonnees[1]) - 20;
hauteurGraphique = Math.max(hauteurGraphique,decalage);
if (typeAssociation>=3) {
typeAssociation -= 2;
// tracé inverse
ligne = "<polyline points=\""+abscisses[1]+","+ordonnees[1];
ligne += " "+abscisses[1]+","+decalage;
ligne += " "+abscisses[0]+","+decalage;
ligne += " "+abscisses[0]+","+ordonnees[0];
ligne += "\" style=\"fill:none; stroke: black; stroke-width: 0.6;"+type[typeAssociation]+" \" />";
lignes.add(ligne);
} else {
// typeAssociation<=2
ligne = "<polyline points=\""+abscisses[0]+","+ordonnees[0];
ligne += " "+abscisses[0]+","+decalage;
ligne += " "+abscisses[1]+","+decalage;
ligne += " "+abscisses[1]+","+ordonnees[1];
ligne += "\" style=\"fill:none; stroke: black; stroke-width: 0.6;"+type[typeAssociation]+" \" />";
lignes.add(ligne);
}
// placement des multiplicités
ligne = "<text x=\""+(abscisses[0]+10)+"\" y=\""+(ordonnees[0]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[0]+"</text>");
ligne = "<text x=\""+(abscisses[1]-10)+"\" y=\""+(ordonnees[1]-10)+"\" text-anchor=\"middle\" font-size=\"8\" fill=\"black\" dominant-baseline=\"central\">";
lignes.add(ligne);
lignes.add(multiplicites[1]+"</text>");

} // fin dernier cas, classe 1 à gauche, U
}

lignes.add("");
return lignes;
} // fin méthode génération lignes SVG de l'association


/**
 * Etablit le côté de contact pour les deux classes.
 * De trois choses l'une :
 * réflexivité
 * en c
 * en u
 */
void etablirCote() {
// si une seule classe, association réflexive
if (classes.length==1) {
classes[0].pointsContacts[2]++;
classes[0].pointsContacts[3]++;
}// fin association réflexive
else {
// association non réflexive
if (indiceCU) {
// association en forme de c
if (classes[0].y<classes[1].y) {
// classe 0 au dessus de classe 1
classes[0].pointsContacts[0]++;
classes[1].pointsContacts[0]++;
} else {
// classe 1 au dessus de classe 0
classes[0].pointsContacts[2]++;
classes[1].pointsContacts[2]++;
}
} else {
// association en forme de u
if (classes[0].x<classes[1].x) {
// classe 0 à gauche de classe 1
classes[0].pointsContacts[1]++;
classes[1].pointsContacts[1]++;
} else {
// classe 1 à gauche de classe 0
classes[0].pointsContacts[3]++;
classes[1].pointsContacts[3]++;
}
}
} // fin cas association non réflexive
} // fin méthode etablirCoté
} // fin classe C-Association


} // fin classe Diagramme de classe