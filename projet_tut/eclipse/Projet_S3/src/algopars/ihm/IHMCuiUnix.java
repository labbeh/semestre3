package algopars.ihm;

/**
 * Classe fille de IHMCui pour les sytèmes UNiX/Linux
 * @author hugo labbé
 * @version 1.0, 2018-12-18
 * */

public class IHMCuiUnix extends IHMCui {

	public IHMCuiUnix() {
		super();
	}

	@Override
	public void print(String str) {
		stdout.print(str);

	}

}
