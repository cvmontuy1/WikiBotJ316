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
 * Version 1.1
 */
package j.wiki.task;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.wikipedia.Wiki;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import j.wiki.ITask;
import j.wiki.Util;
import j.wiki.entry.Command;
import j.wiki.entry.Entry;
import j.wiki.entry.En2Es;
import j.wiki.parser.ParserEntry;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TskImportEnglish
implements ITask
{
	/**
	 * 
	 * @param es_wiki0	Wiki connector to es.wiktionary.org
	 * @param en_wiki0	Wiki connector to en.wiktionary.org
	 * @param args	args[0]  User, args[1] password, args[2] file name, args[3] "UPDATE", args[4] "OVERWRITE"
	 */
	public TskImportEnglish(Wiki es_wiki0, Wiki en_wiki0, String[] args)
	{
		if( args.length < 3)
		{
			throw new IllegalArgumentException("Arguments expected user password control_file [UPDATE]");
		}
		strFileName = args[2];
		
		this.es_wiki = es_wiki0;
		this.en_wiki = en_wiki0;
		
		bUpdateWiki = false;
		bOverwrite = false;
		
		if( args != null && args.length>3)
		{
			if( args[3].toUpperCase().equals("UPDATE") )
			{
				bUpdateWiki = true;
			}
			if( args.length > 4)
			{
				if( args[4].toUpperCase().equals("OVERWRITE") )
				{
					bOverwrite = true;
				}
			}
		}
	}
	
	public List<List<String>> getEntries()
	throws IOException
	{
		String strLine;
		Command cmd;
		String[] strArray;
		
		List<List<String>> lEntries = new ArrayList<List<String>>();
		List<String> lEntriesInt = new ArrayList<String>();
		lEntries.add(lEntriesInt);
		 try {
		      File file = new File(strFileName);
		      fileParent = file.getParentFile();
		      fileParent = new File(fileParent, "data");
		      fileParent.mkdir();
		      Scanner myReader = new Scanner(file);
		      while (myReader.hasNextLine()) {
		        strLine = myReader.nextLine();
		        
		        String regex = "(\\|\\s?)";
		        strArray = strLine.split(regex);

		        if( strArray.length > 0)
		        {
		        	cmd = new Command(strArray);
		        	
	        		mapEntries.put(cmd.entry, cmd);
	        		lEntriesInt.add(cmd.entry);
		        }
		      }
		      myReader.close();
		    } 
		 	catch (FileNotFoundException e) 
		 	{
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		 return lEntries;
	}

	
	public boolean processEntry(String strTitle)
	{
		List<String> content;
		boolean bResult = false;
		String strWikiText;
		Entry entry;
		ParserEntry pentry;
		
	
		try
		{
			content = getEnglish(strTitle);
			if( content.size() == 0)
			{
				Util.reportError("Entry not found:" + strTitle);
			}
			else
			{
				pentry = new ParserEntry("en", strTitle, concatenate(content));				
				entry = En2Es.buildEntry(strTitle, pentry);
				if( entry.isComplete())
				{
					strWikiText = entry.toWiki();
					System.out.println("****************************");
					System.out.println("Entry:"+ strTitle );
					System.out.println(strWikiText);
					
					if( bUpdateWiki )
					{
						if( Util.isNotNullOrEmpty(strWikiText) )
						{
							if( !esExist(strTitle) || bOverwrite)
							{
								es_wiki.edit(strTitle, strWikiText, "Generado con base en el contenido de en.wiktionary");
							}
						}
					}
				}
			}
		}
		catch(Exception ex)
		{
			Util.reportError(ex, "processEntry:", strTitle);			
		}

		return bResult;
	}
	
	public static Command getCommand(String strEntry)
	{
		return mapEntries.get(strEntry);
	}
	
/***************************************
 * 	PRIVATE SECTION
 */
	
	private boolean esExist(String strTitle)
	{
		List<String> content_es;		
		List<String> titles;
		boolean bExist;
		
		titles = new ArrayList<String>();
		titles.add(strTitle);
		
		try
		{
			content_es = es_wiki.getPageText(titles);
			if( content_es == null || content_es.size() == 0 || content_es.get(0) == null)		
			{
				bExist = false;
			}
			else
			{
				bExist = true;
			}
		}
		catch(Exception ex)
		{
			Util.reportError(ex, "esExist");
			bExist = false;;
		}
		return bExist;
	}
	
	private List<String> getEnglish(String strTitle)
	{
		File file;
		List<String> content = null;		
		List<String> titles;
		titles = new ArrayList<String>();
		titles.add(strTitle);
		try
		{
			file = new File(fileParent, strTitle+".wtx");
			if( file.exists() )
			{
				content = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
			}
			else
			{
				content = getEnglish(en_wiki.getPageText(titles));
				Files.write(file.toPath(), content, StandardCharsets.UTF_8);
			}
		}
		catch(Exception ex)
		{
			Util.reportError(ex, "getEnglish");
		}
		return content;
	}
	
	private String concatenate(List<String> content)
	{
		StringBuilder buffer = new StringBuilder();
		
		for(String s: content)
		{
			buffer.append(s).append(Util.LF);
		}
		return buffer.toString();
	}
	
	
	private List<String> getEnglish(List<String> content0)
	{
		List<String> content;
		String[] lines;
		String linesep;
		String strLine;
		boolean bEnglish;
		String matchedText; 
		Matcher matcher2;
		Matcher matcher3;
		Pattern level2;
		Pattern level3;
		
		content = new ArrayList<String>();
		level2 = Pattern.compile("\\=\\=([a-zA-Z0-9 -_]+)\\=\\=" );
		level3 = Pattern.compile("\\=\\=\\=([a-zA-Z0-9 -_]+)\\=\\=\\=" );
		linesep = "[\\n\\r]+";		
		bEnglish = false;
		if( content0 != null)
		{
			for(String string : content0)
			{
				if( string != null)
				{
					lines = string.split(linesep);
					for(String line: lines )
					{
						matcher2 = level2.matcher(line);
						matcher3 = level3.matcher(line);
						
						if( bEnglish )
						{
							strLine = line;
						}
						else
						{
							strLine = null;
						}
			
				        if (!matcher3.find() && matcher2.find()) 
				        {
				            // Retrieve the matched text
				            matchedText = matcher2.group(1);
				            if( matchedText != null)
				            {
					            if( matchedText.equals("english") || matchedText.equals("English")  )
					            {
					            	bEnglish = true;
					            }
					            else
					            {
					            	bEnglish = false;
					            }
				            }
				        }
				        if( bEnglish && strLine != null)
				        {
				        	content.add(strLine);
				        }
					}
				}
			}
		}
		return content;
	}
	
	private File fileParent;
	private boolean bUpdateWiki;
	private String strFileName;
	private Wiki es_wiki, en_wiki;	
	private boolean bOverwrite;
	private static Map<String, Command> mapEntries = new HashMap<String, Command>();
	
}
