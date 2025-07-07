package j.wiki.entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Definition container
 */
public class DefContainer {
	private DefContainer parent;
	private List<DefContainer> children;
	private Definition definition;
	
	public DefContainer(DefContainer parent)
	{
		this.parent = parent;
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
	
	public boolean hasParent()
	{
		return parent != null;
	}
	
	public DefContainer getParent()
	{
		return parent;
	}
	
	public void addDefinition(Definition definition)
	{
		this.definition = definition;
	}
	
	public Definition getParentDefinition()
	{
		Definition def = null;
		
		if( parent != null)
		{
			def = parent.getDefinition();
		}
		return def;
	}
	
	public Definition getDefinition()
	{
		return definition;
	}
}
