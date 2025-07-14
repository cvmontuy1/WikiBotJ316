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
		boolean bIndefinite = true;
		boolean bCountry = false;
		
		for(int iName = 0; iName < names.size(); ++iName )
		{
			tname = names.get(iName);
			switch( iName )
			{
				case 0:
					if( !bHasChildren || names.size() == 1 )
					{
						switch( tname.genrename.genre )
						{
							case Genre.MASCULINE:
								if( tname.genrename.indefinite )
								{
									bIndefinite = true;
									buffer.append("Un ");
								}
								else
								{
									bIndefinite = false;									
									buffer.append("El ");
								}
								break;
							case Genre.FEMENINE:
								if( tname.genrename.indefinite )
								{								
									bIndefinite = true;									
									buffer.append("Una ");
								}
								else
								{
									bIndefinite = false;									
									buffer.append("La ");
								}
								break;
							case Genre.NEUTRE:
								break;
						}
						buffer.append(tname.genrename.name);						
					}
					break;
				case 1:
					if( !bHasChildren )					
					{					
						if( bIndefinite )
						{
							buffer.append(" en ");
						}
						else
						{
							buffer.append(" de ");
						}
					}
					if( tname.type != null && tname.type.isVisible() )
					{
						switch( tname.type.genre)
						{
							case MASCULINE:
								buffer.append("el ");
								break;
							case FEMENINE:
								buffer.append("la ");
								break;
							case NEUTRE:
								break;
						}
						buffer.append(tname.type.type);
						buffer.append(" ");
						if( tname.type.preposition)
						{
							buffer.append("de ");
						}
					}
					if( tname != null && tname.type != null && tname.type.type != null)
					{
						if( "país".equals(tname.type.type ) )
						{
							bCountry = true;
						}
					}
					buffer.append(tname.genrename.name);
					break;
				default:
					if( !bCountry )
					{
						buffer.append(", ");
						buffer.append(tname.genrename.name);
					}
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
					typename.code = Util.getLastWord(words[0]);
					typename.name = TextParser.filterPlaceType(words[1]);					
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
				typename.name = TextParser.filterPlaceType(text);
				if( Dictionary.isCountry(typename.name) )
				{
					typename.type = new PlaceType("país");
				}
			}			
			
			switch(typename.code)
			{
				case "c":
				case "cc":					
					typename.type = new PlaceType("pais");
					break;
				case "carea":
					typename.type = new PlaceType("área", Genre.MASCULINE);
					break;
				case "city":
					typename.type = new PlaceType("ciudad");
					break;
				case "co":
					typename.type = new PlaceType("condado", Genre.MASCULINE);
					if( Util.isNotNullOrEmpty(typename.name) )
					{
						typename.name = typename.name.replace("county", "");
						typename.name = typename.name.replace("County", "");
						typename.name = typename.name.trim();
					}
					break;					
				case "cont":
					typename.type = new PlaceType("continente");
					break;					
				case "dept":
					typename.type = new PlaceType("departamento", Genre.MASCULINE, false);
					break;					
				case "dist":
					typename.type = new PlaceType("distrito", Genre.MASCULINE, false);
					break;					
				case "riv":
					typename.type = new PlaceType("rio", Genre.MASCULINE, false);
					break;					
				case "cdp":
				case "CDP":
					typename.type = new PlaceType("lugar designado por el censo");
					break;		
				case "p":
					typename.type = new PlaceType("provincia", Genre.FEMENINE);
					break;
				case "r":
					typename.type = new PlaceType("región", Genre.FEMENINE);
					break;
				case "s":
					typename.type = new PlaceType("estado", Genre.MASCULINE, true);
					break;		
				case "town":
					typename.type = new PlaceType("pueblo");
					break;
				default:
					break;
					
			}
			typename.genrename = Dictionary.get(typename.name, bForced || typename.type == null);
		}
		return typename;
	}
	
	private static class PlaceType
	{
		String type;  // Ciudad, Estado, Pais
		Genre  genre;
		boolean preposition;
		
		public PlaceType(String type)
		{
			this.type = type;
			this.genre = null;
			this.preposition = false;
		}

		public PlaceType(String type, Genre genre)
		{
			this.type = type;
			this.genre = genre;
			this.preposition = true;			
		}
		
		public PlaceType(String type, Genre genre, boolean preposition)
		{
			this.type = type;
			this.genre = genre;
			this.preposition = preposition;			
		}

		public boolean isVisible()
		{
			return genre != null;
		}
	}

	
	private static class TypeName
	{
		String code;
		PlaceType type;
		String name;
		Dictionary.GenreName genrename;
		
		public boolean isDefined()
		{
			return genrename != null;
		}
	}
	
	private TypeName name;
	private List<TypeName> names;	
}
