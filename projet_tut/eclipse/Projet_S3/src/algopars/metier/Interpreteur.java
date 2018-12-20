package algopars.metier;
import java.util.List;
import java.util.Scanner;

import algopars.Controleur;
import bsh.Interpreter;

public class Interpreteur {
	
	/* ATTRIBUTS */
	
	/**
	 * instance de Code sur laquelle l'interpéteur doit opéré
	 * */
	private Code code;
	
	/**
	 * Interpréteur bsh qui va nous permettre de traduire certains bout de code en java
	 * et de laisser cet interpréteur les gérer
	 * */
	private Interpreter inter;
	
	/**
	 * Indice de la ligne ou on en est dans le code
	 * Prend les valeurs de 0 à nbLigne-1
	 * */
	private int index;
	
	/**
	 * Nombre de "si" pour la gestion des "si" imbriqués
	 * */
	private int nbSi;
	
	/**
	 * Accès au controleur
	 * */
	private Controleur ctrl;
	
	/* CONSTRUCTEUR */
	
	/**
	 * Constructeur de l'interpréteur
	 * @param code un Code
	 * */
	public Interpreteur(Code code, Controleur ctrl){
		this.code = code;
		
		this.ctrl = ctrl;
		
		this.inter = new Interpreter();
		this.inter.setStrictJava(true);
		
		this.index = 0;
		
		this.nbSi = 0;
	}
	
	/* METHODES */
	
	/**
	 * Méthode qui lit l'intégralité du code hors données
	 * */
	public void lireCode(){
		
		while(index<code.code.size()){
			faireLigne();
			index++;
		}
	}
	
	public void faireLigne(){
		//System.out.println("INDEX: " +index);
		List<LigneCode> lignesCode = code.code;
		String ligne = lignesCode.get(index).getContenu();
		ligne = ligne.trim();
		
		if	   (ligne.contains("ecrire")) ecrire(ligne);
		else if(ligne.contains("<--"   )) affecter(ligne);
		else if(ligne.startsWith("si"   ) && ligne.endsWith("alors")) si();
	}
	
	/**
	 * Fonction affecter du pseudo-code pour affecter une valeur à une variable
	 * @param nomVar nom de la variable sur laquelle on veut affecter la valeur
	 * @param valeur la valeur à affecter
	 * @return false si la variable est introuvable
	 * */
	public boolean affecter(String nomVar, String valeur){
		if(code.variables.get(nomVar) != null){
			code.variables.get(nomVar).setValeur(valeur);
			return true;
		}
		return false;
	}
	
	/**
	 * Fonction affecter du pseudo-code pour affecter une valeur à une variable
	 * @param ligneAffectation ligne qui contient l'affectation en String avec symbole d'affectation
	 * @return false si la variable est introuvable
	 * */
	public boolean affecter(String ligneAffectation){
		Scanner scLig = new Scanner(ligneAffectation);
		scLig.useDelimiter("<--");
		
		String nomVar;
		String valeur;
		
		nomVar = scLig.next().replaceFirst("\\s+", "");
		nomVar = nomVar.replaceAll("\\s$", "");
		
		valeur = scLig.next().replaceFirst("\\s+", "");
		valeur = valeur.replaceAll("\\s$", "");
		
		scLig.close();
		
		return affecter(nomVar, valeur);
		
	}
	
	/**
	 * Fonction pour concaténer une chaine à une autre
	 * @param nomVar nom de la variable à concaténer (type CHAINE)
	 * @param aConcat chaine à concaténer à la variable en premier paramètre
	 * @return false si la variable est introuvable ou n'est pas de type CHAINE
	 * */
	public boolean concatener(String nomVar, String aConcat){
		Variable var = code.variables.get(nomVar);
		
		if(var != null){
			if(var.getType() == TypeVariable.CHAINE){
				var.setValeur(var.getValeur() + aConcat);
				return true;
			}	
		}
		return false;
	}
	
