package gestionLog;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class TraiteLog
{
	private List<UnLog> logs;
	
    public TraiteLog(String nomFichierLog)
    {
        String ligne = null;
        this.logs = new ArrayList<>();

        try
        { // ouverture
        	InputStream ips = this.getClass().getResourceAsStream(nomFichierLog);
        	InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader fichier = new BufferedReader(ipsr);

            while ((ligne = fichier.readLine()) != null)
                traiteLigne(ligne); // traitement
            
            fichier.close(); // fermeture
        }
        catch (Exception exc)
        {
            System.out.println("Erreur fichier" + exc);
        }
    }
    
    private void traiteLigne(String ligne)
    {
    	Scanner sc = new Scanner(ligne);
    	
    	this.logs.add(new UnLog(sc.next(), sc.next(), sc.next()));
    	sc.close();
    }
    
    /**
     * @return chaine de caratère contenant le nombre de connexion au site par jours avec une ligne par jours
     * */
    public String toStringQ2()
    {
    	StringBuilder sRet = new StringBuilder();
    	
    	String date;
    	int cptDate;
    	
    	Set<String> datesTraitees = new HashSet<>();
    	
    	for(UnLog log: this.logs)
    	{
    		date 	= log.getDate();
    		cptDate = 0;
    		
    		if(!datesTraitees.contains(date))
    		{
    			sRet.append("Date " +date+ " nb ");
    		
    			for(UnLog temp: this.logs)
    				if(temp.getDate().equals(date)) cptDate++;
    		
    			sRet.append(cptDate +"\n");
    		}
    		datesTraitees.add(date);
    	}
    	
    	return sRet.toString();
    }
    
    /**
     * @return chaine de caractère contenant le nombre de fois qu'à été utiliser une ip ainsi que les dates d'utilisation avec une ligne par ip
     * */
    public String toStringQ3()
    {
    	StringBuilder sRet = new StringBuilder();
    	
    	int cptIp = 0;
    	HashMap<String, Set<String>> mapIps = new HashMap<>();
    	Set<String> datesParIp = new HashSet<>();
    	
    	for(UnLog log: this.logs)
    	{
    		String ip = log.getIp();
    		datesParIp.clear();
    		
    		for(UnLog temp: this.logs)
    		{
    			if(temp.getIp().equals(ip))
    			{
    				cptIp++;
    				datesParIp.add(temp.getDate());
    			}
    		}
    		
    		//mapIps.put(ip, datesParIp);
    		sRet.append(ip +" "+ cptIp +" "+ datesParIp +"\n");
    	}
    	
    	return sRet.toString();
    }

    // LA METHODE MAIN EST COMPLETE
    public static void main(String[] args) {
        TraiteLog traiteLog = new TraiteLog("/countModif.txt");

        System.out.println(traiteLog); // 1er affichage brut

        System.out.println(".....nombre de connexion au site par jour.....");
        System.out.println(traiteLog.toStringQ2());

        System.out.println(".....nombre de fois où une IP a été utilisée....");
        System.out.println(traiteLog.toStringQ3());
        
        /*System.out.println(".....nombre de fois où une IP a été utilisée avec affichage trié par IP (la + utilisée en 1er) et par date....");
        System.out.println(traiteLog.toStringQ4());*/
    }

}
