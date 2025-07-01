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

import java.util.ArrayList;
import java.util.List;

import j.wiki.Util;

public class Pron {
	public String name;
	public List<String> ipas;
	public List<String> audios;
	public String notaaudio;
	
	public Pron()
	{
		ipas = new ArrayList<String>();
		audios = new ArrayList<String>();
	}
	
	public Pron(String strName, String strApi)
	{
		ipas = new ArrayList<String>();
		audios = new ArrayList<String>();
		name = strName;
		addApi(strApi);
	}
	
	/**
	 * Adds an alfhabet phonetic international 
	 * @param strApi
	 */
	public void addApi(String strApi)
	{
		String[] apis;
		
		if( strApi.contains("|") )
		{
			apis = strApi.split("\\s?/\\s?\\|\\s?/\\s?");
			for(String api: apis)
			{
				if( Util.isNotNullOrEmpty(api))
				{
					ipas.add(api);
				}
			}			
		}
		else
		{
			ipas.add(strApi);
		}
	}
	
	public void addAudio(String strAudio)
	{
		audios.add(strAudio);
	}
}
