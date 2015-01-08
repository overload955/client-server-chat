package client_server_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	
	private ServerSocket SRVSOCK = null;
	private Socket SOCK = null;
	public static final int PORT = 444;
	
	private String serverMessage = null;
	private String clientMessage = null;
	
	private String format = "%-10s%s";
	private Scanner keyboard = new Scanner(System.in);
	
	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.run();
	}
	
	public void run() throws IOException {
		// open server socket
		this.SRVSOCK = new ServerSocket(Server.PORT);
		
		do {
			System.out.println(String.format(format, "[Server]:", "No connections! Waiting..."));
			this.SOCK = this.SRVSOCK.accept();
			
			System.out.println(String.format(format, "[Server]:", "Connection detected! Waiting for message..."));
			do {
				// read message sent by Client
				InputStreamReader IR = new InputStreamReader(this.SOCK.getInputStream());
				BufferedReader BR = new BufferedReader(IR);
				
				this.clientMessage = BR.readLine();
				System.out.println(String.format(format, "[Client]:", this.clientMessage));
				
				// send a message to the Client
				if (this.clientMessage != null) {
					PrintStream PS = new PrintStream(this.SOCK.getOutputStream());
					
					if (this.clientMessage.equals("exit")) {
						this.serverMessage = "Ok, go with the wind, dear friend!";
					}
					else {
						this.serverMessage = keyboard.nextLine();
					}
					
					PS.println(this.serverMessage);
				}
			} while (!clientMessage.equals("exit"));
			
		} while (!serverMessage.equals("exit"));
		
		// close server socket
		this.SRVSOCK.close();
		System.out.println(String.format(format, "[Server]:", "Turned off!"));
	}

}
