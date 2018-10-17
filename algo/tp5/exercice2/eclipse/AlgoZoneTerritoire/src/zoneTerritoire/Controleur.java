package zoneTerritoire;

import java.util.List;

import zoneTerritoire.metier.*;
import zoneTerritoire.ihm.*;

public class Controleur
{
	private Gui gui;

	public Controleur()
	{
		this.gui = new Gui(this);
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
}