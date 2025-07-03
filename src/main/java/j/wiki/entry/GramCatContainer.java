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

public class GramCatContainer {
	public GramCat gramcat;
	public List<Definition> defs;
	
	public GramCatContainer()
	{
		defs = new ArrayList<Definition>();
		gramcat = null;
	}
	
	public static GramCatContainer build(GramCat.Subtype subtype)
	{
		GramCatContainer gc_container = new GramCatContainer();
		gc_container.gramcat = GramCat.build(subtype);
		return gc_container;
	}
	
	public int getDefsCount()
	{
		int iDefCnt = 0;
		
		for( Definition def: defs)
		{
			if(!def.hasDefChildren() )
			{
				++iDefCnt;
			}
		}
		return iDefCnt;
	}
	
	public boolean isFlexibleForm()
	{
		return gramcat != null && gramcat.isFlexibleForm();
	}	

	public static boolean isFlexibleForm(GramCat.Subtype subtype)
	{
		return GramCat.build(subtype).isFlexibleForm();	
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
		}
		
		if( gramcat == null)
		{
			gramcat = GramCat.build(def.subtype);
		}
	}
	
	public void addSyn(String strSyn)
	{
		
		if( defs.size() > 0)
		{
			defs.get(defs.size()-1).addSyn(strSyn);
		}
	}
	
	public boolean isTypeEquals(GramCat.Subtype subtype)
	{
		boolean bEquals = false;
		if( gramcat != null )
		{
			bEquals = gramcat.isTypeEquals(subtype);
		}
		return bEquals;
	}
	
	public boolean canContain(GramCat.Subtype subtype)
	{
		boolean bCan = false;
		
		if( gramcat == null)
		{
			bCan = true;
		}
		else
		{
			if( gramcat.isTypeEquals(subtype) )
			{
				bCan = true;
			}
			else if( isFlexibleForm() && isFlexibleForm(subtype) )
			{
				bCan = true;
			}
		}
		
		return bCan;
	}

	void toWiki(Entry entry, StringBuilder buffer)
	{
		buffer.append(Util.LF);
		if( gramcat != null)
		{
			if( gramcat.parent != null)
			{
				buffer.append(Constants.IDENT3).append(" ").append(gramcat.parent).append(" ").append(Constants.IDENT3);
			}
			else
			{
				buffer.append(Constants.IDENT4).append(" ").append(gramcat.wikitext).append(" ").append(Constants.IDENT4);
				if( gramcat.type == GramCat.Type.ADVERB )
				{
					if( Util.isNotNullOrEmpty(entry.adv_comp) )
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
								if( Util.isNotNullOrEmpty(entry.adv_sup) )
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
}
