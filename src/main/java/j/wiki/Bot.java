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
 * Version 1.0
 */
package j.wiki;


import java.util.logging.Logger;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;


import org.wikipedia.Wiki;

/**
 * Project version: 1.1d
 * Main program
 * Executes a task
 * @author CarlosVM
 */
public class Bot {
	final static String ES_DOMAIN = "es.wiktionary.org";
	final static String EN_DOMAIN = "en.wiktionary.org";

	final static int ENTRY_CNT = 20;
	final static boolean bEdit = true;
	final static boolean MARK_BOT = true;
	
	private static Wiki es_wiki;
	private static Wiki en_wiki;
	
	/**
	 * Program entry point
	 * @param args :  user password filepath UPDATE
	 * If UPDATE is not present it does not changes anything 
	 */
	public static void main(String[] args)
	{
		int iCount;		
		List<List<String>> lEntries = null;
		boolean bOk = false;
		boolean bLogged = false;
		ITask task = null;
		
		setLoggingLevel();
		es_wiki = Wiki.newSession(ES_DOMAIN);
		es_wiki.setMarkBot(MARK_BOT);
		
		en_wiki = Wiki.newSession(EN_DOMAIN);
		
		try
		{
			if( args.length >= 2 )
			{
				es_wiki.login(args[0], args[1].toCharArray());
			}
			bOk = true;
			bLogged = true;
		}
		catch(Exception ex)
		{
			if( args.length > 0 )
			{
				Util.reportError(ex, "Login with user ", args[0]);
			}
			else
			{
				Util.reportError(ex, "Login mssing user and password parameters");				
			}
			bOk = false;
		}
		
		if( bOk )
		{
			task = new j.wiki.task.TskImportEnglish(es_wiki, en_wiki, args);
		}
		
		if( bOk )
		{
			try
			{
				lEntries = task.getEntries();

				if( lEntries == null || lEntries.isEmpty() )
				{
					bOk = false;
				}
				else
				{
					if( lEntries.get(0) == null || lEntries.get(0).isEmpty() )
					{
						bOk = false;
					}
				}
				if( ! bOk )
				{
					Util.reportError("No entries found to process");
				}
			}
			catch(Exception ex)
			{
				Util.reportError(ex, "task.getEntries");
				bOk = false;
			}
		}
		
		if( bOk )
		{
			try
			{
				iCount = ENTRY_CNT;
				for(String strTitle: lEntries.get(0))
				{
					if( task.processEntry(strTitle) )
					{
						Thread.sleep(1000);
						--iCount;
					}
					if( iCount <= 0)
					{
						break;
					}
				}
			}
			catch(Exception ex)
			{
				Util.reportError(ex, "processEntry");
			}
		}
		
		
 		if ( bLogged )
		{
			es_wiki.logout();
		}
		Util.report("End processing");
	}
	

	/***
	 * Sets logging level at system level
	 */
	private static void setLoggingLevel()
	{
		Logger rootLogger = LogManager.getLogManager().getLogger("");
		rootLogger.setLevel(Level.SEVERE);
		for (Handler h : rootLogger.getHandlers()) {
		    h.setLevel(Level.SEVERE);
		}
	}

    
}
