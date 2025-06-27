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
		entry = Entry.buildEntry("", pentry);
		
		assertTrue(entry.getDefsCount()==5);
		assertTrue(entry.getEtimCount() == 1);

		//System.out.println(entry.toWiki());
	}	

	@Test
	public void testColmbia()
	{
		Entry entry = null;
		ParserEntry pentry = new ParserEntry("en", "Test", ParserTest.WIKITEXT_COLOMBIA);	
		
		try
		{
			entry = Entry.buildEntry("", pentry);
			//System.out.println(entry.toWiki());			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		assertTrue( entry.getEtimCount() == 1);
		
	}

	//@Test
	public void test2()
	{
		Entry entry;
		ParserEntry pentry = new ParserEntry("en", "Test", ParserTest.WIKITEXT1);	
		entry = Entry.buildEntry("", pentry);
		
		System.out.println(entry.toWiki());
	}

	@Test	
	public void testHanna()
	{
		Entry entry;
		ParserEntry pentry = new ParserEntry("en", "Hanna", ParserTest.WIKITEXT_HANNA);	
		entry = Entry.buildEntry("", pentry);
		
		System.out.println(pentry.toString());
		System.out.println(entry.toWiki());
		
		assertTrue(entry.getEtimCount()==2);		
	}

	
	
	//@Test
	public void testMRI()
	{
		Entry entry;
		ParserEntry pentry = new ParserEntry("en", "MRI", ParserTest.WIKITEXT_MRI);	
		entry = Entry.buildEntry("", pentry);
		
		System.out.println(entry.toWiki());
	}

}
