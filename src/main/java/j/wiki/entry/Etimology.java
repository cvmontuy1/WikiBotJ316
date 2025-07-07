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
import java.util.Collections;
import java.util.List;

import j.wiki.Util;

public class Etimology {
	private int iEtim;	// Número de etimologia
	public enum Type { UNKNOWN, INITIALS };
	public Etimology.Type type;
	public String text;
	public List<EtimLang> etims;
	public List<GramCatContainer> gc_conts;
	
	public Etimology(int i)
	{
		iEtim = i;
		etims = new ArrayList<EtimLang>();
		gc_conts = new ArrayList<GramCatContainer>();
		type = Type.UNKNOWN;
	}
	
	public static Etimology buildInitials(int iEtim, String text)
	{
		Etimology etim = new Etimology(iEtim);
		etim.type = Type.INITIALS;
		etim.text = text;	
		return etim;
	}
	
	public int getEtimId()
	{
		return iEtim;
	}
	public void setEtimId(int iEtim)
	{
		this.iEtim = iEtim; 
	}
	
	public void add(GramCatContainer gc_cont)
	{
		gc_conts.add(gc_cont);
	}
	
	public void addEtimLang(EtimLang etimlang)
	{
		if( etimlang.isComplete() )
		{
			etims.add(etimlang);
		}
	}
	
	public boolean hasDefs()
	{
		int iDefCnt = 0;
		for(GramCatContainer gc_cont: gc_conts )
		{
			iDefCnt += gc_cont.getDefsCount();
		}
		return iDefCnt > 0;
	}
	
	public int getCatsCount()
	{
		return gc_conts.size();		
	}
	
	public int getDefsCount()
	{
		int iCount = 0;
		
		for(GramCatContainer gc_cont: gc_conts)
		{
			iCount = iCount + gc_cont.getDefsCount();
		}
		return iCount;
	}
	
	public String getDefTxt(int iDef)
	{
		StringBuilder buffer = new StringBuilder();
		Definition def;
		List<Definition> defs = new ArrayList<Definition>();
		
		for(GramCatContainer gc_cont: gc_conts)
		{
			for(Definition def2: gc_cont.defs)
			{
				if(! def2.hasDefChildren() )
				{
					defs.add(def2);
				}
			}
		}
		def = defs.get(iDef);
		def.toWiki(buffer,  1);
		
		return buffer.toString();
	}
	
	public GramCatContainer getLastDef()
	{
		GramCatContainer catgram = null;
		
		if( gc_conts.size() > 0)
		{
			catgram = gc_conts.get(gc_conts.size()-1);
		}
		
		return catgram;
	}
	
	public void addSyn(String strSyn)
	{
		GramCatContainer catgram = getLastDef();
		if( catgram != null)
		{
			catgram.addSyn(strSyn);
		}
	}
	
	public void addGramCat(GramCatContainer catgram)
	{
		gc_conts.add(catgram);
	}
	
	public boolean hasFlexibleForm()
	{
		boolean bHasFlexive = false;
		
		for(GramCatContainer gc_cont: gc_conts)
		{
			if( gc_cont.isFlexibleForm() )
			{
				bHasFlexive = true;
				break;
			}
		}
		return bHasFlexive;
	}
	
	public void sort()
	{
		int iGramCat = 0;
		for(GramCatContainer gc_container: gc_conts)
		{
			if( iGramCat < gc_conts.size()-1 )
			{
				if( gc_container.isFlexibleForm() )
				{
					if( !gc_conts.get(iGramCat+1).isFlexibleForm() )
					{
						Collections.swap(gc_conts, iGramCat, iGramCat+1);
					}
				}
			}
			++iGramCat;
		}
	}
	
	public void toWiki(StringBuilder buffer)
	{
		final String WIKI_PREFIX = "{{etimología|leng=en|";
		final String WIKI_PREFIX2 = "{{etim|leng=en|";
		final String WIKI_SUFIX = "}}";
		int iEtim;
		
		switch(type)
		{
			case Type.INITIALS:
				buffer.append(WIKI_PREFIX);				
				buffer.append("siglas|").append(text).append("|nl=s");
				buffer.append(WIKI_SUFIX);
				buffer.append(".");
				break;
			default:
				if( etims.size() > 0 )
				{
					iEtim = 0;
					for( EtimLang eti : etims)
					{
						if( eti.type == EtimLang.Type.SUFFIX )
						{
							buffer.append(WIKI_PREFIX);
							buffer.append("sufijo|");
							buffer.append(eti.text);
							buffer.append("|");
							buffer.append(eti.suffix);
							buffer.append(WIKI_SUFIX);							
						}
						else
						{
							if( iEtim == 0)
							{
								buffer.append(WIKI_PREFIX);
							}
							else
							{
								buffer.append(", y este ");
								buffer.append(WIKI_PREFIX2);
							}
							buffer.append(eti.getLang()).append("|");
							if( !Util.isLinked(eti.text) && Util.isSingleWord(eti.text) )
							{
								buffer.append(Util.link(eti.text));
							}
							else
							{
								buffer.append(eti.text);
							}
							buffer.append("|nl=s");		
							if( Util.isNotNullOrEmpty(eti.transliteration) )
							{
								buffer.append("|tr=").append(eti.transliteration);
							}
							buffer.append(WIKI_SUFIX);
						}						
						++iEtim;
					}
					buffer.append(".");					
				}
				else
				{
					buffer.append(WIKI_PREFIX);					
					buffer.append(WIKI_SUFIX);
					buffer.append(".");
				}
				break;
		}
		buffer.append(Util.LF);
	}
}
