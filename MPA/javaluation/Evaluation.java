package javaluation;

import java.util.*;
import com.sun.javadoc.*;

/**
 * Cette classe permet de noter l'évaluation du code commenté.
 * Avec quatre pondérations : 8 classe, 2 attribut, 3 constructeur, 4 méthode
 * Indice multiplicateur de 1,5 pour la visibilité.
 * @author Frederic Serin, université de Normandie
 * @version 1.2, 2016-02-07
 */
class Evaluation {

/**
 * Note obtenue avec pondération
 * reportée sur le total.
 */
private double[] note;

/**
 * Total de l'évaluation
 */
private double[] total;

/**
 * numéro de la classe évaluée.
 */
private int rang;

/**
 * Le coefficient de référence est le
 * coefficient maximum autorisé dans un élément
 * pour ces éléments contenants.
 * Ainsi, dans une classe protected, aucun élément ne peut dépasser
 * le coefficient de protected même s'il est déclaré public
 */
double coeffReference;

/**
 * permet de choisir l'affichage (vrai) ou non (faux)
 * des commentaires dont l'évaluation qualitative est nécessaire.
 */
boolean indic;

/**
 * Regroupe toutes les informations sur les auteurs
 * afin de vérifier la conformité.
 */
LinkedList<String> author;

/**
 * Rassemble toutes les informations
 * de versions pour les comparer
 */
LinkedList<String> version;

/**
 * Liste les éléments n'ayant pas de 
 * commentaires.
 */
LinkedList<String> nocomment;

/**
 * Contiendra l'ensemble des return manquants.
 */
LinkedList<String> noreturn;

/**
 * Contiendra l'ensemble des @param manquants
 */
LinkedList<String> noparam;


/**
 * Initialisation des notes à 0 ainsi que des totaux maximums.
 */
Evaluation () {
rang = -1;
note = new double[100];
total = new double[100];
for (int i=0; i<100; i++) {
note[i] = 0;
total[i] = 0;
} // fin boucle initialisation
author = new LinkedList<String>();
author.add("Enumération des auteurs cités");
version = new LinkedList<String>();
version.add("Numéros des versions des classes");
nocomment = new LinkedList<String>();
nocomment.add("*Liste des commentaires absents ou de longueurs courtes (à titre indicatif)*");
noreturn = new LinkedList<String>();
noparam = new LinkedList<String>();
} // fin du constructeur

/**
 * Ce constructeur a été ajouté pour permettre de choisir
 * si oui ou non nous voulons voir apparaitre
 * certains éléments permettant une
 * évaluation qualitative.
 * @param indication vraie si affichage de l'assistance
 */
Evaluation (boolean indication) {
this();
this.indic = indication;
} // fin constructeur avec spécification indication

/**
 * Calcule le coefficient appliqué à l'élément.
 * Progression géométrique de 1,5
 * Private vaut 1 et public vaut 3,375
 * Ainsi plus la visibilité est large, et plus il est considéré
 * comme important de commenter.
 * @param element élément du code évalué
 * @return coefficient supérieur à 1
 */
double getCoefficient(ProgramElementDoc element) {
if (element.isPackagePrivate()) return 1.5;
else if (element.isProtected()) return 2.25;
else if (element.isPublic()) return 3.375;
else return 1.0;
} // fin de la méthode getCoefficient

/**
 * Affiche la note locale
 * propre à une classe (identifiée par son rang)
 * @return note entre 0 et 20, -1 si n'existe pas
 */
double score() {
if (total[rang] == 0)return -1;
else
return (Math.round(note[rang]/total[rang]*200.0)/10.0);
} // fin méthode score

/**
 * Donne une note sur 20.
 * @return note entre 0 et 20, -1 si n'existe pas
 */
double globalScore () {
// fait le total des scores
double gNote = 0.0;
double gTotal = 0.0;
for (int i=0; i<=rang; i++) {
gNote += note[i];
gTotal += total[i];
} // fin totaux
// test le cas pas de note
if (gTotal <=0) return -1;
// note sur 20 avec une décimale
return (Math.round(gNote/gTotal*200.0)/10.0);
} // fin méthode globalScore

/**
 * Cette méthode évalue l'entête d'une classe
 * Les commentaires, la présence des balises
 * author et version.
 * @param classe entête évaluée
 * @param classeHote classe encapsulante (null si classe principale)
 */
void add(ClassDoc classe, ClassDoc classeHote) {
// calcul du coeff associé à la visibilité
double coeff=getCoefficient(classe);
/* compteur de classes
seulement incrémenté si classe principale
Les balises author et version ne sont évaluées que pour 
les classes englobantes */
if (classeHote==null) {
rang++;
total[rang] += coeff * 8.0;
coeffReference = coeff;
Tag[] balises = classe.tags("@author");
if (balises.length>0) {
if (indic) {
for (Tag tg : balises) {
author.add(classe.name()+" author: "+tg.text());
} // fin parcours tags author
} // fin si indic
note[rang] += coeff * 2.0;
} // fin de vérification des auteurs
// vérification des versions indiquées (ou pas)
balises = classe.tags("@version");
if (balises.length>0) {
note[rang] += coeff;
if (indic) {
for (Tag tg : balises) {
version.add(classe.name()+" version: "+tg.text());
} // fin parcours tags version
} // fin si indic
} // fin vérification de la version
else {
// pas de version indiquée
version.add("Manque version dans "+classe.name());
} // fin du else
} // fin du cas classe hote
else { // cas d'une classe encapsulée
coeff = Math.min(coeff,coeffReference);
coeffReference = coeff;
total[rang] += coeff * 5.0;
} // fin du else, cas inner class
if (classe.commentText()!="") {
note[rang] += coeff * 5.0;
if (indic && ((classe.commentText()).length() < 100)) {
nocomment.add("Longueur faible des commentaires pour "+classe.name()+" = "+(classe.commentText()).length());
}
} else {
if (indic) {
nocomment.add("Pas de commentaire pour "+classe.name());
}
} // verif commentaires
} // fin de l'évaluation de l'entête de la classe

/**
 * Évaluation des attributs (champs).
 * @param attribut champ (field) d'une classe
 */
void add(FieldDoc attribut) {
double coeff=getCoefficient(attribut);
coeff = Math.min(coeff,coeffReference);
String commentaire = attribut.commentText();
if (commentaire != "") note[rang] += 2.0 * coeff;
else {
if (indic) {
nocomment.add("Pas de commentaire pour l'attribut "+attribut.name());
}
} // verif commentaires

total[rang] += 2.0 * coeff;
} // fin méthode avec field

/**
 * Évaluation commentaires et param
 * attachée aux méthodes et constructeurs.
 * @param member constructeur ou méthode
 * @param coefficient poids du membre évalué
 */
void add(ExecutableMemberDoc member, double coefficient) {
String commentaire = member.commentText();
if (commentaire != "") {
note[rang] += 2.0*coefficient;
if (indic && (commentaire.length() < 40)) nocomment.add("Commentaire de longueur courte ("+commentaire.length()+") pour "+member.name());
} else {
if (indic) {
nocomment.add("Pas de commentaire pour "+member.name());
}
}
total[rang] += 2.0 * coefficient;
Parameter[] parametres = member.parameters();
// s'il y a des paramètres
int nombreTotalparametres = 0;
if (parametres.length>0) {
Set<String> ensembleParametres = new TreeSet<String>();
for (Parameter parame : parametres) {
ensembleParametres.add(parame.name());
} // fin parcours des paramètres
nombreTotalparametres = ensembleParametres.size();
// maintenant que nous avons l'ensemble des paramètres
// controlons s'ils sont commentés
for (ParamTag ptg : member.paramTags()) {
ensembleParametres.remove(ptg.parameterName());
} // fin parcours des param
note[rang] += coefficient* (1.0 - (ensembleParametres.size()/(1.0*nombreTotalparametres)));
total[rang] += coefficient;
if (indic && !ensembleParametres.isEmpty()) {
noparam.add("Manque commentaire param "+(member.containingClass()).name()+"."+member.name());
Iterator<String> lesparams = ensembleParametres.iterator();
while (lesparams.hasNext()) {
noparam.add("Param non commenté : "+lesparams.next());
} // fin parcours params
} // fin de indic vrai
} // fin du cas où il y aurait des paramètres
} // fin méthode avec membre méthode ou constructeur

/**
 * Application aux méthodes, idem que pour les constructeurs
 * mais avec possibilité de return en plus.
 * @param element méthode évaluée
 */
void add(MethodDoc element) {
double coeff=getCoefficient(element);
coeff = Math.min(coeff,coeffReference);
add(element,coeff);
// vérification présence return si besoin
Type tp = element.returnType();
Tag[] leTagReturn = element.tags("@return");
if (tp.toString() != "void") {
total[rang] += coeff;
if (leTagReturn.length>0) note[rang] += coeff;
else noreturn.add("Pas de return pour "+(element.containingClass()).name()+" "+element.name()+" "+element.signature());
} // fin vérification adéquation return et valeur retournée
} // fin de la méthode add pour une méthode

/**
 * évalue un constructeur.
 * Idem qu'une méthode sans de return
 * @param element constructeur
 */
void add(ConstructorDoc element) {
double coeff=getCoefficient(element);
coeff = Math.min(coeff,coeffReference);
add(element,coeff);
} // fin méthode add pour constructeur

/**
 * Liste l'ensemble des points de contrôle
 * notés au cours de l'analyse.
 */
void debriefing() {
String strg;
// versions
while (!version.isEmpty()) {
strg = version.poll();
System.out.println(strg);
} // fin boucle version
while (!author.isEmpty()) {
strg = author.poll();
System.out.println(strg);
} // fin boucle auteurs
while (!noreturn.isEmpty()) {
strg = noreturn.poll();
System.out.println(strg);
} // fin boucle noreturn
while (!noparam.isEmpty()) {
strg = noparam.poll();
System.out.println(strg);
} // fin boucle noparam
while (!nocomment.isEmpty()) {
strg = nocomment.poll();
System.out.println(strg);
} // fin boucle nocomment

} // fin méthode debriefing
} // fin de la classe Evaluation