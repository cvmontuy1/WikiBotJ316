package j.wiki.parser;

import org.junit.jupiter.api.*;

import j.wiki.Util;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

	@Test
	public void testWikiText1()
	{
		ParserEntry entry;
		int lDefCnt;
		
		entry = new ParserEntry("en", "test", WIKITEXT1);
	
		//Util.report("WikiText1:",  entry);		
		//Util.report("WikiText1 def cnt:" + entry.tree.count(TokenType.DEFINITION));
		//Util.report("WikiText1 template cnt:" + entry.tree.count(TokenType.TEMPLATE));

		lDefCnt = entry.tree.count(TokenType.DEFINITION);
		assertNotNull(entry.tree.find(TokenType.BULLET));
		assertNotNull(entry.tree.find(TokenType.DEFINITION));		
		assertNotNull(entry.tree.find(TokenType.FILE));
		assertNotNull(entry.tree.find(TokenType.TEMPLATE,"IPA|en|/ˈiːvnɪŋz/"));
		assertNotNull(entry.tree.find(TokenType.TITLE, 	"Pronunciation"));
		assertNotNull(entry.tree.find(TokenType.TITLE, 	"Etymology 1"));		
		assertNotNull(entry.tree.find(TokenType.TITLE,  "Etymology 2"));
		assertNull(entry.tree.find(TokenType.TITLE, 	"Etymology 3"));

		assertNotNull(entry.tree.find(TokenType.LINK, "evening"));
		assertTrue( lDefCnt == 5 || lDefCnt == 6);	// sometimes count 5 and sometimes 6	
		assertTrue(entry.tree.count(TokenType.BULLET) == 4);
		assertTrue(entry.tree.count(TokenType.TITLE) == 6);
		assertTrue(entry.tree.count(TokenType.TEMPLATE) == 15);
		assertTrue(entry.tree.count(TokenType.LINK) == 7);
		assertTrue(entry.tree.count(TokenType.EXTERNAL_LINK) == 1);
		assertTrue(entry.tree.count(TokenType.FILE) == 1);
	}

	@Test
	public void testHanna()
	{
		ParserEntry entry;
		
		entry = new ParserEntry("en", "test", WIKITEXT_HANNA);
		
		assertNotNull(entry.tree.find(TokenType.TITLE, "Etymology 1") );
		assertNotNull(entry.tree.find(TokenType.TITLE, "Etymology 2") );
		assertTrue(entry.tree.count(TokenType.DEFINITION) == 15);				
	}
	
/***********************************************************}
 * TEST DATA	
 */

	public final static String WIKITEXT1 = 
"""
===Pronunciation===
* {{IPA|en|/ˈiːvnɪŋz/}}
* {{audio|en|LL-Q1860 (eng)-Vealhurl-evenings.wav|a=Southern England}}
* {{hyph|en|eve|nings}}
===Etymology 1===
From {{suffix|en|evening|s|pos2=adverbial suffix}}. Compare with {{cog|nl|avonds}}, {{cog|nds|avends}}, {{cog|de|abends}}.
====Adverb====
{{en-adv|-}}
# {{lb|en|informal|somewhat dated}} In the [[evening]], during the evening.
#* {{RQ:Douglass Narrative|passage=He had ordered her not to go out '''evenings''', and warned her that she must never let him catch her in company with a young man, who was paying attention to her belonging to Colonel Lloyd.}}
### {{place|en|town|test}}.
===Etymology 2===
From {{suffix|en|evening|s|pos2=plural suffix}}.<!-- justified here to show difference from etymology 1 -->
====Noun====
{{head|en|noun form}}
# {{plural of|en|evening}}
[[File:Chien revolver.JPG|thumb|'''Hammer''' ({{senseno|en|firearms}}) of the firing pin]]
[https://en.wikitionary.org]
====Test====
# A [[diminutive]] of [[Susan]] and of related female [[given name]]s.
# A shortening of the male [[given name]] [[Benjamin]] or, less often, of [[Benedict]].

""";

