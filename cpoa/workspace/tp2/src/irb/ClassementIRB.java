package irb;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class ClassementIRB {

	private ArrayList<String> alS	;
	private ArrayList<Pays>   alPays;
	
	public ClassementIRB(String nomFich)
	{
		this.alS 	= new ArrayList<String>();
		this.alPays = new ArrayList<Pays>  ();
		
		// Remplit la liste de String avec les lignes du fichier
		InputStream ips = this.getClass().getResourceAsStream("/" +nomFich);
		InputStreamReader ipsr = new InputStreamReader(ips);
		Scanner    sc = new Scanner(ipsr);
		
		while(sc.hasNextLine()) this.alS.add(sc.nextLine());
		sc.close();
		
		// Trie les données et remplit la liste de Pays
		String[] fields;
		Scanner scNbr;
		
		// Variables qui seront passées en paramètre pour la création d'un Pays
		String nomPays		  ;
		String anneeClassement;
		String codePays		  ;
		
		int    rang	  ;
		double nbPoint;
		
		anneeClassement = nomFich.substring(1, 5);
		
		for(String line: this.alS)
		{
			fields = line.split(",");
			
			// on garde le numéro en enlevant celui entre parenthèses
			scNbr = new Scanner(fields[0]);
			scNbr.useDelimiter("\\(");
			rang = scNbr.nextInt();
			
			codePays = fields[1].substring(0, 3);
			nomPays  = fields[1].substring(3, fields[1].length());
			nbPoint  = Double.parseDouble(fields[2]);
			
			this.alPays.add(new Pays(nomPays, anneeClassement, codePays, rang, nbPoint));
		}
	}
	
	/* ACCESSEURS */
	public int nbPays(){ return this.alS.size(); }
	
	public Pays getPaysByCode(String code)
	{
		for(Pays pays: this.alPays)
			if(pays.getCodePays().equals(code)) return pays;
		
		return null;
	}
	
	public HashSet <String> getEnsembleCodePays()
	{
		HashSet<String> hsRet = new HashSet<String>();
		
		for(Pays pays: this.alPays) hsRet.add(pays.getCodePays());
		return hsRet;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		for(Pays pays: this.alPays) sb.append(pays.toString() +"\n");
		
		return sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ClassementIRB irb2007 = new ClassementIRB("i2007.txt");
		ClassementIRB irb2008 = new ClassementIRB("i2008.txt");
		ClassementIRB irb2009 = new ClassementIRB("i2009.txt");
		ClassementIRB irb2010 = new ClassementIRB("i2010.txt");
		ClassementIRB irb2011 = new ClassementIRB("i2011.txt");
		
		System.out.println(irb2007.getPaysByCode("LUX"));
		System.out.println(irb2007.getEnsembleCodePays());
		
		/*System.out.println("2007 " + irb2007);
		System.out.println("2008 " + irb2008);
		System.out.println("2009 " + irb2009);
		System.out.println("2010 " + irb2010);
		System.out.println("2011 " + irb2011);
		
		System.out.println("nb pays 2007 " + irb2007.nbPays());
		System.out.println("nb pays 2008 " + irb2008.nbPays());
		System.out.println("nb pays 2009 " + irb2009.nbPays());
		System.out.println("nb pays 2010 " + irb2010.nbPays());
		System.out.println("nb pays 2011 " + irb2011.nbPays());*/
		

	}

}
