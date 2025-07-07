package j.wiki.entry;

import org.junit.jupiter.api.*;

import j.wiki.Util;
import j.wiki.parser.ParserEntry;
import j.wiki.parser.ParserTest;

import static org.junit.jupiter.api.Assertions.*;


public class EntryTest {
	@Test
	public void testMaxie()
	{
		Entry entry;
		ParserEntry pentry = new ParserEntry("en", "Maxie", ParserTest.WIKITEXT_MAXIE);	
		entry = En2Es.buildEntry("", pentry);
		
		assertTrue(entry.getEtimCount() == 1);	
		assertTrue(entry.getDefsCount() == 2);
		assertTrue(entry.getPronCount() == 0);
		Util.report("**********Maxie:\n", entry.toWiki());
	}
	
	@Test
	public void testWallace()
	{
		Entry entry;
		ParserEntry pentry = new ParserEntry("en", "Wallace", ParserTest.WIKITEXT_WALLACE);	
		entry = En2Es.buildEntry("", pentry);
		
		assertTrue(entry.getEtimCount() == 1);
		assertTrue(entry.getDefsCount() == 19);	
		assertTrue(entry.getDefTxt(19).contains("Una localidad en el condado de Harrison, Virginia del Este, Estados Unidos"));
		//Util.report("**********Wallace:\n", entry.toWiki());
	}	
	
	@Test
	public void testSpirits()
	{
		Entry entry;
		ParserEntry pentry = new ParserEntry("en", "spirits", ParserTest.WIKITEXT_SPIRITS);	
		entry = En2Es.buildEntry("", pentry);

		assertTrue(entry.getEtimCount() == 1);	
		assertTrue(entry.getDefsCount() == 2);

		//		//Util.report("**********Spirits:\n", entry.toWiki());
	}

	@Test
	public void testCharly()
	{
		Entry entry;
		ParserEntry pentry = new ParserEntry("en", "Charly", ParserTest.WIKITEXT_CHARLY);	
		entry = En2Es.buildEntry("", pentry);
		
		Util.report("**********Charly:\n", entry.toWiki());
		//System.out.println(entry.getDefTxt(1));
		//System.out.println(entry.toWiki());
		
		assertTrue(entry.getDefsCount()== 3);
		assertTrue(entry.getEtimCount() == 1);
		assertTrue(entry.getPronCount() == 0);
		assertTrue(entry.getDefTxt(1).contains(";1: {{hipocorístico|leng=en|Charles}}."));
		assertTrue(entry.getDefTxt(2).contains(";1: {{hipocorístico|leng=en|Charlene}}."));
	}	

	@Test
	public void testColmbia()
	{
		Entry entry = null;
		ParserEntry pentry = new ParserEntry("en", "Test", ParserTest.WIKITEXT_COLOMBIA);	
		
		try
		{
			entry = En2Es.buildEntry("", pentry);
			//System.out.println("Colombia defcount:" + entry.getDefsCount());
			//System.out.println(entry.toWiki());			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		assertTrue(entry.getEtimCount() == 1);
		assertTrue(entry.getDefsCount() == 1);
		assertTrue(entry.getPronCount() == 2);
		
	}

	//@Test
	public void test2()
	{
		Entry entry;
		ParserEntry pentry = new ParserEntry("en", "Test", ParserTest.WIKITEXT1);	
		entry = En2Es.buildEntry("", pentry);
		
		//System.out.println(entry.toWiki());
	}

	@Test	
	public void testHanna()
	{
		Entry entry;
		ParserEntry pentry = new ParserEntry("en", "Hanna", ParserTest.WIKITEXT_HANNA);	
		entry = En2Es.buildEntry("", pentry);
		
		Util.report("**********Hanna:\n", entry.toWiki());
		//System.out.println("Hanna def1:" + entry.getDefTxt(1));
		
		assertTrue(entry.getEtimCount()==2);	
		assertTrue(entry.getDefsCount()==13);
		assertTrue(entry.getPronCount() == 0);
		assertTrue(entry.getDefTxt(1).contains(";1: {{antropónimo femenino|leng=en}}."));
		assertTrue(entry.getDefTxt(2).contains(";1: {{apellido|leng=en}}."));		
	}

	

	//@Test
	public void testMRI()
	{
		Entry entry;
		ParserEntry pentry = new ParserEntry("en", "MRI", ParserTest.WIKITEXT_MRI);	
		entry = En2Es.buildEntry("", pentry);
		
		//System.out.println("MRI etim count:" + entry.getEtimCount());
		
		assertTrue(entry.getEtimCount() == 1);
	}

}
