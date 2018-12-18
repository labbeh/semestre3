package algopars.ihm;

import algopars.Controleur;

public class IHMCuiUnix extends IHMCui {

	public IHMCuiUnix(Controleur ctrl) {
		super(ctrl);
	}

	@Override
	public void print(String str) {
		System.out.print(str);

	}

}
