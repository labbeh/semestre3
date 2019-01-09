package algopars;

import algopars.metier.Code;

/**
 * @author Hugo Labbe, Titouan Cornilleau, Clement Baron, Sebastien Mande,Loan Cadorel 
 * @version 2019-01-08, 2.0
 * */

public class AlgoPars {
	
	/**
	 * Constante chaine de caractere qui sera affichee en cas de saisie incorrect
	 * des parametres au lancement d'AlgoPars
	 * */
	static final String MSG_ERREUR = "Usage: java -jar AlgoPars.jar fichierPscode manuel|auto " +
			   						 "[--force-windows | --force-unix]";
	
	/**
	 * @param args doit etre au minimum le nom du fichier a utiliser
	 * et "auto" ou "manuel" selon le mode voulu
	 */
	public static void main(String[] argv) {
		Code code;
		Controleur ctrl;
		
		// aura pour valeur w ou u (windows / unix) en fonction du système
		// de la machine
		char os = ' ';
		
		// delai en secondes pour le mode auto
		final long delai = 1;
		
		// on vérifie qu'au moins un fichier et le mode sont passer en paramètre
		if (argv.length < 2) erreur();
		
		// on vérifie si l'utilisateur à forcer le choix de l'os ou non
		if(argv.length > 2){
			if	   (argv[2].equalsIgnoreCase("--force-windows")) os = 'w';
			else if(argv[2].equalsIgnoreCase("--force-unix"	  )) os = 'u';
		}
		else os = detecterOs();
		
		
		code = new Code(argv[0]);
		ctrl = new Controleur(code, os);
		
		// on applique le choix du mode
		if	   (argv[1].equalsIgnoreCase("manuel")) ctrl.lancerPasAPas();
		else if(argv[1].equalsIgnoreCase("auto"	 )) ctrl.lancerAuto(delai);
		else erreur();
		
		
	}
	
	/**
	 * Methode de classe detectant le systeme d'exploitation de la machine sur laquelle
	 * on lance AlgoPars
	 * Cela permettra au controleur d'instancier la bonne ihm console pour la prise en
	 * charge des sequences ANSI sous windows
	 * La methode detecte s'il s'agit de windows ou non, s'il ne s'agit pas de windows
	 * on considere qu'il s'agit d'un systeme UNiX/Linux
	 * 
	 * @return un caractere qui aura pour valeur 'w' si windows est detecte, 'u' dans le cas contraire
	 * */
	private static char detecterOs(){
		char os;
		
		if(System.getProperty("os.name").toLowerCase().startsWith("Windows"))
			os ='w';
		else
			os = 'u';
		
		return os;
	}
	
	/**
	 * Afficher un message d'erreur et ferme le programme avec le code
	 * de status 1
	 * */
	private static void erreur(){
		System.out.println(MSG_ERREUR);
		System.exit(1);
	}
}