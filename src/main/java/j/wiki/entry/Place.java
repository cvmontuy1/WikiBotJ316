package j.wiki.entry;

import java.util.ArrayList;
import java.util.List;

import j.wiki.Util;

public class Place extends TextSrc {
	
	public Place(Template template)
	{
		names = new ArrayList<TypeName>();
		
		TypeName typename;
		
		typename = build(template.getParameter(2 /* Place type*/), true /* trans forced */);
		
		if(typename.isDefined() )
		{
			names.add(typename);
		}
		else
		{
			Util.reportError("Place missing translation for<", template.getParameter(2), ">");			
		}		

		for(int i=3; i<=template.getUnnamedParsCnt(); ++i)
		{
			typename = build( template.getParameter(i), false);
			if( i == 3)
			{
				name = typename;
			}
			if( typename.isDefined() )
			{
				names.add( typename ) ;
			}
		}		
	}
	
	public String getName()
	{
		return name.genrename.name;
	}
	
	@Override
	public boolean isEmpty()
	{
		return Util.isNullOrEmpty(getText(false));
	}
	
	
	@Override
	public String getText(boolean bHasChildren)
	{
		StringBuilder buffer = new StringBuilder();
		TypeName tname;
		
		for(int iName = 0; iName < names.size(); ++iName )
		{
			tname = names.get(iName);
			switch( iName )
			{
				case 0:
					if( !bHasChildren )
					{
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
					}
					break;
				case 1:
					if( !bHasChildren )
					{					
						buffer.append(" en ");
					}
					if( tname.type.equals(TYPE_COUNTY) )
					{
						buffer.append("el ");
					}
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
		if( text != null)
		{
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
				case "carea":
					typename.type = "área";
					break;
				case "city":
					typename.type = "ciudad";
					break;
				case "cc":
				case "co":
					typename.type = TYPE_COUNTY;
					if( Util.isNotNullOrEmpty(typename.name) )
					{
						typename.name = typename.name.replace("county", "");
						typename.name = typename.name.replace("County", "");
						typename.name = "condado de " + typename.name.trim();
					}
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
				case "cdp":
				case "CDP":
					typename.type = "lugar designado por el censo";
					break;		
				case "p":
					typename.type = "provincia";
					break;
				case "r":
					typename.type = "región";
					break;
				case "s":
					typename.type = "estado";
					break;					
				default:
					typename.type = "";
					
			}
			typename.genrename = Dictionary.get(typename.name, bForced || typename.type == "");
		}
		return typename;
	}

	
	private static class TypeName
	{
		String code;
		String type;  // Ciudad, Estado, Pais
		String name;
		Dictionary.GenreName genrename;
		
		public boolean isDefined()
		{
			return genrename != null;
		}
	}
	
	private final static String TYPE_COUNTY = "county";
	private TypeName name;
	private List<TypeName> names;	
}
