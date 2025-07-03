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
import j.wiki.parser.ParserEntry;
import j.wiki.parser.Token;
import j.wiki.parser.TokenType;
import j.wiki.task.TskImportEnglish;

public class Entry {
	public String lang;
	public String entry;
	public List<Pron> prons;
	public List<String> homophons;
	public boolean bWikipedia;	
	public List<Etimology> etims;
	public List<String> estrans;  // tomado de las traducciones
	public String adv_comp;
	public String adv_sup;
		
	public Entry()
	{
		etims = new ArrayList<Etimology>();
		prons = new ArrayList<Pron>();
		homophons = new ArrayList<String>();
		estrans = new ArrayList<String>();
		
		defcontL1	= null;
		defcontL2	= null;

		bWikipedia = false;
	}

//*********************************************************************	
// Audit functions
	
	public int Count()
	{
		return etims.size();
	}
	
	public int getCatsCount()
	{
		int iCount = 0;
		for(Etimology e: etims)
		{
			iCount = iCount + e.getCatsCount();
		}
		return iCount;
	}
	
	public int getDefsCount()
	{
		int iCount = 0;
		for(Etimology e: etims)
		{
			iCount = iCount + e.getDefsCount();
		}
		return iCount;		
	}
	
	public int getPronCount()
	{
		return prons.size();
	}
	
	public int getEtimCount()
	{
		return etims.size();
	}
	
	public boolean isComplete()
	{
		boolean bIsComplete;
		
		bIsComplete = false;
		if( Util.isNotNullOrEmpty(lang) && 
		    Util.isNotNullOrEmpty(entry) &&
		    etims.size() > 0 &&
		    hasDefs()
		  )
		{
			bIsComplete = true;
		}
		return bIsComplete;
	}
	
	public boolean hasDefs()
	{
		boolean bHas = false;
		
		for(Etimology etimology: etims)
		{
			if( etimology.hasDefs() )
			{
				bHas = true;
				break;
			}
		}
		return bHas;
	}
	
	public String getDefTxt(int iDef)
	{
		String strDef = "";
		
		if( iDef > 0)
		{
			--iDef;
		}
		for(Etimology etimology: etims)
		{
			if( etimology.getDefsCount() > iDef)
			{
				strDef = etimology.getDefTxt(iDef);
				break;
			}
			iDef = iDef - etimology.getDefsCount();
		}
		
		return strDef;
	}

	/**
	 * Generates wiki content
	 * @return
	 */
	public String toWiki()
	{
		StringBuilder buffer = new StringBuilder();
		int iPron, iAudio, iDef;
	
		buffer.append("{{creado_por_bot}}").append(Util.LF);
		buffer.append("{{desambiguación|}}").append(Util.LF);
		buffer.append(Constants.IDENT2).append(" {{lengua|en}} ").append(Constants.IDENT2).append(Util.LF);
		buffer.append("{{pron-graf|leng=en");
		
		iPron = 0;		
		for(Pron pron: prons)
		{
			for(int iIPA = 0; iIPA<pron.ipas.size(); ++iIPA)
			{
				if( iPron == 0 )
				{
					if( iIPA == 0)
					{
						buffer.append(Util.LF).append("|").append(pron.getIPAType(iIPA)).append("=");
					}
					else
					{
						buffer.append("|").append(pron.getIPAType(iIPA)).append(iIPA+1).append("=");					
					}
				}
				else
				{
					if( iIPA == 0)
					{
						buffer.append(Util.LF).append("|").append(iPron+1).append(pron.getIPAType(iIPA)).append("=");
					}
					else
					{
						buffer.append("|").append(iPron+1).append(pron.getIPAType(iIPA)).append(iIPA+1).append("=");					
					}					
				}
				buffer.append(pron.getIPAValue(iIPA));
			}
			iAudio = 0;
			for(String audio: pron.audios)
			{
				if( iPron == 0)
				{
					if( iAudio == 0 )
					{
						buffer.append("|audio=");
					}
					else
					{
						buffer.append("|audio").append(iAudio).append("=");
					}
				}
				else
				{
					if( iAudio == 0 )
					{
						buffer.append("|").append(iPron+1).append("audio=");
					}
					else
					{
						buffer.append("|").append(iPron+1).append("audio").append(iAudio+1).append("=");
					}					
				}
						
				buffer.append(audio);
				++iAudio;
			}
			if( iPron == 0)
			{
				if( Util.isNotNullOrEmpty(pron.name) )
				{
					buffer.append("|pron=").append(pron.getPronName());
				}
			}
			else
			{
				if( Util.isNotNullOrEmpty(pron.name) )
				{
					buffer.append("|").append(iPron+1).append("pron=").append(pron.getPronName());
				}				
			}
			++iPron;
		}
		buffer.append("}}").append(Util.LF);
	
		for( Etimology etimology: etims)
		{
			iDef = 1;
			if( !etimology.hasFlexibleForm() )
			{
				if( etimology.iEtim == 0)
				{
					buffer.append(Util.LF);
					buffer.append(Constants.IDENT3).append(" Etimología ").append(Constants.IDENT3).append(Util.LF);				
				}
				else
				{
					buffer.append(Util.LF);					
					buffer.append(Constants.IDENT3).append(" Etimología ").append(etimology.iEtim).append(Constants.IDENT3).append(Util.LF);
				}
				etimology.toWiki(buffer);
			}

			for(GramCatContainer catgram : etimology.gc_conts)
			{
				catgram.toWiki(this, buffer);
				for( Definition def: catgram.defs )
				{
					if( !def.hasDefChildren() )
					{
						iDef = def.toWiki(buffer, iDef);
					}
				}
			}
		}
		
		if( bWikipedia )
		{
			buffer.append(Util.LF);
			buffer.append("==== Véase también ====").append(Util.LF);
			buffer.append("{{w|leng=en}}").append(Util.LF);
		}

		buffer.append(Util.LF).append("== Referencias y notas ==").append(Util.LF);
		buffer.append("<references />").append(Util.LF);
		
		
		return buffer.toString();
	}
	

