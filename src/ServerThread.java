import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;


public class ServerThread implements Runnable {

	private Server server;
	private Socket socket;

	public ServerThread(Server server, Socket socket) {
		super();
		this.server = server;
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			System.out.println("uspostavljena kokenkcija");

			String request = in.readLine();
			System.out.println("SERVER: stiglo od klijenta: " + request);
			if(request.startsWith("GET")){
				out.println("stigao GET request");
			}else if(request.startsWith("POST")){
				out.println("stigao POST request");
			}else if(request.startsWith("PUT")){
				out.println("stigao PUT request");
			}else if(request.startsWith("DELETE")){
				out.println("stigao DELETE request");
			}else{
				out.println("stigao nepoznat metod");
			}

			String metod;
			String verizijaProtokola;
			String resurs;
			StringTokenizer token = new StringTokenizer(request," ");
			metod = token.nextToken();
			verizijaProtokola = token.nextToken();
			resurs = token.nextToken();

			List<String> listaHedera = new ArrayList<>();

			String linija = in.readLine();

			while (!linija.equals("")){
				listaHedera.add(linija);
				linija = in.readLine();
			}

			System.out.println("stigao je request: " + metod + " sa verzijom protokola: " + verizijaProtokola + " i resursom: " + resurs);

			for (String s : listaHedera) {
				System.out.println(s);
			}
			System.out.println("zatvaramo konekciju");
			socket.close();

		} catch (IOException e) {
			System.out.println("Connection closed");
		}

	}

}
