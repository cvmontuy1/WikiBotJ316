package j.wiki.entry;

import j.wiki.Util;

public class TextSrcSimple extends TextSrc {

	private String text;
	
	public TextSrcSimple(String txt)
	{
		this.text = txt;
	}
	
	@Override
	public boolean isEmpty()
	{
		return Util.isNullOrEmpty(text);
	}
	
	@Override
	public String getText(boolean bChildren) {
		// TODO Auto-generated method stub
		return text;
	}

}
