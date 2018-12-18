package algopars.ihm;

import java.io.PrintStream;

import org.fusesource.jansi.*;

import algopars.Controleur;

public class IHMCuiWindows extends IHMCui {
	
	/**
	 * Objet printstream pour la sortie standard
	 * */
	private PrintStream stdout = new PrintStream(System.out);
	
	public IHMCuiWindows(Controleur ctrl) {
		super(ctrl);
		AnsiConsole.systemInstall();
	}

	@Override
	public void print(String s) {
		stdout.print(s);
		stdout.flush();
		
	}

}
