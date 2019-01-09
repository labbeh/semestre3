package algopars.metier;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Classe permettant de charger en memoire un fichier pseudo-code
 * @author Hugo Labbe, Titouan Cornilleau, Clement Baron, Sebastien Mande,Loan Cadorel 
 * @version 2018-12-17, 1.0
 * */

final class LectureFichier {
    
	/**
	 * Lit le fichier dont le nom est passe en parametre
	 * @param lien lien vers le fichier a charger
	 * @return un String avec le contenu du fichier
	 * */
    public static String lire(String lien){
        StringBuilder sRep = new StringBuilder();
        
        try{
        	InputStream ips = new LectureFichier().getClass().getResourceAsStream("/"+lien);
        	InputStreamReader ipsr = new InputStreamReader(ips);
            Scanner scFile = new Scanner(ipsr);
            
            while(scFile.hasNext()){
                sRep.append(scFile.nextLine().replaceAll("\t", "") + "\n");
            }
            
            scFile.close();
        }
        catch(Exception e){
        	System.err.println("Fichier " + lien + 
        					   " introuvable ou inaccessible en lecture");
        	System.exit(1);
        }
        
        return sRep.toString();
    }
    
    /**
     * Pour que cette classe utilitaire ne puisse pas etre instanciee
     * */
    private LectureFichier(){}
}