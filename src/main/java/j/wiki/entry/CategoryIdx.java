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
	public GramCat.Subtype subtype;
	public int idx;
	
	public CategoryIdx(String strValue)
	{
		char[] chars = strValue.toCharArray();
		int iChar;
		boolean bFound;
		String strSubtype;
		String strIdx;
		
		iChar = 0;
		bFound = false;
		for( char c: chars)
		{
			if( Character.isDigit(c) )
			{
				bFound = true;
				break;
			}
			++iChar;
		}
		
		if( bFound )
		{
			strSubtype = strValue.substring(0, iChar);
			strIdx = strValue.substring(iChar);
		}
		else
		{
			strSubtype = strValue.trim();
			strIdx = "0";
		}
		
		try
		{

			subtype = GramCat.getSubytype(strSubtype);			
			idx = Integer.parseInt(strIdx);
		}
		catch(Exception ex)
		{
			idx = 0;
			Util.reportError(ex, "CategoryIdx invalid", strValue);
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
