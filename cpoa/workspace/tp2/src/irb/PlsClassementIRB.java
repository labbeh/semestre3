package irb;

import java.util.HashSet;

public class PlsClassementIRB
{
	private ClassementIRB irb2007;
	private ClassementIRB irb2008;
	private ClassementIRB irb2009;
	private ClassementIRB irb2010;
	private ClassementIRB irb2011;

	public PlsClassementIRB()
	{
		this.irb2007 = new ClassementIRB("i2007.txt");
		this.irb2008 = new ClassementIRB("i2008.txt");
		this.irb2009 = new ClassementIRB("i2009.txt");
		this.irb2010 = new ClassementIRB("i2010.txt");
		this.irb2011 = new ClassementIRB("i2011.txt");
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
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		PlsClassementIRB pls = new PlsClassementIRB();
		pls.deltaPays();

	}

}
