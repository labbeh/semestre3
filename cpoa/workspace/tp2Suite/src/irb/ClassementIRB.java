package irb;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class ClassementIRB {

	private ArrayList<String> alS	;
	private ArrayList<Pays>   alPays;
	
	/**
	 * constructeur pour irbmoy78910
	 * */
	public ClassementIRB(ClassementIRB classementIRB, String anneeClassement)
	{
		this.alPays = new ArrayList<>();
		// on utilise le constructeur par recopie pour que l'alPays du classement courant et celle du classement
		// en paramètre ne pointe pas sur les mêmes objets pays (problème constétés après tests)
		for(Pays p: classementIRB.alPays) this.alPays.add( new Pays(p) );
		
		for(Pays p: this.alPays)
		{
			p.setAnneeClassement(anneeClassement);
			p.setRang(0);
		}
		
		// on crééer des instance de classement des autres années
		ClassementIRB irb2008 = new ClassementIRB("i2008.txt");
		ClassementIRB irb2009 = new ClassementIRB("i2009.txt");
		ClassementIRB irb2010 = new ClassementIRB("i2010.txt");
		
		// on calcul la moyenne sur les 4 ans
		for(Pays p: this.alPays)
		{
			String codePays = p.getCodePays();
			
			double nbPts2007 = classementIRB.getPaysByCode(codePays).getNbPoint();
			double nbPts2008 = irb2008.getPaysByCode(codePays).getNbPoint();
			double nbPts2009 = irb2009.getPaysByCode(codePays).getNbPoint();
			double nbPts2010 = irb2010.getPaysByCode(codePays).getNbPoint();
			
			double moy = (nbPts2007 + nbPts2008 + nbPts2009 + nbPts2010) / 4;
			
			p.setNbPoint(moy);
		}
		
		// on tire la liste et màj du rang
		this.majRang();
		
		// on affecte le nouveau rang à chaque pays
		/*for(int cpt=0; cpt<this.alPays.size(); cpt++)
			this.alPays.get(cpt).setRang(cpt+1);*/
	}
	
	/**
	 * constructeur pour crééer un ClassementIRB à partir d'un fichier
	 * */
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
	
	/**
	 * tire la liste de pays en fonction des points et met à jours le rang
	 * */
	public void majRang()
	{
		Collections.sort(this.alPays);
		
		// on affecte le nouveau rang à chaque pays
		for(int cpt=0; cpt<this.alPays.size(); cpt++)
			this.alPays.get(cpt).setRang(cpt+1);
	}
	
	/* ACCESSEURS */
	public int nbPays(){ return this.alPays.size(); }
	
	/**
	 * @param code d'un pays
	 * @return le pays dont le code est passé en paramètre, null si le paramètre ne correspond à aucun code pays
	 * */
	public Pays getPaysByCode(String code)
	{
		for(Pays pays: this.alPays)
			if(pays.getCodePays().equals(code)) return pays;
		
		return null;
	}
	
	/**
	 * @return HashSet contenant l'ensemble des codes pays des pays présents dans ce classement
	 * */
	public HashSet <String> getEnsembleCodePays()
	{
		HashSet<String> hsRet = new HashSet<String>();
		
		for(Pays pays: this.alPays) hsRet.add(pays.getCodePays());
		return hsRet;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		for(Pays pays: this.alPays) sb.append(pays.toString() +"\n");
		
		return sb.toString();
	}

}
