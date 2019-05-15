import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
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

			System.out.println("uspostavljena konekcija");

			String request = in.readLine();
			System.out.println("stiglo od klijenta: " + request);

			String metod;
			String resurs;
			String verizijaProtokola;

			StringTokenizer token = new StringTokenizer(request," ");
			metod = token.nextToken();
			resurs = token.nextToken();
			verizijaProtokola = token.nextToken();

			List<String> listaHedera = new ArrayList<>();

			String linija = in.readLine();

			while (!linija.equals("")){
				listaHedera.add(linija);
				linija = in.readLine();
			}

			System.out.println("stigao je:\n request: " + metod + "\n verzija protokola: " + verizijaProtokola + "\n resurs: " + resurs);

			for (String s : listaHedera) {
				System.out.println(s);
			}

			switch (metod) {
				case "GET":
					System.out.println("stigao GET request");

					String nazivStranice ="";
					boolean filePronadjen = false;

					if (resurs.startsWith("/zdravo")) {
						String imeOsobe = resurs.replace("/zdravo/","");
						String projectsHtmlContent = "<html><head></head><body><p><b>Hello " + imeOsobe + "!<b></p></body></html>";
						out.print("HTTP/1.1 200 OK\r\n");
						out.print("Content-Length:" + projectsHtmlContent.getBytes().length + "\r\n");
						out.print("\r\n");
						out.print(projectsHtmlContent);
						out.flush();
					} else if(resurs.endsWith(".html")){
						nazivStranice = resurs.substring(1);
						File file = new File("static");
						File[] files = file.listFiles();

						for (File file1 : files) {
							if(file1.getName().equals(nazivStranice)){
								out.print("HTTP/1.1 200 OK\r\n");
								out.print("Content-Length:" + file1.length() + "\r\n");
								out.print("\r\n");
								out.print(new String(Files.readAllBytes(file1.toPath())));
								out.flush();
								filePronadjen = true;
								System.out.println("pronadjen fajl");
								break;
							}
						}
						if(filePronadjen == false){
							System.out.println("Fajl nije pronadjen");
							out.print("HTTP/1.1 404 Not Found\r\n");
							out.print("\r\n");
							out.flush();
						}
					} else if (resurs.startsWith("/api")) {
						out.print("HTTP/1.1 200 OK\r\n");
						String json = "{ \"temperatura\":24, \"vlaznost\":94, \"pritisak\":998 }";
						out.print("Content-Length:" + json.getBytes().length + "\r\n");
						out.print("Content-Type: application/json\r\n");
						out.print("\r\n");
						out.print(json);
						out.flush();
					} else {
						out.print("HTTP/1.1 404 Not Found\r\n");
						out.print("\r\n");
						out.flush();
					}

					break;
				case "POST":
					System.out.println("stigao POST request");
					out.print("HTTP/1.1 200 OK\r\n");
                    out.print("\r\n");

					break;
				case "PUT":
					System.out.println("stigao PUT request");
					out.print("HTTP/1.1 200 OK\r\n");
                    out.print("\r\n");

					break;
				case "DELETE":
					System.out.println("stigao DELETE request");
					out.print("HTTP/1.1 200 OK\r\n");
                    out.print("\r\n");

					break;
				default:
					System.out.println("stigao nepoznat metod");
					out.print("HTTP/1.1 405 Method Not Allowed\r\n");
                    out.print("\r\n");

					break;
			}

			System.out.println("zatvaranje konekcije");

            socket.close();

		} catch (IOException e) {
			System.out.println("Connection closed");
		}

	}

}