	/**
	 * Fonction si du pseudo-code
	 * */
	public void si(){
		int nbSi = 1;
		// on stock la ligne de la condition
		String condition = code.code.get(index).getContenu();
		
		// puis on ne garde que la condition et on la traite
		
		// on enlève les mots si et alors aux bouts des lignes
		condition = condition.replaceAll("si ", "");
		condition = condition.replaceAll("alors", "");
		condition = condition.trim();
		
		// on remplace les mot conditionnels du pseudo-code
		// par des expressions java pour utiliser l'interpéteur bsh
		condition = condition.replaceAll("ET", "&&");
		condition = condition.replaceAll("OU", "||");
		condition = condition.replaceAll("XOU", "|");
		condition = condition.replaceAll("=", "==");
		
		condition = condition.replaceAll("vrai", "true");
		condition = condition.replaceAll("faux", "false");

		if(expressionBooleenne(condition)){
			index++;
			
			while(!code.code.get(index).getContenu().equals("fsi") &&
				  !code.code.get(index).getContenu().equals("sinon")){
				faireLigne();
				index++;
			}
			
			while(!code.code.get(index).getContenu().equals("fsi"))
				index++;
		}
		else{
			index++;
			
			while(!code.code.get(index).getContenu().equals("sinon") && nbSi != 1 || nbSi > 0){
				
				if(code.code.get(index).getContenu().equals("si")) nbSi++;
				if(code.code.get(index).getContenu().equals("fsi")) nbSi--;
				index++;
			}
			
			if(code.code.get(index).equals("sinon")){
				while(!code.code.get(index).equals("fsi")){
					faireLigne();
					index++;
				}
				
			}
		}
		
		/*if(expressionBooleenne(condition)){
			while(!code.code.get(index).getContenu().equalsIgnoreCase("fsi") && index < code.code.size()-1){
				index++;
				
				if(code.code.get(index).getContenu().equalsIgnoreCase("sinon"))
					while(!code.code.get(index).getContenu().equalsIgnoreCase("fsi"))
						index++;
					
				faireLigne();
			}
		}
		else{
			
			do{
				index++;
				if(code.code.get(index).getContenu().equals("si" )) nbSi++;
				if(code.code.get(index).getContenu().equals("fsi")) nbSi--;
					
				
			}
			//while( nbSi > 0 );
			while( nbSi > 1 || !(code.code.get(index).getContenu().equals("sinon" )) );
			
			if(code.code.get(index).getContenu().equals("sinon")){
				
				index++;
				while(!code.code.get(index).getContenu().equals("fsi")){
					faireLigne();
					index++;
					
				}
			}
		}*/
	}
	
	/**
	 * Fonction écrire du pseudo-code pour écrire une chaine
	 * */
	private String traiterEcrire(String texte){
		// remplacer les sysout par des appls ihm
		
		// récupération du contenu à afficher entre les parenthèses
		// de la fonction écrire avec un Scanner
		Scanner scLig = new Scanner(texte);
		scLig.useDelimiter("\\(");
		
		// Chaine qui contiendra la ligne
		String ligne = new String();
		
		// on fait un premier sc.next() dans le vide pour passer
		// le mot "écrire"
		scLig.next();
		
		// on récupère la partie de la ligne à afficher...
		ligne = scLig.next();
		
		// ... et on la traite
		ligne = ligne.replaceFirst(" ", "");
		ligne = ligne.replaceAll("\\s$", "");
		ligne = ligne.replaceAll("\\)", "");
		ligne = ligne.replaceAll("\\\"", "");
		
		scLig.close();
		
		return ligne;
	}
	
	/**
	 * Fonction écrire du pseudo-code pour écrire la valeur d'une variable
	 * */
	public void ecrire(Variable var){
		ctrl.afficher(var.getValeur());
	}
	
	/**
	 * Fonction ecrire du pseudo-code pour afficher une chaine en paramètre
	 * */
	public void ecrire(String texte){
		if(texte.contains("\"")) ctrl.afficher(traiterEcrire(texte));
		else 					 ecrire(code.variables.get(traiterEcrire(texte)));
	}
	
	/**
	 * Fonction lire du pseudo-code
	 * */
	public void lire(){
		ctrl.lireClavier();
	}
	
	/**
	 * Evalue une expression booléenne et retourne sa valeur
	 * @param expression un String
	 * @return vrai ou faux
	 * */
	public boolean expressionBooleenne(String expression){
		Boolean bRet = false;
		
		// récupère le noms des variables en une ligne
		String nomsVars = new String();
		
		// pour récupérer le nom des variables concernées par l'expression, on enlève
		// tout les opérateurs logiques dans un premier temps...
		Scanner sc = new Scanner(expression);
		sc.useDelimiter("&& | \\|+ | == | < | > | <= | >=");
		
		while(sc.hasNext()) nomsVars += sc.next().replaceAll("\\s+", "") +" ";
		sc.close();
		
		// ... puis on enlève les éventuelles nombres
		nomsVars = nomsVars.replaceAll("\\d", "");
		
		// ... et les autres types de caractères
		nomsVars = nomsVars.replaceAll("\\'.\\'", "");
		
		// on parcours la chaine pour obtenir les variables conernées
		String[] tabVars = nomsVars.split("\\s+");
		
		// Début avec bsh
		try{
			Variable temp;
			// on rentre les variables dans l'interpreteur bsh
			for(int i=0; i<tabVars.length; i++){
				temp = code.variables.get(tabVars[i]);
				
				if(temp.getType() == TypeVariable.ENTIER)
					inter.eval("int " + tabVars[i] + " = " +temp.getValeur());
				
				else if(temp.getType() == TypeVariable.REEL)
					inter.eval("double " + tabVars[i] + " = " +temp.getValeur());
				
				else if(temp.getType() == TypeVariable.CARACTERE)
					inter.eval("char " + tabVars[i] + " = '" +temp.getValeur() +"'");
				
			}
			
			bRet = (Boolean)inter.eval(expression);
		}
		catch(Exception e){e.printStackTrace();}
		
		return bRet;
	}
	
	/**
	 * Incrémente de 1 l'index
	 * */
	public void incIndex(){
		index++;
	}
	
	/**
	 * Retourne l'index de la ligne de code courante
	 * @return un int
	 * */
	public int getIndex(){
		return index;
	}
}
