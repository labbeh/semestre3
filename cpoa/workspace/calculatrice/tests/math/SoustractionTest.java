package math;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SoustractionTest
{
	private Soustraction sous;
	
	@Before
	public void setUp() throws Exception
	{
		this.sous = new Soustraction();
	}

	@After
	public void tearDown() throws Exception
	{
		this.sous = null;
	}
	
	@Test
	public void testCalculer() throws Exception
	{
	   assertEquals(123, this.sous.calculer(126, 4));
	}

}
