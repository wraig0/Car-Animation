
/*
Server.java
Author:   1506036
Modified: 13/4/2018
*/
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	private final int PORT = 5000;
	private int numClients = 0;
	private Socket socket;
	private ServerSocket serverSocket;
	private DataOutputStream output;
	private DataInputStream input;
	private String request = null;
	private String response = null;
	private String data;
	private ServerThread serverThread;
	ArrayList<ServerThread> conns = new ArrayList<ServerThread>();

	public static void main(String[] args) throws IOException {
		new Server();
	}

	public Server() {
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server up..");
			while (true) {
				socket = serverSocket.accept();
				serverThread = new ServerThread(socket, this);
				conns.add(serverThread);
				serverThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
