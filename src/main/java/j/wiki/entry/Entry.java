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
		
		bWikipedia = false;
	}
	
	public boolean isComplete()
	{
		boolean bIsComplete;
		
		bIsComplete = false;
		if( Util.isNotNull(lang) && 
		    Util.isNotNull(entry) &&
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
	
	
	public void addDim(int iEtim, String strName)
	{
		List<String> words = TextParser.filter(strName);
		for( String name: words)
		{
			addDefinition(iEtim, T_GN_DIM, name);
		}
	}
	
	public void addNounPlural(int iEtim, String strPlural)
	{
		addDefinition(iEtim, T_NOUN_PLURAL, strPlural);
	}

	public void addPresent3S(int iEtim, String strPlural)
	{
		addDefinition(iEtim, T_PRESENT_3S, strPlural);
	}

	
	public void addGivenName(int iEtim, String strName)
	{
		if( strName.equals(T_UNISEX) )
		{
			addDefinition(iEtim, T_GN_MALE, "");
			addDefinition(iEtim, T_GN_FEMALE, "");
		}
		else
		{
			if( strName.equals(MALE) )
			{
				addDefinition(iEtim, T_GN_MALE, "");
			}
			else if( strName.equals(FEMALE) )
			{
				addDefinition(iEtim, T_GN_FEMALE, "");
			}
		}
	}
	
	public void addPlace(int iEtim, List<String> args)
	{
		String strPlace;
		
		strPlace = TextParser.getPlace(args);
		if( Util.isNotNull(strPlace) )
		{
			addDefinition(iEtim, T_PLACE, strPlace);
		}
	}
	
	public void addSurname(int iEtim)
	{
		addDefinition(iEtim, T_SURNAME, "");
	}
	
	public void addAdv(int iEtim, String comparative, String superlative)
	{	
		adv_comp = comparative;
		adv_sup = superlative;
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
	
	public boolean isJustFlexiveForm()
	{
		boolean bIsJustFlexive;
	
		bIsJustFlexive = false;
		
		if( etims.size() > 0 )
		{
			bIsJustFlexive = etims.get(0).hasFlexibleForm();
		}
		return bIsJustFlexive;
	}

	/**
	 * Generates wiki content
	 * @return
	 */
	public String toWiki()
	{
		StringBuilder buffer = new StringBuilder();
		int iPron, iAudio, iAPI, iDef;
		
		prepareWiki();
		
		buffer.append("{{esbozo}}").append(Util.LF);
		buffer.append("{{desambiguación|}}").append(Util.LF);
		buffer.append(IDENT2).append(" {{lengua|en}} ").append(IDENT2).append(Util.LF);
		buffer.append("{{pron-graf|leng=en");
		
		iPron = 0;		
		for(Pron pron: prons)
		{
			iAPI = 0;
			for(String ipa: pron.ipas)
			{
				if( iPron == 0 )
				{
					if( iAPI == 0)
					{
						buffer.append(Util.LF).append("|").append("fono=");
					}
					else
					{
						buffer.append("|").append("fono").append(iAPI+1).append("=");					
					}
				}
				else
				{
					if( iAPI == 0)
					{
						buffer.append(Util.LF).append("|").append(iPron+1).append("fono=");
					}
					else
					{
						buffer.append("|").append(iPron+1).append("fono").append(iAPI+1).append("=");					
					}					
				}
				buffer.append(ipa);
				++iAPI;
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
				if( pron.name.equals(UK) )
				{
					buffer.append("|pron=Reino Unido");
				}
				else if( pron.name.equals(US))
				{
					buffer.append("|pron=Estados Unidos");
				}
			}
			else
			{	
				if( pron.name.equals(UK) )
				{
					buffer.append("|").append(iPron+1).append("pron=Reino Unido");
				}
				else if( pron.name.equals(US))
				{
					buffer.append("|").append(iPron+1).append("pron=Estados Unidos");
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
					buffer.append(IDENT3).append(" Etimología ").append(IDENT3).append(Util.LF);;				
				}
				else
				{
					buffer.append(Util.LF);					
					buffer.append(IDENT3).append(" Etimología ").append(etimology.iEtim).append(IDENT3).append(Util.LF);
				}
				etimology.toWiki(buffer);
			}

			for(GramCat catgram : etimology.gramcats)
			{
				catgram.toWiki(this, buffer);
				for( Definition def: catgram.defs )
				{
					iDef = def.toWiki(buffer, iDef);				
				}
/*		
		==== {{sustantivo|en}} ====
		{{inflect.en.sust.nocontable}}
		{{inflect.en.sust.nocontable}}
		{{inflect.en.sust.reg}}
		{{inflect.en.sust.sg-pl||}}
		;1: {{plm|}}, [[]], [[]].
*/
			}
/*
	  		==== Locuciones ====
	 
			{{trad-arriba|Locuciones}}
			* {{l|en|}}
			{{trad-abajo}}
*/
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
	
	public static CategoryIdx getCategory(String strCat)
	{
		CategoryIdx catidx = null;
		
		strCat = strCat.toUpperCase();
		
		if( strCat.startsWith(T_NOUN_PLURAL) )
		{
			catidx = new CategoryIdx(T_NOUN_PLURAL, strCat);
		}
		else if( strCat.startsWith(T_NOUN_PROPER) )
		{
			catidx = new CategoryIdx(T_NOUN_PROPER, strCat);
		}
		else if( strCat.startsWith(T_NOUN) )
		{
			catidx = new CategoryIdx(T_NOUN, strCat);			
		}
		else if( strCat.startsWith(T_ADVERB) )
		{
			catidx = new CategoryIdx(T_ADVERB, strCat);
		}
		
		if( catidx == null)
		{
			Util.reportError("Unknown categoy:", strCat);
		}
		return catidx;
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
	

	public static Entry buildEntry(String strTitle, ParserEntry pentry)
	{
		int iEtimology;
		Template template;
		
		Entry entry = new Entry();
		
		entry.lang = "en";
		entry.entry = strTitle;
		
		
		iEtimology = 0;
		for(Token token : pentry.getChildren())
		{

			switch(token.getType())
			{
				case TokenType.TEMPLATE:
					template = new Template(token);
					switch(template.getName())
					{
						case TEMPLATE_PLURAL:
							if( template.getParameter(1).equals(ENGLISH) )
							{
								entry.addNounPlural(iEtimology, template.getParameter(2));
							}
							break;
						case TEMPLATE_INFLECTION:
							if( template.getParameter(1).equals(ENGLISH) )
							{
								if( template.getParameter(4).contains("s-verb-form") )
								{
									entry.addPresent3S(iEtimology, template.getParameter(2));
								}
							}
							break;
						case TEMPLATE_IPA:
							if( template.getParameter(1).equals(ENGLISH) )
							{							
								if( template.containsParameter("a") )
								{
									if( isIPA_US(template.getParameter("a") ) ) 
									{
										entry.addAPI(Entry.US, template.getParameter(2));
									}
									else if( isIPA_UK(template.getParameter("a")))
									{
										entry.addAPI(Entry.UK, template.getParameter(2));
									}
								}
								else
								{
									entry.addAPI(Entry.GE, template.getParameter(2));
								}
							}
							break;
						case TEMPLATE_AUDIO:
							if( template.getParameter(1).equals(ENGLISH) )
							{							
								if( template.containsParameter("a") )
								{
									if( isIPA_US(template.getParameter("a") ) ) 
									{
										entry.addAudio(Entry.US, template.getParameter(2));
									}
									else if( isIPA_UK(template.getParameter("a")))
									{
										entry.addAudio(Entry.UK, template.getParameter(2));
									}									
								}
							}
							break;
						case TEMPLATE_WIKIPEDIA1:
							if( template.getParameter(1).equals("Wikipedia") )
							{
								entry.setWikipedia();
							}
							break;
						case TEMPLATE_WIKIPEDIA2:
							entry.setWikipedia();
							break;
						case TEMPLATE_DIMINUTIVE:
							if( template.getParameter(1).equals(ENGLISH) )
							{
								entry.addDim(iEtimology, template.getParameter(2));
							}
							break;
						case TEMPLATE_GIVENNAME:
							if( template.containsParameter("dimof") )
							{
								entry.addDim(iEtimology, template.getParameter("dimof"));
							}
							else if ( template.containsParameter("dim") )
							{
								entry.addDim(iEtimology, template.getParameter("dim"));
							}
							else if( template.containsParameter("gender") )
							{
								entry.addGivenName(iEtimology, template.getParameter("gender"));	
							}
							else 
							{
								entry.addGivenName(iEtimology, template.getParameter(2));
							}
							break;
						case TEMPLATE_HOMOPHONE:
							if( template.getParameter(1).equals(ENGLISH) )
							{
								for(int i=1; i<=template.getUnnamedParsCnt(); ++i)
								{
									entry.addHomophon(template.getParameter(i));
								}
							}
							break;
						case TEMPLATE_SYN1:
						case TEMPLATE_SYN2:
							if( template.getParameter(1).equals(ENGLISH) )
							{
								for(int i=1; i<=template.getUnnamedParsCnt(); ++i)
								{
									entry.addSyn(iEtimology, template.getParameter(i));
								}								
							}
							break;
						case TEMPLATE_ADV:
							entry.addAdv(iEtimology, template.getParameter(1), template.getParameter(2));
							break;
						case TEMPLATE_SURNAME:
							entry.addSurname(iEtimology);
							break;
						case TEMPLATE_PLACE:
							if( template.getParameter(1).equals(SPANISH) )
							{
								for(int i=1; i<=template.getUnnamedParsCnt(); ++i)
								{
									entry.addPlace(iEtimology, template.getParameters() );
								}				
							}
							break;
						case TEMPLATE_T1:
						case TEMPLATE_T2:
						case TEMPLATE_T3:
						case TEMPLATE_T4:
						case TEMPLATE_T5:
							if( template.getParameter(1).equals(SPANISH) )
							{
								for(int i=1; i<=template.getUnnamedParsCnt(); ++i)
								{
									entry.addEsTrans(template.getParameter(i));
								}
							}
							break;
					}
					break;
				case TokenType.TITLE:
					if( isEtimology(token) )
					{
						iEtimology = getEtimology(token);
					}
					break;
				case TokenType.DEFINITION:
				case TokenType.BULLET:					
					String dim = TextParser.isDiminutive(token.getValue());
					if( Util.isNotNull(dim) )
					{
						entry.addDim(iEtimology, dim);
					}
					break;
				default:
						// tokens ignorados
			}			

			/*			
			
			matcher = patSufix.matcher(line);
			if( matcher.find() )
			{
				bProc = true;
			}					

			matcher = patDer.matcher(line);
			if( matcher.find() )
			{
				bProc = true;
			}		
			
			matcher = patBor.matcher(line);
			if( matcher.find() )
			{
				bProc = true;
			}		
			
			matcher = patAfix.matcher(line);
			if( matcher.find() )
			{
				bProc = true;
			}		
*/			
		}
		
		return entry;
	}
	
	
//*********************************************************
// PRIVATE SECTOIN	
	
	private static boolean isIPA_US(String value)
	{
		return  value.contains("US") ||
				value.contains("GA") ||
				value.contains("United States");
	}
	
	private static boolean isIPA_UK(String value)
	{
		return value.contains("UK") ||
			   value.contains("England");
				
	}
	
	private static boolean isEtimology(Token t)
	{
		boolean bIsEtim = false;
		if( t.getType() == TokenType.TITLE)
		{
			bIsEtim = t.getValue().contains("Etimology");
		}
		return bIsEtim;
	}
	
	private static int getEtimology(Token t)
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
	
	private void addHomophon(String strHomophone)
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
	
	private void setWikipedia()
	{
		bWikipedia = true;
	}
	
	/*
	 * International phonetic alphabet 
	 */
	private void addAPI(String strCode, String ipa)
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
	

	private void prepareWiki()
	{
		int iEtim = 0;
		int iEtimCnt = 0;
		boolean bAdded = false;
		
		clear();
		
		if( etims.size() > 1 )
		{
			if( !etims.get(0).hasDefs() )
			{
				etims.remove(0);
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
						addDefinition(etim.iEtim, def.type, def.text, true);
						bAdded = true;
						break;
					}
				}
				if( !bAdded )
				{
					Etimology etim = new Etimology(nextEtimId());
					etims.add(etim);					
					addDefinition(etim.iEtim, def.type, def.text, true);
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
			if( etims.get(0).gramcats.size() == 1 )
			{
				for(Definition def : etims.get(0).gramcats.get(0).defs )
				{
					if( def.type == T_PLACE)
					{
						 def.type =  T_NOUN_PROPER;
						 StringBuilder bufferTr = new StringBuilder();
						 for(String tr : estrans)
						 {						 
							 if(! bufferTr.isEmpty() )
							 {
								 bufferTr.append(", ");
							 }
							 bufferTr.append("[[").append(tr).append("]]");
						 } 
						 if( !bufferTr.isEmpty() )
						 {
							 bufferTr.append(".");
						 }
						 def.text = bufferTr.toString();
					}
				}
			}
		}
	}
	
	private void addDefinition(int iEtim, String strType, String strDef)
	{
		addDefinition(iEtim, strType, strDef, false);
	}
	
	private int addDefinition(int iEtim, String strType, String strDef, boolean bLiteral)
	{
		Etimology etimology;
		boolean bFound = false;
		boolean bAdded = false;
		Definition def;
		
		strDef = Util.trim(strDef);
		def = new Definition(strType, strDef, bLiteral);
		
		
		if( iEtim == 0 && etims.size() == 1)
		{
			if( !def.isFlexibleForm() && etims.get(0).hasFlexibleForm() )
			{
				++iEtim;
			}
		}
		
		if( etims.size() <= iEtim )
		{
			int iSize = etims.size();
			for(int i= iSize;  i<iEtim+1; ++i )
			{				
				etims.add(new Etimology(i) );
			}
		}		
		
		
		etimology = etims.get(iEtim);
		
		for(GramCat catgram1: etimology.gramcats)
		{
			if( catgram1.canContain(strType) )
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
					bAdded = true;
				}
				break;
			}
		}
		if( !bFound && !bAdded)
		{
			GramCat gramcat;
			gramcat = new GramCat();
			gramcat.addDefinition( def );
			etimology.gramcats.add(gramcat);
		}
		return iEtim;
	}
	
	
	public final static String T_NOUN_PLURAL	= "NOUN_PLURAL";
	public final static String T_NOUN_PROPER	= "NOUN_PROPER";
	public final static String T_NOUN			= "NOUN";
	public final static String T_ADVERB			= "ADVERB";
	public final static String T_PRESENT_3S		= "PRESENT_3S";
	public final static String T_PLACE 			= "PLACE";
	public final static String T_SURNAME		= "SURNAME";
	public final static String T_GN_MALE		= "GIVEN_NAME_MALE";
	public final static String T_GN_FEMALE		= "GIVEN_NAME_FEMALE";
	public final static String T_UNISEX			= "unisex";
	public final static String T_GN_DIM			= "GIVEN_NAME_DIM";
	
	public final static String IDENT1	= "=";
	public final static String IDENT2	= "==";
	public final static String IDENT3	= "===";
	public final static String IDENT4	= "====";
	

	private final static String MALE = "male";
	private final static String FEMALE = "female";	
	
	public final static String UK = "UK";
	public final static String US = "US";
	public final static String GE = "";
	
	public final static String ENGLISH = "en";
	public final static String SPANISH = "es";
	public final static String TEMPLATE_PLURAL = "plural of";
	public final static String TEMPLATE_INFLECTION = "infl of";
	public final static String TEMPLATE_IPA = "IPA";
	public final static String TEMPLATE_AUDIO = "audio";
	public final static String TEMPLATE_WIKIPEDIA1 = "pedia";
	public final static String TEMPLATE_WIKIPEDIA2 = "Wikipedia";
	public final static String TEMPLATE_DIMINUTIVE = "diminutive of";
	public final static String TEMPLATE_GIVENNAME = "given name";
	public final static String TEMPLATE_HOMOPHONE = "homophones";
	public final static String TEMPLATE_SYN1	  = "syn";
	public final static String TEMPLATE_SYN2	  = "synonyms";
	public final static String TEMPLATE_ADV		  = "en-adv";
	public final static String TEMPLATE_SURNAME	  = "surname";
	public final static String TEMPLATE_PLACE	  = "place";
	public final static String TEMPLATE_T1		  = "t";
	public final static String TEMPLATE_T2		  = "t+";
	public final static String TEMPLATE_T3		  = "t-check";
	public final static String TEMPLATE_T4		  = "t+check";
	public final static String TEMPLATE_T5		  = "tt";
	
	
	
	private static String LAST_L4;	
}
