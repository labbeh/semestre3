package algopars.metier;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class LectureFichier {
    
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
        catch(Exception e){e.printStackTrace();}
        
        return sRep.toString();
    }
    
    private LectureFichier(){}
}