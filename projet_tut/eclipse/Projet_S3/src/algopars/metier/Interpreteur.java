package algopars.metier;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

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
	 * Permet de savoir si la ligne est une expression boléenne et si c'est le cas
	 * sa valeur
	 * */
	private Boolean etatLigne;
	
	/**
	 * Accès au controleur
	 * */
	private Controleur ctrl;
	
	/**
	 * Empile les conditions des Tant-que
	 * */
	private Stack<Integer> conditionsTq ;
	
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
		
		this.etatLigne = null;
		
		this.conditionsTq = new Stack<>();
		
		//this.nbSi = 1;
		//System.out.println(code.code);
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
		etatLigne = null;
		
		List<LigneCode> lignesCode = code.code;
		String ligne = lignesCode.get(index).getContenu();
		ligne = ligne.trim();
		
		//System.out.println("LIGNE: " +ligne);
		
		if	   (ligne.contains("ecrire")) ecrire(ligne);
		else if(ligne.contains("<--"   )) affecter(ligne);
		else if(ligne.endsWith("++"	   )) incrementerVariable();
		else if(ligne.substring(0,2).startsWith("si"   ) && ligne.endsWith("alors")) si();
		else if(ligne.startsWith("sinon")) passerSinon();
		else if(ligne.substring(0,2).startsWith("tq") && ligne.endsWith("faire")) tantQue();
		else if(ligne.startsWith("ftq")) ftq();
		else if(ligne.startsWith("fsi"));
		
		else{
			System.err.println("Erreur: code illisible ligne " +code.code.get(index).getNumLig());
			System.exit(1);
		}
		
		//System.out.println(code.variables);
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
		
		// nom de la variable ou on doit faire l'affectation
		String nomVar;
		
		// valeur à affecter: peut être une valeur unique ou une expression
		String valeur;
		
		// éléments de la valeur à affecter en cas d'expression composée
		//String[] eltsValeur;
		
		nomVar = scLig.next().replaceFirst("\\s+", "");
		nomVar = nomVar.replaceAll("\\s$", "");
		
		//eltsValeur = valeur.split("")
		
		//if(eltsValeur.length == 0){
		valeur = scLig.next().replaceFirst("\\s+", "");
		valeur = valeur.replaceAll("\\s$", "");
		//}
		//if(valeur.endsWith("++")) valeur = incrementerVariable();
		
		scLig.close();
		
		return affecter(nomVar, valeur);
		
	}
	
	/**
	 * Fonction pour incrémenter une variable entière avec ++
	 * */
	void incrementerVariable(){
		String ligne = code.code.get(index).getContenu();
		ligne = ligne.replaceAll("\\s+", "");
		//System.out.println(ligne.replaceAll("\\s+", ""));
		
		Scanner scLig = new Scanner(ligne);
		scLig.useDelimiter("\\+");
		
		Variable var = code.variables.get(scLig.next());
		
		scLig.close();
		
		// si on tente d'incrémenter une variable qui n'est pas un nombre
		// on signal la ligne de l'érreur et on arrete le programme
		if(var.getType() != TypeVariable.ENTIER && var.getType() != TypeVariable.REEL){
			System.err.println("Erreur ligne " +index);
			System.exit(1);
		}
		
		int val = Integer.parseInt(var.getValeur());
		val++;
		
		var.setValeur(Integer.toString(val));
		//System.out.println("valeur : " +val);
		
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
		//System.out.println("rentre dans si");
		//nbSi++;
		//System.out.println("nbSi: " +nbSi);
		// on stock la ligne de la condition
		String condition = code.code.get(index).getContenu();
		//System.out.println("CONDITION SI: " +condition);
		//System.out.println(condition);
		
		
		if(condition.substring(0,2).equalsIgnoreCase("si"))
			condition = condition.substring(2, condition.length());
		//System.out.println("COND: " +condition);
		
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
		
		boolean expression = expressionBooleenne(condition);
		
		int nbSi = 1;
		if(!expression){
			//index++;
			do{
				index++;
				
				if(code.code.get(index).getContenu().startsWith("si") && code.code.get(index).getContenu().endsWith("alors")){
					//if(nbSi == -1) nbSi = 1;
					/*else*/		   nbSi++;
				}
				if(code.code.get(index).getContenu().startsWith("fsi")){
					//if(nbSi == -1) nbSi = 0;
					/*else*/ nbSi--;
					//index++;
				}
				
				if(nbSi == 1 && code.code.get(index).getContenu().startsWith("sinon")){
					nbSi = 0;
					//System.out.println("avec nbSi = " +nbSi+ " et sur la ligne + " +code.code.get(index).getContenu());
				}

			}
			while(nbSi > 0);
		}
	}
	
	/**
	 * Fonction Tant-que du pseudo-code
	 * */
	public void tantQue(){
		// on stock la ligne de la condition
		String condition = code.code.get(index).getContenu();
		//System.out.println(condition);
		

		
		// on enlève les mots tq et faire aux bouts des lignes
		condition = condition.replaceAll("tq","" );
		condition = condition.replaceAll("faire", "");
		
		// on remplace les mot conditionnels du pseudo-code
		// par des expressions java pour utiliser l'interpéteur bsh
		condition = condition.replaceAll("ET", "&&");
		condition = condition.replaceAll("OU", "||");
		condition = condition.replaceAll("XOU", "|");
		condition = condition.replaceAll("=", "==");
		
		condition = condition.replaceAll("vrai", "true");
		condition = condition.replaceAll("faux", "false");
		
		//System.out.println("CONDITION: " +condition);
		
		boolean expression = expressionBooleenne(condition);
		
		if (expression){
			conditionsTq.push(index);
			System.out.println("OK: " +code.code.get(index));
		}
		
		int nbTq = 1;
		if (!expression){
			do{
				
			index++;
			
			if(code.code.get(index).getContenu().startsWith("tq") && code.code.get(index).getContenu().endsWith("faire")){
				nbTq++;
			}
			if(code.code.get(index).getContenu().startsWith("ftq")){
				nbTq--;
				
			}
			
			}while(nbTq > 0);
			
		}
		
	}
	
	/**
	 * Fonction qui vérifie que la condition d'un tant-que est toujours vraie 
	 * */	
	public void ftq(){
	
		index = conditionsTq.pop() - 1;
		//System.out.println(code.code.get(index));
	
	}
	
	/**
	 * Fonction permettant de passer des sinon si la condition d'un si est vérifiée
	 * */
	private void passerSinon(){
		//System.out.println(condition);
		
		while(!code.code.get(index).getContenu().startsWith("fsi"))
			index++;

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
		sc.useDelimiter("&& | \\+ | == | < | > | <= | >=");
		
		while(sc.hasNext()) nomsVars += sc.next().replaceAll("\\s+", "") +" ";
		sc.close();
		
		// ... puis on enlève les éventuelles nombres
		nomsVars = nomsVars.replaceAll("\\d", "");
		
		// ... et les autres types de caractères
		nomsVars = nomsVars.replaceAll("\\'.\\'", "");
		
		
		// on parcours la chaine pour obtenir les variables conernées
		String[] tabVars = nomsVars.split("\\s+");
		
		//for(String s: tabVars) System.out.println("tabvar: " +s);
		
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
		
		this.etatLigne = bRet;
		
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
	
	/**
	 * Retourne l'état de la ligne
	 * @return true, false ou null si la ligne n'est pas une expression booléenne
	 * */
	public Boolean getEtatLigne(){
		return etatLigne;
	}
}