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
package j.wiki.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

import j.wiki.Util;

public class Parser 
{	
	   public Token parse(String wikitext, boolean bIgnoreTxtDecs) 
	   {
	        String text;		   
	        Token rootToken = new Token(TokenType.ROOT, "ROOT", 0, wikitext.length());
	        if( bIgnoreTxtDecs )
	        {
	        	text = wikitext;
	        	text = text.replaceAll("'''", "");	        	
	        	text = text.replaceAll("''", "");
	        }
	        else
	        {
	        	text = wikitext.trim();
	        }
	        text = parserPhase1(text);
	        parseRecursive(text, 0, text.length(), rootToken);
	        return rootToken;
	    }	

/***************************************************
 * 	PRIVATE SECTION
 */
	   private String parserPhase1(String original)
	   {
		   return original.replaceAll(TEMPLATE_WIKI , "$1").replaceAll(TEMPLATE2, "$1"); 
	   }

	    private int parseRecursive(String wikitext, int startPos, int endPos, Token parentToken) {
	    	Token child;
	    	TokenType type;
	    	
	        int currentPos = startPos;

	        
	        while (currentPos < endPos) {
	            Result bestMatch = findNextToken(wikitext, currentPos, endPos);
	            type = bestMatch.type;
	            
	            if (bestMatch.isEmpty()) {
	                // Add remaining as text
	                currentPos = addTextToken(wikitext, currentPos, endPos, parentToken, true);
	                continue;
	            }
	            
	            // Add text before the token if any
	            if ( bestMatch.start() > currentPos ) {
	                addTextToken(wikitext, currentPos, bestMatch.start(), parentToken, false);
	            }
	            
	            // Handle the token
	            Token newToken = new Token(type, bestMatch.getContent(), bestMatch.start(), bestMatch.end());
	            newToken.setLevel(bestMatch.level);
	            if( newToken.getType() == TokenType.LINK )
	            {
	            	child = parentToken.getLastChildren();
	            	if( child != null )
	            	{
	            		if( 
	            			child.getType() == TokenType.DEFINITION || 
           					child.getType() == TokenType.TEXT || 
         					child.getType() == TokenType.BULLET ) 	
		            	{
		            		child.appendValue(newToken.getValue());
		            		child.addChild(newToken);
		            	}
	            	}
	            	else
	            	{
	            		newToken.setType( TokenType.TEXT);
	            		parentToken.addChild(newToken);
	            	}
	            }
	            else
	            {
	            	parentToken.addChild(newToken);
	            }
	            
	            // For container tokens, parse their content recursively
	            if ( type.isContainer() ) 
	            {
	                currentPos = parseRecursive(wikitext, bestMatch.start() + newToken.getPrefixLen(), bestMatch.end() - newToken.getPrefixLen(), newToken);
	                if( currentPos == bestMatch.end() - newToken.getPrefixLen())
	                {
	                	currentPos = bestMatch.end();
	                }
	            } 
	            else 
	            {
	                currentPos = bestMatch.end();
	            }
	        }
	        
	        return currentPos;
	    }

	    private Result findNextToken(String wikitext, int startPos, int endPos) {
	    	Result result = new Result();
	    	char c;	    	
       
	        c = wikitext.charAt(startPos);
	        while( (c == '\r' || c == '\n') && startPos < endPos-1)
		    {
	        	startPos++;
	        	c = wikitext.charAt(startPos);
		    }        
	        
	        for (Map.Entry<TokenType, Pattern> entry : PATTERNS.entrySet()) {
	            Matcher matcher = entry.getValue().matcher(wikitext);
	            matcher.region(startPos, endPos);     	
      
	            
	            if (matcher.find()) 
	            {           	
	                if (result.matchresult == null || matcher.start() < result.matchresult.start()) 
	                {
	                    result.matchresult = matcher.toMatchResult();
	                    result.type = entry.getKey();
	                }
	            }
	        }       
	        
	        return result;
	    }

	    private int addTextToken(String wikitext, int startPos, int endPos, Token parentToken, boolean bUptoLine)
	    {
	    	int iPos;
	        String text;
	        char c;
	        
	        c = wikitext.charAt(startPos);
	        while( (c == '\r' || c == '\n') && startPos < endPos-1)
		    {
	        	startPos++;
	        	c = wikitext.charAt(startPos);
		    }
	        
	        if( bUptoLine )
	        {
		        iPos = startPos + 1;
		        if( iPos < endPos )
		        {
			        c = wikitext.charAt(iPos);
			        while(c != '\r' && c != '\n' && iPos < endPos-2)
			        {
			        	++iPos;
			        	c = wikitext.charAt(iPos);	        	
			        }
			        if( c == '\r' || c == '\n')
			        {
				        if( iPos > startPos && iPos < endPos-1 )
				        {
				        	endPos = iPos+1;
				        }
			        }
		        }
	        }
	        
	        text = wikitext.substring(startPos, endPos);
	        if( !parentToken.getValue().equals(text) )
	        {
	        	Token child = parentToken.getLastChildren();
	        	if( child != null && ( child.getType() == TokenType.DEFINITION || child.getType() == TokenType.BULLET ) )
        		{
        			child.appendValue(text);
	        	}
	        	else
	        	{
			        if (!text.trim().isEmpty()) 
			        {
			            Token textToken = new Token(TokenType.TEXT, text, startPos, endPos);
			            parentToken.addChild(textToken);
			        }
	        	}
	        }
	        return endPos;
	    }


