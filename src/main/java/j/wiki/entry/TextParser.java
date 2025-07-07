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
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import j.wiki.Util;


/**
 * Tries to parser some estandard texts for diminutives and places
 */
public class TextParser {

	public static String isDiminutive(String text)
	{
		String words;
		Matcher matcher;
		
		words = null;
		matcher = PAT_DIM1.matcher(text);
		if( matcher.find() )
		{
			words = matcher.group();
		}
		else
		{
			matcher = PAT_DIM2.matcher(text);
			if( matcher.find())
			{
				words = matcher.group();
			}
		}		

		return words;
	}

	public static List<String> filter(String words)
	{
		return filter0(words, TO_IGNORE);
	}
	
	public static List<String> filterConnectors(String words)
	{
		return filter0(words, CONNECTORS);
	}

	

/*************
 * PRIVATE SECITON
 */
	
	private static List<String> filter0(String words, String[] filter)
	{
		List<String> filtered;		
		String[] words2;
		String strLC;
		boolean bIgnore;

		words2 = split(words);
		filtered = new ArrayList<String>();
		
		for(String w: words2)
		{
			strLC = w.toLowerCase();
			bIgnore = false;
			for( String test: filter)
			{
				if( test.equals(strLC) )
				{
					bIgnore = true;
					break;
				}
			}
			if( !bIgnore )
			{
				if( !filtered.contains(w) )
				{
					filtered.add(w);
				}
			}
		}		
		
		return filtered;
	}
	
	
	private static String[] split(String str)
	{
		String[] words;
		String regex = "(\\s*,\\s+of\\s+|\\s*,\\s*|\\s+and\\sof\\s+|\\s+and\\s+|\\s+or\\s+|\\s+or\\s?,|\\s+of\\s+|:|;|\\.|\\[\\[|\\]\\]|\\s+)";
		words = null;
		if( str != null )
		{
			if( str.contains(" and ") || str.contains(" or ") || str.contains(",") || str.contains(" of ") || str.contains(" the ") || str.contains(" a ") || str.contains(" an "))
			{
				words = str.split(regex);
				for(int iWord=0; iWord < words.length; ++iWord)
				{
					words[iWord] = Util.trim(words[iWord]);
				}
			}
			else
			{
				words = new String[1];
				words[0] = str;
			}
		}
		else
		{
			Util.reportError("split(null)");
		}
		
		return words;
	}
	
    private final static String WORDS = "([A-Za-z0-9\\-áéíóúñÑàèìòùäëïöüâêîôûæœçÆŒÇ&\\s\\,\\.\\:\\;]+)";	
	private final static Pattern PAT_DIM1 = Pattern.compile("A?\\s+diminutive\\s+of\\s+" + WORDS + "+", Pattern.MULTILINE);	
	private final static Pattern PAT_DIM2 = Pattern.compile("A?\\s+shortening\\s+of\\s+" + WORDS + "+", Pattern.MULTILINE);
	
	private final static String[] LOC_ADJ = {"inhabited", ""};
	private final static String[] CONNECTORS = { "a", "an", "in", "on", "of", "and", "or", "from", "the"};
	private final static String[] NO_NOUNS = {
			"female", "male", "ambiguous", "unisex",
			"always", "ever", "frequently", "generally", "hardly", "normally", "occasionally", "often", "rarely", "seldom", "sometimes", "usually",
			"also", "dim.", "diminutive", "given", "less", "like", "more", "name", "names", "neither", "shortening", "related", "then", "them" 
			};	
	
	private final static String[] TO_IGNORE;
	static 
	{
		List<String> words = new ArrayList<String>();
		
		words.addAll(Arrays.asList(CONNECTORS));
		words.addAll(Arrays.asList(NO_NOUNS));
		TO_IGNORE = words.toArray(new String[0]);
	}
	
	
}
