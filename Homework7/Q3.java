import java.util.*;

public class Q3
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		TreeMap <Integer, Integer> a = new TreeMap <Integer, Integer>();
		TreeMap <Integer, Integer> b = new TreeMap <Integer, Integer>();
		int m = input.nextInt();
		while (m-- > 0)
		{
			int x = input.nextInt();
			if (a.containsKey(x))
			{
				int cnt = a.get(x);
				a.remove(x);
				a.put(x, cnt + 1);
			}
			else
				a.put(x, 1);
		}
		int n = input.nextInt();
		while (n-- > 0)
		{
			int x = input.nextInt();
			if (b.containsKey(x))
			{
				int cnt = b.get(x);
				b.remove(x);
				b.put(x, cnt + 1);
			}
			else
				b.put(x, 1);
		}
		for (int x: a.keySet())
		{
			int tmp = 0;
			if (b.containsKey(x))
				tmp = b.get(x);
			for (int i = 1; i <= a.get(x) - tmp; i++)
				System.out.print(x + " ");
		}
	}
}