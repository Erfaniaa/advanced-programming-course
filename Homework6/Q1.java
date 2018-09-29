import java.util.*;
import java.io.*;
import java.net.*;

class Server extends Thread
{
	private int port;
	private DataInputStream input;
	private FileOutputStream output;
	private Socket socket;
	private ServerSocket serverSocket;
	private String savePath;
	private boolean isListening;
	public Server(int p, String path)
	{
		try
		{
			port = p;
			savePath = path;
			serverSocket = new ServerSocket(p);
			output = new FileOutputStream(path);
			serverSocket.setSoTimeout(10000);
		}
		catch (Exception e) {}
	}
	public Server() {}
	public void run()
	{
		while (isListening)
		{
			try
			{
				Socket socket = serverSocket.accept();
				input = new DataInputStream(socket.getInputStream());
				int b;
				while ((b = input.read()) != -1)
					output.write(b);
				stopListening();
			}
			catch (Exception e) {}
		}
	}
	public void startListening()
	{
		isListening = true;
		start();
	}
	public void stopListening()
	{
		interrupt();
		try
		{
			if (output != null)
				output.close();
			if (socket != null)
				socket.close();
			if (serverSocket != null)
				serverSocket.close();
		}
		catch (Exception e) {}
		isListening = false;
	}
	public boolean isListening()
	{
		return isListening;
	}
}


class Client
{
	private FileInputStream input;
	private DataOutputStream output;
	private Socket socket;
	private String filePath;
	public Client(String ip, int port, String path)
	{
		try
		{
			filePath = path;
			socket = new Socket(ip, port);
			input = new FileInputStream(path);
		}
		catch (Exception e) {}
	}
	public Client() {}
	public void sendFile()
	{
		try
		{
			output = new DataOutputStream(socket.getOutputStream());
			int b;
			while ((b = input.read()) != -1)
				output.write(b);
			socket.close();
			input.close();
		}
		catch (Exception e) {}
	}
}

public class Q1
{
	public static void main(String args[])
	{
		Scanner input = new Scanner(System.in);
		Server server = new Server();
		Client client = new Client();
		while (true)
		{
			String line = input.nextLine();
			String words[] = line.split("[ ]{1,}");
			if (words[0].equals("exit"))
				break;
			else if (words[0].equals("concat"))
			{
				String[] src = new String[words.length - 2];
				String dst = words[words.length - 1];
				for (int i = 1; i <= words.length - 2; i++)
					src[i - 1] = words[i];
				concat(src, dst);
			}
			else if (words[0].equals("tree"))
			{
				printTree(words.length > 1 ? words[1] : ".", 0);
			}
			else if (words[0].equals("listen"))
			{
				server.stopListening();
				server = new Server(Integer.parseInt(words[1]), words[2]);
				server.startListening();
			}
			else if (words[0].equals("send"))
			{
				client = new Client(words[1], Integer.parseInt(words[2]), words[3]);
				client.sendFile();
			}
			else if (words[0].equals("disconnect"))
			{
				if (server.isListening())
					server.stopListening();
				else
					System.out.println("Error");
			}
		}
	}
	public static void concat(String src[], String dst)
	{
		try
		{
			PrintWriter out = new PrintWriter(dst);
			for (String path: src)
			{
				Scanner in = new Scanner(new File(path));
				while (in.hasNextLine())
					out.println(in.nextLine());
			}
			out.close();
		}
		catch (Exception e) {}
	}
	public static void printTree(String dir, int depth)
	{
		File folder = new File(dir);
		if (!folder.isDirectory())
		{
			System.out.println("Invalid path");
			return;
		}
		for (File f: folder.listFiles())
		{
			for (int i = 1; i <= depth; i++)
				System.out.print("│   ");
			System.out.println("├── " + f.getName());
			if (f.isDirectory())
				printTree(f.getPath(), depth + 1);
		}
	}
}