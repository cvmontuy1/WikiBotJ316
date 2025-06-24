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

public class Dictionary 
{
	private static Map<String, String> map;
	
	static 
	{
		// Generic places
		put("several", "varios");
		put("ghost town", "pueblo fantasma");
		put("village", "pueblo");
		put("small city", "ciudad");
		put("neighborhood", "vecindario");
		put("town", "pueblo");
		put("small town", "poblado");
		put("unincorporated comunity", "comunidad");
		put("comunity", "comunidad");
		put("borough", "distrito");
		put("township", "municipio");
		put("barangay", "barrio");
		
		// States
		map = new HashMap<String, String>();
		
		// Countries
		put("Australia");
		put("Barbados");	
		put("Bahamas", "Bahamas");
		put("Belice");
		put("Canada");
		put("England", "Inglaterra");
		put("Ireland", "Irlanda");
		put("India");
		put("Jamaica");		
		
		put("Kenya", "Kenia");
		put("Puerto Rico");		
		put("Liberia");
		put("New Zealand", "Nueva Zelanda");
		put("South Africa", "Sudafrica");

		put("UK", "Reino Unido");
		put("United Kingdom", "Reino Unido");
		put("United States", "Estados Unidos");
		put("United States of America", "Estados Unidos");		
		put("USA", "Estados Unidos");
		put("US", "Estados Unidos");
		put("Scotland", "Escocia");
		put("Trinidad and Tobago", "Trinidad y Tobago");

		
		
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
	
	public static String get(String english)
	{
		String spanish;
		
		spanish = map.get(english);
		
		return spanish; 
	}

	
// *************************************************************************	
//	private section
	private static void put(String strValue)
	{
		map.put(strValue, strValue);
	}

	private static void put(String english, String spanish)
	{
		map.put(english, spanish);
	}
	
	
}
