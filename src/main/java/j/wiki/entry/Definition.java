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


public class Definition {	
	public GramCat gramcat;  // Calculado
	public String text;
	public String type;		// 
	public boolean bLiteral;
	public boolean bNoLink;
	public int intIdx;
	public List<String> syns;
	private DefContainer defcontainer;
	
	public Definition(String type, String stext)	
	{	
		this.type = type;
		this.text = stext;
		bLiteral = false;
		this.defcontainer = null;
	
		syns = new ArrayList<String>();		
	}
	
	public Definition(String type, String stext, boolean bLiteral)	
	{
		this(type, stext);
		this.bLiteral = bLiteral;	
	}
	
	public void setContainer(DefContainer container)
	{
		if( container != null)
		{
			this.defcontainer = container;
			container.addDefinition(this);
		}		
	}
	
	public boolean hasDefChildren()
	{
		boolean bHasChildren = false;
		
		if( defcontainer != null )
		{
			if( defcontainer.hasChildren() )
			{
				bHasChildren = true;
			}
		}
		return bHasChildren;
	}

	
	public boolean equals(Definition def)
	{
		boolean bEq;
		
		if( text == null )
		{
			bEq = type.equals(def.type) && text == def.text;
		}
		else
		{
			bEq =  type.equals(def.type) && text.equals(def.text);
		}
		
		return bEq;
	}
	
	public void addSyn(String strSyn)
	{
		boolean bFound;
		
		bFound = false;
		if( ! Util.isNullOrEmpty(strSyn) )
		{
			for(String str: syns)
			{
				if(str.equals(strSyn) )
				{
					bFound = true;
					break;
				}
			}
	
			if( !bFound )
			{
				syns.add(strSyn);
			}
		}
	}
	
	public boolean isFlexibleForm()
	{
		return GramCat.isFlexibleForm(type);
	}

	public void setGramCat(GramCat gramcat)
	{
		if( this.gramcat == null || this.gramcat.type.equals(gramcat.type))
		{
			this.gramcat = gramcat;
		}
		else
		{
			Util.reportError("Definition.setGramCat types are different ");
		}
	
	}

	public int toWiki(StringBuilder buffer, int iDef)
	{
		
		switch(type)
		{
			case Entry.T_NOUN_PLURAL:
				appendLevel4(buffer, gramcat.wikitext);
				
				buffer.append(";").append(iDef).append(": ");
				if( bLiteral )
				{
					buffer.append(text).append(Util.LF);
				}
				else
				{
					buffer.append("{{forma sustantivo plural|").append(text).append("|en}}.").append(Util.LF);
				}
				appendSyns(buffer);				
				++iDef;				
				break;
			case Entry.T_PLACE:
				buffer.append(";").append(iDef).append(": ");
				buffer.append(text).append(Util.LF);
				++iDef;				
				break;
			case Entry.T_PRESENT_3S:
				appendLevel4(buffer, gramcat.wikitext);				
				buffer.append(";").append(iDef).append(": ");
				buffer.append("{{forma verbo-en|").append(text).append("|3s|presente}}.").append(Util.LF);
				++iDef;				
				break;
			case Entry.T_SURNAME:
				buffer.append(";").append(iDef).append(": ");
				buffer.append("{{apellido|leng=en}}.").append(Util.LF);
				++iDef;				
				break;
			case Entry.T_GN_MALE:
				buffer.append(";").append(iDef).append(": ");
				buffer.append("{{antropónimo masculino|leng=en}}.").append(Util.LF);
				++iDef;				
				break;
			case Entry.T_GN_FEMALE:
				buffer.append(";").append(iDef).append(": ");
				buffer.append("{{antropónimo femenino|leng=en}}.").append(Util.LF);
				++iDef;				
				break;
			case Entry.T_UNISEX1:
			case Entry.T_UNISEX2:				
				buffer.append(";").append(iDef).append(": ");
				buffer.append("{{antropónimo ambiguo|leng=en}}.").append(Util.LF);
				++iDef;				
				break;
			case Entry.T_GN_DIM:
				buffer.append(";").append(iDef).append(": ");					
				buffer.append("{{hipocorístico|leng=en|").append(text).append("}}.").append(Util.LF);
				++iDef;					
				break;	
			case Entry.T_NOUN_PROPER:
				buffer.append(";").append(iDef).append(": ");
				buffer.append(text).append(Util.LF);
				
				appendSyns(buffer);
				++iDef;
				break;
			case Entry.T_NOUN:
				buffer.append(";").append(iDef).append(": ");
				if(bNoLink)
				{
					buffer.append(text).append(".").append(Util.LF);
				}
				else
				{
					buffer.append("{{plm|").append(text).append("}}.").append(Util.LF);
				}
				appendSyns(buffer);				
				++iDef;				
				break;
			case Entry.T_ADVERB:
				buffer.append(";").append(iDef).append(": ");
				if(bNoLink)
				{
					buffer.append(text).append(".").append(Util.LF);
				}
				else
				{
					buffer.append("{{plm|").append(text).append("}}.").append(Util.LF);
				}
				appendSyns(buffer);			
				
				++iDef;
				break;
			default:
				Util.reportError("Mssing defintion type:", type);
				break;
				
		}
		
		return iDef;
	}
	
	private void appendSyns(StringBuilder buffer)
	{
		if( syns.size() > 0 )
		{
			buffer.append(Util.LF).append("{{sinónimo|leng=en");

			for(String syn: syns)
			{
				buffer.append("|");
				buffer.append(syn);
			}
			buffer.append(Util.LF).append("}}").append(Util.LF);
		}		
	}

	private void appendLevel4(StringBuilder buffer, String title)
	{
		if( !title.equals(Entry.getLastL4()) )
		{
			buffer.append(Entry.IDENT4).append(" ").append(title).append(" ").append(Entry.IDENT4).append(Util.LF);
		}
		Entry.setLastL4(title);
	}
	

}

