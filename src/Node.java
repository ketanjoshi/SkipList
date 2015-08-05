
public class Node<E extends Comparable<? super E>>
{
	public E data;
	public int level;
	public Node<E> next[];
	public int span[];

	public Node()
	{
		data = null;
		next = null;
	}

	public Node(E value)
	{
		data = value;
		next = null;
	}

	public Node(E value, int level)
	{
		data = value;
		this.level = level;
		next = new Node[level];
		span = new int[level];
	}

	@Override
	public boolean equals(Object obj)
	{
		Node nodeObj = (Node) obj;
		return this.data.equals(nodeObj.data);
	}

	@Override
	public String toString()
	{
		return String.format("Data : %s\tLevel: %s", data, level);
	}

}