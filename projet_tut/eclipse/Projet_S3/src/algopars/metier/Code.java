package algopars.metier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

import java.util.List;

import bsh.Interpreter;

public final class Code {
	/***********************/
	/* ATTRIBUTS DE CLASSE */
	/***********************/
	//static Interpreter interpreter = new Interpreter();

	/************************/
	/* ATTRIBUTS D'INSTANCE */
	/************************/
	
	/**
	 * Tableau contenant l'intégralité du fichier sans aucune modification,
	 * Sans suppressions d'espaces ou autre ...
	 * */
	private String[] contenuFichier;

	/**
	 * Tableau associatif pour associer un objet Variable à son nom
	 * */
	protected HashMap<String, Variable> variables;

	/**
	 * Tableau associatif pour associer un objet Constante à som nom
	 * */
	protected HashMap<String, Constante> constantes;

	/**
	 * Contenu complet du code ligne par ligne or données
	 * */
	protected List<String> code;

	/**
	 * Constructeur d'un Code à partir d'un fichier
	 * 
	 * @param nom du fichier en String           
	 * @return un Code
	 */
	public Code(String fichier) {
		// initialisation des attributs
		this.variables = new HashMap<>();
		this.constantes = new HashMap<>();

		// initialisation de la List qui contiendra les lignes du code
		this.code = new LinkedList<>();

		LectureFichier.lire(fichier);
		this.contenuFichier = LectureFichier.lire(fichier).split("\n");

		//System.out.println(afficherPseudoCode(contenuFichier));
		interpretation(contenuFichier);
	}
	
	
	
	public String afficherPseudoCode() {
		String r = new String();
		for (int i = 0; i < contenuFichier.length; i++) {
			r += String.format("|%2s %-50s|\n", Integer.toString(i), contenuFichier[i]);
		}

		return r;
	}
	
	public String afficherDonnees() {
		String r = new String();
		r += "|    NOM    |   TYPE    |  VALEUR   |\n";
		for (Object v : variables.keySet()) {
			r += String.format("| %-10s| %-10s| %-10s|\n", v,  variables.get(v).getType(), variables.get(v));
		}
		
		return r;
	}

	private void interpretation(String[] tab) {
		int i = 2; // on démarra ligne 2 car les 2 premières lignes sont la déclaration
				   // de l'algo et du premier block
		try {
			while (!tab[i].equalsIgnoreCase("variable:")) {
				if(!tab[i].equalsIgnoreCase(""))
					this.creerConstantes(tab[i]);
				i++;
			}
			
			i ++;
			while (!tab[i].equalsIgnoreCase("DEBUT")) {
				if(!tab[i].equalsIgnoreCase(""))
					this.creerVariable(tab[i]);
				i++;
			}
			
			i++;
			while(!tab[i].equalsIgnoreCase("FIN")){
				if(!tab[i].matches("\\s+"))
					this.code.add(tab[i].replaceAll("\\s+", " "));
				i++;
			}
			
			/*for(String ligne: code)
				System.out.println(ligne);*/

		}
		catch (Exception e) {
			System.out.println("Problème ligne " +i);
		}
		
	}

	/**
	 * Méthode pour remplir la HashMap des variables
	 * */
	private void creerVariable(String ligne) {
		// on stock le nom des varaibles dans un Set
		// pour le cas ou il y a plusieurs variables sur une ligne
		Set<String> nomsVars = new HashSet<>();

		// pour stocker le type de la variable
		String type = new String();

		// Scanner pour traiter la ligne en paramètre
		Scanner scLig = new Scanner(ligne);
		scLig.useDelimiter(":");

		// on gère le cas de plusieurs variables sur la même ligne
		// séparés par des virgules
		if (ligne.contains(",")) {
			// deuxième scanner pour les variables sur la même ligne
			Scanner sc = new Scanner(scLig.next());
			sc.useDelimiter(",");

			while (sc.hasNext())
				nomsVars.add(sc.next().replaceAll("\\s+", ""));
			
			sc.close();
		} else
			nomsVars.add(scLig.next());

		// on récupère le type de la variable
		type = scLig.next().replaceAll("\\s+", "");

		// on ajoute la variable (sans valeur) à la HashMap
		for (String nomVar : nomsVars)
			variables.put(nomVar, new Variable(TypeVariable.trouverType(type)));
		
		scLig.close();
	}

	/**
	 * Méthode pour remplir la HashMap des constantes
	 * */
	private void creerConstantes(String ligne) {
		// on stock le nom des constantes dans un Set
		// pour le cas ou il y a plusieurs constantes sur une ligne
		Set<String> nomsConsts = new HashSet<>();

		// pour stocker le type de la constante
		String type = new String();

		// Scanner pour traiter la ligne en paramètre
		Scanner scLig = new Scanner(ligne);
		scLig.useDelimiter(":");

		// on gère le cas de plusieurs constantes sur la même ligne
		// séparés par des virgules
		if (ligne.contains(",")) {
			// deuxième scanner pour les constantes sur la même ligne
			Scanner sc = new Scanner(scLig.next());
			sc.useDelimiter(",");

			while (sc.hasNext())
				nomsConsts.add(sc.next());
			
			sc.close();
		} else
			nomsConsts.add(scLig.next());

		// on récupère le type de la constante
		type = scLig.next();

		// on ajoute la constante (sans valeur) à la HashMap
		for (String nomCons : nomsConsts)
			constantes.put(nomCons, new Constante(TypeVariable.trouverType(type)));
		
		scLig.close();
	}

}
