package algopars.metier;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import algopars.Controleur;
import bsh.Interpreter;

/**
 * @author Hugo Labbe, Titouan Cornilleau, Clement Baron, Sebastien Mande,Loan Cadorel 
 * @version 2018-01-07, 1.0
 * */

public class Interpreteur {
	
	/*----------------------*/
	/* ATTRIBUTS D'INSTANCE */
	/*----------------------*/
	
	/**
	 * Instance de Code sur laquelle l'interpreteur doit opere
	 * */
	private Code code;
	
	/**
	 * Interpreteur bsh qui va nous permettre de traduire certains bouts de code en java
	 * et de laisser cet interpreteur les gerer
	 * */
	private Interpreter inter;
	
	/**
	 * Indice de la ligne ou on en est dans le code
	 * Prend les valeurs de 0 a nbLigne-1
	 * */
	private int index;
	
	/**
	 * Compteur d'index 
	 * */
	private int cptIndex ;
	
	/**
	 * Permet de savoir si la ligne est une expression boleenne et si c'est le cas
	 * sa valeur
	 * */
	private Boolean etatLigne;
	
	/**
	 * Acces au controleur
	 * */
	private Controleur ctrl;
	
	/**
	 * Empile les conditions des Tant-que
	 * */
	private Stack<Integer> conditionsTq;
	
	/**
	 * File de stockage de la trace d'execution
	 * */
	private List<String> trace;
	
	/*--------------*/
	/* CONSTRUCTEUR */
	/*--------------*/
	/**
	 * Constructeur de l'interpreteur
	 * @param code un Code
	 * */
	public Interpreteur(Code code, Controleur ctrl){
		this.code = code;
		
		this.ctrl = ctrl;
		
		this.inter = new Interpreter();
		this.inter.setStrictJava(true);
		
		this.index 	  = 0;
		this.cptIndex = 0;
		
		this.etatLigne = null;
		
		this.conditionsTq = new Stack<>();
		
		this.trace = new ArrayList<>();
	}
	
	/*----------*/
	/* METHODES */
	/*----------*/
	
	/**
	 * Methode qui lit l'integralite du code hors donnees
	 * */
	public void lireCode(){
		
		while(index<code.code.size()){
			faireLigne();
			index++;
			cptIndex++;
		}
	}
	
	/**
	 * Lance le programme jusqu'a la ligne saisie en parametre
	 * Si le numero de la ligne est inferieur à la ligne en cours l'execution
	 * est relancee jusqu'a cette ligne
	 * @param position String qui debute par l suivi du numero de la ligne
	 * @return vrai si le programme a pu atteindre la ligne saisie
	 * */
	public boolean allerLigne(String position){
		position = position.replaceAll("l", "");
		
		int numLig = 0;
		
		try{
			numLig = Integer.parseInt(position);
		}
		catch(NumberFormatException evt){
			erreur();
		}
		
		// on ne peux pas aller avant la premiere ligne du block DEBU
		if(numLig < code.code.get(0).getNumLig() || !code.numsLigsUtils.contains(numLig)){
			ctrl.afficher("Numéro de ligne invalide");
			
			return false;
		}
		
		index    = 0 ;
		cptIndex = 0 ;
		
		while(numLig > code.code.get(index).getNumLig()){
			faireLigne();
			index++ ;
			cptIndex++ ;
			
		}
		
		return true;
	}
	
	/**
	 * Permet de revenir en arriere dans le code
	 * */
	public void retourArriere(){
			if(index > 0 ){
				index = 0 ;
				
				cptIndex -=  2;
				System.out.println(cptIndex);
					
				for(int i = 0 ; i < cptIndex ; i ++){	
					faireLigne();
					index++;	
				}
			}
	}
	
