package j.wiki.entry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import j.wiki.parser.Parser;
import j.wiki.parser.Token;

public class PlaceTest {

	@Test
	public void testPlace()
	{
		String strTemplate = "{{place|en|rural municipality|in eastern|p/Saskatchewan|full=the {{w|Rural Municipality of Wallace No. 243}}}}";
		Token token;
		Parser parser;
		Place place;
		
		parser = new Parser();
		token = parser.parse(strTemplate, false);
		

		Template template = new Template(token.getChild(0));
		place = new Place(template);
	
		assertTrue(template.getParameter(1).equals("en"));
		assertTrue(template.getParameter(2).equals("rural municipality"));
		assertTrue(template.getParameter(3).equals("in eastern"));
		assertTrue(template.getParameter(4).equals("p/Saskatchewan"));				
	}
	
}
