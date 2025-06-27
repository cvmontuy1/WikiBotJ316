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

public class EtimLang {
	public enum Type { BORROWED, SUFIX, PREFIX, DEFAULT};
	public Type type;
	public String lang;		
	public String text;
	public String tran; // transliteration
	
	public EtimLang(Type type, String lang, String text)
	{
		this.type = type;
		this.lang = lang;
		this.text = text;
	}

	public EtimLang(Type type, String lang, String text, String tran)
	{
		this.type = type;
		this.lang = lang;
		this.text = text;
		this.tran = tran;
	}
	
}
