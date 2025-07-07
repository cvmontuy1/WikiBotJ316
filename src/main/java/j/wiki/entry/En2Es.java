package j.wiki.entry;

import j.wiki.Util;
import j.wiki.parser.ParserEntry;
import j.wiki.parser.Token;
import j.wiki.parser.TokenType;

public class En2Es {

	public static Entry buildEntry(String strTitle, ParserEntry pentry)
	{
		int iEtimology;
		Template template;
		String diminutive;
		String inf_name;
		
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
								inf_name = template.getParameter(4);
								if( inf_name.contains("s-verb-form") )
								{
									entry.addVerbForm(iEtimology, GramCat.Subtype.VERB_3S, template.getParameter(2));
								}
								else if( inf_name.equals("ing-form"))
								{
									entry.addVerbForm(iEtimology, GramCat.Subtype.VERB_ING, template.getParameter(2));
								}
								else if( inf_name.equals("ed-form"))
								{
									entry.addVerbForm(iEtimology, GramCat.Subtype.VERB_ED, template.getParameter(2));
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
										entry.addAPI(Constants.US, template.getParameter(2));
									}
									if( isIPA_UK(template.getParameter("a")))
									{
										entry.addAPI(Constants.UK, template.getParameter(2));
									}
									if( isIPA_AU(template.getParameter("a")) )
									{
										entry.addAPI(Constants.AU, template.getParameter(2));
									}
									if( isIPA_CA(template.getParameter("a")) )
									{
										entry.addAPI(Constants.CA, template.getParameter(2));
									}									
								}
								else
								{
									entry.addAPI(Constants.GE, template.getParameter(2));
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
										entry.addAudio(Constants.US, template.getParameter(2));
									}
									else if( isIPA_UK(template.getParameter("a")))
									{
										entry.addAudio(Constants.UK, template.getParameter(2));
									}
									else if( isIPA_AU(template.getParameter("a")))
									{
										entry.addAudio(Constants.AU, template.getParameter(2));
									}		
									else if( isIPA_CA(template.getParameter("a")))
									{
										entry.addAudio(Constants.CA, template.getParameter(2));
									}
								}
							}
							break;
						case TEMPLATE_WIKIPEDIA1:
							if( template.getUnnamedParsCnt() > 0 )
							{
								if( template.getParameter(1).equals("Wikipedia") )
								{
									entry.setWikipedia();
								}
							}
							else
							{
								entry.setWikipedia();								
							}
							break;
						case TEMPLATE_WIKIPEDIA2:
						case TEMPLATE_WIKIPEDIA3:	
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
							if( template.getParameter(1).equals(ENGLISH) )
							{
								entry.addPlace(iEtimology, template);			
							}
							break;
						case TEMPLATE_T1:	// Translations
						case TEMPLATE_T2:
						case TEMPLATE_T3:
						case TEMPLATE_T4:
						case TEMPLATE_T5:
							if( template.getParameter(1).equals(SPANISH) )
							{
								for(int i=2; i<=template.getUnnamedParsCnt(); ++i)
								{
									entry.addEsTrans(template.getParameter(i));
								}
							}
							break;
						case TEMPLATE_BOR1:
						case TEMPLATE_BOR2:		
						case TEMPLATE_DER:
						case TEMPLATE_INH:
						case TEMPLATE_INH1:
						case TEMPLATE_INH2:							
							if( template.getParameter(1).equals(ENGLISH) )
							{				
								if( Util.isNotNullOrEmpty(template.getParameter("tr") ))
								{
									entry.addEtimology(iEtimology, EtimLang.Type.BORROWED, template.getParameter(2), template.getParameter(3), template.getParameter("tr"));
								}
								else
								{
									entry.addEtimology(iEtimology, EtimLang.Type.BORROWED, template.getParameter(2), template.getParameter(3), null);
								}
							}
							break;
						case TEMPLATE_SUFFIX:
							if( template.getParameter(1).equals(ENGLISH) )
							{		
								entry.addEtimSufix(iEtimology, EtimLang.Type.SUFFIX, template.getParameter(1), template.getParameter(2), template.getParameter(3) );								
							}
							break;
						case TEMPLATE_INITIALS:
							if( template.getParameter(1).equals(ENGLISH) )
							{
								entry.addInitials(template.getParameter(2));
							}
							break;
					}
					break;
				case TokenType.TITLE:
					if( Entry.isEtimology(token) )
					{
						iEtimology = Entry.getEtimologyId(token);
					}
					else if( Entry.isNoun(token.getValue()) )
					{
						entry.addGramCat(iEtimology, GramCat.Subtype.NOUN);
					}
					else if( Entry.isProperNoun(token.getValue()) )
					{
						entry.addGramCat(iEtimology, GramCat.Subtype.NOUN_PROPER);
					}
					break;
				case TokenType.DEFINITION:
				case TokenType.BULLET:					
					entry.setDefContainer(token);
					
					diminutive = TextParser.isDiminutive(token.getValue());
					if( Util.isNotNullOrEmpty(diminutive) )
					{
						entry.addDim(iEtimology, diminutive);
					}			
					break;
				default:
						// Ignored tokens
			}			
		}
		
		entry.prepareWiki();
		
		return entry;
	}
	