public final static String WIKITEXT_HANNA =  // Hanna
"""			
{{wikipedia}}
===Etymology 1===
Variant spelling of {{m|en|Hannah}} or romanization of {{der|en|he|חַנָּה|tr=Ḥanâ}}, chiefly for the mother of [[Samuel]], from {{m|he|חַנָּה||[[grace]], [[gracious]], [[graced]] with [[child]]|tr=ḥanâ}}. As an Oklahoman town, named for Hanna Bullett, one of the initial settlers. As a Polish village, named for the queen {{w|Anna Jagiellon}}.
====Proper noun====
{{en-proper noun|~|s}}
# {{lb|en|uncommon}} {{given name|en|female|from=Hebrew|varof=Hannah}}.
# {{lb|en|rare}} {{surname|en|from=matronymics}} ''based on [[Hannah]]''.
# {{place|en|town|p/Alberta|c/Canada}}.
# {{place|en|village|c/Poland}}.
# {{place|en|locale|c/US}}:
## {{place|en|unincorporated community|s/Louisiana}}.
## {{place|en|town|s/Oklahoma}}.
## {{place|en|unincorporated community|s/West Virginia}}.
## {{place|en|town|s/Wyoming}}.
===Etymology 2===
From anglicization of {{der|en|ga|[[Ó]] [[Annadh|hAnnaigh]]||[[descendant]] of [[Annadh]]}} under influence from [[#Etymology 1|''Hanna'']] and {{m|en|Hannah}}. As a ghost town in Missouri and unincorporated community in South Dakota, named for Ohio senator {{w|Mark Hanna}}. As an unincorporated community on Ute land in Utah, named for postmaster William P. Hanna.
====Proper noun====
{{en-proper noun}}
# {{surname|en|Irish and Scottish|from=Irish}}.
# {{place|en|locale|c/US}}:
## {{place|en|unincorporated community/and/CDP|co/LaPorte County|c/Indiana|;|named for a state judge}}.
## {{place|en|unincorporated community|s/South Dakota}}.
## {{place|en|unincorporated community|on [[Ute]] land in|s/Utah}}.
## {{place|en|ghost town|s/Missouri}}.
=====Alternative forms=====
* {{sense|surname}} {{alter|en|Hannah|Hannay|Hanney}}
===Anagrams===
* {{anagrams|en|a=aahnn|Hanan}}
"""	;
	
public final static String WIKITEXT_CHARLY  =  
"""
==English==

===Proper noun===
{{en-proper-noun}}

# {{given name|en|unisex}}.
## {{given name|en|male|varof=Charlie|dimof=Charles}}.
## {{given name|en|female|varof=Charley|dimof=Charlene,Charlotte}}.
""";	
	
   public final static String WIKITEXT_COLOMBIA =
"""
==English==
[[Image:Flag of Colombia.svg|thumb|right|250px]]

===Etymology===
{{bor+|en|es|Colombia}}, from {{der|en|it|Colombo|gloss=Cristoforo Colombo}}; {{named-after|en|nat=Genoese|occ=explorer|Christopher Columbus|wplink==|born=1451|died=1506|nocap=1}}. Equivalent to {{af|en|Colombo|-ia}}; {{piecewise doublet|en|Columbia|nocap=1}}.

===Pronunciation===
* {{IPA|en|/kʰəˈlʌm.bi.ə/|a=UK,US}}
* {{IPA|en|/koˈlom.bjɐ/|a=Philippines}}
* {{audio|en|LL-Q1860 (eng)-Soundguys-Colombia.wav|a=UK}}
* {{audio|en|en-us-Colombia.ogg|a=US}}
* {{homophones|en|Columbia}}

===Proper noun===
{{en-prop}}

# {{senseid|en|Q739}}{{place|en|country|cont/South America|capital=Bogotá|official=Republic of Colombia}}.
#: {{syn|en|New Granada<q:historical>}}
#* {{quote-journal|en|author=David Charter|title=Fafo diplomacy: How Colombia found out Trump meant business|magazine=w:The Times|url=https://www.thetimes.com/us/news-today/article/fafo-meaning-colombia-trump-tariffs-migrants-d6xhncq5z|date=2025-01-27|passage=Fafo works with small trading nations such as '''Colombia''' because US markets could quickly adapt to the greater expense of its coffee, flowers and other exports. There will be a different order of chaos, though, if Trump follows through on tariffs on the EU (17 per cent of US imports), Mexico (15 per cent), China (14 per cent) or Canada (12 per cent).|archiveurl=https://archive.ph/G8DaN|archivedate=2025-01-27}}

====Translations====
{{trans-top|id=Q739|country in South America}}
* Tagalog: {{t|tl|Kolombiya}}
* Yiddish: {{t|yi|קאָלאָמביע|f}}
* Yoruba: {{t|yo|Kòlóńbíà}}, {{t|yo|Kòlóḿbíà}}
{{trans-bottom}}

===See also===
{{list:countries in South America/en}}

===Further reading===
* {{pedia}}
""";		
   
