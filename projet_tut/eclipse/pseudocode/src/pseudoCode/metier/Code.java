package pseudoCode.metier;

import pseudoCode.metier.vars.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import pseudoCode.metier.vars.Variable;

public class Code
{
	private HashMap<String, Variable> variables;
	private List<String> lignesCode;
	
	
	
	public Code(HashMap<String, Variable> variables, List<String> lignesCode) {
		super();
		
		this.variables = variables;
		this.lignesCode = lignesCode;
	}
	
	public void interpreter()
	{
		List<String> morceauCode = new ArrayList<>();
		String code = new String();
		
		for(String ligne: this.lignesCode)
		{
			code += ligne +"\n";
		}
		
		//System.out.println(code);
		Scanner sc = new Scanner(code);
		String ligneCourante = new String();
		
		while(sc.hasNextLine())
		{
			ligneCourante = sc.nextLine();
			
			if(ligneCourante.substring(0, 2).equals("si"))
			{
				morceauCode.add(ligneCourante);
				
				while(!ligneCourante.equals("fsi"))
				{
					ligneCourante = sc.nextLine();
					morceauCode.add(ligneCourante);
				}
				this.si(morceauCode);
			}
			
			else if(ligneCourante.length() > 8 && ligneCourante.substring(0, 8).equals("afficher"))
			{
				String nomVar = ligneCourante.substring(9, ligneCourante.length()-1);
				//System.out.println(nomVar);
				
				this.afficher(this.getVariableByName(nomVar));
			}
			
		}
	}
	
	private Variable getVariableByName(String nom)
	{
		return this.variables.get(nom);
	}
	
	private void si(List<String> codeAtraite)
	{
		//System.out.println(codeAtraite);
		StringBuilder condition = new StringBuilder(codeAtraite.get(0).substring(3, codeAtraite.get(0).length()));
		
		
		/*Scanner sc = new Scanner(condition);
		String temp = new String();
		
		while(sc.hasNext())
		{
			
		}*/
	}
	
	public void afficher(Variable var)
	{
		System.out.println(var.getValeur());
	}



	public static void main(String[] args) {
		List<Variable> lv = new ArrayList<>();
		List<String> lc = new ArrayList<>();
		
		//lv.add(new Variable(TypeVarible.ENTIER, "i1","4"));
		//lv.add(new Variable(TypeVarible.CHAINE, "chTest","j'aime bien ce chiffre"));
		
		lc.add("DEBUT");
		
		lc.add("si i1 = 4 alors");
		
		lc.add("afficher(i1)");
		lc.add("i1 <- 3");
		
		lc.add("fsi");
		
		lc.add("afficher(i1)");
		lc.add("afficher(chTest)");
		
		lc.add("FIN");
		
		Tableau tab = new Tableau(TypeVariable.CARACTERE, 3);
		
		tab.ajouterElt(new Variable(TypeVariable.CARACTERE, "a"));
		tab.ajouterElt(new Variable(TypeVariable.CARACTERE, "b"));
		
		System.out.println(tab);
		ArrayList<String> al = new ArrayList<>();
		System.out.println(al);
		
		//Code c = new Code(lv, lc);
		//c.interpreter();
		
		//System.out.println(lc);

	}

}
