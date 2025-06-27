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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.wikipedia.Wiki;
import j.wiki.ITask;
import j.wiki.Util;
import j.wiki.template.TemplateTransf;

/**
 * Cambia la plantilla ejemplo y trad por ejemplo
 * @author CarlosVM
 */
public class TskChgTemplateExample
implements ITask
{
	final static String TEMPLATE_OLD = "Template:ejemplo_y_trad";
	final static String ENGLISH_ENTRY = "{{lengua|en}}";
	private Wiki wiki;	
	
	public TskChgTemplateExample(Wiki _wiki)
	{
		wiki = _wiki;
	}

	public List<List<String>> getEntries()
	throws IOException
	{
		return wiki.whatTranscludesHere(List.of(TEMPLATE_OLD), Wiki.MAIN_NAMESPACE );
	}

	
	/*
	 * Returns true if the edition was made
	 */
	public boolean processEntry(String strTitle)
	{
		String strPageContent = null;
		Pattern pattern;
		Matcher matcher;
		int iIdxFound;
		int iBraCnt;
		char[] charArrayContent;
		StringBuilder buffer;
		Fix fix;
		List<Fix> fixes = new ArrayList<Fix>();
		boolean bProcOk = false;
		
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
			charArrayContent = strPageContent.toCharArray();
			
			if(strPageContent.contains(ENGLISH_ENTRY) )
			{
				pattern = Pattern.compile("\\u007b\\u007bejemplo_y_trad[^\\u007d.]*");
				matcher = pattern.matcher(strPageContent);
				while( matcher.find() )
				{
					iIdxFound = strPageContent.indexOf(matcher.group());
					if( iIdxFound > 0)
					{
						buffer = new StringBuilder();
						iBraCnt = 0;
						while(iIdxFound < charArrayContent.length )
						{
							buffer.append(charArrayContent[iIdxFound] );
							if( charArrayContent[iIdxFound ] == '}' )
							{
								--iBraCnt;
							}
							if( charArrayContent[iIdxFound ] == '{' )
							{
								++iBraCnt;
							}
							if( iBraCnt == 0)
							{
								break;
							}
							++iIdxFound;
						}
						fix = new Fix();
						fix.strOld = buffer.toString();						
						fix.strNew = templateTransform(buffer.toString());
						
						if(  fix.isOk() )
						{
							fixes.add(fix);
						}

					}
				} // while matches			
				
			} // end if english
		}
		
		if( !fixes.isEmpty())
		{				
			for(Fix fix2: fixes)
			{
				strPageContent = strPageContent.replace(fix2.strOld, fix2.strNew);
			}
	
			try
			{
				Util.report("Procesing:", strTitle);
				wiki.edit(strTitle,  strPageContent,  "Sustitución de plantilla ejemplo_y_trad por ejemplo");
				bProcOk = true;
			}
			catch(Exception ex)
			{
				Util.reportError(ex, "editando página", strTitle);
			}
		}
		return bProcOk;
	}
	
	private static String templateTransform(String strTemplate) 
	{
		String strEjemplo = "";
		Map<String, String> arguments = TemplateTransf.parse(strTemplate);
		if( arguments.containsKey("URLcapítulo") && arguments.containsKey("título") )
		{
			strEjemplo = TemplateTransf.buildEjemplo(arguments);
			contentTest.add(strEjemplo);
		}
		return strEjemplo;
	}
	


	static List<String> contentTest = new ArrayList<String>();
	
	private static class Fix
	{
		String strOld;
		String strNew;
		
		public boolean isOk()
		{
			return Util.isNotNullOrEmpty(strOld) && Util.isNotNullOrEmpty(strNew);
		}
	}
    
}
