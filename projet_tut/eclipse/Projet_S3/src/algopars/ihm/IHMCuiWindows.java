package algopars.ihm;

import org.fusesource.jansi.*;

/**
 * Classe fille de IHMCui pour les sytèmes Windows
 * @author hugo labbé
 * @version 1.0, 2018-12-18
 * */

public class IHMCuiWindows extends IHMCui {
	
	/**
	 * Constructeur d'une ihm console pour windows qui appel le constructeur par défaut
	 * de la classe mère puis initialise une "AnsiConsole" de l'API JANSI pour la prise
	 * en charge des séquences d'échappement ANSI sous windows
	 * */
	public IHMCuiWindows() {
		super();
		AnsiConsole.systemInstall();
		stdout = AnsiConsole.out();
	}
	
	/**
	 * Définit la méthode abstraite print pour l'affichage d'un String
	 * sur la sortie standart
	 * */
	@Override
	public void print(String s) {
		stdout.print(s);
		stdout.flush();
		
	}
}