	public static void clear()
	{
		LAST_L4 = "";
	}
	
	public static String getLastL4()
	{
		return LAST_L4;
	}
	
	public static void setLastL4(String str)
	{
		LAST_L4 = str;
	}
	
	public void addNounPlural(int iEtim, String strPlural)
	{
		addDefinition(iEtim, GramCat.Subtype.NOUN_PLURAL, strPlural);
	}

	public void addVerbForm(int iEtim, GramCat.Subtype subtype, String strPlural)
	{
		addDefinition(iEtim, subtype, strPlural);
	}

	
	public void addDim(int iEtim, String strName)
	{
		List<String> words = TextParser.filter(strName);
		for( String name: words)
		{
			if( Util.isNotNullOrEmpty(name))
			{
				addDefinition(iEtim, GramCat.Subtype.GN_DIMINUTIVE, name);
			}
		}
	}
	
	public void addGramCat(int iEtim, GramCat.Subtype subtype)
	{
		Etimology etimology = null;
		GramCatContainer gc_container;		
		boolean bFound;
		
		while(etimology == null)
		{
			if( etims.size() <= iEtim )
			{
				int iSize = etims.size();
				for(int i= iSize;  i<iEtim+1; ++i )
				{				
					etims.add(new Etimology(i) );
				}
			}				
			
			etimology = etims.get(iEtim);
			if( !GramCatContainer.isFlexibleForm(subtype) && etimology.hasFlexibleForm() )
			{
				etimology = null;
				++iEtim;
			}
		}
		
		bFound = false;
		for(GramCatContainer gc_cont: etimology.gc_conts)
		{
			if( gc_cont.isTypeEquals(subtype) )
			{
				bFound = true;
				break;
			}
		}
		
		if( !bFound)
		{
			gc_container = GramCatContainer.build(subtype);
			etimology.gc_conts.add(gc_container);
		}
				
	}
	
	public void addGivenName(int iEtim, String strName)
	{
		if( strName.equals(Constants.UNISEX1) || strName.equals(Constants.UNISEX2) )
		{
			addDefinition(iEtim, GramCat.Subtype.GN_UNISEX, "");
		}
		else
		{
			if( strName.equals(Constants.MALE) )
			{
				addDefinition(iEtim, GramCat.Subtype.GN_MALE, "");
			}
			else if( strName.equals(Constants.FEMALE) )
			{
				addDefinition(iEtim, GramCat.Subtype.GN_FEMALE, "");
			}
		}
	}
	
	public void addPlace(int iEtim, Template template)
	{
		StringBuilder buffer = new StringBuilder();
		String strPlace;
		Definition definition;
		Place place;
		
		place = new Place(template);
		
		strPlace = place.toWiki();
		if( defcontL2 != null)
		{
			buffer.append(strPlace);
			definition = defcontL1.getDefinition();
			if( definition != null && Util.isNotNullOrEmpty(definition.text) )				
			{			
				buffer.append(", ").append(definition.text);
			}
			strPlace = buffer.toString();
		}
		if( Util.isNotNullOrEmpty(strPlace) )
		{
			addDefinition(iEtim, GramCat.Subtype.PLACE, strPlace);
		}
	}
	
