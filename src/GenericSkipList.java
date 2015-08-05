import java.util.Iterator;
import java.util.Random;

/**
 * Implementation of skip list
 * @author ketanjoshi
 */
public class GenericSkipList<T extends Comparable<? super T>> implements Iterable<T>
{
	
	private Node<T> head;
	private Node<T> tail;
	private int size = 0;

	private int maxLevel = 10;
	private double maxSize = Math.pow(2, maxLevel);
	
	private T lastElement = null;

	public GenericSkipList()
	{
		head = new Node<T>(null, maxLevel);
		tail = new Node<T>(null, maxLevel);
		
		for (int i = 0; i < maxLevel; i++)
		{
			head.next[i] = new Node();
			tail.next[i] = new Node();
			head.next[i] = tail.next[i];
			head.span[i] = 0;
		}
		size = 0;
	}
	
	/**
	 * @param x - Node of Floor or Ceiling function parameter.
	 * @return nearest lower element if x is not present, else returns x.
	 */
	private Node<T> FindNearestLower(Node<T> x)
	{
		Node<T> p = head;
		
		if(IsEmpty())
			return null;

		for (int i = maxLevel - 1; i >= 0; i--)
		{
			if(p.next[i].data != null)
			{
				while (p.next[i].data.compareTo(x.data) < 0)
				{
					p = p.next[i];
					if (p.next[i].data == null)
						break;
				}
			}
		}
		if(p.next[0].data != null)
		{
			if (p.next[0].data.compareTo(x.data) == 0)
				return p.next[0];
			else
				return p;				
		}
		else
			return p;
		
	}

	/**
	 * Procedure searches the given element in the skip list.
	 * @param x : element of type <T> to be searched.
	 * @return element itself if found else null, array of node locations where search moved downwards.
	 */
	private ElementLookup FindNode(Node<T> x)
	{
		Node<T> p = head;
		ElementLookup elem = new ElementLookup(maxLevel);
		
		if(IsEmpty())
		{
			elem.element = null;
			elem.prev = null;
			return elem;
		}

		for (int i = maxLevel - 1; i >= 0; i--)
		{
			if(p.next[i].data != null)
			{
				while (p.next[i].data.compareTo(x.data) < 0)
				{
					p = p.next[i];
					if (p.next[i].data == null)
						break;
				}
			}

			elem.prev[i] = new Node<T>();
			elem.prev[i] = p;
		}
		if(p.next[0].data != null)
		{
			if (p.next[0].data.compareTo(x.data) == 0)
				elem.element = p.next[0];
		}
		else
			elem.element = null;
		return elem;
	}
	
	/**
	 * Removes an element (if present) from the skiplist.
	 * @param x - element to be removed.
	 * @return true if element deleted, false if element not present.
	 */
	public boolean Remove(T x)
	{
		Node<T> node = new Node<>(x);
		ElementLookup place = FindNode(node);
		
		if(place.element == null)
		{
			return false;
		}
		
		Node<T> p = place.element;

		if(p.next[0] == tail.next[0])
		{
			lastElement = (T) place.prev[0].data;
		}

		for (int i = 0; i < p.level; i++)
			place.prev[i].next[i] = p.next[i];

		size--;
		
		return true;
		
	}
	
	/**
	 * Checks if element is present in the skiplist.
	 * @param x - element to be checked.
	 * @return true if present, else false.
	 */
	public boolean Contains(T x)
	{
		ElementLookup place = FindNode(new Node(x));
		return place.element != null ? true : false;
	}

	/**
	 * Procedure inserts given element in the skip list 
	 * @param x : element of type <T> to be inserted
	 */
	public void Add(T x)
	{
		Node<T> node = new Node<>(x);
		ElementLookup place = FindNode(node);
		
		if(place.element != null)
		{
//			System.out.println("Element already present. Cannot insert duplicate element.");
			return;
		}
		else
		{
			int level = ChooseLevel();
			Node<T> insertNode = new Node(x, level);

			if (IsEmpty())
			{
				for (int i = 0; i < level; i++)
				{
					insertNode.next[i] = head.next[i];
					head.next[i] = insertNode;
				}
			}
			else
			{
				for (int i = 0; i < level; i++)
				{
					insertNode.next[i] = new Node();
					insertNode.next[i] = place.prev[i].next[i];
					place.prev[i].next[i] = insertNode;
				}
			}
			if(insertNode.next[0] == tail.next[0])
				lastElement = insertNode.data;
			
			size++;
			
			if(IsMaxLevelAchieved())
			{
				RebuildHeadTail();
			}
		}
	}


	private void RebuildHeadTail()
	{
		Node<T> tempHeadNext[] = new Node[maxLevel];
		int previosMaxLevel = maxLevel;

		for (int i = 0; i < maxLevel; i++)
		{
			tempHeadNext[i] = new Node<T>();
			tempHeadNext[i] = head.next[i];
		}
		maxLevel += 5;
		maxSize = Math.pow(2, maxLevel);
		
		head = new Node<T>(null, maxLevel);
		tail = new Node<T>(null, maxLevel);
		
		for (int i = 0; i < previosMaxLevel; i++)
		{
			head.next[i] = new Node();
			tail.next[i] = new Node();
			head.next[i] = tempHeadNext[i];
		}
		
		for (int i = previosMaxLevel; i < maxLevel; i++)
		{
			head.next[i] = new Node();
			tail.next[i] = new Node();
			head.next[i] = tail.next[i];
		}
	}

	private boolean IsMaxLevelAchieved()
	{
		return size >= maxSize;
	}

	/**
	 * Random level generator for new nodes. This is the size of the "next" array of the node.
	 * @return - level of "next" pointer array for the node.
	 */
	private int ChooseLevel()
	{
		int l = 1;
		Random rand = new Random();
		while(rand.nextInt(2) == 1 && l < maxLevel)
		{
			l++;
		}
		return l;
	}

	/**
	 * Checks if skiplist is empty.
	 * @return - true if empty, else false.
	 */
	public boolean IsEmpty()
	{
		return size == 0;
	}

	@Override
	public Iterator<T> iterator()
	{
		return null;
	}

	/**
	 * Calculates flooring function. Find the Greatest element that is <= x. 
	 * @param x - element for which floor needs to be calculated.
	 * @return floor value of x.
	 */
	public T Floor(T x)
	{
		Node<T> xNode = new Node<T>(x);
		Node<T> previousNode = FindNearestLower(xNode);
		
		if(previousNode == null)
			return null;
		else
			return (T) previousNode.data;
	}

	/**
	 * Calculates ceiling function. Find least element that is >= x.
	 * @param x - element for which ceiling needs to be calculated.
	 * @return ceiling value of x.
	 */
	public T Ceiling(T x)
	{
		Node<T> xNode = new Node<T>(x);
		Node<T> previousNode = FindNearestLower(xNode);
		
		if(previousNode == null)
			return null;
		else
		{
			if(previousNode.data.compareTo(x) == 0)
				return previousNode.data;
			else if(previousNode.next[0] != null)
				return previousNode.next[0].data;
			else
				return null;
		}
	}

	/**
	 * @return last element in the skiplist.
	 */
	public T Last()
	{
		if(IsEmpty())
		{
			System.out.println("Invalid operation. List is empty.");
			return null;
		}
		return lastElement;
	}

	/**
	 * @return first element in the skiplist.
	 */
	public T First()
	{
		if(IsEmpty())
		{
			System.out.println("Invalid operation. List is empty.");
			return null;
		}
		return head.next[0].data;
	}

	public T FindIndex(T x)
	{
		return null;
	}

}
