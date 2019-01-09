package algopars.metier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

import java.util.List;

/**
 * @author Hugo Labbe, Titouan Cornilleau, Clement Baron, Sebastien Mande,Loan Cadorel 
 * @version 2019-01-07, 1.0
 * */

public final class Code {

	/*----------------------*/
	/* ATTRIBUTS D'INSTANCE */
	/*----------------------*/
	/**
	 * Tableau contenant l'integralite du fichier sans aucune modification,
	 * Sans suppressions d'espaces ou autre ...
	 * */
	public final String[] contenuFichier;

	/**
	 * Tableau associatif pour associer un objet Variable a son nom
	 * */
	public HashMap<String, Variable> variables;

	/**
	 * Tableau associatif pour associer un objet Constante a son nom
	 * */
	public HashMap<String, Constante> constantes;

	/**
	 * Contenu complet du code ligne par ligne ou donnees
	 * */
	protected List<LigneCode> code;
	
	/**
	 * Permet de connaitre les lignes de codes que l'on doit mettre
	 * en evidence dans la trace d'execution (sans les lignes blanches ...)
	 * */
	protected List<Integer> numsLigsUtils;
	
	
	/**
	 * Indice de la ligne du code ou l'on se trouve
	 * */
	private int numLig;
	

	/**
	 * Constructeur d'un Code a partir d'un fichier
	 * @param fichier nom du fichier en String           
	 */
	public Code(String fichier) {
		// initialisation des attributs
		this.variables 	= new HashMap<>();
		this.constantes = new HashMap<>();

		// initialisation de la List qui contiendra les lignes du code
		this.code = new ArrayList<>();
		this.numsLigsUtils = new LinkedList<>();
		

		LectureFichier.lire(fichier);
		this.contenuFichier = LectureFichier.lire(fichier).split("\n");

		interpretation();
		
		this.numLig = numsLigsUtils.get(0);
	}
	
	/**
	 * Lancer l'interpretation du code
	 * */
	private void interpretation() {
		int i = 2; // on démarre ligne 2 car les 2 premieres lignes sont la declaration
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
			while (!contenuFichier[i].equalsIgnoreCase("FIN")){
				if(!contenuFichier[i].matches("\\s+") && !contenuFichier[i].equalsIgnoreCase("")){
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
	 * Methode pour remplir la HashMap des variables
	 * */
	private void creerVariable(String ligne) {
		// on stock le nom des variables dans un Set
		// pour le cas ou il y a plusieurs variables sur une ligne
		Set<String> nomsVars = new HashSet<>();

		// pour stocker le type de la variable
		String type = new String();

		// Scanner pour traiter la ligne en parametre
		Scanner scLig = new Scanner(ligne);
		scLig.useDelimiter(":");

		// on gere le cas de plusieurs variables sur la meme ligne
		// separes par des virgules
		if (ligne.contains(",")) {
			// deuxieme scanner pour les variables sur la meme ligne
			Scanner sc = new Scanner(scLig.next());
			sc.useDelimiter(",");

			while (sc.hasNext())
				nomsVars.add(sc.next().replaceAll("\\s+", ""));
			
			sc.close();
		} else
			nomsVars.add(scLig.next().replaceAll("\\s+", ""));

		// on récupere le type de la variable
		type = scLig.next().replaceAll("\\s+", "");

		// on ajoute la variable (sans valeur) à la HashMap
		for (String nomVar : nomsVars)
			variables.put(nomVar, new Variable(TypeVariable.trouverType(type)));
		
		scLig.close();
	}

	/**
	 * Methode pour remplir la HashMap des constantes
	 * */
	private void creerConstantes(String ligne) {
		// on stock le nom des constantes dans un Set
		// pour le cas ou il y a plusieurs constantes sur une ligne
		Set<String> nomsConsts = new HashSet<>();

		// pour stocker le type de la constante
		String type = new String();

		// Scanner pour traiter la ligne en parametre
		Scanner scLig = new Scanner(ligne);
		scLig.useDelimiter(":");

		// on gere le cas de plusieurs constantes sur la meme ligne
		// sépares par des virgules
		if (ligne.contains(",")) {
			// deuxieme scanner pour les constantes sur la meme ligne
			Scanner sc = new Scanner(scLig.next());
			sc.useDelimiter(",");

			while (sc.hasNext())
				nomsConsts.add(sc.next());
			
			sc.close();
		} else
			nomsConsts.add(scLig.next());

		// on recupere le type de la constante
		type = scLig.next();

		// on ajoute la constante (sans valeur) a la HashMap
		for (String nomCons : nomsConsts)
			constantes.put(nomCons, new Constante(TypeVariable.trouverType(type)));
		
		scLig.close();
	}
	
	/**
	 * remettre les variables a pas de valeur
	 * */
	public void reinitVars(){
		Set<String> clefs = variables.keySet();
		
		for(String clef: clefs)
			variables.get(clef).setValeur(null);
	}
	
	/**
	 * Permet de modifier si oui ou non on trace une variable
	 * @param nomVar nom de la variable dont on doit changer l'option de tracage
	 * @param vrai pour tracer la variable nomVar
	 * @return vrai si la variable a ete trouvee
	 * */
	public boolean tracerVariable(String nomVar, boolean aTracer){
		Variable var = variables.get(nomVar);
		
		if(var == null) return false;
		
		var.setTracage(aTracer);
		return true;
	}
	
	/*---------------*/
	/* MODIFICATEURS */
	/*---------------*/
	/**
	 * Permet d'incrementer le numero de ligne courante
	 * pour le surlignage
	 * */
	public void incNumLig(){
		numLig++;
	}
	
	/**
	 * Defini le numero de ligne courante
	 * @param numLig numero de la ligne a affecter
	 * */
	public void setNumLig(int index) {
		this.numLig = code.get(index).getNumLig();
		
	}
	/*------------*/
	/* ACCESSEURS */
	/*------------*/
	
	
	/**
	 * Retourne le nombre de lignes que contient le fichier pseudo-code
	 * @return le nombre de lignes utiles en entier
	 * */
	public int getNbLig(){
		return code.size();
	}
	
	/**
	 * Retourne le numero de la ligne en cours
	 * @return ligne en cours en entier
	 * */
	public int getNumLig(){
		return numLig;
	}
	/**
	 * Retourne la liste de code
	 * @return Liste
	 * */
	public List<LigneCode> getCode(){
		return code ;
	}
	
	/**
	 * Retourne la valeur de la ligne utile a l'indice i dans la liste
	 * de numeros de lignes utiles
	 * @param index indice dans la liste entier
	 * @return entier numero de ligne utile a l'indice i
	 * */
	public int getNumLigUtilAt(int index){
		return numsLigsUtils.get(index);
	}

}