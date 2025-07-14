package j.wiki.entry;

public enum Genre {
	MASCULINE,
	FEMENINE,
	NEUTRE;
	public String toString()
	{
		String str = "";
		switch(this)
		{
			case MASCULINE:
				str = "m";
				break;
			case FEMENINE:
				str = "f";
				break;
			case NEUTRE:
				str = "n";
				break;			
		}
		return str;
	}
}
