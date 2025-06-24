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
package j.wiki;

public class Util {

	public static void report(Object... objs)
	{
		StringBuilder buffer = new StringBuilder();
		for(Object o: objs)
		{
			if( o != null)
			{
				buffer.append(o.toString());
			}
		}
		System.out.println(buffer.toString());
	}
	
	public static void reportError(Object... objs)	
	{
		reportError(null, objs);
	}

	public static void reportError(Exception ex, Object... objs)	
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("Error at ");
		for(Object o: objs)
		{
			if( o != null)
			{
				buffer.append(o.toString());
			}
		}
		if( ex != null)
		{
			buffer.append(" -> " ).append(ex.toString());
		}
		System.out.println(buffer.toString());
		
	}
	
	public static boolean isNullOrEmpty(String str)
	{
		return str == null || str.isEmpty();
	}
	
	
	public static boolean isNotNull(String str)
	{
		return str != null && str.length() > 0;
	}
	
	public static String trim(String str)
	{
		String strResult;
		
		if( str != null)
		{
			strResult = str.trim();
		}
		else
		{
			strResult = "";
		}
		
		return strResult;
	}
	
	public static String substring(String str, int iStart)
	{
		String substring = null;
		
		if( str.length() > iStart+1)
		{
			substring = str.substring(iStart+1);
		}
		return substring;
	}	

	public final static String LF = "\r\n";
}
