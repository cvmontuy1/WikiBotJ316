package j.wiki;

import org.junit.jupiter.api.*;


public class UtilTest {
	@Test
	public void test1()
	{
		assert(Util.isNullOrEmpty(""));
		assert(Util.isNullOrEmpty(null));
		assert(!Util.isNotNullOrEmpty(null));
		assert(!Util.isNotNullOrEmpty(""));		
		
		assert(!Util.isSingleWord(null));
		assert(!Util.isSingleWord(""));		
		assert(!Util.isSingleWord("a b"));	
		assert(!Util.isSingleWord("a\tb"));
		assert(Util.isSingleWord("a_1"));		
		assert(Util.isSingleWord("a"));		
		assert(Util.isSingleWord("word"));		

		assert(!Util.isLinked(null));
		assert(!Util.isLinked(""));		
		assert(!Util.isLinked("[https...]"));		
		assert(Util.isLinked("[[https]]"));		

		assert( Util.getLastWord(null).equals(""));
		assert( Util.getLastWord("").equals(""));
		assert( Util.getLastWord("word").equals("word"));
		assert( Util.getLastWord("The word").equals("word"));
	}
}
