package irb;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlsClassementIRBTest
{
	private PlsClassementIRB pls		;
	private ClassementIRB 	 irbMoy78910;

	@Before
	public void setUp() throws Exception
	{
		this.pls 		 = new PlsClassementIRB();
		this.irbMoy78910 = new ClassementIRB(new ClassementIRB("i2007.txt"), "moy78910");
	}

	@After
	public void tearDown() throws Exception
	{
		this.pls 		 = null;
		this.irbMoy78910 = null;
	}

	@Test
	public void testMoyPoints()
	{
		HashSet<String> codesPays = this.irbMoy78910.getEnsembleCodePays();
		
		for(String code: codesPays)
			assertEquals(new Double(this.irbMoy78910.getPaysByCode(code).getNbPoint()), 
						 new Double(this.pls.moyPoints(code))
						);
	}
	
	@Test
	public void testAnneeMoy()
	{
		// test sur LUX et KOR
		assertEquals(this.pls.anneeMoy("LUX"), new String("2008"));
		assertEquals(this.pls.anneeMoy("KOR"), new String("2009"));
	}

}
