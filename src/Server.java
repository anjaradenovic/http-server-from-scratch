import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class Server {

	public Server() throws Exception {

		// server soket za slusanje
		ServerSocket serverSocket = new ServerSocket(12518);

		while (true) {
			// prihvata server novu konekciju, novi soket
			System.out.println("server ceka konekciju");
			Socket socket = serverSocket.accept();
			// formira novi tred
			ServerThread serverThread = new ServerThread(this, socket);
			 Thread nasThread = new Thread(serverThread);
			 nasThread.start();
			 
		}	
	}


}
