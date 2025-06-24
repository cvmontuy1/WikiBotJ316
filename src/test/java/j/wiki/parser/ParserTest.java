package j.wiki.parser;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

	@Test
	public void test1()
	{
		ParserEntry entry;
		
		entry = new ParserEntry("en", "test", WIKITEXT1);
		
		System.out.println("Entry");
		System.out.println(entry.toString());
		
		assertNotNull(entry.tree.find(TokenType.BULLET));
		assertNotNull(entry.tree.find(TokenType.DEFINITION));		
		assertNotNull(entry.tree.find(TokenType.FILE));
		assertNotNull(entry.tree.find(TokenType.TEMPLATE,"IPA|en|/ˈiːvnɪŋz/"));
		assertNotNull(entry.tree.find(TokenType.TITLE, "Pronunciation"));
		assertNotNull(entry.tree.find(TokenType.TITLE, "Etymology 1"));		
		assertNotNull(entry.tree.find(TokenType.TITLE, "Etymology 2"));
		assertNull(entry.tree.find(TokenType.TITLE, "Etymology 3"));

		assertNotNull(entry.tree.find(TokenType.LINK, "evening"));
		assertTrue(entry.tree.count(TokenType.DEFINITION) == 4);		
		assertTrue(entry.tree.count(TokenType.BULLET) == 6);
		assertTrue(entry.tree.count(TokenType.TITLE) == 7);
		assertTrue(entry.tree.count(TokenType.TEMPLATE) == 14);
		assertTrue(entry.tree.count(TokenType.LINK) == 1);
		assertTrue(entry.tree.count(TokenType.EXTERNAL_LINK) == 1);
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
====References====
* [http://www.thefreedictionary.com/evenings www.thefreedictionary.com]
===Anagrams===
* {{anagrams|en|a=eeginnsv|eevnings}}
[[File:Chien revolver.JPG|thumb|'''Hammer''' ({{senseno|en|firearms}}) of the firing pin]]

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

====Derived terms====
* {{l|en|Colombian}}

====Translations====
{{trans-top|id=Q739|country in South America}}
* Adyghe: {{t|ady|Коломбие}}
* Afrikaans: {{t|af|Colombia}}
* Albanian: {{t|sq|Kolumbí|f}} {{qualifier|indefinite}}, {{t+|sq|Kolumbía|f}} {{qualifier|definite}}
* Amharic: {{t|am|ኮሎምቢያ}}
* Arabic: {{t|ar|كُولُومْبِيَا|f|tr=kolombiyā}}, {{t|ar|كُلُمبِية|f|tr=kolombiyā}}, {{t|ar|كُلُمبيا|f|tr=kolombiyā}}
*: Egyptian Arabic: {{t|arz|كولومبيا|f|tr=kulumbya}}
* Armenian: {{t+|hy|Կոլումբիա}}
* Aromanian: {{t|rup|Culumbii|f}} {{qualifier|indefinite}}, {{t|rup|Culumbia|f}} {{qualifier|definite}}
* Assamese: {{t|as|কলম্বিয়া}}
* Asturian: {{t+|ast|Colombia|f}}
* Azerbaijani: {{t+|az|Kolumbiya}}
* Basque: {{t|eu|Kolonbia}}
* Belarusian: {{t|be|Калу́мбія|f}}
* Bengali: {{t+|bn|কলম্বিয়া}}, {{t|bn|কলোমবিয়া}}
* Bhutanese: {{t|dz|ཀོ་ལོམ་བི་ཡ}}
* Breton: {{t|br|Kolombia}}
* Bulgarian: {{t|bg|Колу́мбия|f}}
* Burmese: {{t|my|ကိုလံဘီယာ}}
* Catalan: {{t+|ca|Colòmbia|f}}
* Cherokee: {{t|chr|ᎪᎸᎻᏈᎢᎠ}}
* Chinese:
*: Cantonese: {{t|yue|哥倫比亞}}
*: Mandarin: {{t+|cmn|哥倫比亞|tr=Gēlúnbǐyà}}
*: Wu: {{t|wuu|哥倫比亞}}
* Czech: {{t+|cs|Kolumbie|f}}
* Danish: {{t+|da|Colombia|n}}
* Dhivehi: {{t|dv|ކޮލަންބިއާ}}
* Dutch: {{t+|nl|Colombia|n}}, {{t|nl|Colombië|n}} {{q|dated}}
* Esperanto: {{t+|eo|Kolombio}}
* Estonian: {{t+|et|Colombia}}, {{t|et|Kolumbia}}
* Faroese: {{t|fo|Kolumbia}}
* Finnish: {{t+|fi|Kolumbia}}
* French: {{t+|fr|Colombie|f}}
* Friulian: {{t|fur|Colombie|f}}
* Galician: {{t+|gl|Colombia|f}}
* Georgian: {{t+|ka|კოლუმბია}}
* German: {{t+|de|Kolumbien|n}}
* Greek: {{t+|el|Κολομβία|f}}
* Gujarati: {{t|gu|કોલમ્બિયા}}
* Haitian Creole: {{t|ht|Kolonbi}}
* Hawaiian: {{t|haw|Kolomepia}}
* Hebrew: {{t+|he|קוֹלוֹמְבְּיָה|f|tr=kolombya}}
* Hindi: {{t+|hi|कोलम्बिया}}
* Hungarian: {{t+|hu|Kolumbia}}
* Icelandic: {{t+|is|Kólumbía|f}}
* Ido: {{t+|io|Kolumbia}}
* Indonesian: {{t+|id|Kolombia}}
* Interlingua: {{t|ia|Colombia}}
* Irish: {{t|ga|Colóim|f|alt=An Cholóim}}
* Italian: {{t+|it|Colombia|f}}
* Japanese: {{t+|ja|コロンビア|tr=Koronbia}}
* Kalenjin: {{t-check|kln|kol}}
* Kamba: {{t|kam|Kolombia}}
* Kannada: {{t|kn|ಕೊಲೊಂಬಿಯ}}
* Kazakh: {{t+|kk|Колумбия}}
* Khmer: {{t+|km|កូឡុំប៊ី}}
* Kikuyu: {{t|ki|Kolombia}}
* Korean: {{t+|ko|^콜롬비아}}, {{t+|ko|^꼴롬비아}} {{qualifier|North Korea}}
* Kurdish:
*: Northern Kurdish: {{t+|kmr|Kolombiya}}
* Kyrgyz: {{t|ky|Колумбия}}
* Lao: {{t|lo|ໂກລົມບີ}}
* Latvian: {{t+|lv|Kolumbija|f}}
* Lithuanian: {{t+|lt|Kolumbija|f}}
* Luhya: {{t|luy|Kolombia}}
* Luo: {{t|luo|Kolombia}}
* Macedonian: {{t|mk|Колумбија|f}}
* Malay: {{t+|ms|Colombia}}
* Malayalam: {{t|ml|കൊളംബിയ}}
* Maori: {{t|mi|Koromōpia}}
* Marathi: {{t|mr|कोलंबिया}}
* Meru: {{t|mer|Kolombia}}
* Mongolian:
*: Cyrillic: {{t|mn|Колумби}}
* Navajo: {{t|nv|Kolámbiya}}
* Nepali: {{t|ne|कोलोम्बिया}}
* Norman: {{t|nrf|Colombie|f}}
* Norwegian:
*: Bokmål: {{t|nb|Colombia|n}}
* Occitan: {{t+|oc|Colómbia|f}}, {{t|oc|Colòmbia|f}}
* Odia: {{t|or|କଲମ୍ବିଆ}}
* Ossetian: {{t|os|Колумби}}
* Pashto: {{t|ps|کولومبيا|f|tr=kolombyā}}
* Persian:
*: Dari: {{t|prs|کولَمْبِیَا}}
*: Iranian Persian: {{t|fa-ira|کُلُمْبِیا}}
* Polish: {{t+|pl|Kolumbia|f}}
* Portuguese: {{t+|pt|Colômbia|f}}
* Punjabi: {{t|pa|ਕੋਲੰਬੀਆ}}
* Romanian: {{t+|ro|Columbia|f}}
* Russian: {{t+|ru|Колу́мбия|f}}
* Scots: {{t|sco|Colombie}}
* Serbo-Croatian:
*: Cyrillic: {{t|sh|Колу̀мбија|f}}
*: Roman: {{t+|sh|Kolùmbija|f}}
* Shan: {{t|shn|မိူင်းၵူဝ်ႇလမ်ႇပီႇယႃႇ}}
* Sicilian: {{t|scn|Culummia|f}}
* Silesian: {{t|szl|Kolůmbja|f}}
* Sinhalese: {{t|si|කොලොම්‍බියාව}}
* Slovak: {{t|sk|Kolumbia|f}}
* Slovene: {{t+|sl|Kolumbija|f}}
* Spanish: {{t+|es|Colombia|f}}
* Swahili: {{t+|sw|Kolombia}}
* Swedish: {{t+|sv|Colombia|n}}
* Tagalog: {{t|tl|Kolombiya}}
* Tajik: {{t+|tg|Кулумбия}}
* Tamil: {{t|ta|கொலம்பியா}}
* Tatar: {{t|tt|Колумбия}}
* Telugu: {{t+|te|కొలంబియా}}
* Thai: {{t+|th|โคลอมเบีย}}
* Tibetan: {{t|bo|ཁོ་ལོམ་བི་ཡ}}
* Tigrinya: {{t|ti|ኮሎምብያ}}
* Turkish: {{t+|tr|Kolombiya}}
* Turkmen: {{t|tk|Kolumbiýa}}
* Ukrainian: {{t+|uk|Колу́мбія|f}}
* Urdu: {{t|ur|کولَمْبِیا|m}}
* Uyghur: {{t|ug|كولومبىيە}}
* Uzbek: {{t+|uz|Kolumbiya}}
* Vietnamese: {{t|vi|Cô-lôm-bi-a}}
* Yiddish: {{t|yi|קאָלאָמביע|f}}
* Yoruba: {{t|yo|Kòlóńbíà}}, {{t|yo|Kòlóḿbíà}}
{{trans-bottom}}

===See also===
{{list:countries in South America/en}}

===Further reading===
* {{pedia}}

==Asturian==
{{wikipedia|lang=ast}}

===Proper noun===
{{ast-proper noun|f}}

# {{tcl|ast|Colombia|id=Q739}}

==Central Huasteca Nahuatl==

===Proper noun===
{{head|nch|proper noun}}

# {{tcl|nch|Colombia|id=Q739}}
""";		   
}
