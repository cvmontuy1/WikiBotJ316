This Java program processes a text file provided as an argument, containing a list of entries.
For each entry, the program retrieves content from en.wiktionary.org and attempts to convert it into the format used by es.wiktionary.org. If the entry does not exist on es.wiktionary.org, 
it will be created automatically.

Version 1.1.e supports importing the following types of content:

Given names, Family names, Place names, Plural forms, Verbs ending in -ing, Verbs ending in -ed, Initials

Dependencies:
Uses the wiki-java library:
  groupId:    org.wikipedia  
  artifactId: wiki-java  
  version:    0.39-SNAPSHOT  

Tested on: JRE 1.8