	/**
	 * Methode lancee a chaque ligne du code du pseudo-code
	 * */
	public void faireLigne(){
		etatLigne = null;
		
		List<LigneCode> lignesCode = code.code;
		String ligne = lignesCode.get(index).getContenu();
		ligne = ligne.trim();
				
		if	   (ligne.contains("ecrire") && contientFonction(ligne)) ecrire(ligne, false);
		else if(ligne.contains("ecrire")) ecrire(ligne);
		else if(ligne.startsWith("lire") && ligne.endsWith(")")) lire(ligne);
		else if(ligne.contains("<--"   )) affecter(ligne);
		else if(ligne.endsWith("++"	   )) incrementerVariable();
		
		else if(ligne.substring(0,2).startsWith("si"   ) && ligne.endsWith("alors")) si();
		else if(ligne.startsWith("sinon")) passerSinon();
		else if(ligne.substring(0,2).startsWith("tq") && ligne.endsWith("faire")) tantQue();
		
		else if(ligne.startsWith("ftq")) ftq();
		else if(ligne.startsWith("fsi"));
		else if(ligne.contains("plancher"))plancher();
		else if(ligne.contains("plafond"))plafond();
		else if(ligne.contains("arrondi"))arrondi();
		else if(ligne.contains("hasard"))hasard();
		else if(ligne.contains("aujourd'hui"))aujourdhui();
		else erreur();
	}
	
	/**
	 * Sert pour ecrire le resultat d'une fonction
	 * Defini si le ecrire contient une fonction
	 * */
	private boolean contientFonction(String ligne){
		return ligne.contains("plafond") || ligne.contains("plancher" ) 
										 || ligne.contains("arrondi") 
										 || ligne.contains("hasard") 
										 || ligne.contains("aujourd'hui");
	}
	
	/**
	 * Fonction affecter du pseudo-code pour affecter une valeur a une variable
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
	 * Fonction affecter du pseudo-code pour affecter une valeur a une variable
	 * @param ligneAffectation ligne qui contient l'affectation en String avec symbole d'affectation
	 * @return false si la variable est introuvable
	 * */
	public boolean affecter(String ligneAffectation){
		Scanner scLig = new Scanner(ligneAffectation);
		scLig.useDelimiter("<--");
		
		// nom de la variable ou on doit faire l'affectation
		String nomVar;
		
		// valeur a affecter: peut être une valeur unique ou une expression
		String valeur;
		
		// elements de la valeur a affecter en cas d'expression composee
		
		nomVar = scLig.next().replaceFirst("\\s+", "");
		nomVar = nomVar.replaceAll("\\s$", "");
		
		
		valeur = scLig.next().replaceFirst("\\s+", "");
		valeur = valeur.replaceAll("\\s$", "");
		
		scLig.close();
		
		return affecter(nomVar, valeur);
		
	}
	
	/**
	 * Fonction pour incrementer une variable entiere avec ++
	 * */
	void incrementerVariable(){
		String ligne = code.code.get(index).getContenu();
		ligne = ligne.replaceAll("\\s+", "");
		
		Scanner scLig = new Scanner(ligne);
		scLig.useDelimiter("\\+");
		
		Variable var = code.variables.get(scLig.next());
		
		scLig.close();
		
		// si on tente d'incrementer une variable qui n'est pas un nombre
		// on signal la ligne de l'erreur et on arrete le programme
		if(var.getType() != TypeVariable.ENTIER && var.getType() != TypeVariable.REEL){
			System.err.println("Erreur ligne " +index);
			System.exit(1);
		}
		
		int val = Integer.parseInt(var.getValeur());
		val++;
		
		var.setValeur(Integer.toString(val));
		
	}
	
