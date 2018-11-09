package javaluation;

import com.sun.javadoc.*;

/**
 * La classe Javalution est une classe
 * utilisant la Doclet de la commande javadoc
 * afin de parcourir le contenu des classes
 * et de fournir des informations sur la présence
 * des commentaires et balises
 * dans des classes java.
 * @author Frederic Serin, université de Normandie
 * @version 1.2, 2016-02-07
 **/
public abstract class Javaluation extends Doclet {

/**
 * Instance permettant de calculer
 * une note sur 20
 */
static Evaluation evaluation;

/**
 * ce constructeur est par défaut.
 * Il appelle le constructeur supérieur.
 */
public Javaluation() {
super();
}

/**
 * La méthode start est obligatoire car c'est elle qui est 
 * appelée par la commande javadoc.
 * Elle appelle la méthode go qui est spécifique 
 * au projet d'évaluation.
 * @param root document instancié par le traitement des fichiers java
 * @return vrai si l'opération javadoc a réussi
 */
    public static boolean start(RootDoc root) {
evaluation  = new Evaluation(false);

return go(root);
} // fin méthode start
/**
 * la méthode go est la partie commune du logiciel. Elle est 
 * appelée par start qui, elle-même, est déclenchée par la commande javadoc.
 * 
go organise l'ensemble des opérations nécessaires à l'évaluation de tous les éléments contenus dans
 * les paquetages et classes passés en
 * argument de javadoc.
 *
 * Les éléments sont les classes, les attributs, les constructeurs,
 * les méthodes et les classes encapsulées.
 * @param root document instancié par le traitement des fichiers java
 * @return vrai si l'opération javadoc a réussi
 */
    public static boolean go(RootDoc root) {
// parcours de tous les paquetages présents dans la commande
// Cette partie est externalisée car n'intervient pas dans l'évaluation
parcoursPaquetages(root);
// toutes les classes rencontrées dans l'analyse
// parcours de toutes les classes
        for (ClassDoc cd : root.classes()) {
if (cd.containingClass()==null) {
parcoursElements(cd,null);
System.out.println("Classe "+cd.name()+" estimée à "+evaluation.score()+"/20");
} // uniquement si classe principale
        } // fin de la boucle parcourant toutes les classes
System.out.println("Note globale  sur 20 = "+evaluation.globalScore());
        return true;
    } // fin de la méthode go

/**
 * Partie parcourant l'ensemble
 * des paquetages indiqués dans la  commande
 * javadoc.
 * Cette partie n'intervient pas dans l'évaluation.
 * @param root ensemble des éléments racine (classes) à parcourir
 */
static void parcoursPaquetages(RootDoc root) {
for (PackageDoc pd : root.specifiedPackages()) {
System.out.println("Paquetage "+pd.name());
if (pd.commentText() == "") System.out.println("Absence de commentaires du paquetage.");
else System.out.println("Présence de package.html");
System.out.println("Nombre d'éléments dans le paquetage = "+(pd.allClasses()).length);
} // fin parcours des paquetages
} // fin méthode parcoursPaquetages

/**
 * Méthode qui parcours tous les éléments
 * d'une classe, attributs,
 * constructeurs et méthodes.
 * @param laClasse classe étudiée
 * @param classePrincipale null si la classe étudiée n'est pas hébergée
 */
static void parcoursElements(ClassDoc laClasse, ClassDoc classePrincipale) {
evaluation.add(laClasse,classePrincipale);
// parcours de tous les champs (attributs)
for (FieldDoc fd : laClasse.fields()){
evaluation.add(fd);
} // fin parcours champs
// parcours des constructeurs
for (ConstructorDoc concd : laClasse.constructors()){
// vérification que le constructeur n'est pas par défaut
if (concd.position().line()!=laClasse.position().line()) evaluation.add(concd);
} // fin parcours constructeurs
// parcours des methodes
for (MethodDoc md : laClasse.methods()) {
evaluation.add(md);
} // fin parcours des méthodes
// parcours des classes encapsulées
for (ClassDoc ced : laClasse.innerClasses()) {
evaluation.add(ced,laClasse);
} // fin parcours des classes encapsulées
} // fin méthode parcours éléments

} // fin de la classe Javaluation