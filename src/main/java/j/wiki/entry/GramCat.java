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

public class GramCat {
	public String type;
	public String name;
	public String wikitext;
	public String parent;		// Flexible form

	public List<Definition> defs;
	
	public GramCat()
	{
		defs = new ArrayList<Definition>();
		name = null;
		wikitext = null;
		parent = null;
	}
	
	public static GramCat build(String type)
	{
		GramCat gramcat = new GramCat();
		gramcat.setCategoryTitles(type);
		return gramcat;
	}
	
	public boolean isFlexibleForm()
	{
		return parent != null;
	}	
	
	public void addDefinition(Definition def)
	{
		boolean bFound = false;
		
		for(Definition def0: defs)
		{
			if( def0.equals(def) )
			{
				bFound = true;
			}
		}
		if( !bFound )
		{
			defs.add(def);
			def.setGramCat(this);
		}
		
		if( name == null)
		{
			setCategoryTitles(def.type);
		}
	}
	
	public void addSyn(String strSyn)
	{
		
		if( defs.size() > 0)
		{
			defs.get(defs.size()-1).addSyn(strSyn);
		}
	}
	
	public boolean canContain(String type)
	{
		boolean bCan = false;
		GramCat catEntry;
		
		if( this.type == null)
		{
			bCan = true;
		}
		else
		{
			catEntry = GramCat.build(type);
			if( this.type.equals(catEntry.type) )
			{
				bCan = true;
			}
			else if( isFlexibleForm() && catEntry.isFlexibleForm() )
			{
				bCan = true;
			}
		}
		
		return bCan;
	}
	
	public void setCategoryTitles(String type)
	{
		parent = null;
		this.type = type;
		
		switch(type)
		{
			case Entry.T_NOUN_PLURAL:
				parent = "Forma flexiva";
				name = "Forma sustantiva";
				wikitext = name;

				break;
			case Entry.T_PRESENT_3S:
				parent = "Forma flexiva";
				name = "Forma verbal";
				wikitext = name;				
				break;
			case Entry.T_PLACE:
			case Entry.T_SURNAME:
			case Entry.T_GN_MALE:
			case Entry.T_GN_FEMALE:
			case Entry.T_UNISEX:
			case Entry.T_GN_DIM:
				name = "Sustantivo propio";
				wikitext = "{{sustantivo propio|en}}";
				type = Entry.T_NOUN_PROPER;
				
				break;
			case Entry.T_ADVERB:
				name = "Adverbio";
				wikitext = "{{adverbio|en}}";				
				break;
			case Entry.T_NOUN:
				name = "Sustantivo";
				wikitext = "{{sustantivo|en}}";
				break;
			default:
				Util.reportError("Missing category for type:", type);
				break;
				
		}	
	}		
		
	void toWiki(Entry entry, StringBuilder buffer)
	{
		buffer.append(Util.LF);
		if( parent != null)
		{
			buffer.append(Entry.IDENT3).append(" ").append(parent).append(" ").append(Entry.IDENT3);
		}
		else
		{
			buffer.append(Entry.IDENT4).append(" ").append(wikitext).append(" ").append(Entry.IDENT4);
			if( type.equalsIgnoreCase( Entry.T_ADVERB ) )
			{
				if( Util.isNotNull(entry.adv_comp) )
				{
					switch(entry.adv_comp)
					{
						case "-":
							buffer.append(Util.LF).append( "{{inflect.en.adv|-}}");
							break;
						case "+":
							buffer.append(Util.LF).append( "{{inflect.en.adv|+}}");							
							break;
						default:
							if( Util.isNotNull(entry.adv_sup) )
							{
								buffer.append(Util.LF).append( "{{inflect.en.adv|").append(entry.adv_comp).append("|").append(entry.adv_sup);								
							}
							break;
					}
				}
			}
		}
		
		buffer.append(Util.LF);
	}
}
