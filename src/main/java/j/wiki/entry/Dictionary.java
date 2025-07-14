/*
 * This file is part of WikiBotJ316.
 *
 * WikiBotJ316 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WikiBotJ316 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WikiBotJ316. If not, see <https://www.gnu.org/licenses/>.
 * 
 * Author: Carlos Valenzuela Montuy
 */

package j.wiki.entry;

import java.util.HashMap;
import java.util.Map;

import j.wiki.Util;


public class Dictionary 
{
	public static GenreName get(String english, boolean bForced)
	{
		GenreName genrename;
		
		english =  english.replaceAll("\\p{Punct}", "");
		english = english.trim();
		if( Util.isNotNullOrEmpty(english))
		{
			if( map.containsKey(english))
			{
				genrename = map.get(english);
			}
			else
			{
				if( mapCountry.containsKey(english))
				{
					genrename = mapCountry.get(english);
				}
				else
				{
					if( bForced )
					{
						genrename = null;
					}
					else
					{
						english = Util.concatenate(TextParser.filterConnectors(english));
						genrename = new GenreName(english, Genre.NEUTRE, true);
					}
				}
			}		
		}
		else
		{
			genrename = null;
		}
		
		return genrename; 
	}
	
	public static boolean isCountry(String english)
	{
		return mapCountry.containsKey(english);
	}
	
	public static boolean contains(String english)
	{
		return map.containsKey(english) || isCountry(english);
	}