public final static String WIKITEXT_MRI =   
"""
{{also|mri}}
==English==

===Pronunciation===
* {{audio|en|LL-Q1860 (eng)-Wodencafe-MRI.wav|a=US}}

===Noun===
{{en-noun|~}}

# {{lb|en|medicine}} {{initialism of|en|magnetic resonance imaging}}.

====Translations====
{{trans-top-also|magnetic resonance imaging|magnetic resonance imaging}}
* Portuguese: {{t|pt|IRM|f}}
* Russian: {{t+|ru|МРТ}}
* Spanish: {{t+|es|TRM}}.
* Xibe: {{t|sjo|ᠠᡞᠮ ᠠᠯ ᠠᡞ|tr=aim al ai}}
{{trans-bottom}}

===Verb===
{{en-verb}}

# {{lb|en|transitive|rare|medicine}} To [[take]] an MRI [[scan]] of.
#* {{quote-book|en|year=2005|author=w:Donald Hall|title=The Best Day the Worst Day: Life with Jane Kenyon|location=Boston|publisher={{w|Houghton Mifflin Harcourt|Houghton Mifflin Company}}|isbn=0618478019|page={{gbooks|OoSwJtwmbZcC|100|MRIed|altpg=99–100}}|passage=When the delirium stopped and it was safe to take Versed, her doctors doped her up and she managed to endure the MRI, on condition that I remain with her all the time. They '''MRIed''' her skull for thirty minutes, and two days later '''MRIed''' her spine for an hour.}}
#* {{quote-book|en|year=2014|author=w:Mark Herzlich|title=What It Takes: Fighting For My Life and My Love of the Game|location=New York, NY|publisher=w:New American Library|isbn=9780451468796|page={{gbooks|ofUlAgAAQBAJ|74|MRIing}}|passage=Remarkably, no one to that point had suggested '''MRIing''' my leg, which is where my pain actually was. Everyone surmised that the issue was really in my back. Dr. Smith scheduled an MRI for that very day.}}

===Anagrams===
* {{anagrams|en|a=imr|IRM|MIR|Mir|RMI|miR|mir|rim}}
""";		

public final static String WIKITEXT_SPIRITS =
"""
===Pronunciation===
* {{IPA|en|/ˈspɪɹɪts/}}
* {{audio|en|en-us-spirits.ogg|a=US}}
* {{audio|en|LL-Q1860 (eng)-Mélange a trois-spirits.wav|a=UK}}
===Verb===
{{head|en|verb form}}
# {{infl of|en|spirit||s-verb-form}}
===Noun===
{{head|en|noun form}}
# {{plural of|en|spirit}}
===Noun===
{{en-noun|p}}
# {{lb|en|chiefly|UK}} Distilled alcoholic beverages.{{attn|en| the definition below, at the top of the translation box, is considerably more specific: which is correct? }}
#: {{syn|en|liquor|q1=chiefly American English}}
====Translations====
{{trans-top|strong alcoholic drink derived from fermentation and distillation}}
* Romanian: {{t+|ro|spirt|n}}, {{t|ro|spirturi|n-p}}
* Russian: {{t+|ru|спирт|m}}
* Spanish: {{t+|es|licor}}
* Swahili: {{t|sw|spiriti}}
{{trans-bottom}}
===See also===
* {{l|en|hard liquor}}
* {{l|en|short drink}}<!--should be created!-->, {{l|en|shooter}}
===Anagrams===
* {{anagrams|en|a=iiprsst|tripsis}}
{{C|en|Distilled beverages}}
""";

