
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {
	//create an instance variable for the port that server will listen for connections
	public int port;
	private BufferedReader in;
	private PrintWriter out;
	//create a scanner for taking user input
	private Scanner scan = new Scanner(System.in);
	//create a serversocket for listening for connections
	private ServerSocket serverSocket;
	//create a socket for establishing connections
	private Socket clientSocket;
	
	//Constructor for the ChatServer which takes a port number as an input parameter.
	//This port is used for listening for client connections
	public ChatServer(int port) {
        this.port = port;
    }
	
	//method to send messages to the client
	public void sendMsg() {
		Thread sender = new Thread(new Runnable() {
			String msg;

			@Override
			public void run() {
				//while loop to keep 
				while (true) {
					System.out.println("Type message to begin");
					msg = scan.nextLine();
					out.println(msg);
					// flush any data which might be buffering
					out.flush();
					
				}

			}

		});
		sender.start();

	}

	public void listenForMsg() {
		Thread receive = new Thread(new Runnable() {
			String msg;

			@Override
			public void run() {
				try {
					msg = in.readLine();

					while (msg != null) {
						if (msg.equals("q")) {
							System.out.println("Client has disconnected");
							closeEverything(clientSocket, in, out, serverSocket);
						}
						System.out.println("Client : " + msg);
						msg = in.readLine();
						
					} 
					System.out.println("Client disconnected");

				} catch (IOException e) {
					closeEverything(clientSocket, in, out, serverSocket);
				}
			}

		});
		receive.start();
	}

	public void closeEverything(Socket socket, BufferedReader br, PrintWriter pw, ServerSocket serverSocket) {
		// close all resources
		// check if they are null to prevent a null pointer exception being generated
		// all streams will also be closed as the wrappers are closed below
		try {
			if (br != null) {
				br.close();
			}
			if (pw != null) {
				pw.close();
			}
			if (socket != null) {
				socket.close();
			}
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// create a method to initiate the server
	public void startServer() {
		// try block for error handling
		try {
			serverSocket = new ServerSocket(port);
			//notify the server that the serverSocket is listening on the specified port
			System.out.println("Server online waiting for host");
			System.out.println("Chat Server is listening on port " + port);
			clientSocket = serverSocket.accept();
			//notify the server that a connection has been established
			System.out.println("Connection established. Host online " + "Host Address " + clientSocket.getInetAddress()+ " on port " + clientSocket.getPort());
			//create the input and output streams for sending and receiving data from the client
			out = new PrintWriter(clientSocket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			// call the two method for sending and receiving client messages.
			listenForMsg();
			sendMsg();
		} catch (IOException e) {
			// call the close method to shut down resources if an exception is generated
			closeEverything(clientSocket, in, out, serverSocket);
		}
	}

	public static void main(String[] args) {
		
		//https://www.codejava.net/java-se/networking/how-to-create-a-chat-console-application-in-java-using-socket
		 if (args.length < 1) {
	            System.out.println("Syntax: java ChatServer <port-number>");
	            System.exit(0);
	        }
	        int port = Integer.parseInt(args[0]);
		// create an instance of the server and initiate.
		ChatServer server = new ChatServer(port);
		server.startServer();
	

	}
}
