package diagramme;

import java.util.Set;
import java.util.LinkedList;
import java.util.HashMap;

/**
 * Les graphiques sont des documents au format SVG généré de façon indépendante
 * et identifier par un nom suffixé par svg.
 * Une analyse, avec le même identifiant enrichi par -analyse.txt
 * est également générée.
 * Ils ont vocation à être référencés dans la balise figure dans les documents Latitude.
 * La méthode ecrire permet la génération des documents.
 * @author Frédéric Serin, Université Le Havre Normandie, LITIS
 * @version 1.2, 2018-02-12
 * @since 2.0, 2018-02-06
 */
public class Graphique {

/**
 * Chemin suivi du nom pour parvenir au fichier.
 * Permet de créer les fichiers au même niveau que le fichier txt d'origine.
 * Ce nom ne possède pas de suffixe pour pouvoir être utilisé en svg et autres annexes.
 */
String  nomFichierSortie;

/**
 * Ensemble des lignes SVG
 */
LinkedList<String> code_SVG;

/**
 * Commentaires générés suite à l'analyse du texte.
 */
LinkedList<String> description;

/**
 * Cet ensemble de parties sera ajoutée au lignes regroupées dans description
 * qui représente le fichier texte analyse.
 * On y trouve le préambule de présentation, les avertissements, etc
 * Concaténée à description 
 */
Remarques remarques;

/**
 * Largeur du graphique, permet de définir la zone du graphique
 * dans le document SVG.
 */
int largeurGraphique;

/**
 * Hauteur du graphique, permet de définir la zone du graphique
 * dans le document SVG.
 */
int hauteurGraphique;

/**
 * Constructeur qui initialise les attributs composés.
 */
public Graphique() {
code_SVG = new LinkedList<String>();
description = new LinkedList<String>();
// les remarques remplacent et complètent les avertissements
remarques = new Remarques();
// dimensions par défaut, surement inapropriées
largeurGraphique = 500;
hauteurGraphique = 500;
} // fin constructeur initialisation

/**
 * Pour générer une figure svg.
 * @param nomFichierSortie nom du fichier sans suffixe
 * @param lignes code à analyser servant à la génération
 */
public Graphique(String  nomFichierSortie, LinkedList<String> lignes) {
this();
this. nomFichierSortie =  nomFichierSortie;
} // fin constructeur avec paramètres

/**
 * Retourne le nom complet du fichier SVG
 * dans lequel est écrit le code du diagramme de classe généré.
 * @return fichier suffixé par svg
 */
public String getNomFichierSVG() {
return (nomFichierSortie+".svg");
} // fin méthode retour du nom de fichier SVG

/**
 * Ajout d'une ligne dans une des remarques
 * présente comme composant de l'ensemble des remarques
 * @param indice numéro de l'ensemble des remarques
 * @param ligne ajoutée aux remarques précisées
 */
void addRemarque(Integer indice, String ligne) {
remarques.add(indice, ligne);
}

/**
 * Retourne l'ensemble des lignes
 * présentent à un indice de remarques
 * @param indice numéro sans vérification de son existence
 * @return ensemble de lignes de type String
 */
LinkedList<String> getRemarques(Integer indice) {
return remarques.get(indice);
}

class Remarques {

HashMap<Integer,LinkedList<String>> composants;

/**
 * Construit l'ensemble vide initial des remarques.
 */
Remarques() {
 composants = new HashMap<Integer,LinkedList<String>>();
} // fin construction d'une remarque composite

/**
 * Ajout d'une ligne dans une des remarques
 * présente comme composant de l'ensemble des remarques
 * @param indice numéro de l'ensemble des remarques
 * @param ligne ajoutée aux remarques précisées
 */
void add(Integer indice, String ligne) {
LinkedList<String> valeur;
// récupération de l'ensemble des lignes existantes à cet indice
if ( composants.containsKey(indice)) valeur = composants.get(indice);
else {
valeur = new LinkedList<String>();
composants.put(indice,valeur);
} // fin else : création si besoin
valeur.add(ligne);
} // fin ajout d'une ligne dans la bonne remarque

/**
 * Retourne l'ensemble des lignes
 * présentent à un indice de remarques
 * @param indice numéro sans vérification de son existence
 * @return ensemble de lignes de type String
 */
LinkedList<String> get(Integer indice) {
try {
return (composants.get(indice));
} catch (Exception e) {
return null;
}
} // fin retour de toutes les lignes présentent dans une remarque

} // fin de la classe hébergée Remarques

} // fin de la surclasse Graphique