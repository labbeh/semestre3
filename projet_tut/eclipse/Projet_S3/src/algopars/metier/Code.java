package algopars.metier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

import java.util.List;

import algopars.ihm.CouleursANSI;

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
	public HashMap<String, Variable> variables;

	/**
	 * Tableau associatif pour associer un objet Constante à som nom
	 * */
	public HashMap<String, Constante> constantes;

	/**
	 * Contenu complet du code ligne par ligne or données
	 * */
	protected List<LigneCode> code;
	//protected List<String> code;
	
	/**
	 * Permet de connaitre les lignes de codes que l'on doit mettre
	 * en évidence dans la trace d'exécution (sans les lignes blanches ...)
	 * */
	private List<Integer> numsLigsUtils;
	
	/**
	 * Indice de la ligne utile ou on est
	 * */
	private int iLigUtil;
	
	/**
	 * Indice de la ligne du code ou on se trouve
	 * */
	private int numLig;

	/**
	 * Constructeur d'un Code à partir d'un fichier
	 * 
	 * @param fichier nom du fichier en String           
	 */
	public Code(String fichier) {
		// initialisation des attributs
		this.variables 	= new HashMap<>();
		this.constantes = new HashMap<>();

		// initialisation de la List qui contiendra les lignes du code
		this.code = new ArrayList<>();
		this.numsLigsUtils = new LinkedList<>();
		
		this.iLigUtil = 0;

		LectureFichier.lire(fichier);
		this.contenuFichier = LectureFichier.lire(fichier).split("\n");

		interpretation();
		
		//this.numLig = getNumLig();
		this.numLig = numsLigsUtils.get(0);
		//System.out.println("TAILLE: " +code.get(0).getNumLig());
		System.out.println(code);
	}
	
	
	/**
	 * Génère sous forme de String le code à afficher
	 * @return un String
	 * */
	public String afficherPseudoCode() {
		String r = new String();
		for (int i = 0; i < contenuFichier.length; i++) {
			if(i == numLig) r += CouleursANSI.VERT.getCouleurFond();
			
			r += String.format("|%2s %-50s|\n", Integer.toString(i), contenuFichier[i]);
			r += CouleursANSI.NORMAL.getCouleurFond();
		}

		return r;
	}
	
	/**
	 * Génère le block "données" à afficher et ne met que les variables a tracer
	 * @return un String 
	 * */
	public String afficherDonnees() {
		String r = new String();
		r += "|    NOM    |   TYPE    |  VALEUR   |\n";
		for (String v : variables.keySet()) {
			if(variables.get(v).estAtracer())
				r += String.format("| %-10s| %-10s| %-10s|\n", v, 
									variables.get(v).getType(),
									variables.get(v));
		}
		
		return r;
	}
	
	/**
	 * Lancer l'interprétation du code
	 * */
	private void interpretation() {
		int i = 2; // on démarre ligne 2 car les 2 premières lignes sont la déclaration
				   // de l'algo et du premier block
		
		try {
			numsLigsUtils.add(0);
			
			
			while (!contenuFichier[i].equalsIgnoreCase("variable:")) {
				if(!contenuFichier[i].equalsIgnoreCase("")){
					this.creerConstantes(contenuFichier[i]);
					numsLigsUtils.add(i);
				}
				i++;
			}
			
			i ++;
			while (!contenuFichier[i].equalsIgnoreCase("DEBUT")) {
				if(!contenuFichier[i].equalsIgnoreCase("")){
					this.creerVariable(contenuFichier[i]);
					numsLigsUtils.add(i);
				}
				i++;
			}
			
			i++;
			while(!contenuFichier[i].equalsIgnoreCase("FIN")){
				if(!contenuFichier[i].matches("\\s+")){
					this.code.add(new LigneCode(i, contenuFichier[i].replaceAll("\\s+", " ")));
					numsLigsUtils.add(i);
				}
				i++;
			}

		}
		catch (Exception e) {
			System.out.println("Problème ligne " +i);
		}
		
	}

	/**
	 * Méthode pour remplir la HashMap des variables
	 * */
	private void creerVariable(String ligne) {
		// on stock le nom des variables dans un Set
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
	/* MODIFICATEURS */
	/**
	 * Permet d'incrémenter le numéro de ligne courante
	 * pour le surlignage
	 * */
	public void incNumLig(){
		numLig++;
	}
	
	/**
	 * Défini le numéro de ligne courante
	 * @param numLig numéro de la ligne à affecter
	 * */
	public void setNumLig(int index) {
		this.numLig = code.get(index).getNumLig();
		
	}
	
	/* ACCESSEURS */
	
	/**
	 * Retourne le numéro de la ligne en cours puis incrémente l'indice de 1
	 * @return un entier
	 * */
	/*public int getNumLigUtil(int i){
		iLigUtil = i;
		return numLig = numsLigsUtils.get(i);
	}*/
	
	
	/*public int getIligUtil(){
		return iLigUtil;
	}*/
	
	/**
	 * Retourne le nombre de ligne que contient le fichier pseudo-code
	 * @return le nombre de lignes utiles en entier
	 * */
	public int getNbLig(){
		return code.size();
	}
	
	/**
	 * Retourne la valeur de la ligne utile à l'indice i dans la liste
	 * de numéros de lignes utiles
	 * @param index indice dans la liste entier
	 * @return entier numéro de ligne utile à l'indice i
	 * */
	public int getNumLigUtilAt(int index){
		return numsLigsUtils.get(index);
	}

}
