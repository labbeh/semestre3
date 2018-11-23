package ihm;

public class IHMCui {

	public IHMCui()
	{
		
	}
	
	public void afficher(String[] code, String[] trace)
	{
		StringBuilder sb = new StringBuilder();
		
		String[] aAfficher = new String[code.length];
		
		for(int i=0; i<aAfficher.length; i++)
			aAfficher[i] = "/ " +code[i];
		
		
		int tailleMax = IHMCui.lengthMax(aAfficher);
		
		for(int i=0; i<aAfficher.length; i++)
		{
			for(int i2=aAfficher[i].length(); i2<tailleMax; i2++)
				aAfficher[i] += " ";
			
			aAfficher[i] += "/";
		}
		
		StringBuilder motif = new StringBuilder();
		
		for(int i=0; i<tailleMax; i++)
			motif.append("/");
		
		sb.append(motif +"\n");
		
		for(int i=0; i<aAfficher.length; i++)
			sb.append(aAfficher[i] +"\n");
		
		sb.append(motif);
		
		System.out.println(sb);
	}
	
	private static int lengthMax(String[] aAfficher)
	{
		int taille = 0;
		
		for(int i=0; i<aAfficher.length; i++)
			if(aAfficher[i].length() > taille)
				taille = aAfficher[i].length();
		
		return taille;
	}

}
