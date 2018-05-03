
/* 
ServerThread.java
Author:   1506036
Modified: 03/05/2018
*/
import java.io.*;
import java.net.*;

public class ServerThread extends Thread {

	private DataOutputStream output;
	private DataInputStream input;
	Socket socket;
	String textInput;
	Server server;

	public ServerThread(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
	}

	public void run() {

		try {
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());

			while (true) {
				while (input.available() == 0) {
					try {
						Thread.sleep(10L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				textInput = input.readUTF();
				sendStringToAll(textInput);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendString(String message) {
		try {
			output.writeUTF(message);
			output.writeUTF("pong\n");
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendStringToAll(String message) {
		for (int i = 0; i < server.conns.size(); i++) {
			ServerThread thread = server.conns.get(i);
			thread.sendString(message);
		}
	}

}