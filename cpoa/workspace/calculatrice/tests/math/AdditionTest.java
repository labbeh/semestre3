package math;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AdditionTest
{
	private Addition add;
	
	//initialisation des ressources utiles aux tests
	@Before
	public void setUp() throws Exception
	{
		this.add  = new Addition	  ();

	}
	
	//libération des resources utilisées
	@After
	public void tearDown() throws Exception
	{
		this.add  = null;
	}

	@Test
	public void testCalculer() throws Exception
	{
	   assertEquals(new Long(4), add.calculer(new Long(1), new Long(3)));
	}
	 
	@Test
	public void testLireSymbole() throws Exception
	{
	    assertEquals((Character)'-', add.lireSymbole());
	}

}
