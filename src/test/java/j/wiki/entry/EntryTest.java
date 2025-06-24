package j.wiki.entry;

import org.junit.jupiter.api.*;

import j.wiki.parser.ParserEntry;
import j.wiki.parser.ParserTest;

import static org.junit.jupiter.api.Assertions.*;


public class EntryTest {

	@Test
	public void test1()
	{
		Entry entry;
		ParserEntry pentry = new ParserEntry("en", "Charly", ParserTest.WIKITEXT_CHARLY);	
		entry = Entry.buildEntry("", pentry);
		
		System.out.println(entry.toWiki());
	}
	
	@Test	
	public void test2()
	{
		Entry entry;
		ParserEntry pentry = new ParserEntry("en", "Test", ParserTest.WIKITEXT1);	
		entry = Entry.buildEntry("", pentry);
		
		System.out.println(entry.toWiki());
	}
	
}
