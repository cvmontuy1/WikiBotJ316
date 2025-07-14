package j.wiki.entry;

import j.wiki.Util;
import j.wiki.parser.Parser;
import j.wiki.parser.Token;

import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;



public class TemplateTest {

	@Test
	public void testPlace()
	{
		String strTemplate = "{{place|en|unincorporated community|on [[Ute]] land in|s/Utah}}";
		Token token;
		Parser parser;
		
		parser = new Parser();
		token = parser.parse(strTemplate, false);

		Template template = new Template(token.getLastChildren());
	
		assertTrue(template.getParameter(1).equals("en"));
		assertTrue(template.getParameter(2).equals("unincorporated community"));
		assertTrue(template.getParameter(3).equals("on Ute land in"));
		assertTrue(template.getParameter(4).equals("s/Utah"));		
	}
	

	
	@Test
	public void testPlace2()
	{
		String strTemplate = "{{place|en|city|on|isl/{{w|Lulu Island}}|in Greater|city/Vancouver|p/British Columbia}}";
		Token token;
		Parser parser;
		
		parser = new Parser();
		token = parser.parse(strTemplate, false /*  */);

		Template template = new Template(token.getLastChildren());
	
		assertTrue(template.getParameter(1).equals("en"));
		assertTrue(template.getParameter(2).equals("city"));
		assertTrue(template.getParameter(3).equals("on"));
		assertTrue(template.getParameter(4).equals("isl/Lulu Island"));		
	}
	
	@Test
	public void testPlace3()
	{
		String strTemplate = "{{place|en|town/seat|dist/Tasman|in the north of the|isl/South Island|c/New Zealand|tcl_noextratext=1}}";
		Token token;
		Parser parser;
		Place place;
		
		parser = new Parser();
		token = parser.parse(strTemplate, false /*  */);

		Template template = new Template(token.getLastChildren());
		place = new Place(template);

		assertTrue(template.getParameter(1).equals("en"));
		assertTrue(template.getParameter(2).equals("town/seat"));	
		assertTrue(place.getText().equals("La sede de el distrito Tasman, Nueva Zelanda"));
		
	}
	
	@Test
	public void testPlace4()
	{
		String strTemplate = "{{place|en|Places in <<cc/England>>}}";
		Token token;
		Parser parser;
		Place place;
		
		parser = new Parser();
		token = parser.parse(strTemplate, false /*  */);

		Template template = new Template(token.getLastChildren());
		place = new Place(template);	
		assertTrue(place.getText(true).equals("Inglaterra"));
	}

	@Test
	public void testDer()
	{
		String strTemplate = "{{der|en|he|חַנָּה|tr=Ḥanâ}}";
		Token token;
		Parser parser;
		
		parser = new Parser();
		token = parser.parse(strTemplate, false);

		Template template = new Template(token.getLastChildren());
	
		assertTrue(template.getParameter(1).equals("en"));
		assertTrue(template.getParameter(2).equals("he"));
		assertTrue(template.getParameter(3).equals("חַנָּה"));
		assertTrue(template.getParameter("tr").equals("Ḥanâ"));		
	}

	
	
}
