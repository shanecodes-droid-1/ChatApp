
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	//create an instance variable for the port that will form a connection with the server
	public int port;
	//create a socket for establishing connections
	private Socket clientSocket;
	private BufferedReader in;
	private PrintWriter out;
	//create a scanner for taking user input
	private Scanner scan = new Scanner(System.in);
	private String hostname;
	
	public ChatClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

	public void sendMsg() {

		Thread sender = new Thread(new Runnable() {
			String msg;

			@Override
			public void run() {
				while (true) {
					System.out.println("Begin typing your message. To exit the chat enter q");
					msg = scan.nextLine();
					out.println(msg);
					out.flush();
					if (msg.equals("q")) {
						closeEverything(clientSocket, in, out);
						break;
					}

				}

			}

		});
		sender.start();

	}

	public void listenForMsg() {
		Thread receiver = new Thread(new Runnable() {
			String msg;
			//runs on a thread as listening is a blocking an operation
			//allows client to listen and send at the same time
			@Override
			public void run() {
				try {
					msg = in.readLine();
					//while loop used to read message from server
					//while message has content, it is read.
					while (msg != null) {
						System.out.println("Server : " + msg);
						msg = in.readLine();
					}
					System.out.println("Server disconnected");

					
				} catch (IOException e) {
					closeEverything(clientSocket, in, out);
				}
			}

		});
		receiver.start();

	}

	public void startClient() {
		try {

			clientSocket = new Socket(hostname, port);
			System.out.println("Client connected to" + clientSocket.getInetAddress() + " on port " + clientSocket.getPort());
			out = new PrintWriter(clientSocket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			listenForMsg();
			sendMsg();

		} catch (IOException e) {
			closeEverything(clientSocket, in, out);

		}
	}

	public void closeEverything(Socket socket, BufferedReader br, PrintWriter pw) {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		//https://www.codejava.net/java-se/networking/how-to-create-a-chat-console-application-in-java-using-socket
		//if statement to check if the user has enter a command line argument for the hostname and port number
		// if they have not been entered the input returns and the user is displayed an error message and allowed
		// re-input the correct format to open a connection with the server. 
		  if (args.length < 2) 
			  {System.out.println("Syntax: java ChatClient <hostname> <port-number>");
			  return;}
			 // The two command line arguments are accessed using an array index to assign the input to the hostname and port instance variables
	        String hostname = args[0];
	        int port = Integer.parseInt(args[1]);
	        // an instance of the ChatClient class is created and takes the hostname and port as input parameters for its constructor
		ChatClient client = new ChatClient(hostname, port);
		//call the method startClient to allow for sending and receiving of messages and establishing a connection with the server.
		client.startClient();
		
		}

	}


