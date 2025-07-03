package j.wiki.entry;

import org.junit.jupiter.api.*;

import j.wiki.parser.ParserEntry;
import j.wiki.parser.ParserTest;

import static org.junit.jupiter.api.Assertions.*;


public class EntryTest {

	@Test
	public void testCharly()
	{
		Entry entry;
		ParserEntry pentry = new ParserEntry("en", "Charly", ParserTest.WIKITEXT_CHARLY);	
		entry = En2Es.buildEntry("", pentry);
		
		System.out.println(entry.getDefTxt(1));
		System.out.println(entry.toWiki());
		
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
		
		System.out.println(entry.toWiki());
	}

	@Test	
	public void testHanna()
	{
		Entry entry;
		ParserEntry pentry = new ParserEntry("en", "Hanna", ParserTest.WIKITEXT_HANNA);	
		entry = En2Es.buildEntry("", pentry);
		
		System.out.println(entry.toWiki());
		System.out.println("Hanna def1:" + entry.getDefTxt(1));
		
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
		
		System.out.println(entry.toWiki());
	}

}
