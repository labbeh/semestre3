package algopars;

import algopars.metier.Code;

public class AlgoPars {
	public static void main(String[] argv) {
		if (argv.length < 1){
			System.out.println("erreur passez un nom de fichier en paramètre");
			System.exit(0);
		}
		Code code = new Code(argv[0]);	
		
		Controleur ctrl = new Controleur(code);
		ctrl.lancer();	
		
	}
	
}