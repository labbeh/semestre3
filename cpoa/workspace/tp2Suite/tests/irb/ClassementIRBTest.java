package irb;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClassementIRBTest
{
	private ClassementIRB irbMoy78910;
	
	@Before
	public void setUp() throws Exception
	{
		this.irbMoy78910 = new ClassementIRB(new ClassementIRB("i2007.txt"), "moy78910");
	}

	@After
	public void tearDown() throws Exception
	{
		this.irbMoy78910 = null;
	}

	@Test
	public void testNbPays()
	{
		assertEquals(this.irbMoy78910.nbPays(), 95);
	}
	
	@Test
	public void getPaysByCode()
	{
		assertEquals(this.irbMoy78910.getPaysByCode("LUX").getCodePays(), new String("LUX"));
		assertEquals(this.irbMoy78910.getPaysByCode("NZL").getCodePays(), new String("NZL"));
	}

}