	// *************************************************************************	
//		private section
	
	
	static 
	{
		
		map = new HashMap<String, GenreName>();
		mapCountry = new HashMap<String, GenreName>();

		
		// Generic places
		put("barangay", "barrio", Genre.MASCULINE);
		put("borough", "distrito", Genre.MASCULINE);
		put("capital city", "capital", Genre.FEMENINE, false /* definite La capital */);
		put("CDP", "localidad", Genre.FEMENINE);  //  census-designated place 
		put("city", "ciudad", Genre.FEMENINE);	
		put("civil parish", "territorio", Genre.MASCULINE);
		put("city-state", "ciudad estado", Genre.FEMENINE);
		put("council area", "área municipal", Genre.FEMENINE);		
		put("county", "condado", Genre.MASCULINE);
		put("county seat", "sede", Genre.FEMENINE, false);
		put("country", "país", Genre.MASCULINE);
		put("community", "comunidad", Genre.FEMENINE);
		put("electoral division", "distrito", Genre.MASCULINE);
		put("ghost town", "pueblo fantasma", Genre.MASCULINE);
		put("gulf", "golfo", Genre.MASCULINE);
		put("hamlet", "caserio", Genre.MASCULINE);
		put("island", "isla", Genre.FEMENINE);		
		put("lake", "lago", Genre.MASCULINE);
		
		put("locality", "localidad", Genre.FEMENINE);	
		put("locale", "lugar", Genre.MASCULINE);
		put("market town", "pueblo", Genre.MASCULINE);
		put("megacity", "megalópolis", Genre.FEMENINE);
		put("monarchy", "monarquía", Genre.FEMENINE);
		put("municipality", "municipio", Genre.MASCULINE);
		put("national capital", "capital nacional", Genre.FEMENINE);		
		put("national park", "parque nacional", Genre.MASCULINE);		
		put("neighborhood", "vecindario", Genre.MASCULINE);		
		put("neighbourhood", "vecindario", Genre.MASCULINE);
		put("number of places", "varios lugares");
		put("number places", "varios lugares");
		put("ocean", "oceano", Genre.MASCULINE);
		put("outer northern suburb", "suburbio", Genre.MASCULINE);
		put("park", "parque", Genre.MASCULINE);		
		put("port", "puerto", Genre.MASCULINE);		
		put("port city", "ciudad portuaria", Genre.FEMENINE );
		put("prefecture", "prefectura", Genre.FEMENINE);
		put("protectorate", "protectorado", Genre.MASCULINE);
		put("province", "provincia", Genre.FEMENINE);		
		
		put("region", "región", Genre.FEMENINE);
		put("republic", "república", Genre.FEMENINE);
		put("river", "río", Genre.MASCULINE);
		put("sea", "mar", Genre.MASCULINE);
		put("seat", "sede", Genre.FEMENINE, false /* definite */);
		put("settlement", "asentamiento", Genre.MASCULINE);
		put("several", "varios");	
		put("state capital", "capital estatal", Genre.FEMENINE);
		put("state park", "parque estatal", Genre.MASCULINE);
		put("suburb", "suburbio", Genre.MASCULINE);
		put("town", "pueblo", Genre.MASCULINE);
		put("town/seat", "sede", Genre.FEMENINE, false /* definite */);
		put("townland", "townland", Genre.MASCULINE );
		put("township", "municipio", Genre.MASCULINE);
		put("twp", "municipio", Genre.MASCULINE);
		put("ucomm", "comunidad", Genre.FEMENINE);
		put("valley", "valle", Genre.MASCULINE);
		put("village", "villa", Genre.FEMENINE);
		put("volcano", "volcán", Genre.MASCULINE);
		put("zone", "zona", Genre.FEMENINE);

		
	
		// Countries that uses English
		putCountry("Australia");
		putCountry("Barbados");	
		putCountry("Bahamas", "Bahamas");
		putCountry("Belice");
		putCountry("Canada", "Canadá");
		putCountry("England", "Inglaterra");
		putCountry("Guyana");
		putCountry("Ireland", "Irlanda");
		putCountry("India");
		putCountry("Jamaica");
		putCountry("Kenya", "Kenia");
		putCountry("Puerto Rico");		
		putCountry("Liberia");
		putCountry("New Zealand", "Nueva Zelanda");
		putCountry("UK", "Reino Unido");		
		putCountry("United Kingdom", "Reino Unido");
		putCountry("United States", "Estados Unidos");
		putCountry("United States of America", "Estados Unidos");		
		putCountry("USA", "Estados Unidos");
		putCountry("US", "Estados Unidos");
		putCountry("Republic of Zambia", "Zambia");		
		putCountry("Scotland", "Escocia");
		putCountry("South Africa", "Sudáfrica");
		putCountry("Trinidad and Tobago", "Trinidad y Tobago");
		putCountry("Zambia");
		
		// Other countries
		putCountry("Albania");
		putCountry("Austria");
		putCountry("Argentina");
		putCountry("Belgium", "Bélgica");
		putCountry("Bulgaria");
		putCountry("Brazil", "Brasil");
		putCountry("Colombia", "Colombia");		
		putCountry("China","China");
		putCountry("Congo");
		putCountry("Croatia", "Croacia");
		putCountry("Cuba", "Cuba");
		putCountry("Denmark", "Dinamarca");
		putCountry("Egypt", "Egipto"); 
		putCountry("Estonia");
		putCountry("Ethiopia", "Etiopía");
		putCountry("Finland", "Finlandia");
		putCountry("France", "Francia");
		putCountry("Greece", "Grecia");
		putCountry("Germany", "Alemania");
		putCountry("Greenland", "Groenlandia");
		putCountry("Guatemala");	
		putCountry("Honduras");		
		putCountry("Hungary", "Hungria");		
		putCountry("Iceland", "Islandia");
		putCountry("India");
		putCountry("Iraq", "Irak");
		putCountry("Italy", "Italia");
		putCountry("Japan", "Japón");
		putCountry("Liechtenstein", "Liechtenstein");		
		putCountry("Lithuania", "Lituania");
		putCountry("Luxembourg", "Luxemburgo");
		putCountry("Malta");
		putCountry("Mexico", "México");
		putCountry("Monaco");
		putCountry("Montenegro");	
		putCountry("Netherlands", "Holanda");
		putCountry("Nicaragua");
		putCountry("Norway", "Noruega");
		putCountry("Panama", "Panamá");
		putCountry("Peru", "Perú");
		putCountry("Poland", "Polonia");
		putCountry("Portugal");
		putCountry("Romania", "Rumania");
		putCountry("Russia", "Rusia");
		putCountry("Spain", "España");
		putCountry("Sweden", "Suecia");
		putCountry("Switzerland", "Suiza");
		putCountry("South America", "Sudamérica");
		putCountry("Turkey", "Turquía");
		putCountry("Vatican City", "Ciudad del Vaticano");
		
		

		// Provincias de Canada
		put("Alberta");
		put("British Columbia");
		put("Manitoba");
		put("New Brunswick");
		put("Newfoundland");
		put("Labrador");
		put("Nova Scotia", "Nueva Escocia");
		put("Ontario");
		put("Prince Edward", "Pincipe Edward");
		put("Island","Isla");
		put("Quebec");
		put("Saskatchewan");
		put("Northwest Territories");
		put("Nunavut");
		put("Yukon");
		
		// Satates USA
		put("Alabama");
		put("Alaska");
		put("Arizona");
		put("Arkansas");
		put("California");
		put("Connecticut");
		put("Colorado");
		put("Delaware");
		put("Florida");

		put("Georgia");
		put("Hawaii", "Hawái");	
		put("Idaho");
		put("Illinois");
		put("Indiana");
		put("Iowa");
		put("Kansas");
		put("Kentucky");
		put("Lousiana");
		put("Maine");
		put("Maryland");
		put("Massachusetts");
		put("Michigan");
		put("Minnesota");
		put("Mississippi", "Misisipí");
		put("Missouri");
		put("Montana");
		put("Nebraska");
		put("Nevada");
		put("New Hamshire");
		put("New Jersey");
		put("New Mexico");
		put("New York");
		put("North Carolina", "Carolina del Norte");
		put("North Dakota", "Dakota del Norte");
		put("Ohio");
		put("Oklahoma");
		put("Oregn");
		put("Pennsylvania");
		put("Rhode Island");
		put("South Carolina", "Carolina del Sur");
		put("South Dakota", "Dakota del Sur");
		put("Tennessee");
		put("Texas");
		put("Utah");
		put("Vermont");
		put("Virginia");
		put("Washington");
		put("West Virginia", "Virginia del Este");
		put("Wisconsin");  
		put("Wyoming");
		put("Washington DC");
		put("Washington D.C.");		
		
		
		// Ciudades
		put("London", "Londres");
		 
	}
	

	
	private static void put(String strValue)
	{
		put(strValue, strValue, Genre.NEUTRE, true /* indefinido */);
	}

