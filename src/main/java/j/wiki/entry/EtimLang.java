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

import j.wiki.Util;

public class EtimLang {
	public enum Type { BORROWED, SUFFIX, PREFIX, DEFAULT};
	public Type type;
	private String lang;		
	public String text;
	public String suffix;
	public String transliteration; // transliteration
	
	public EtimLang(Type type, String lang, String text)
	{
		this.type = type;
		if( mapLang.containsKey(lang) )
		{
			this.lang = mapLang.get(lang);
		}
		else
		{
			this.lang = lang;
		}
		
		this.text = text;
	}

	public EtimLang(Type type, String lang, String text, String tran)
	{
		this(type, lang, text);
		this.transliteration = tran;
	}
	
	public static EtimLang buildSuffix(String lang, String text, String suffix)
	{
		EtimLang etimlang;
		
		etimlang = new EtimLang(Type.SUFFIX, lang, text);
		etimlang.suffix = suffix;
		
		return etimlang;
	}
	
	public boolean isComplete()
	{
		return Util.isNotNullOrEmpty(lang) && Util.isNotNullOrEmpty(text) && !"-".equals(text); 
	}
	
	public String getLang()
	{
		return lang;
	}
	
	private static Map<String, String> mapLang = new HashMap<String, String>();
	static
	{
		mapLang.put("xno", "fro");
	};
}
