import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    public Server() throws Exception {

        ServerSocket serverSocket = new ServerSocket(12518);

        while (true) {
            System.out.println("server ceka konekciju");
            Socket socket = serverSocket.accept();
            ServerThread serverThread = new ServerThread(socket);
            Thread nasThread = new Thread(serverThread);
            nasThread.start();

        }
    }

}
