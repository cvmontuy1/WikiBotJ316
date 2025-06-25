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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import j.wiki.Util;
import j.wiki.parser.Token;

public class Template {
	
	public Template(Token token)
	{
		String[] arguments;
		String[] name_value;
		
		try
		{
			arguments = token.getValue().split("\\|");
		}
		catch(Exception ex)
		{
			arguments = null;
			Util.reportError("Invalid template value", token.toString());
		}
		
		params = new HashMap<String,String>();
		if( arguments != null)
		{
			name = arguments[0];
			for(int iArg=1; iArg<arguments.length; ++iArg)
			{
				if( arguments[iArg].contains("=") )
				{
					name_value = arguments[iArg].split("=");
					if(name_value.length == 2)
					{
						params.put(name_value[0], name_value[1]);
					}
					else
					{
						params.put(String.valueOf(iArg), arguments[iArg]);						
					}
				}
				else
				{
					params.put(String.valueOf(iArg), arguments[iArg]);
					iUnnamedParsCnt = iArg;
				}
			}
		}
	}
	
	public List<String> getParameters()
	{
		List<String> params = new ArrayList<String>();
		
		for(int i=1; i< getUnnamedParsCnt(); ++i)
		{
			params.add( getParameter(i));
		}
		
		return params;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getParameter(int iPos)
	{
		return params.get(String.valueOf(iPos));
	}
	
	public String getParameter(String name)
	{
		return params.get(name);		
	}
	
	public boolean containsParameter(String name)
	{
		return Util.isNotNull(getParameter(name));
	}
	
	public int getUnnamedParsCnt()
	{
		return iUnnamedParsCnt;
	}
	
/*****************
 * PRIVATE SECTION	
 */
	private String name;
	private Map<String, String> params;
	private int iUnnamedParsCnt;


}
