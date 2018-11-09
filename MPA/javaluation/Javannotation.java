package javaluation;

import com.sun.javadoc.*;

/**
 * La classe Javannotation hérite de Javaluation et propose 
 * les mêmes fonctionnalités. En plus, elle propose d'indiquer les
 * points nécessitant un regard particulier.
 * @author Frédéric Serin
 * @version 1.2, 2016-01-31
 */
public abstract class Javannotation extends Javaluation {

/**
 * ce constructeur est par défaut.
 * Il appelle le constructeur supérieur.
 */
public Javannotation() {
super();
}

/**
 * La méthode start est obligatoire car c'est elle qui est 
 * appelée par la commande javadoc.
 * Elle permet l'exécution des opérations définies.
 * @param root document instancié par le traitement des fichiers java
 * @return vrai si l'opération javadoc a réussi
 */
    public static boolean start(RootDoc root) {
evaluation  = new Evaluation(true);
boolean retourJavadoc = go(root);
// ici affichage des traces utiles
evaluation.debriefing();
        return retourJavadoc;
    } // fin de la méthode start

} // fin de la classe Javannotation