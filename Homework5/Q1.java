import java.util.*;

class Vector2D
{
	public int x, y;
	public Vector2D(int _x, int _y)
	{
		x = _x;
		y = _y;
	}
	public boolean isEqual(Vector2D v)
	{
		return x == v.x && y == v.y;
	}
	public double length()
	{
		return Math.sqrt(x * x + y * y);
	}
}

abstract class Cell
{
	public int x, y;
	protected int initX, initY;
	protected boolean white;
	protected char ch;
	protected Vector<Vector2D> atkVec;
	public Cell(int _x, int _y, boolean _white)
	{
		initX = x = _x;
		initY = y = _y;
		white = _white;
		atkVec = new Vector<Vector2D>();
	}
	public boolean isWhite()
	{
		return white;
	}
}

class Rook extends Cell
{
	public Rook(int _x, int _y, boolean _white)
	{
		super(_x, _y, _white);
		ch = (white ? 'r' : 'R');
		for (int i = -7; i <= +7; i++)
		{
			atkVec.add(new Vector2D(i, 0));
			atkVec.add(new Vector2D(0, i));
		}
	}
}

class Knight extends Cell
{
	public Knight(int _x, int _y, boolean _white)
	{
		super(_x, _y, _white);
		ch = (white ? 'n' : 'N');
		for (int i = -2; i <= +2; i++)
			for (int j = -2; j <= +2; j++)
				if (i * i + j * j == 5)
					atkVec.add(new Vector2D(i, j));
	}
}

class Bishop extends Cell
{
	public Bishop(int _x, int _y, boolean _white)
	{
		super(_x, _y, _white);
		ch = (white ? 'b' : 'B');
		for (int i = 1; i <= 7; i++)
			for (int j = -1; j <= +1; j++)
				for (int k = -1; k <= +1; k++)
					if (j != 0 && k != 0)
						atkVec.add(new Vector2D(j * i, k * i));
	}
}

class King extends Cell
{
	public King(int _x, int _y, boolean _white)
	{
		super(_x, _y, _white);
		ch = (white ? 'k' : 'K');
		for (int i = -1; i <= +1; i++)
			for (int j = -1; j <= +1; j++)
				if (i != 0 || j != 0)
					atkVec.add(new Vector2D(i, j));
	}
}

class Queen extends Cell
{
	public Queen(int _x, int _y, boolean _white)
	{
		super(_x, _y, _white);
		ch = (white ? 'q' : 'Q');
		for (int i = 1; i <= 7; i++)
			for (int j = -1; j <= +1; j++)
				for (int k = -1; k <= +1; k++)
					if (j != 0 || k != 0)
						atkVec.add(new Vector2D(j * i, k * i));
	}
}

class Pawn extends Cell
{
	public Pawn(int _x, int _y, boolean _white)
	{
		super(_x, _y, _white);
		ch = (white ? 'p' : 'P');
		int dirY = 1;
		if (y > 3)
			dirY = -1;			
		for (int i = -1; i <= +1; i++)
			atkVec.add(new Vector2D(i, dirY));
		atkVec.add(new Vector2D(0, dirY * 2));
	}
}

class EmptyCell extends Cell
{
	public EmptyCell(int _x, int _y)
	{
		super(_x, _y, false);
		ch = '*';
	}
}

class Table
{
	public Cell[][] cells;
	public Table()
	{
		cells = new Cell[8][8];
	}
	public Cell cellAt(int x, int y)
	{
		x = Math.min(Math.max(x, 0), 7);
		y = Math.min(Math.max(y, 0), 7);
		return cells[x][y];
	}
	public int getState()
	{
		return 0;
	}
	public int move(int srcX, int srcY, int dstX, int dstY)
	{
		Cell src = cells[srcX][srcY];
		Cell dst = cells[dstX][dstY];
		Vector2D moveVec = new Vector2D(dstX - srcX, dstY - srcY);
		boolean found = false;
		for (Vector2D v: src.atkVec)
			if (v.isEqual(moveVec))
			{
				if (src instanceof Pawn && moveVec.length() == 2.0 && (src.x != src.initX || src.y != src.initY))
					continue;
				found = true;
				break;
			}
		if (!found)
		{
			System.out.println("Invalid move");
			return -1;
		}
		if (dst instanceof EmptyCell || (dst.isWhite() != src.isWhite() && !(dst instanceof King)))
		{
			changePlace(srcX, srcY, dstX, dstY);
			return -1;
		}
		//TODO
		return 0;
	}
	private void changePlace(int srcX, int srcY, int dstX, int dstY)
	{
		cells[dstX][dstY] = cells[srcX][srcY];
		cells[dstX][dstY].x = dstX;
		cells[dstX][dstY].y = dstY;
		cells[srcX][srcY] = new EmptyCell(srcX, srcY);
	}
}

class Chess
{
	private Table table;
	private boolean whitesTurn;
	public Chess()
	{
		table = new Table();
		whitesTurn = true;
	}
	// public void start()
	// {

	// }
	// public void finish()
	// {

	// }
	public boolean isWhitesTurn()
	{
		return whitesTurn;
	}
	public void move(int srcX, int srcY, int dstX, int dstY)
	{
		srcX -= 'A';
		dstX -= 'A';
		srcY -= '1';
		dstY -= '1';
		if (srcX < 0 || srcX > 7 || dstX < 0 || dstY > 7
			|| table.cellAt(srcX, srcY).isWhite() != whitesTurn || table.cellAt(srcX, srcY) instanceof EmptyCell)
		{
			System.out.println("Invalid move");
			return;
		}
		if (table.move(srcX, srcY, dstX, dstY) == 0)
			whitesTurn = !whitesTurn;
	}
	public int getState()
	{
		//TODO
		return 0;
	}
	public void printTable()
	{
		for (int i = 7; i >= 0; i--)
		{
			for (int j = 0; j < 8; j++)
				System.out.print(table.cellAt(j, i).ch);
			System.out.println("");
		}
	}
	public void setTable(String[] s)
	{
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
			{
				char ch = s[j].charAt(i);
				if (ch == 'r' || ch == 'R')
					table.cells[i][j] = new Rook(i, j, ch == 'r');
				else if (ch == 'q' || ch == 'Q')
					table.cells[i][j] = new Queen(i, j, ch == 'q');
				else if (ch == 'n' || ch == 'N')
					table.cells[i][j] = new Knight(i, j, ch == 'n');
				else if (ch == 'k' || ch == 'K')
					table.cells[i][j] = new King(i, j, ch == 'k');
				else if (ch == 'p' || ch == 'P')
					table.cells[i][j] = new Pawn(i, j, ch == 'p');
				else if (ch == 'b' || ch == 'B')
					table.cells[i][j] = new Bishop(i, j, ch == 'b');
				else
					table.cells[i][j] = new EmptyCell(i, j);
			}
	}
}

public class Q1
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		Chess chess = new Chess();
		String tableStr[] = new String[8];
		for (int i = 7; i >= 0; i--)
			tableStr[i] = input.next();
		chess.setTable(tableStr);
		int cnt = input.nextInt();
		for (int i = 0; i < cnt; i++)
		{
			String s = input.next();
			char srcX = s.charAt(0), srcY = s.charAt(1), dstX = s.charAt(3), dstY = s.charAt(4);
			chess.move(srcX, srcY, dstX, dstY);
			chess.printTable();
		}
	}
}