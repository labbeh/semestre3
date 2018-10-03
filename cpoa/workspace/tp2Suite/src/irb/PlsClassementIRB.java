package irb;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

public class PlsClassementIRB
{
	private ClassementIRB irb2007;
	private ClassementIRB irb2008;
	private ClassementIRB irb2009;
	private ClassementIRB irb2010;
	
	private ClassementIRB irb2011;
	
	private ClassementIRB irbMoy78910;
	
	// associer le nom d'un pays avec toutes ses instances dans les alPays des différents classements
	private HashMap<String, ArrayList<Pays>> mapPays;

	public PlsClassementIRB()
	{
		this.irb2007 = new ClassementIRB("i2007.txt");
		this.irb2008 = new ClassementIRB("i2008.txt");
		this.irb2009 = new ClassementIRB("i2009.txt");
		this.irb2010 = new ClassementIRB("i2010.txt");
		this.irb2011 = new ClassementIRB("i2011.txt");
		
		this.irbMoy78910 = new ClassementIRB(this.irb2007, "moy78910");
		
		/* tp2 fin */
		this.mapPays = new HashMap<String, ArrayList<Pays>>();
		
		// classements de 2007 à 2010: les pays sont les mêmes donc on ne fait qu'une liste de code pays
		for(String code: this.irb2007.getEnsembleCodePays())
		{
			ArrayList<Pays> instancesPaysParAn = new ArrayList<>();
			Pays pCourant = this.irb2007.getPaysByCode(code); // pour éviter d'avoir à appeler la méthode une nouvelle fois la méthode
															  // au moment de l'ajout dans la hash map
			
			instancesPaysParAn.add(pCourant);
			instancesPaysParAn.add(this.irb2008.getPaysByCode(code));
			instancesPaysParAn.add(this.irb2009.getPaysByCode(code));
			instancesPaysParAn.add(this.irb2010.getPaysByCode(code));
			
			this.mapPays.put(pCourant.getNomPays(), instancesPaysParAn);
		}
		
		//System.out.println(this.irbMoy78910.getPaysByCode("RSA").getNbPoint());
		//System.out.println(this.moyPoints("RSA"));
		
		
	}
	
	public double moyPoints(String codeP)
	{
		double moy 	    = 0.0;
		double sommePts = 0.0;
		
		String nomPays = this.irb2007.getPaysByCode(codeP).getNomPays();
		ArrayList<Pays> paysCourant = this.mapPays.get(nomPays);
		System.out.println(this.mapPays.get(nomPays));
		
		for(Pays p: paysCourant)
		{
			sommePts += p.getNbPoint();
		}
		
		moy = sommePts / paysCourant.size();
		
		return moy;
	}
	
	// existe l'année 1 mais pas l'année 2
	public HashSet <Pays> nAppartientPas(ClassementIRB an1, ClassementIRB an2)
	{
		HashSet<String> codesAn1 = an1.getEnsembleCodePays();
		HashSet<String> codesAn2 = an2.getEnsembleCodePays();
		
		HashSet<String> hsCodesDiff = new HashSet<String>();
		HashSet<Pays> 	hsRet		= new HashSet<Pays>	 ();
		
		for(String codeAn1: codesAn1)
			if(!codesAn2.contains(codeAn1)) hsCodesDiff.add(codeAn1);
			
		for(String code: hsCodesDiff)
			hsRet.add(an1.getPaysByCode(code));
			
		return hsRet;
	}
	
	public void deltaPays()
	{
		  System.out.println("Pays en 2007 qui ne sont pas en 2008 " + nAppartientPas(irb2007, irb2008));		
		  System.out.println("Pays en 2008 qui ne sont pas en 2009 " + nAppartientPas(irb2008, irb2009));		
		  System.out.println("Pays en 2009 qui ne sont pas en 2010 " + nAppartientPas(irb2009, irb2010));	
		  System.out.println("Pays en 2010 qui ne sont pas en 2011 " + nAppartientPas(irb2010, irb2011));
		  
		  System.out.println(this.irbMoy78910);
	}
	

	@Override
	public String toString()
	{
		return "PlsClassementIRB [irbMoy78910=" + irbMoy78910 + "]";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		PlsClassementIRB pls = new PlsClassementIRB();
		//pls.deltaPays();
		System.out.println(pls);
		
		//System.out.println(pls.irbMoy78910.nbPays());

	}

}