	private static void put(String strValue, String spanish)
	{
		put(strValue, spanish, Genre.NEUTRE, true /* indefinite */);
	}
	
	private static void put(String english, String spanish, Genre genre)
	{
		put(english, spanish, genre, true /* indefinite */);		
	}
	
	private static void put(String english, String spanish, Genre genre, boolean bIndefinite)
	{
		map.put(english, new GenreName(spanish, genre, bIndefinite));		
	}
	
	private static void putCountry(String english, String spanish)
	{
		mapCountry.put(english,  new GenreName(spanish, Genre.NEUTRE, true));
	}
	
	private static void putCountry(String english)
	{
		mapCountry.put(english,  new GenreName(english, Genre.NEUTRE, true));
	}

	public static class GenreName
	{
		Genre genre;
		String name;
		boolean indefinite;
		
		public GenreName(String name, Genre genre, boolean indefinite)
		{
			this.name = name;
			this.genre = genre;
			this.indefinite = indefinite;
		}
		
		public String toString()
		{
			StringBuilder buffer = new StringBuilder();
			
			buffer.append("name:").append(name).append(" genre:").append(genre.toString());
			
			return buffer.toString();
		}
	}
	
	private static Map<String, GenreName> map;	
	private static Map<String, GenreName> mapCountry;
}
