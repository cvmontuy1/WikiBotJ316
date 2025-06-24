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

import j.wiki.Util;

/**
 * Grammar category and index
 */
public class CategoryIdx {
	public String type;
	public int idx;
	
	public CategoryIdx(String strType, String strValue)
	{
		type = strType;
		try
		{
			idx = Integer.parseInt(strValue.substring(strType.length()));
		}
		catch(Exception ex)
		{
			idx = 0;
			Util.reportError(ex, "CategoryIdx invalid number");
		}
	}
	
	/*
	 * 
	 */
	public static CategoryIdx build(String strValue)
	{
		CategoryIdx catidx = null;
		
		return catidx; 
	}
}