	public void addSurname(int iEtim)
	{
		addDefinition(iEtim, GramCat.Subtype.SURNAME, "");
	}
	
	public void addAdv(int iEtim, String comparative, String superlative)
	{	
		adv_comp = comparative;
		adv_sup = superlative;
	}
	
	public void setWikipedia()
	{
		bWikipedia = true;
	}
	
	/*
	 * International phonetic alphabet 
	 */
	public void addAPI(String strCode, String ipa)
	{
		boolean bFound;
		
		bFound = false;
		for(Pron pron: prons)
		{
			if( pron.name.equals(strCode) )
			{
				bFound = true;
				pron.addApi(ipa);
			}
		}
		if( !bFound )
		{
			prons.add(new Pron(strCode, ipa));
		}
	}
	
	public void addEsTrans(String strTrans)
	{
		estrans.add(strTrans);
	}
	
	public void addAudio(String strCode, String strAudio)
	{
		boolean bFound;
		
		bFound = false;
		for(Pron pron: prons)
		{
			if( pron.name.equals(strCode) )
			{
				bFound = true;
				pron.addAudio(strAudio);
			}
		}		
		
		if( !bFound )
		{
			Pron pron = new Pron();
			pron.name = strCode;
			pron.audios.add(strAudio);
			prons.add(pron);
		}		
	}
	
	public void addSyn(int iEtim, String strSyn)
	{
		Etimology etimology;
		
		if( etims.size() <= iEtim )
		{
			int iSize = etims.size();
			for(int i= iSize;  i<iEtim+1; ++i )
			{				
				etims.add(new Etimology(i) );
			}
		}
		
		etimology = etims.get(iEtim);
		etimology.addSyn(strSyn);
	}

	public void addEtimology(int iEtim, EtimLang.Type type, String lang, String words, String transliteración)
	{
		Etimology etim = getEtimology(iEtim);
		etim.addEtimLang(new EtimLang(type, lang, words, transliteración));
	}
	
	public void addInitials(String strInitials)
	{
		Etimology etimology;
		int iEtim = 0;
		boolean bFound = false;
	
		for( Etimology e: etims)
		{
			if(
				e.type == Etimology.Type.UNKNOWN ||
				(e.type == Etimology.Type.INITIALS && e.text.equals(strInitials))
			)
			{
				if( e.type == Etimology.Type.UNKNOWN  )
				{
					e.type = Etimology.Type.INITIALS;
					e.text = strInitials;
				}
				bFound=true;
				break;
			}
			if( iEtim <= e.iEtim )
			{
				iEtim = e.iEtim + 1;
			}
		}
		
		if( !bFound )
		{
			etimology = Etimology.buildInitials(iEtim, strInitials);		
			etims.add(etimology);
		}
	}
	
	public void addHomophon(String strHomophone)
	{
		boolean bFound = false;
		for(String str: homophons)
		{
			if( str.equals(strHomophone))
			{
				bFound = true;
				break;
			}			
		}
		
		if( !bFound )
		{
			homophons.add(strHomophone);
		}
	}

	public static boolean isEtimology(Token t)
	{
		boolean bIsEtim = false;
		if( t.getType() == TokenType.TITLE)
		{
			bIsEtim = t.getValue().contains("Etymology");
		}
		return bIsEtim;
	}
	
	public static int getEtimologyId(Token t)
	{
		int iEtim = 0;
		String words[];
		
		try
		{
			words = t.getValue().split("\\s");
			if( words != null && words.length >= 2 )
			{
				try
				{
					iEtim = Integer.parseInt(words[1]);
				}
				catch(Exception ex0)
				{
					Util.reportError("Invalid etimology number", t);
				}
			}
		}
		catch(Exception ex)
		{
			
		}
		
		return iEtim;
	}

	public static boolean isNoun(String noun)
	{
		return noun.equals(Constants.NOUN);
	}
	
	public static boolean isProperNoun(String noun)
	{
		return noun.equals(Constants.PROPER_NOUN);
	}
	
