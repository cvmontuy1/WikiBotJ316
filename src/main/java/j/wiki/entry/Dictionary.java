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
	public static final char MASCULINE = 'm';
	public static final char FEMENINE = 'f';
	public static final char NEUTRE = 'n';

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
				if( bForced )
				{
					genrename = null;
				}
				else
				{
					english = Util.concatenate(TextParser.filterConnectors(english));
					genrename = new GenreName(english, NEUTRE);
				}
			}		
		}
		else
		{
			genrename = null;
		}
		
		return genrename; 
	}
	
	public static boolean contains(String english)
	{
		return map.containsKey(english);
	}

	static 
	{
		
		// States
		map = new HashMap<String, GenreName>();

		
		// Generic places
		put("barangay", "barrio", MASCULINE);
		put("borough", "distrito", MASCULINE);
		put("CDP", "localidad", FEMENINE);  //  census-designated place 
		put("city", "ciudad", FEMENINE);		
		put("city-state", "ciudad estado", FEMENINE);
		put("county", "condado", MASCULINE);
		put("county seat", "Sede de condado", NEUTRE);
		put("country", "país", MASCULINE);
		put("community", "comunidad", FEMENINE);
		put("ghost town", "pueblo fantasma", MASCULINE);
		put("gulf", "golfo", MASCULINE);
		put("hamlet", "caserio", MASCULINE);
		put("island", "isla", FEMENINE);		
		put("lake", "lago", MASCULINE);
		
		put("locality", "localidad", FEMENINE);	
		put("locale", "lugar", MASCULINE);
		put("megacity", "megalópolis", FEMENINE);
		put("monarchy", "monarquía", FEMENINE);
		put("municipality", "municipio", MASCULINE);
		put("national capital", "capital nacional", FEMENINE);		
		put("national park", "parque nacional", MASCULINE);		
		put("neighborhood", "vecindario", MASCULINE);		
		put("ocean", "oceano", MASCULINE);
		
		put("park", "parque", MASCULINE);		
		put("port", "puerto", MASCULINE);		
		put("prefecture", "prefectura", FEMENINE);
		put("protectorate", "protectorado", MASCULINE);
		put("province", "provincia", FEMENINE);		
		
		put("region", "región", FEMENINE);
		put("republic", "república", FEMENINE);
		put("rural municipality", "municipio", MASCULINE);
		put("several", "varios");	
		put("small city", "ciudad", FEMENINE);		
		put("small town", "poblado", MASCULINE);
		put("state capital", "capital estatal", FEMENINE);
		put("state park", "parque estatal", MASCULINE);
		put("statutory town", "pueblo", MASCULINE);
		put("statutory city", "ciudad", MASCULINE);
		put("town", "pueblo", MASCULINE);		
		put("township", "municipio", MASCULINE);
		put("twp", "municipio", MASCULINE);
		put("unincorporated community", "comunidad", FEMENINE);
		put("ucomm", "comunidad", FEMENINE);
		put("valley", "valle", MASCULINE);
		put("village", "villa", FEMENINE);
		put("volcano", "volcán", MASCULINE);
		put("zone", "zona", FEMENINE);

		
	
		// Countries that uses English
		put("Australia");
		put("Barbados");	
		put("Bahamas", "Bahamas");
		put("Belice");
		put("Canada", "Canadá");
		put("England", "Inglaterra");
		put("Guyana");
		put("Ireland", "Irlanda");
		put("India");
		put("Jamaica");
		put("Kenya", "Kenia");
		put("Puerto Rico");		
		put("Liberia");
		put("New Zealand", "Nueva Zelanda");
		put("UK", "Reino Unido");		
		put("United Kingdom", "Reino Unido");
		put("United States", "Estados Unidos");
		put("United States of America", "Estados Unidos");		
		put("USA", "Estados Unidos");
		put("US", "Estados Unidos");
		put("Republic of Zambia", "Zambia");		
		put("Scotland", "Escocia");
		put("South Africa", "Sudáfrica");
		put("Trinidad and Tobago", "Trinidad y Tobago");
		put("Zambia");
		
		// Other countries
		put("Albania");
		put("Austria");
		put("Belgium", "Bélgica");
		put("Bulgaria");
		put("Croatia", "Croacia");
		put("Denmark", "Dinamarca");
		put("Estonia");
		put("Finland", "Finlandia");
		put("France", "Francia");
		put("Greece", "Grecia");
		put("Germany", "Alemania");
		put("Hungary", "Hungria");		
		put("Iceland", "Islandia");
		put("Italy", "Italia");
		put("Liechtenstein", "Liechtenstein");		
		put("Lithuania", "Lituania");
		put("Luxembourg", "Luxemburgo");
		put("Malta");		
		put("Monaco");
		put("Montenegro");	
		put("Netherlands", "Holanda");
		put("Norway", "Noruega");
		put("Poland", "Polonia");
		put("Portugal");
		put("Romania", "Rumania");
		put("Russia", "Rusia");
		put("Spain", "España");
		put("Sweden", "Suecia");
		put("Switzerland", "Suiza");
		put("Turkey", "Turquía");
		put("Vatican City", "Ciudad del Vaticano");
		
		

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
		 
	}
	

	
// *************************************************************************	
//	private section
	private static void put(String strValue)
	{
		put(strValue, strValue, NEUTRE);
	}

	private static void put(String strValue, String spanish)
	{
		put(strValue, spanish, NEUTRE);
	}
	
	private static void put(String strValue, char gender)
	{
		put(strValue, strValue, gender);
	}

	private static void put(String english, String spanish, char gender)
	{
		map.put(english, new GenreName(spanish, gender));		
	}
	
	
	public static class GenreName
	{
		char genre;
		String name;
		
		public GenreName(String name, char genre)
		{
			this.name = name;
			this.genre = genre;
		}
	}
	
	private static Map<String, GenreName> map;	
}
