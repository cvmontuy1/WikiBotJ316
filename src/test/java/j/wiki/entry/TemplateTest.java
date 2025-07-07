package j.wiki.entry;

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
