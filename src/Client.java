import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Client {

    public Client() throws Exception {

        System.out.println("klijent pokusava da uspostavi vezu sa serverom");
        Socket socekt = new Socket("localhost", 12518);
        System.out.println("uspostavljena veza sa serverom");

        PrintWriter out = new PrintWriter(new OutputStreamWriter(socekt.getOutputStream()), true);
        InputStream socketInputStream = socekt.getInputStream();
        BufferedInputStream in = new BufferedInputStream(socketInputStream);

        Scanner tastatura = new Scanner(System.in);
        String request = tastatura.nextLine();
        out.println(request);

        System.out.println("CLIENT: " + request);

        while(true){
           String sledecaLinija = tastatura.nextLine();
           out.println(sledecaLinija);

           if(sledecaLinija.equals("")){
               break;
           }
        }

        List<String> listaHedera = new ArrayList<>();

        String porukaSrevera = getLine(in);
        System.out.println("Poruka od servera: " + porukaSrevera);

        porukaSrevera = getLine(in);

        while (!porukaSrevera.equals("")){
            listaHedera.add(porukaSrevera);
            porukaSrevera = getLine(in);
        }
        System.out.println(listaHedera);

        int contentLength = 0;

        for (String s : listaHedera) {
           if(s.startsWith("Content-Length:")) {
               contentLength = Integer.parseInt(s.split(":")[1].trim());
               break;
           }
        }

        if(contentLength!=0){
            byte[] buffer = new byte[contentLength];
            int charactersRead = in.read(buffer);
            System.out.println("Procitano karaktera: " + charactersRead);
            String s = new String(buffer);
            System.out.println("Content je:\n" + s);
        } else {
            System.out.println("Nije bilo nikakvog content-a");
        }

        socekt.close();
    }

    public static void main(String[] args) {
        try {
            new Client();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private String getLine(InputStream inputStream) {

        byte[] bytes = new byte[4096];
        int procitanoBajtova = 0;

        try {
            while (true) {

                int procitaniBajtKaoInt = inputStream.read();
                if (procitaniBajtKaoInt == -1) {
                    return "";
                }

                bytes[procitanoBajtova] = (byte) procitaniBajtKaoInt;
                procitanoBajtova++;

                if (procitanoBajtova > 1) {
                    if (bytes[procitanoBajtova - 1] == '\n' && bytes[procitanoBajtova - 2] == '\r') {
                        byte[] noviNizBajtova = new byte[procitanoBajtova - 2];

                        for (int i = 0; i < procitanoBajtova - 2; i++) {
                            noviNizBajtova[i] = bytes[i];
                        }
                        String s = new String(noviNizBajtova);
                        System.out.println("Procitana linija: " + s);
                        return s;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
