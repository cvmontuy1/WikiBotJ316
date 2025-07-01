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
	public int iEtim;	// Número de etimologia
	public enum Type { UNKNOWN, INITIALS };
	public Etimology.Type type;
	public String text;
	public List<EtimLang> etims;
	public List<GramCat> gramcats;
	
	public Etimology(int i)
	{
		iEtim = i;
		etims = new ArrayList<EtimLang>();
		gramcats = new ArrayList<GramCat>();
		type = Type.UNKNOWN;
	}
	
	public static Etimology buildInitials(int iEtim, String text)
	{
		Etimology etim = new Etimology(iEtim);
		etim.type = Type.INITIALS;
		etim.text = text;	
		return etim;
	}
	
	public void addEtimLang(EtimLang etimlang)
	{
		etims.add(etimlang);
	}
	
	public boolean hasDefs()
	{
		int iDefCnt = 0;
		for(GramCat gramcat: gramcats )
		{
			iDefCnt += gramcat.getDefsCount();
		}
		return iDefCnt > 0;
	}
	
	public int getCatsCount()
	{
		return gramcats.size();		
	}
	
	public int getDefsCount()
	{
		int iCount = 0;
		
		for(GramCat g: gramcats)
		{
			iCount = iCount + g.getDefsCount();
		}
		return iCount;
	}
	
	public GramCat getLastDef()
	{
		GramCat catgram = null;
		
		if( gramcats.size() > 0)
		{
			catgram = gramcats.get(gramcats.size()-1);
		}
		
		return catgram;
	}
	
	public void addSyn(String strSyn)
	{
		GramCat catgram = getLastDef();
		if( catgram != null)
		{
			catgram.addSyn(strSyn);
		}
	}
	
	public void addGramCat(GramCat catgram)
	{
		gramcats.add(catgram);
	}
	
	public boolean hasFlexibleForm()
	{
		boolean bHasFlexive = false;
		
		for(GramCat gramcat: gramcats)
		{
			if( gramcat.isFlexibleForm() )
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
		for(GramCat gramcat: gramcats)
		{
			if( iGramCat < gramcats.size()-1 )
			{
				if( gramcat.isFlexibleForm() )
				{
					if( !gramcats.get(iGramCat+1).isFlexibleForm() )
					{
						Collections.swap(gramcats, iGramCat, iGramCat+1);
					}
				}
			}
			++iGramCat;
		}
	}
	
	public void toWiki(StringBuilder buffer)
	{
		String PREFIX = "{{etimología|leng=en|";
		String PREFIX2 = "{{etim|leng=en|";
		String SUFIX = "}}";
		int iEtim;
		
		switch(type)
		{
			case Type.INITIALS:
				buffer.append(PREFIX);				
				buffer.append("siglas|").append(text).append("|nl=s");
				buffer.append(SUFIX);
				buffer.append(".");
				break;
			default:
				if( etims.size() > 0 )
				{
					iEtim = 0;
					for( EtimLang eti : etims)
					{
						if( iEtim == 0)
						{
							buffer.append(PREFIX);							
						}
						else
						{
							buffer.append(", y este ");
							buffer.append(PREFIX2);
						}
						buffer.append(eti.lang).append("|");
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
						buffer.append(SUFIX);						
						++iEtim;
					}
					buffer.append(".");					
				}
				else
				{
					buffer.append(PREFIX);					
					buffer.append(SUFIX);
					buffer.append(".");
				}
				break;
		}
		buffer.append(Util.LF);
	}
}
