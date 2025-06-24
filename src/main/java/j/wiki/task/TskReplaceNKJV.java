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
package j.wiki.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.wikipedia.Wiki;

import j.wiki.ITask;
import j.wiki.Util;

public class TskReplaceNKJV
implements ITask
{
	final private static String TEMPLATE_OLD = "Template:NKJV";	
	final private static String OLD_TEXT= "{{NKJV}}";
	private Wiki wiki;
	public TskReplaceNKJV(Wiki _wiki)
	{
		wiki = _wiki;
	}
	
	public List<List<String>> getEntries()
	throws IOException
	{
		return wiki.whatTranscludesHere(List.of(TEMPLATE_OLD), Wiki.MAIN_NAMESPACE );
	}
	
	public boolean processEntry(String strTitle)
	{
		boolean bResult = false;
		
		String strPageContent = null;
		
		try
		{
			List<String> titles = new ArrayList<String>();
			titles.add(strTitle);
			List<String> content = wiki.getPageText(titles);
	
			if( content != null && !content.isEmpty() )
			{
				strPageContent = content.get(0);
			}
		}
		catch(Exception ex)
		{
			Util.reportError(ex, "getPageText", strTitle);
		}
		
		if( strPageContent != null)
		{
			if( strPageContent.contains(OLD_TEXT) )
			{
				
				bResult = true;
			}	
			
		}
		
		
		
		return bResult;
	}
}
