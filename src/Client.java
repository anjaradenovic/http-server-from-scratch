import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {

    public Client() throws Exception {

        System.out.println("klijent pokusava da uspostavi vezu sa serverom");
        Socket socekt = new Socket("localhost", 12518);
        System.out.println("uspostavljena veza sa serverom");

        PrintWriter out = new PrintWriter(new OutputStreamWriter(socekt.getOutputStream()), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socekt.getInputStream()));

        // unos sa konzole
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



        // cekamo odgovor od servera
        String porukaSrevera = in.readLine();

        System.out.println("Poruka od servera: " + porukaSrevera);
        System.out.println();

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
}
