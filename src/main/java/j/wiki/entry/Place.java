package j.wiki.entry;

import java.util.ArrayList;
import java.util.List;

public class Place {
	
	public Place(Template template)
	{
		this.template = template;
		names = new ArrayList<TypeName>();
		
		TypeName typename;
		
		typename = build(template.getParameter(2 /* Place type*/), true /* trans forced */);
		
		if(typename.isDefined() )
		{
			names.add(typename);
		}
		
		for(int i=3; i<=template.getUnnamedParsCnt(); ++i)
		{
			typename = build( template.getParameter(i), false);
			if( typename.isDefined() )
			{
				names.add( typename ) ;
			}
		}
		
	}
	
	public String toWiki()
	{
		StringBuilder buffer = new StringBuilder();
		TypeName tname;
		
		for(int iName = 0; iName < names.size(); ++iName )
		{
			tname = names.get(iName);
			switch( iName )
			{
				case 0:
					switch( tname.genrename.genre )
					{
						case 'm':
							buffer.append("Un ");
							break;
						case 'f':
							buffer.append("Una ");						
							break;
						case 'n':
							break;
					}
					buffer.append(tname.genrename.name);
					break;
				case 1:
					buffer.append(" en ");
					buffer.append(tname.genrename.name);
					break;
				default:
					buffer.append(", ");
					buffer.append(tname.genrename.name);
					break;
			}
		}

		return buffer.toString();
	}
	
/***************************************************
 * PRIVATE SECTION
 */
	public static TypeName build(String text, boolean bForced)
	{
		TypeName typename;
		String[] words;
		
		typename = new TypeName();
		if( text.contains("/") )
		{
			words = text.split("/");
			if( words.length == 2)
			{
				typename.code = words[0];
				typename.name = words[1];					
			}
			else
			{
				typename.code = "";
				typename.name = text;
				for(String w: words)
				{
					if( Dictionary.contains(w))
					{
						typename.name = w;
						break;
					}
				}
			}
		}
		else
		{
			typename.code = "";
			typename.name = text;				
		}
		switch(typename.code)
		{
			case "c":
				typename.type = "pais";
				break;
			case "r":
				typename.type = "región";
				break;
			case "p":
				typename.type = "provincia";
				break;
			case "s":
				typename.type = "estado";
				break;					
			case "cc":
			case "co":
				typename.type = "condado";
				break;					
			case "cont":
				typename.type = "continente";
				break;					
			case "dept":
				typename.type = "departamento";
				break;					
			case "dist":
				typename.type = "district";
				break;					
			case "riv":
				typename.type = "rio";
				break;					
			case "carea":
				typename.type = "área";
				break;					
			case "cdp":
			case "CDP":
				typename.type = "lugar designado por el censo";
				break;					
			default:
				typename.type = "";
				
		}
		typename.genrename = Dictionary.get(typename.name, bForced || typename.type == "");
		return typename;
	}

	
	private static class TypeName
	{
		int   order;
		String code;
		String type;  // Ciudad, Estado, Pais
		String name;
		Dictionary.GenreName genrename;
		
		public boolean isDefined()
		{
			return genrename != null;
		}
	}
	

	private Template template;
	private List<TypeName> names;	
}
