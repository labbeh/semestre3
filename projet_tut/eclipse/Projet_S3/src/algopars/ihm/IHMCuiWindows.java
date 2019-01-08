package algopars.ihm;

import org.fusesource.jansi.*;

/**
 * Classe fille de IHMCui pour les sytèmes Windows
 * @author hugo labbé
 * @version 1.0, 2018-12-18
 * */

public class IHMCuiWindows extends IHMCui {
	
	public IHMCuiWindows() {
		super();
		AnsiConsole.systemInstall();
	}

	@Override
	public void print(String s) {
		stdout.print(s);
		stdout.flush();
		
	}

}