	    private class Result
	    {
	    	MatchResult matchresult;
	    	TokenType type;
	    	int level;			// Indicates title level or definition or bullet levels
	    	
	    	public Result()
	    	{
	    		matchresult = null;
	    		type = null;
	    		level = 0;
	    	}
	    	
	    	public boolean isEmpty()
	    	{
	    		return matchresult == null;
	    	}
	    	
	    	public int start()
	    	{
	    		return matchresult.start();
	    	}
	    	
	    	public int end()
	    	{
	    		return matchresult.end();
	    	}
	    	
	    	public String getContent()
	    	{
	    		int iIdx;
	    		String complete = "";
	    		String content = "";
	    		
	    		complete = group(0);
	    		if( type.usesValue() )
	    		{
		    		content = group(1);
		    		if( Util.isNullOrEmpty(content) )
		    		{
		    			content = complete;
		    		}
	    		}
	    		
	    		switch( type )
	    		{
	    			case TokenType.BULLET:
		    			if( complete.length() >= 2)
		    			{
		    				content = "2";
		    			}
		    			else
		    			{
		    				content ="l";
		    			}
	    				break;
	    			case TokenType.TITLE:
	    				iIdx = 0;
	    				while( complete.charAt(iIdx) == '='  && iIdx < complete.length()-1)
	    				{
	    					++iIdx;
	    				}
	    				level = iIdx;
	    				break;
	    			case TokenType.DEFINITION:	    				
	    				iIdx = 0;
	    				while( iIdx < complete.length() && complete.charAt(iIdx) == '#')
	    				{
	    					++iIdx;
	    				}
	    				level = iIdx;
	    				break;
	    			default:
	    				break;
	    		}
	    		return content;
	    	}
	    	
	    	public String group(int igroup)
	    	{
	    		String strGrp = "";
	    		int iGroupCnt = matchresult.groupCount();
	    		if ( iGroupCnt >= igroup )
	    		{
	    			strGrp = matchresult.group(igroup);
	    		}
	    		else
	    		{
	    			Util.reportError("Group count:", iGroupCnt, " requested:", igroup);
	    		}
	    		return strGrp;
	    	}	    	    	
	    }	    
   
	    private final static String WORDS = "([A-Za-z0-9/\\-áéíóúñÑàèìòùäëïöüâêîôûæœçÁÉÍÓÚÄËÏÖÜÆŒÇ&\\s\\,\\.\\:\\;]+)";
	    
	    private static final Map<TokenType, Pattern> PATTERNS = new HashMap<TokenType, Pattern>();
	    public static final Pattern PAT_LINK = Pattern.compile("\\[\\[" + WORDS + "(\\|" + WORDS+")?\\]\\]");
	    public static final String  TEMPLATE_WIKI = "\\{\\{w\\|" + WORDS + "\\}\\}";
	    public static final String  TEMPLATE2 =  "<<" + WORDS + ">>";
	    
	    static 
	    {    
	    	PATTERNS.put(TokenType.TEMPLATE,	Pattern.compile("\\{\\{(.+?)\\}\\}", Pattern.DOTALL));
	    	PATTERNS.put(TokenType.LINK, 		PAT_LINK);
	    	PATTERNS.put(TokenType.TITLE,		Pattern.compile("=+" + WORDS + "=+"));
	    	PATTERNS.put(TokenType.DEFINITION, 	Pattern.compile("^(###|##|#)", Pattern.MULTILINE));
	    	PATTERNS.put(TokenType.BULLET,		Pattern.compile("^(\\s*\\*\\s|\\s*##?\\*\\s)", Pattern.MULTILINE));	    	
	    	PATTERNS.put(TokenType.BOLD,		Pattern.compile("'''WORDS'''", Pattern.DOTALL));
	    	PATTERNS.put(TokenType.ITALIC,		Pattern.compile("''WORDS''", Pattern.DOTALL));
	    	PATTERNS.put(TokenType.COMMENT,		Pattern.compile("<!--(.+?)-->", Pattern.DOTALL));
	    	PATTERNS.put(TokenType.REFERENCE, 	Pattern.compile("<ref(.+?)</ref>", Pattern.DOTALL));
	    	PATTERNS.put(TokenType.FILE,		Pattern.compile("\\[\\[File:(.+?)\\]\\]", Pattern.DOTALL));
	    	PATTERNS.put(TokenType.EXTERNAL_LINK, Pattern.compile("\\[(https?://.+?)\\]"));
	    }
    
}