	/**
	 * Fonction pour concatener une chaine a une autre
	 * @param nomVar nom de la variable à concatener (type CHAINE)
	 * @param aConcat chaine à concatener a la variable en premier parametre
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
	 * Fonction Plancher du pseudo code
	 * */
	public int plancher(){
		String pl = code.code.get(index).getContenu();
		pl.trim();
		pl = pl.replaceAll("plancher", "");
		pl = pl.replaceAll("\\(", "");
		pl = pl.replaceAll("\\)", "");
		pl = pl.trim();
		pl = pl.trim();
		if (code.variables.containsKey(pl)) 
		{
			Variable var = code.variables.get(pl);
			
			if(var.getType() == TypeVariable.REEL)
				var.setValeur((int) Double.parseDouble(var.getValeur())+"");
			else 
				erreur();
			return  (int)Double.parseDouble(var.getValeur());
		}
		return (int)Double.parseDouble(pl);
	}
	/**
	 * Fonction plancher du pseudo code
	 * @param String chaine
	 */
	public int plancher(String chaine){

		chaine.trim();
		chaine = chaine.replaceAll("plancher", "");
		chaine = chaine.trim();
		if (code.variables.containsKey(chaine)) 
		{
			Variable var = code.variables.get(chaine);
			
			if(var.getType() == TypeVariable.REEL)
				var.setValeur((int) Double.parseDouble(var.getValeur())+"");
			else 
				erreur();
			return  (int)Double.parseDouble(var.getValeur());
		}
		return (int)Double.parseDouble(chaine);

		
	}
	
	/**
	 * Fonction plafond du pseudo code
	 */
	public int plafond(){
		String pl = code.code.get(index).getContenu();
		pl.trim();
		pl = pl.replaceAll("plafond", "");
		pl = pl.replaceAll("\\(", "");
		pl = pl.replaceAll("\\)", "");
		pl = pl.trim();
		if (code.variables.containsKey(pl)) 
		{
			Variable var = code.variables.get(pl);
			
			if(var.getType() == TypeVariable.REEL)
				var.setValeur( 1+(int)Double.parseDouble(var.getValeur())+"");
			else 
				erreur();
			return 1+ (int)Double.parseDouble(var.getValeur());
		}
		return 1+(int)Double.parseDouble(pl);
		
	}
	/**
	 * Fonction plafond du pseudo code
	 * @param String chaine
	 */
	public int plafond(String chaine){
		
		chaine = chaine.replaceAll("plafond", "");
		chaine.trim();

		if (code.variables.containsKey(chaine)) 
		{
			Variable var = code.variables.get(chaine);
			
			if(var.getType() == TypeVariable.REEL)
				var.setValeur( 1+(int)Double.parseDouble(var.getValeur())+"");
			else 
				erreur();
				
			return 1+ (int)Double.parseDouble(var.getValeur());
			
		}
		return 1+(int) Double.parseDouble(chaine);
		
	}
	/**
	 * Fonction arrondi du pseudo code
	 * */
	public int arrondi(){
		String pl = code.code.get(index).getContenu();
		pl.trim();
		pl = pl.replaceAll("arrondi", "");
		pl = pl.replaceAll("\\(", "");
		pl = pl.replaceAll("\\)", "");
		pl = pl.trim();
		if (code.variables.containsKey(pl)) 
		{
			Variable var = code.variables.get(pl);
			
			if(var.getType() == TypeVariable.REEL)
				var.setValeur((int) Double.parseDouble(var.getValeur())+"");
			else 
				erreur();
			return (int) Math.round(Double.parseDouble(var.getValeur()));
		}
		return (int) Math.round(Double.parseDouble(pl));
		
		
		
	}
	/**
	 * Fonction arrondi du pseudo code
	 * */
	public int arrondi(String arr){
		arr.trim();
		arr = arr.replaceAll("arrondi", "");
		arr = arr.trim();
		if (code.variables.containsKey(arr)) 
		{
			Variable var = code.variables.get(arr);
			
			if(var.getType() == TypeVariable.REEL)
				var.setValeur((int) Math.round(Double.parseDouble(var.getValeur()))+"");
			else 
				erreur();
			return (int) Math.round(Double.parseDouble(var.getValeur()));
		}
		return (int) Math.round(Double.parseDouble(arr));
	}
	/**
	 * Fonction hasard du pseudo code
	 * */
	public int hasard(){
		String pl = code.code.get(index).getContenu() ;
		int num ;
		
		pl = pl.replaceAll("hasard", "");
		pl = pl.replaceAll("\\(", "");
		pl = pl.replaceAll("\\)", "");
		num = Integer.parseInt(pl) ;
		
		return (int) (Math.random()*num) ;
		
	}
	/**
	 * Fonction hasard du pseudo code
	 * */
	public int hasard(String has){	
		
		has = has.replaceAll("hasard", "");	
		has = has.trim();
		return(int)  (Math.random() * Integer.parseInt(has) );
		
	}
	/**
	 * Fonction aujourd'hui du pseudo code
	 * */
	public String aujourdhui(){

		return (new java.util.Date().toString());
	}	
	/**
	 * Fonction aujourd'hui du pseudo code
	 * */
	public String aujourdhui(String auj){

		return (new java.util.Date().toString());
	}
	
