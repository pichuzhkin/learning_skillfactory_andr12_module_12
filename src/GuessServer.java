import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GuessServer {


    private ArrayList<GuessClientWorker> connectedClients = new ArrayList<>();

    private ServerSocket serverSocket;

    GuessServer() throws IOException {
        serverSocket = new ServerSocket(8888);

    }

    public void run() {
        System.out.println("Now waiting for incoming clients...");
        while (true) {


            try {
                // принимаем подключение
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                // подключаем
                connectedClients.add(new GuessClientWorker(socket, this));


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws IOException {
        new GuessServer().run();

    }
}
