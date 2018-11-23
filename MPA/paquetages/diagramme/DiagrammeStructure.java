package diagramme;

import java.util.LinkedList;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
import fichiers.Fichier;
import fichiers.SVGFabrique;
import fichiers.Fusion;

/**
 * Les diagrammes de structure correspondent aux diagrammes de classe et d'objet en UML.
 * Ces deux types sont définis par deux subclasses de la classe présente.
 * la classe dispose d'un main pour lire un fichier de description
 * d'un diagramme d'objet UML.
 * Toutes les classes utiles à la conception de diagrammes
 * sont encapsulées.
 * Les classes et les objets sont déclarées par la même syntaxe
 * <A> MaClasse ou maClasse
 * Et les associations ou liens par
 * A 0..1 ----- * B
 * sans multiplicité pour les diagrammes d'objet.
 * Ajout du caractère clé c ou u pour les associations contournantes.
 * @author Frédéric Serin, Université Le Havre Normandie, LITIS
 * @version 2.0, 2018-03-22
 */
public abstract class DiagrammeStructure extends Graphique {

/**
 * Abscisse des coordonnées par défaut
 * données aux classes dans leur ordre d'apparition.
 */
int refX;

/**
 * Ordonnée des coordonnées par défaut
 * données aux classes dans leur ordre d'apparition.
 */
int refY;

/**
 * Compteur qui permet de numéroter les associations
 * pour les descriptions, entendu qu'un identifiant n'est pas possible
 * avec les données portées par une association.
*/
int numAssociation = 0;

/**
 * Pour générer un diagramme de structure.
 * @param nomFichierSortie nom complet avec chemin du fichier de sortie (non suffixé)
 * @param lignes code à analyser servant à la génération
 */
public DiagrammeStructure(String nomFichierSortie, LinkedList<String> lignes) 
throws Exception {
// initialisation des attributs de Graphique
super( nomFichierSortie, lignes);
// initialisation des coordonnées appliquées aux classes dans l'ordre de déclaration
refX = 0;
refY = 0;
} // fin constructeur intermédiaire entre diagrammes et graphique

} // fin classe abstraite diagramme de structure