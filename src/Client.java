import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

class Client {
    private static int POTR = 3333;
    private static String IP = "localhost";

    public static void main(String[] args) throws IOException {

        Socket server = null;

        try {
            server = new Socket(IP, POTR);
        } catch (UnknownHostException e) {
            System.err.println(e);
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(server.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        String msg;

        while ((msg = stdIn.readLine()) != null) {
            out.println(msg);
            System.out.println(in.readLine());
        }
    }
}