	/**
	 * Fonction si du pseudo-code
	 * */
	public void si(){
		// on stock la ligne de la condition
		String condition = code.code.get(index).getContenu();
		
		
		if(condition.substring(0,2).equalsIgnoreCase("si"))
			condition = condition.substring(2, condition.length());
		
		// puis on ne garde que la condition et on la traite
		// on enlève les mots si et alors aux bouts des lignes
		condition = condition.replaceAll("si ", "");
		condition = condition.replaceAll("alors", "");
		condition = condition.trim();
		
		// on remplace les mot conditionnels du pseudo-code
		// par des expressions java pour utiliser l'interpreteur bsh
		condition = condition.replaceAll("ET", "&&");
		condition = condition.replaceAll("OU", "||");
		condition = condition.replaceAll("XOU", "|");
		condition = condition.replaceAll("=", "==");
		
		condition = condition.replaceAll("vrai", "true");
		condition = condition.replaceAll("faux", "false");
		
		boolean expression = expressionBooleenne(condition);
		
		int nbSi = 1;
		if(!expression){
			do{
				index++;
				
				if(code.code.get(index).getContenu().startsWith("si") && code.code.get(index).getContenu().endsWith("alors")){
					nbSi++;
				}
				if(code.code.get(index).getContenu().startsWith("fsi")){
					nbSi--;
				}
				
				if(nbSi == 1 && code.code.get(index).getContenu().startsWith("sinon")){
					nbSi = 0;
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

		
		// on enleve les mots tq et faire aux bouts des lignes
		condition = condition.replaceAll("tq","" );
		condition = condition.replaceAll("faire", "");
		
		// on remplace les mot conditionnels du pseudo-code
		// par des expressions java pour utiliser l'interpreteur bsh
		condition = condition.replaceAll("ET", "&&");
		condition = condition.replaceAll("OU", "||");
		condition = condition.replaceAll("XOU", "|");
		condition = condition.replaceAll("=", "==");
		
		condition = condition.replaceAll("vrai", "true");
		condition = condition.replaceAll("faux", "false");
		
		
		boolean expression = expressionBooleenne(condition);
		
		if (expression){
			conditionsTq.push(index);
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
	 * Fonction qui verifie que la condition d'un tant-que est toujours vraie 
	 * */	
	public void ftq(){
		index = conditionsTq.pop() - 1;
	}
	
	/*-----------------*/
	/* METHODES PRIVEES*/
	/*-----------------*/
	
	/**
	 * Fonction lancee en cas de ligne illisible par l'interpreteur
	 * Affiche la ligne de l'erreur et ferme le programme avec le code 
	 * de status 1
	 * */
	private void erreur(){
		System.err.println("Erreur: code illisible ligne " +
						    code.code.get(index).getNumLig()
						  );
		System.exit(1);
	}
	
	
	
	/**
	 * Fonction permettant de passer des sinon si la condition d'un si est vérifiee
	 * */
	private void passerSinon(){	
		while(!code.code.get(index).getContenu().startsWith("fsi"))
			index++;
	}
	
	
	/**
	 * Traitement avant affichage d'une chaine dans la fonction ecrire
	 * */
	private String traiterEcrire(String texte){
		
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
		ligne = ligne.replaceAll("\\s$", "");
		ligne = ligne.replaceAll("\\)", "");
		ligne = ligne.replaceAll("\\\"", "");
		
		scLig.close();
		
		return ligne;
	}
	
	/**
	 * Fonction ecrire du pseudo-code pour ecrire la valeur d'une variable
	 * */
	public void ecrire(Variable var){
		trace.add(var.getValeur());
	}
	
	/**
	 * Fonction ecrire du pseudo-code pour afficher une chaine en parametre
	 * */
	public void ecrire(String texte){
		if(texte.contains("\"")){
			trace.add(traiterEcrire(texte));
		}
		else ecrire(code.variables.get(traiterEcrire(texte)));
	}
	
	/**
	 * Fonction pour ecrire le resultat d'une fonction primitive
	 * @param textFct texte de la fonction
	 * @param b ne sert qu'à differencier cette fonction ecrire des celle
	 * pour ecrire un texte
	 * */
	public void ecrire(String texteFct, boolean b){
		texteFct = texteFct.replaceAll("ecrire", "");
		texteFct = texteFct.replaceAll("\\(", "");
		texteFct = texteFct.replaceAll("\\)", "");
		
		
		if(texteFct.contains("plafond")) texteFct = plafond(texteFct) +"";
		else if(texteFct.contains("plancher")) texteFct = plancher(texteFct) +"";
		else if(texteFct.contains("hasard")) texteFct = hasard(texteFct) +"";
		else if(texteFct.contains("arrondi")) texteFct = arrondi(texteFct) +"";
		else if(texteFct.contains("aujourd'hui")) texteFct = aujourdhui(texteFct) +"";
		// . . .
		
		trace.add(texteFct);
	}
	
	/**
	 * Fonction lire du pseudo-code
	 * */
	public void lire(String ligne){
		ligne = ligne.replaceAll("lire", "");
		ligne = ligne.replaceAll("\\(" , "");
		ligne = ligne.replaceAll("\\)" , "");
		
		Variable var = code.variables.get(ligne);
		
		ctrl.afficher(ctrl.afficherPseudoCode());
		ctrl.afficher(ctrl.afficherDonnees()   );
		ctrl.afficherTrace();
		
		if(var == null) erreur();
		
		if(var.getType() == TypeVariable.CHAINE) var.setValeur(ctrl.lireClavier());
		if(var.getType() == TypeVariable.ENTIER) var.setValeur("" +ctrl.lireEntier ());
		if(var.getType() == TypeVariable.REEL  ) var.setValeur("" +ctrl.lireReel   ());
		
		ctrl.netoyer();
	}
	
	/**
	 * Evalue une expression booleenne et retourne sa valeur
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
	 * Decremente le compteur d'Index
	 * */
	public void decreCptIndex(){
		index--;
	}
	
	/**
	 * Incremente le compteur d'index
	 * */
	public void incCptIndex(){
		cptIndex++;
	}
	
	/**
	 * Incremente de 1 l'index
	 * */
	public void incIndex(){
		index++;
	}
	
	/*------------*/
	/* ACCESSEURS */
	/*------------*/
	/**
	 * Retourne l'index de la ligne de code courante
	 * @return un int
	 * */
	public int getIndex(){
		return index;
	}
	
	/**
	 * Retourne le compteur d'index
	 * @return un int 
	 * */
	public int getCptIndex(){
		return cptIndex;
	}
	
	/**
	 * Retourne l'etat de la ligne
	 * @return true, false ou null si la ligne n'est pas une expression booleenne
	 * */
	public Boolean getEtatLigne(){
		return etatLigne;
	}
	
	/**
	 * Retourne la trace d'execution
	 * @return une List de String
	 * */
	public List<String> getTrace(){
		return trace;
	}
}