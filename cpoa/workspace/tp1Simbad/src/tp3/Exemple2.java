package tp3;

import simbad.gui.Simbad;

public class Exemple2
{

	/**
	 * @param args premier élément nom du fichier
	 */
	public static void main(String[] argv)
	{
		if(argv.length == 0){
			System.out.println("ERREUR: donnez un fichier de configuration en paramètre");
			System.exit(0);
		}
		
		System.out.println("Lancement... ");
		
		MyEnv  env   = new MyEnv(argv[0]	);
		Simbad frame = new Simbad(env ,false);
		
		env.setSimulator(frame.getSimulatorInstance());
	}

}