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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import j.wiki.Util;

public class Token {
    private TokenType type;
    private String value;
    private Token parent;
    private List<Token> children;
    private int startPos;
    private int endPos;
    private int level;
	private int index;  

    public Token(TokenType type, String value, int startPos, int endPos) {
        this.type = type;
        this.value = value;
        this.startPos = startPos;
        this.endPos = endPos;
        this.children = new ArrayList<>();
        fixValue();
    }

    // Getters and setters
    public TokenType getType() { return type; }
    public String getValue() { return value; }
    public Token getParent() { return parent; }
    public List<Token> getChildren() { return children; }
    public int getStartPos() { return startPos; }
    public int getEndPos() { return endPos; }
    
    public int getLevel()	{ return level; }
    public void setLevel(int level) { this.level = level; }
    public void setType(TokenType type) { this.type = type; }
    
    public void setParent(Token parent) { this.parent = parent; }
    public void addChild(Token child) {
        child.setParent(this);
        children.add(child);
    }	
    
    public void appendValue(String value)
    {
    	if( Util.isNullOrEmpty(this.value)  )
    	{
    		this.value = value.trim();
    	}
    	else
    	{
    		if( this.value.endsWith("|") )
    		{
    			this.value = this.value + value.trim();
    		}
    		else
    		{
    			this.value = this.value + " " + value.trim();
    		}
    	}
    }
    
    public boolean hasChildren()
    {
    	return children != null && children.size() > 0;
    }
    
    public Token getLastChildren()
    {
    	Token child = null;
    	
    	if( hasChildren() )
    	{
    		child = children.get(children.size()-1);    		
    	}
    		
    	return child;
    }
    
    public Token getLastChildrenDeep()
    {
    	Token child = null;
    	Token grandson;

    	child = getLastChildren();
    	if( child != null && child.hasChildren() )
    	{
    		grandson = child.getLastChildren();
    		if( grandson != null)
    		{
    			child = grandson;
    		}
    	}
    	
    	return child;
    }

    
    
    public Token getLastChildren(TokenType type)
    {
    	Token child = null;
    	
    	if( children != null && children.size() > 0)
    	{
    		for(int i=children.size()-1;i>=0; --i ) 
    		{
    			child = children.get(i);
    			if( child.getType() == type)
    			{
    				break;
    			}
    		}
    	}
    		
    	return child;    	
    }
    
    public int getPrefixLen()
    {
    	int iLen;
    	switch(type)
    	{
    		case TokenType.TITLE:
        		iLen = level;
        		break;
        	default:
        		iLen = type.getPrefixLen();
        		break;
    	}
    	return iLen;
    }
    
    public Token find(TokenType type)
    {
    	return find(type, null);
    }
    
    public Token find(TokenType type, String value)
    {
    	Token token = null;
    	
    	if( this.type == type && ( value == null || this.value.equals(value)) )
		{
			token = this;
		}
    	else
    	{
    		for(Token t: children)
    		{
    			if( t.type == type && ( value == null || t.value.equals(value)) )
    			{
    				token = t;
    				break;
    			}
    		}
    	}
    	
    	if( token == null)
    	{
    		for(Token t: children)
    		{
    			token = t.find(type, value);
    			if( token !=null)
    			{
    				break;
    			}
    		}    		
    	}
    	
    	return token;		
    }
    
    public int count(TokenType type)
    {
    	
    	int iCount = 0;
    	
    	if( this.type == type )
		{
    		++iCount;
		}
    	else
    	{
    		for(Token t: children)
    		{
    			iCount = iCount + t.count(type);
    		}
    	}    	
    	return iCount;
    }

	public String toString()
	{
		StringBuilder buffer = new StringBuilder();
		
		buffer.append(getIdent()).append("type:").append(type);

		if( level > 0)
		{
			buffer.append(" level:").append(level);
		}
		if( Util.isNotNullOrEmpty(value))
		{
			buffer.append(" value:").append(value);
		}
		buffer.append(Util.LF);
		
		for(Token child: children)
		{
			buffer.append(child.toString());
		}
		
		return buffer.toString();
	}

//**********************************************************************************
//PRIVATE
	
	private void fixValue()
	{
		Matcher matcher;
		String group; 
		boolean bLinkFound = false;
		
		if( type.equals(TokenType.TEMPLATE) )
		{
			StringBuilder resultado = new StringBuilder();			
			 matcher = Parser.PAT_LINK.matcher(value);
			 while (matcher.find()) 
			 {
				 if( matcher.groupCount() >= 3)
				 {				
					 group = matcher.group(3);
					 if( group == null)
					 {
						 group = matcher.group(1);
					 }
				 }
				 else
				 {
					 group = matcher.group(1);					 
				 }
		         matcher.appendReplacement(resultado, group);
		         bLinkFound = true;
			 }
			 if( bLinkFound )
			 {
				 value = resultado.toString();
			 }
			 
		}
	}
	
	/****
	 *  
	 * @return the depth level in the tree
	 */
	private int depth()
	{
		Token lparent;
		int iDeep = 0;
		
		lparent = parent;
		while( lparent != null)
		{
			++iDeep;
			lparent = lparent.parent;
		}
		return iDeep;
	}	

	private String getIdent()
	{
		StringBuilder buffer = new StringBuilder();
		int iDepth = depth();
		
		for(int i=0; i<iDepth; ++i)
		{
			buffer.append("\t");
		}
		
		return buffer.toString();
	}	
 
}
