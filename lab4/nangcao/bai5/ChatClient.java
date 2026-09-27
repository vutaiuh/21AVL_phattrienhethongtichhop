import java.io.*;
import java.net.*;
import java.util.Scanner;
public class ChatClient {

    private static final String HOST = "127.0.0.1";
    private static final int    PORT = 7000;

    public static void main(String[] args) throws IOException {
        System.out.println("=== TCP Chat Client ===");
        System.out.println("Connect to " + HOST + ":" + PORT + " ...");

        try (
            Socket         socket = new Socket(HOST, PORT);
            BufferedReader in     = new BufferedReader(
                                        new InputStreamReader(socket.getInputStream()));
            PrintWriter    out    = new PrintWriter(socket.getOutputStream(), true);
            Scanner        sc     = new Scanner(System.in)
        ) {
            System.out.println("Connected!\n");

            // Thread read message
            Thread reader = new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println("\n[Server] " + line);
                        System.out.print(">> ");   // dawing again prompt
                    }
                } catch (IOException e) {
                    System.out.println("\n[!] Mất kết nối với server.");
                }
            });
            reader.setDaemon(true);
            reader.start();

            // Thread  main call user
            while (true) {
                System.out.print(">> ");
                if (!sc.hasNextLine()) break;

                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                out.println(line);

                if (line.equalsIgnoreCase("QUIT")) {
                    System.out.println("Out connection.");
                    break;
                }
            }
        }
    }
}