public final static String WIKITEXT_WALLACE =
"""
{{wp}}
===Etymology===
An {{der|en|ang|-}} byname for a [[Welshman]] or [[Breton]], from {{der|en|xno|waleis||foreign}}; see {{m|ang|wielisc}}.
===Pronunciation===
* {{IPA|en|/ˈwɒlɪs/|a=RP}}
* {{audio|en|LL-Q1860 (eng)-Vealhurl-Wallace.wav|a=Southern England}}
* {{IPA|en|/ˈwɑləs/|/ˈwɔləs/|a=GA}}
* {{rhymes|en|ɒlɪs|s=2}}
* {{hmp|en|Wallis}}
===Proper noun===
{{en-proper noun|~}}
# {{lb|en|countable}} {{surname|en|Scottish|from=nicknames}}, notably of the Scottish patriot {{w|William Wallace}}.{{cln|en|surnames from Old English}}
# {{lb|en|countable}} {{given name|en|male|from=surnames|usage=19th century and later}}.
# A [[placename]]:
## {{place|en|town|s/Victoria|c/Australia}}.
## {{place|en|locale|c/Canada}}.
### {{place|en|community|p/Nova Scotia|;|named for William Wallace}}.{{cln|en|eponyms}}
### {{place|en|community|p/Ontario}}.
### {{place|en|rural municipality|in eastern|p/Saskatchewan|full=the {{w|Rural Municipality of Wallace No. 243}}}}.
## {{place|en|locale|c/US}}.
### {{place|en|CDP|co/Calaveras County|s/California|;|named for surveyor John Wallace}}.
### {{place|en|city/county seat|county/Shoshone County|s/Idaho|;|named for founder Col. William R. Wallace}}.
### {{place|en|town|s/Indiana|;|named for Indiana governor {{w|David Wallace (Indiana politician)|David Wallace}}}}.
### {{place|en|city|s/Kansas|;|named for the nearby {{w|Fort Wallace}}}}.
### {{place|en|CDP|s/Louisiana}}.
### {{place|en|unincorporated community|twp:Suf/Curtis|co/Alcona County|s/Michigan}}.
### {{place|en|unincorporated community|twp:Suf/Mellen|co/Menominee County|s/Michigan}}.
### {{place|en|unincorporated community|s/Missouri|;|named for a railroad official}}.
### {{place|en|village|s/Nebraska}}.
### {{place|en|town|s/North Carolina}}.
### {{place|en|CDP|s/South Carolina}}.
### {{place|en|town|s/South Dakota|;|named for the original owner of the town site}}.
### {{place|en|CDP|co/Harrison County|s/West Virginia}}.
====Derived terms====
{{col|en|Wallace effect|Wallacea|Wallacean|Wallace County|Wallace Line|Wallace-Woodworth}}
====References====
<references/>
* Patrick Hanks, Flavia Hodges, (2001) ''A Concise Dictionary of First Names'', Oxford University Press.
===See also===
* {{l|en|Wallis}}	
""";
public final static String WIKITEXT_MAXIE =
"""
==English==
===Alternative forms===
* {{l|en|Maxy}}
===Etymology===
From {{suffix|en|Max|ie}}.
===Proper noun===
{{en-proper noun}}
# A [[diminutive]] of the male name [[Max]].
# A [[diminutive]] of the female name [[Maxine]].
""";
}
