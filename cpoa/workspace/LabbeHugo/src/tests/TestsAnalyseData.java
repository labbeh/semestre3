package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twitter.AnalyseData;

public class TestsAnalyseData
{
	private AnalyseData ad;
	
	@Before
	public void setUp() throws Exception
	{
		this.ad = new AnalyseData("Test.csv");
	}

	@After
	public void tearDown() throws Exception
	{
		this.ad = null;
	}

	@Test
	public void testNbTweet()
	{
		assertEquals(3, ad.nbTweet("CompteInfosNews"));
		assertEquals(2, ad.nbTweet("francebleu"));
		assertEquals(2, ad.nbTweet("funradio_fr"));
	}

}
