package client_server_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	
	private Socket SOCK = null;
	private static final int PORT = Server.PORT;
	private static final String IP = "localhost";
	
	private String serverMessage = null;
	private String clientMessage = null;
	
	private String format = "%-10s%s";
	private Scanner keyboard = new Scanner(System.in);
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Client client = new Client();
		client.run();
	}
	
	public void run() throws UnknownHostException, IOException {
		// create a socket on the specificated ip and port
		SOCK = new Socket(Client.IP, Client.PORT);
		
		do {
			// send a message to the server
			clientMessage = keyboard.nextLine();
			PrintStream PS = new PrintStream(SOCK.getOutputStream());
			PS.println(clientMessage);
			
			// read response from the server
			InputStreamReader IR = new InputStreamReader(SOCK.getInputStream());
			BufferedReader BR = new BufferedReader(IR);
			serverMessage = BR.readLine();
			System.out.println(String.format(format, "[Server]:", serverMessage));
		} while (!clientMessage.equals("exit"));
		
		// close client socket
		SOCK.close();
		System.out.println(String.format(format, "[Client]:", "Turned off!"));
		
	}

}
