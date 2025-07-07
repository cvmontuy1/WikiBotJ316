package j.wiki.entry;

public abstract class TextSrc {
	public String getText()
	{
		return getText(false);
	}
	
	public abstract boolean isEmpty();
	public abstract String getText(boolean bChildren);
	
	public boolean equals(TextSrc txtsrc)
	{
		return getText().equals(txtsrc.getText());
	}
}
