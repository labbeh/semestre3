package math;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

public class Calculatrice
{
	private Addition 	 add ;
	private Soustraction sous;
	
	public Calculatrice()
	{
		this.add  = new Addition	();
		this.sous = new Soustraction();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		JUnitCore runner = new JUnitCore();
	    runner.addListener(new TextListener(System.out));
	    runner.run(AllTests.class);

	}

}