//*****************************************************************************************
// Private section	

	private static boolean isIPA_US(String value)
	{
		return  value.contains(Constants.US)	||
				value.contains("GA") 			||
				value.contains("GenAm") 			||
				value.contains("United States");
	}
	
	private static boolean isIPA_UK(String value)
	{
		return value.contains(Constants.UK) ||
			   value.contains("RP")			||
			   value.contains("England")	||
			   value.contains("United Kingdom");
				
	}
	
	private static boolean isIPA_AU(String value)
	{
		return value.contains(Constants.AU) ||
			   value.contains("Australia");
				
	}
	
	private static boolean isIPA_CA(String value)
	{
		return value.contains(Constants.CA) ||
				   value.contains("Canada");
		
	}

	public final static String ENGLISH = "en";
	public final static String SPANISH = "es";
	public final static String NOUN = "Noun";
	public final static String PROPER_NOUN = "Proper noun";
	
	public final static String TEMPLATE_PLURAL			= "plural of";
	public final static String TEMPLATE_INFLECTION		= "infl of";
	public final static String TEMPLATE_IPA				= "IPA";
	public final static String TEMPLATE_AUDIO			= "audio";
	public final static String TEMPLATE_WIKIPEDIA1		= "pedia";
	public final static String TEMPLATE_WIKIPEDIA2		= "Wikipedia";
	public final static String TEMPLATE_WIKIPEDIA3		= "wp";
	public final static String TEMPLATE_DIMINUTIVE		= "diminutive of";
	public final static String TEMPLATE_GIVENNAME		= "given name";
	public final static String TEMPLATE_HOMOPHONE		= "homophones";
	public final static String TEMPLATE_SYN1			= "syn";
	public final static String TEMPLATE_SYN2			= "synonyms";
	public final static String TEMPLATE_ADV				= "en-adv";
	public final static String TEMPLATE_SURNAME			= "surname";
	public final static String TEMPLATE_PLACE			= "place";
	public final static String TEMPLATE_T1				= "t";			//*** TRANSLATIONS
	public final static String TEMPLATE_T2				= "t+";
	public final static String TEMPLATE_T3				= "t-check";
	public final static String TEMPLATE_T4				= "t+check";
	public final static String TEMPLATE_T5				= "tt";
	public final static String TEMPLATE_INITIALS		= "initialism of";	//*** ETIMOLOGIES
	public final static String TEMPLATE_BOR1			= "bor";
	public final static String TEMPLATE_BOR2			= "bor+";	
	public final static String TEMPLATE_DER				= "der";
	public final static String TEMPLATE_INH				= "inherited";
	public final static String TEMPLATE_INH1			= "inh+";
	public final static String TEMPLATE_INH2			= "inh";	
	public final static String TEMPLATE_SUFFIX			= "suffix";
	
}
