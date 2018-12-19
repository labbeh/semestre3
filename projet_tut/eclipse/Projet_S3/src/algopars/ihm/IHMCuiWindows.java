package algopars.ihm;

import org.fusesource.jansi.*;

import algopars.Controleur;

public class IHMCuiWindows extends IHMCui {
	
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