	public void prepareWiki()
	{
		int iEtim = 0;
		int iEtimCnt = 0;
		boolean bAdded = false;
		
		clear();
		
		for(Etimology etim: etims)
		{
			if( !etim.hasDefs() )
			{
				etims.remove(etim);
			}
		}

		Command cmd = TskImportEnglish.getCommand(entry);
		if( cmd != null && cmd.hasDefs() )
		{
			iEtim = etims.get(0).iEtim;
			for(Definition def : cmd.defs)
			{
				bAdded = false;
				for(Etimology etim: etims)
				{
					if( !etim.hasFlexibleForm() )
					{
						addDefinition(etim.iEtim, def.subtype, def.text, true);
						bAdded = true;
						break;
					}
				}
				if( !bAdded )
				{
					Etimology etim = new Etimology(nextEtimId());
					etims.add(etim);					
					addDefinition(etim.iEtim, def.subtype, def.text, true);
				}
			}
		}
		
		iEtim = 0;
		for( Etimology etimology: etims)
		{
			etimology.sort();
			if( iEtim < etims.size()-1 )
			{
				if( etimology.hasFlexibleForm() )
				{
					if( !etims.get(iEtim+1).hasFlexibleForm() )
					{
						Collections.swap(etims, iEtim, iEtim+1);
						++iEtimCnt;						
					}
				}
				else
				{
					++iEtimCnt;
				}
			}
			else
			{
				if( !etimology.hasFlexibleForm() )
				{
					++iEtimCnt;
				}
			}
			++iEtim;			
		}
		
		if(etims.size()  > 1)
		{
			if( iEtimCnt > 1)
			{
				iEtim = 1;
			}
			else
			{
				iEtim = 0;
			}
			for( Etimology etimology: etims)
			{
				etimology.iEtim = iEtim;
				++iEtim;
			}
		}
		
		if( etims.size() == 1)
		{
			if( etims.get(0).gc_conts.size() == 1 )
			{
				for(Definition def : etims.get(0).gc_conts.get(0).defs )
				{
					
				}
			}
		}
	}
	
	public void setDefContainer(Token token)
	{
		switch(token.getLevel())
		{
			case 1:
				defcontL1 = new DefContainer(null, token.getLevel());
				defcontL2 = null;
				break;
			case 2:
				defcontL2 = new DefContainer(defcontL1, token.getLevel());
				break;
		}
	}
	
//*********************************************************
// PRIVATE SECTOIN	
	
	private int nextEtimId()
	{
		int iEtim = 0;
		for(Etimology etim: etims)
		{
			if( iEtim <= etim.iEtim)
			{
				iEtim = etim.iEtim;
			}
		}
		return iEtim + 1;
	}

	
	private Etimology getEtimology(int i)
	{
		Etimology etim = null;
		
		for(Etimology e: etims)
		{
			if( e.iEtim == i)
			{
				etim = e;
				break;
			}
		}
		
		if( etim == null)
		{
			etim = new Etimology(i);
			etims.add(etim);
		}
		
		return etim;
	}
	
	
	private void addDefinition(int iEtim, GramCat.Subtype subtype, String strDef)
	{
		addDefinition(iEtim, subtype, strDef, false);
	}
	
	private int addDefinition(int iEtim, GramCat.Subtype subtype, String strDef, boolean bLiteral)
	{
		Etimology etimology;
		boolean bFound = false;
		boolean bAdded = false;
		Definition def;
		
		strDef = Util.trim(strDef);
		def = new Definition(subtype, strDef, bLiteral);
		etimology = null;
		
		if( def.isFlexibleForm() )
		{
			for(Etimology etim : etims)
			{
				if( etim.hasFlexibleForm() )
				{
					etimology = etim;
				}
			}

			if( etimology == null)
			{
				iEtim = etims.size() + 1;
				etimology = new Etimology(iEtim);
				etims.add(etimology);
			}
		}
		else
		{
			etimology = getEtimology(iEtim);
		}
		
		
		for(GramCatContainer catgram1: etimology.gc_conts)
		{
			if( catgram1.canContain(subtype) )
			{
				for(Definition def1 : catgram1.defs)
				{
					if( def1.equals(def) )
					{
						bFound = true;
						break;
					}
				}
				if( !bFound )
				{
					catgram1.addDefinition(def);
					if( defcontL2 != null)
					{
						def.setContainer(defcontL2);
					} 
					else if( defcontL1 != null )
					{
						def.setContainer(defcontL1);
					}		
					
					bAdded = true;
				}
				break;
			}
		}
		if( !bFound && !bAdded)
		{
			GramCatContainer gc_container;
			gc_container = new GramCatContainer();
			gc_container.addDefinition( def );
			etimology.add(gc_container);
			if( defcontL2 != null)
			{
				def.setContainer(defcontL2);
			} 
			else if( defcontL1 != null )
			{
				def.setContainer(defcontL1);
			}
		}
		return iEtim;
	}

	private static DefContainer defcontL1;
	private static DefContainer defcontL2;
	
	private static String LAST_L4;
}
