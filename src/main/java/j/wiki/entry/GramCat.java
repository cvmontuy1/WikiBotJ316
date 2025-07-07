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

public class GramCat {
	public Subtype subtype;
	public Type type;
	public String name;
	public String wikitext;
	public String parent;		// Flexible form
	
	private static Map<GramCat.Subtype, GramCat> map = new HashMap<GramCat.Subtype, GramCat>();

	public static GramCat build(Subtype subtype)
	{
		GramCat gramcat;
		
		gramcat = map.get(subtype);
		if( gramcat == null )
		{
			gramcat = new GramCat();
			gramcat.parent = null;
			gramcat.subtype = subtype;
		
			switch(subtype)
			{
				case NOUN_PLURAL:
					gramcat.parent = "Forma flexiva";
					gramcat.name = "Forma sustantiva";
					gramcat.wikitext = gramcat.name;
					gramcat.type = Type.INFLECTION;
					break;
				case VERB_3S:
				case VERB_ING:
				case VERB_ED:				
					gramcat.parent = "Forma flexiva";
					gramcat.name = "Forma verbal";
					gramcat.wikitext = gramcat.name;	
					gramcat.type = Type.INFLECTION;
					break;
				case NOUN_PROPER:
				case PLACE:
				case SURNAME:
				case GN_MALE:
				case GN_FEMALE:
				case GN_UNISEX:			
				case GN_DIMINUTIVE:
					gramcat.name = "Sustantivo propio";
					gramcat.wikitext = "{{sustantivo propio|en}}";
					gramcat.type = Type.NOUN_PROPER;
					
					break;
				case ADVERB:
					gramcat.name = "Adverbio";
					gramcat.wikitext = "{{adverbio|en}}";
					gramcat.type = Type.ADVERB;
					break;
				case NOUN:
					gramcat.name = "Sustantivo";
					gramcat.wikitext = "{{sustantivo|en}}";
					gramcat.type = Type.NOUN;
					break;
				default:
					Util.reportError("Missing category for type:", subtype);
					break;
					
			} // end swith
			map.put(subtype, gramcat);
		}
		return gramcat;
	}
	
	public boolean isTypeEquals(Subtype subtype)
	{	
		return type == build(subtype).type;		
	}
	
	public static Subtype getSubytype(String value)
	{
		Subtype subtype;
		if( value != null )
		{
			value = value.toLowerCase();
		}
		
		switch(value)
		{
			case "noun":
				subtype = Subtype.NOUN;
				break;
			case "adverb":
				subtype = Subtype.ADVERB;
				break;
			case "verb":
				subtype = Subtype.VERB;
				break;
			case "proper noun":
				subtype = Subtype.NOUN_PROPER;
				break;				
			default:
				subtype = null;
				Util.reportError("Invalid subtype ", value);
				break;
		}
		return subtype;
	}

	public boolean isFlexibleForm()
	{
		return parent != null;
	}	
	
	public enum Type {
		NOUN,
		ADVERB,
		NOUN_PROPER,
		INFLECTION,
	}
	
	public enum Subtype 
	{
		NOUN,
		VERB,
		ADVERB,
		NOUN_PROPER,
		NOUN_PLURAL,
		VERB_3S,
		VERB_ING,
		VERB_ED,
		PLACE,
		SURNAME,		
		GN_MALE,		// male given name 
		GN_FEMALE,		// female given name
		GN_UNISEX,		// unisex given name
		GN_DIMINUTIVE,	// diminutive given name		
	}
}
