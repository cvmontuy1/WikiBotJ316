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

package j.wiki.template;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import j.wiki.Util;

public class TemplateTransf {

		public static Map<String,String> parse(String strInput)
		{
			String strTemplate;
			int iLen;
			int iArg;
			StringBuilder paramBuilder;
			String[] segments;
			String[] param_value;
			Map<String,String> map = new HashMap<String, String>();
			
			strTemplate = strInput.trim();
			iLen = strTemplate.length();
			
			strTemplate = strTemplate.substring(2, iLen-2);
			segments = strTemplate.split("\\|");
			
			iArg = 0;
			for(int iSeg=0; iSeg<segments.length; ++iSeg)
			{
				if( segments[iSeg].contains("="))
				{
					param_value = segments[iSeg].split("=");
					paramBuilder = new StringBuilder();
					for(int i=1; i<param_value.length; ++i)
					{
						if( i>1)
						{
							paramBuilder.append("=");
						}
						paramBuilder.append(param_value[i]);
					}
					map.put(param_value[0], paramBuilder.toString());
					
				}
				else
				{
					if(iArg == 0)
					{
						map.put("TEMPLATE_NAME", segments[iSeg]);
					}
					else
					{
						map.put(String.valueOf(iArg), segments[iSeg]);
					}
					++iArg;
				}
			}
			return map;
		}
		
		public static String buildEjemplo(Map<String, String> map)
		{
			StringBuilder buffer = new StringBuilder();
			String strTradPasaje;
			
			strTradPasaje = map.get("capítulo2");
			if( j.wiki.Util.isNullOrEmpty(strTradPasaje) )
			{
				strTradPasaje = map.get("capítulo");
			}
			
			buffer.append("{{ejemplo|");
			buffer.append(map.get("1"));
			buffer.append("|c=libro");
			if( map.get("edición").contains("New King James") )
			{
				buffer.append("|v={{NKJV}}");
			}
			else
			{
				buffer.append("|v=").append(map.get("edición"));
			}
			buffer.append("|t=Bible");
			buffer.append("|pasaje=").append(map.get("capítulo"));
			buffer.append("|u=").append( improveURL(map.get("URLcapítulo")));
			buffer.append("|trad=").append(map.get("2"));
			buffer.append("|tradc=libro");
			buffer.append("|tradv=").append(map.get("edición2"));
			buffer.append("|tradt=Biblia");
			buffer.append("|tradpasaje=").append(strTradPasaje);
			buffer.append("|tradu=").append( improveURL(map.get("URLcapítulo2")) );
			

			buffer.append("}}");
			return buffer.toString();
		}
		
		/**
		 * Cambia el que la URL no apunte al versiculo sino al cápitulo.
		 * @param url  url to change
		 * @return new url
		 */
		public static String improveURL(String url)
		{
			String strURL;
			String strGroup;
			String strNewGroup;
			int iIdx;
			Matcher matcher; 
			
			// %204%3A4&version
			Pattern pattern = Pattern.compile("[\\u002520|\\u002b][0123456789]{1,3}\\u00253A[0123456789]{1,3}");
			matcher = pattern.matcher(url);
			strURL = url;
			if( matcher.find() )
			{
				strGroup = matcher.group();
				iIdx = strGroup.indexOf("%3A");
				if( iIdx > 0 )
				{
					strNewGroup = strGroup.substring(0, iIdx);
					strURL = url.replace(strGroup, strNewGroup);
				}
				
			}

			return strURL;
		}

}
