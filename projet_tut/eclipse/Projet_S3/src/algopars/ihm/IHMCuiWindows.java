package algopars.ihm;

import org.fusesource.jansi.*;

/**
 * Classe fille de IHMCui pour les sytemes Windows
 * @author Hugo Labbe, Titouan Cornilleau, Clement Baron, Sebastien Mande,Loan Cadorel 
 * @version 1.0, 2018-12-18
 * */

public class IHMCuiWindows extends IHMCui {
	
	/**
	 * Constructeur d'une ihm console pour windows qui appel le constructeur par defaut
	 * de la classe mere puis initialise une "AnsiConsole" de l'API JANSI pour la prise
	 * en charge des sequences d'échappement ANSI sous windows
	 * */
	public IHMCuiWindows() {
		super();
		AnsiConsole.systemInstall();
		stdout = AnsiConsole.out();
	}
	
	/**
	 * Definit la methode abstraite print pour l'affichage d'un String
	 * sur la sortie standart
	 * */
	@Override
	public void print(String s) {
		stdout.print(s);
		stdout.flush();
		
	}
}
