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
	public GramCat gramcat;  
	private TextSrc txtsrc;
	public GramCat.Subtype subtype;		// 
	public boolean bLiteral;
	public boolean bNoLink;
	public int intIdx;
	public List<String> syns;
	private DefContainer defcontainer;
	
	public Definition( GramCat.Subtype subtype, TextSrc txtsrc, boolean bLiteral)	
	{	
		this.subtype = subtype;
		this.txtsrc = txtsrc;
		this.bLiteral = bLiteral;
		this.defcontainer = null;
		gramcat = GramCat.build(subtype);
		syns = new ArrayList<String>();		
	}	

	public Definition(GramCat.Subtype subtype, String text, boolean bLiteral)	
	{
		this(subtype, new TextSrcSimple(text), bLiteral);
	}
	
	public void setContainer(DefContainer container)
	{
		if( container != null)
		{
			this.defcontainer = container;
			container.addDefinition(this);
		}		
	}
	
	public boolean hasParent()
	{
		return (defcontainer!=null && defcontainer.hasParent());
	}
	
	public boolean hasParentText()
	{
		boolean bHasText = false;
		Definition definition;
		
		if( hasParent() )
		{
			definition = defcontainer.getParent().getDefinition();
			if( definition != null)
			{
				bHasText = !definition.isEmpty();
			}
		}
		return bHasText; 
	}
	
	public String getParentText()
	{
		return defcontainer.getParent().getDefinition().getText();
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
	
	
	public boolean isEmpty()
	{
		return txtsrc == null || txtsrc.isEmpty();
	}
	
	public TextSrc getTextSrc()
	{
		return txtsrc;
	}
	
	public String getText()
	{
		return txtsrc.getText(hasDefChildren());
	}

	
	public boolean equals(Definition def)
	{
		boolean bEq;
		
		if( txtsrc.isEmpty() )
		{
			bEq = subtype == def.subtype && def.isEmpty();
		}
		else
		{
			bEq =  subtype == def.subtype && txtsrc.equals(def.getTextSrc());
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
		return GramCatContainer.isFlexibleForm(subtype);
	}

	
	public int toWiki(StringBuilder buffer, int iDef)
	{		
		switch(subtype)
		{
			case NOUN_PLURAL:
				appendLevel4(buffer, gramcat.wikitext);
				
				buffer.append(";").append(iDef).append(": ");
				if( bLiteral )
				{
					buffer.append(getText()).append(Util.LF);
				}
				else
				{
					buffer.append("{{forma sustantivo plural|").append(getText()).append("|en}}.").append(Util.LF);
				}
				appendSyns(buffer);				
				++iDef;				
				break;
			case PLACE:
				buffer.append(";").append(iDef).append(": ");
				buffer.append(getText());
				if( hasParentText() )
				{
					if( !buffer.toString().endsWith(getParentText()) )
					{
						buffer.append(", ").append(getParentText());
					}
				}
				buffer.append(".");
				buffer.append(Util.LF);
				++iDef;				
				break;
			case VERB_3S:
				appendLevel4(buffer, gramcat.wikitext);				
				buffer.append(";").append(iDef).append(": ");
				buffer.append("{{forma verbo-en|").append(getText()).append("|3s|presente}}.").append(Util.LF);
				++iDef;				
				break;
			case VERB_ING:
				appendLevel4(buffer, gramcat.wikitext);				
				buffer.append(";").append(iDef).append(": ");
				buffer.append("{{participio|leng=en|").append(getText()).append("|presente}}.").append(Util.LF);
				++iDef;				
				break;
			case VERB_ED:
				appendLevel4(buffer, gramcat.wikitext);				
				buffer.append(";").append(iDef).append(": ");
				buffer.append("{{forma verbo-en|").append(getText()).append("|pasado}}.").append(Util.LF);
				++iDef;
				buffer.append(";").append(iDef).append(": ");				
				buffer.append("{{forma verbo-en|").append(getText()).append("|participio}}.").append(Util.LF);			
				++iDef;				
				break;				
			case SURNAME:
				buffer.append(";").append(iDef).append(": ");
				buffer.append("{{apellido|leng=en}}.").append(Util.LF);
				++iDef;				
				break;
			case GN_MALE:
				buffer.append(";").append(iDef).append(": ");
				buffer.append("{{antropónimo masculino|leng=en}}.").append(Util.LF);
				++iDef;				
				break;
			case GN_FEMALE:
				buffer.append(";").append(iDef).append(": ");
				buffer.append("{{antropónimo femenino|leng=en}}.").append(Util.LF);
				++iDef;				
				break;
			case GN_UNISEX:			
				buffer.append(";").append(iDef).append(": ");
				buffer.append("{{antropónimo ambiguo|leng=en}}.").append(Util.LF);
				++iDef;				
				break;
			case GN_DIMINUTIVE:
				buffer.append(";").append(iDef).append(": ");					
				buffer.append("{{hipocorístico|leng=en|").append(getText()).append("}}.").append(Util.LF);
				++iDef;					
				break;	
			case NOUN_PROPER:
				buffer.append(";").append(iDef).append(": ");
				buffer.append(getText()).append(Util.LF);
				
				appendSyns(buffer);
				++iDef;
				break;
			case NOUN:
				buffer.append(";").append(iDef).append(": ");
				if(bNoLink)
				{
					buffer.append(getText()).append(".").append(Util.LF);
				}
				else
				{
					buffer.append("{{plm|").append(getText()).append("}}.").append(Util.LF);
				}
				appendSyns(buffer);				
				++iDef;				
				break;
			case ADVERB:
				buffer.append(";").append(iDef).append(": ");
				if(bNoLink)
				{
					buffer.append(getText()).append(".").append(Util.LF);
				}
				else
				{
					buffer.append("{{plm|").append(getText()).append("}}.").append(Util.LF);
				}
				appendSyns(buffer);			
				
				++iDef;
				break;
			default:
				Util.reportError("Mssing defintion type:", subtype);
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
			buffer.append(Constants.IDENT4).append(" ").append(title).append(" ").append(Constants.IDENT4).append(Util.LF);
		}
		Entry.setLastL4(title);
	}
	

}

