import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


class Server {
    private static int PORT = 3333;

    public static void main(String[] args) throws IOException {

        class ClientConn implements Runnable {
            private Socket client;

            ClientConn(Socket client) {
                this.client = client;
            }

            public void run() {
                BufferedReader in = null;
                PrintWriter out = null;

                try {
                    in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    out = new PrintWriter(client.getOutputStream(), true);
                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

                    String msg;

                    while ((msg = stdIn.readLine()) != null) {
                        out.println(msg);
                        System.out.println(in.readLine());
                    }
                } catch (IOException e) {
                    System.err.println(e);
                    return;
                }

                String msg;
                try {
                    while ((msg = in.readLine()) != null) {
                        System.out.println("Клиент: " + msg);

                    }
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }

        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");

        } catch (IOException e) {
            System.err.println("Нет ответа от порта: " + PORT);
            System.err.println(e);
            System.exit(1);
        }

        Socket client = null;
        while (true) {
            try {
                client = server.accept();
                System.out.println("Клиент подключился!");
            } catch (IOException e) {
                System.err.println("Ошибка");
                System.err.println(e);
                System.exit(1);
            }
            Thread t = new Thread(new ClientConn(client));
            t.start();
        }
    }
}