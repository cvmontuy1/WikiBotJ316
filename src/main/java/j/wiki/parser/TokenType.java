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

public enum TokenType {
	ROOT		(false, false, 0),
    TEXT		(false, false, 0),
    TITLE		(true, true, 1),
    TEMPLATE	(true, true, 2),
    LINK		(true, true, 2),
    DEFINITION	(false, false, 0),
    BULLET		(false, false, 0),
    BOLD		(true, true, 3),
    ITALIC		(true, true, 2),
    COMMENT		(false, true, 3),
    REFERENCE	(false, true, 3),
    CATEGORY	(false, true, 3),
    EXTERNAL_LINK (false, true, 3),
	FILE		(true, true, 2);
	
	private TokenType(boolean bContainer, boolean bContent, int prefixLen)
	{
		this.bContent = bContainer;
		this.bValue = bContent;
		this.prefixLen = prefixLen;
	}
    public boolean isContainer()
    {
    	return bContent;
    }
    public boolean usesValue()
    {
    	return bValue;
    }
    public int getPrefixLen()
    {
    	return prefixLen;
    }
    private boolean bContent;
	private boolean bValue;
	private int prefixLen;
}
