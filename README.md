# ChatApp
The main structure of the program was based on the below two references
https://medium.com/nerd-for-tech/create-a-chat-app-with-java-sockets-8449fdaa933
https://www.daniweb.com/programming/software-development/code/448361/multithreaded-simple-client-server-chat-console-program

ChatServer
A construtor is used for ChatServer class. It takes a port number as an input parameter. 
This port is used for listening for client connections

The method sendMsg is used to send messages to the client. It is run on a thread to allow full duplex communication.
A scanner is used to allow the server write messages to the client. 
A while loop is used to keep the communication open. 
out - is an instance of PrintWriter and is used to send data to the client.  
The thread is initiated by calling sender.start().

The method listenForMsg is used to receive messages from the client. It is also run on a thread for full dulpex.
in - is an instance of BufferedReader and is used to receive data from the client. 
A while loop is used to control the reading of input, it returns true while there is a message stored as a String - msg.
A nested if statement is used to check if the client has entered q to exit the chat. 
If true it prints to screen that the client has disconnected and calls the closeEverything method to shut down system resources.
A try block is used for error handling. 
If an IO exception is generated the closeEverything method is called to gracefully close all system resources.
The thread is started by calling receive.start()

The method closeEverything takes a Socket, BufferedREader, PrintWriter and ServerSocket as input parameters.
A try block is used for error handling.
An if statement is used to prevent a nullpointerexception being generated as it checks if all of the parameters are null before closing them.
It is used for the graceful closing of system resources.
All input and output streams will be closed as their wrappers are closed.

The method startServer is used for starting up the server side of the connection.
It instantiates the ServerSocket by providing the port number which is inputted as a command line argument.
ServerSocket then listens on the specified port for communications from a client.
If a client is also trying to connect on the same port it establishes a Socket connecton with the instance variable clientSocket.
Information on the connection is printed to the screen - the ip address and port number.
Both output and input streams are created by instantiating the PrintWriter (output) and BufferedReader (input).
The listenForMsg and sendMsg methods are then called to allow sending and receiving of text.
A try block is used for error handling. If an IO exception is generated the closeEverything method is called to close system resources gracefully.

main method
Reference for command line arguments 
https://www.codejava.net/java-se/networking/how-to-create-a-chat-console-application-in-java-using-socket
An if statement is used to check if the user has specified the port number by checking if args contains a value.
If no value is specified for the port number an error message is printed to the screen.
If the users enters a port number this is then assigned to the instanse variable port.
This is then used to listen for a client connection. 

ChatClient
The class chat client has a construtor which takes a hostname and port for establishing connections with the server.

The method sendMsg is used to send messages to the server. 
It has the same basic implementaton as the server side but contains and if statement to check is the client has entered q
The closeEverything method is then called to close system resources. This breaks the while loop and ends the client chat. 

listenForMsg is used to receive messages from the server. It has similar implementation as the server side.

startClient is used to instantiate the client. 
A try block is used to handle IO exceptions and will call closeEverything if one is generated.
It creates a socket connection by providing the hostname and port taken as a command line argument when running the program.
Both an output stream and input stream are created and assigned to a BufferedReader(input) and PrintWriter(output).
The listenMsg and sendMsg are called within the method.

closeEverything takes a Socket, BufferedReader and PrintWriter as an input parameters and uses an if statement to check 
if they are null to prevent a null pointer exception before closing the input parameters.

main method
if statement to check if the user has enter a command line argument for the hostname and port
if they have not been entered the input returns and the user is displayed an error message and allowed
re-input the correct format to open a connection with the server. 
An instance of ChatClient is created an assigned to client, the method startClient is then called to start the client side communication.

Running porgram
The ChatServer program needs to be run first to listen for connections from a client.
The port number is specified as a command line argument when running the ChatServer.
To run the program on the command line enter in the following format 
java ChatServer XXXX
Were XXXX is the port number
The ChatClient program is run from the command line by entering the following format
java ChatClient XXXX YYYY
Were XXXX is the localhost address and YYYY is the port number
To exit the ChatClient program input q and press enter to end the chat.
