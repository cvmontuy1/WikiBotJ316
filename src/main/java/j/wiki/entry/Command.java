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

public class Command {
	public String entry;
	public List<Definition> defs;
	
	public Command(String[] strArray)
	{   	
    	entry = Util.trim( strArray[0] );
    	defs = new ArrayList<Definition>();
    	
    	for(int i=1; i<strArray.length; ++i)
    	{
    		if( Util.isNotNullOrEmpty(strArray[i]))
    		{
    			buildDefinition(defs, strArray[i], i);
    		}
    	}
	}
	
	public boolean hasDefs()
	{
		return defs.size()>0;
	}
	
	private Definition buildDefinition(List<Definition> defs, String strValue, int iPos)
	{
		Definition def = null;
		String[] words;
		CategoryIdx catidx;
		if( strValue != null && strValue.contains("="))
		{
			words = strValue.split("=");
			for(int i=0; i<words.length; ++i)
			{
				words[0] = words[0].trim();				                      
			}
			catidx = Entry.getCategory(words[0]);
			if( catidx != null)
			{
				def = new Definition(catidx.type, words[1], true);
				def.intIdx = catidx.idx;
				defs.add(def);
			}
		}
		else
		{
			Util.reportError("building definition: Missing '=' on <", strValue, "> at position:", iPos);
		}
		return def;
	}
	
}
