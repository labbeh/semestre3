package rappel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class RappelFichier {
	
	public RappelFichier()
	{
		// dans le repertoire "donnees"
        String nomFichier = "/i2007.txt";

        String ligne = null;

        try { // ouverture
        	InputStream ips = this.getClass().getResourceAsStream(nomFichier);
        	InputStreamReader ipsr = new InputStreamReader(ips);
        	BufferedReader fichier = new BufferedReader(ipsr);
            // traitement
            while ((ligne = fichier.readLine()) != null) {               
                System.out.println(ligne);
            }
            
            // fermeture
            fichier.close();
        } catch(Exception exc) {
            System.out.println("Erreur fichier" + exc);
        }
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new RappelFichier();

	}

}
