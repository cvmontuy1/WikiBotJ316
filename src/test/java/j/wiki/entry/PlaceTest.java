package j.wiki.entry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import j.wiki.Util;
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
	
	@Test	
	public void testPlace2()
	{
		String strTemplate = "{{place|en|major river|in [[Scotland]], flowing from|carea/South Lanarkshire|past|carea/North Lanarkshire|through|carea/Glasgow|and past|carea/Renfrewshire|and|carea/West Dunbartonshire|to the|place/Firth of Clyde}}";
		Token token;
		Parser parser;
		Place place;
		
		parser = new Parser();
		token = parser.parse(strTemplate, false);
		

		Template template = new Template(token.getChild(0));
		place = new Place(template);
		Util.report("place2:",  place.getText() );
		
	}
	
}
