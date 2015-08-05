
public class ElementLookup
{
	public Node element;
	public Node prev[];
	
	public ElementLookup(int maxLevel)
	{
		element = null;
		prev = new Node[maxLevel];
	}
}
