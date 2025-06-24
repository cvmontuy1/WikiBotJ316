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
	public List<Etim> etims;
	public List<GramCat> gramcats;
	
	public Etimology(int i)
	{
		iEtim = i;
		etims = new ArrayList<Etim>();
		gramcats = new ArrayList<GramCat>();
	}
	
	public boolean hasDefs()
	{
		return gramcats.size() > 0;
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
		
		if( etims.size() == 0 )
		{
			buffer.append("{{etimología|leng=en|}}.").append(Util.LF);
		}
		else
		{
			
		}
	}
}
