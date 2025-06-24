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

import java.util.List;

import j.wiki.Util;

public class ParserEntry {
	public String lang;
	public String name;
	public Token tree;
	
	public ParserEntry(String lang, String name, String wikitext)
	{
		Parser parser = new Parser();
		
		this.lang = lang;
		this.name = name;		
		
		this.tree = parser.parse(wikitext, true /* ignore text decorations like links or bold italics */);
		
	}
	
	public ParserEntry(String lang, String name, Token node)
	{
		this.lang = lang;
		this.name = name;
		this.tree = node;
	}
	
	public String toString()
	{
		StringBuilder buffer = new StringBuilder();
		
		buffer.append("Entry language: ").append(lang).append(" name:").append(name).append(Util.LF);
		buffer.append("Entry conntent:").append(Util.LF);
		buffer.append(tree.toString());
		
		return buffer.toString();
	}
	
	public List<Token> getChildren()
	{
		return tree.getChildren();
	}
}
