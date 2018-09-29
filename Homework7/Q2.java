import java.util.*;
import java.lang.*;

class Vector3D implements Comparable
{
	int x, y, z;
	public Vector3D(int _x, int _y, int _z)
	{
		x = _x;
		y = _y;
		z = _z;
	}
	private int len2()
	{
		return x * x + y * y + z * z;
	}
	public boolean equals(Object o)
	{
		Vector3D v = (Vector3D)o;
		return x == v.x && y == v.y && z == v.z;
	}
	public int compareTo(Object o)
	{
		Vector3D v = (Vector3D)o;
		if (len2() != v.len2())
			return ((Integer)len2()).compareTo(v.len2());
		if (x != v.x)
			return ((Integer)x).compareTo(v.x);
		if (y != v.y)
			return ((Integer)y).compareTo(v.y);
		return ((Integer)z).compareTo(v.z);
	}
}

public class Q2
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		TreeSet<Vector3D> s = new TreeSet<Vector3D>();
		int n = input.nextInt();
		for (int i = 0; i < n; i++)
			s.add(new Vector3D(input.nextInt(), input.nextInt(), input.nextInt()));
		for (Vector3D v: s)
			System.out.println(v.x + " " + v.y + " " + v.z);
	}
}