package j.wiki.entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Definition container
 */
public class DefContainer {
	private int level;
	private DefContainer parent;
	private List<DefContainer> children;
	private Definition definition;
	
	public DefContainer(DefContainer parent, int level)
	{
		this.parent = parent;
		this.level = level;
		children = new ArrayList<DefContainer>();
		if( parent != null)
		{
			parent.children.add(this);
		}
	}
	
	public boolean hasChildren()
	{
		return children.size() > 0;
	}
	
	public DefContainer getParent()
	{
		return parent;
	}
	
	public void addDefinition(Definition definition)
	{
		this.definition = definition;
	}
	
	public Definition getDefinition()
	{
		return definition;
	}
}
