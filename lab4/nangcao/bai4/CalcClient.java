import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CalcClient {

    private static final String HOST = "127.0.0.1";
    private static final int    PORT = 9000;

    public static void main(String[] args) throws IOException {
        System.out.println("=== Client control ===");
        System.out.println("Syntax : CALC <toantu> <so1> <so2>");
        System.out.println("Ex   : CALC + 100 200");
        System.out.println("Enter 'quit' to cancel.\n");

        try (
            Socket socket       = new Socket(HOST, PORT);
            PrintWriter    out  = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in   = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner        sc   = new Scanner(System.in)
        ) {
            System.out.println("Connect to " + HOST + ":" + PORT + "\n");

            while (true) {
                System.out.print(">> ");
                if (!sc.hasNextLine()) break;

                String line = sc.nextLine().trim();
                if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                    System.out.println("Out.");
                    break;
                }
                if (line.isEmpty()) continue;

                out.println(line);
                String response = in.readLine();
                System.out.println("<< " + response + "\n");
            }
        }
    }
}