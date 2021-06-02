import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class GuessClientWorker implements Runnable {
    Socket socket;
    GuessServer serverRef;
    String clientName;

    int minSecret = 10;
    int maxSecret = 20;
    int attemptCount = 3;

    public GuessClientWorker(Socket socket, GuessServer serverRef) {

        this.socket = socket;
        this.serverRef = serverRef;
        new Thread(this).start();
    }


    Scanner in;
    PrintStream out;


    public void run() {
        try {

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();


            in = new Scanner(is);
            out = new PrintStream(os);


            int port = socket.getPort();

            out.println("You are now connected to the Guess game");
            out.print("Your name, please: ");
            String input = in.nextLine();


            int secret = (new java.util.Random()).nextInt(maxSecret - minSecret) + minSecret;


            if (input == null || input.isEmpty()) {
                this.clientName = "Mysterious" + port + "Anonymous";
            } else {
                this.clientName = input;

            }

            out.println("So, " + clientName + ", you now have " + attemptCount + " attempts to guess, which number I, the mighty server, randomly chosen");
            out.println("Guess numbers between " + minSecret + " and " + maxSecret);

            int guess = 0;
            for (int i = 1; i <= attemptCount; i++) {
                out.print("Current attempt: " + i + "; Your guess: ");
                try {
                    guess = Integer.parseInt(in.nextLine());
                } catch (NumberFormatException e)
                {
                    out.println("Your input was weird. Type only integers, please");
                }
                if (guess == secret) {
                    out.println("Congrats! Your guess is right");
                    break;
                } else {
                    out.println("Wrong!");
                }
            }
            out.println("The secret was " + secret
                    +
                    ((guess == secret)
                            ? ", and you guessed it right. Well done!"
                            : ", but you failed"));


            out.println("Goodbye");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}