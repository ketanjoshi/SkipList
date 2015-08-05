import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;


public class SkipListDriver
{
	public static void main(String[] args)
	{
		Scanner sc = null;

		if (args.length > 0) {
			File file = new File(args[0]);
			try {
				sc = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			sc = new Scanner(System.in);
		}

		String operation = "";
		long operand = 0;
		int modValue = 997;
		long result = 0;
		Long returnValue = null;
		GenericSkipList<Long> skipList = new GenericSkipList<Long>();

		long startTime = System.currentTimeMillis();

		try
		{
			while (!((operation = sc.next()).equals("End")))
			{
				switch (operation)
				{
				case "Add":
				{
					operand = sc.nextLong();
					skipList.Add(operand);
					result = (result + 1) % modValue;
					break;
				}
				case "Contains":
				{
					operand = sc.nextLong();
					if (skipList.Contains(operand))
						result = (result + 1) % modValue;
					break;
				}
				case "Remove":
				{
					operand = sc.nextLong();
					if (skipList.Remove(operand))
						result = (result + 1) % modValue;
					break;
				}
				case "Floor":
				{
					operand = sc.nextLong();
					returnValue = skipList.Floor(operand);
					if (returnValue != null)
						result = (result + returnValue) % modValue;
					break;
				}
				case "Ceiling":
				{
					operand = sc.nextLong();
					returnValue = skipList.Ceiling(operand);
					if (returnValue != null)
						result = (result + returnValue) % modValue;
					break;
				}
				case "FindIndex":
				{
					operand = sc.nextLong();
					returnValue = skipList.FindIndex(operand);
					if (returnValue != null)
						result = (result + returnValue) % modValue;
					break;
				}
				case "First":
				{
					returnValue = skipList.First();
					if (returnValue != null)
						result = (result + returnValue) % modValue;
					break;
				}
				case "Last":
				{
					returnValue = skipList.Last();
					if (returnValue != null)
						result = (result + returnValue) % modValue;
					break;
				}
				}
			}
		} catch (Exception e)
		{
			System.out.println("Exception thrown for : " + operation + " " + operand);
			e.printStackTrace();
		}

		// End Time
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;

		System.out.println(result + " " + elapsedTime);
		
		
		
		//TreeSet
		TreeSet<Long> tree = new TreeSet<Long>();
		sc = null;
		if (args.length > 0) {
			File file = new File(args[0]);
			try {
				sc = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			sc = new Scanner(System.in);
		}

		result = 0;
		returnValue = null;
		operand = 0;
		operation = "";
		startTime = System.currentTimeMillis();

		while (!((operation = sc.next()).equals("End")))
		{
			switch (operation)
			{
			case "Insert":
			case "Add":
				operand = sc.nextLong();
				tree.add(operand);
				result = (result + 1) % modValue;
				break;
			case "Find":
			case "Contains":
				operand = sc.nextLong();
				if (tree.contains(operand))
					result = (result + 1) % modValue;
				break;
			case "Delete":
			case "Remove":
				operand = sc.nextLong();
				if (tree.remove(operand))
					result = (result + 1) % modValue;
				break;
			case "Floor":
				operand = sc.nextLong();
				returnValue = tree.floor(operand);
				if (returnValue != null)
					result = (result + returnValue) % modValue;
				break;
			case "Ceiling":
				operand = sc.nextLong();
				returnValue = tree.ceiling(operand);
				if (returnValue != null)
					result = (result + returnValue) % modValue;
				break;
			case "FindIndex":
				operand = sc.nextLong();
				break;
			case "First":
				returnValue = tree.first();
				if (returnValue != null)
					result = (result + returnValue) % modValue;
				break;
			case "Last":
				returnValue = tree.last();
				if (returnValue != null)
					result = (result + returnValue) % modValue;
				break;
			}
		}

		endTime = System.currentTimeMillis();
		elapsedTime = endTime - startTime;

		System.out.println(result + " " + elapsedTime);

	}

}
