import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Bai 6: Message Log Client
 *
 * Usage:
 *   1. Connect -> server greets
 *   2. Send:  HELLO <clientId>
 *   3. Send:  <any message text>   (repeated)
 *   4. Send:  QUIT
 */
public class LogClient {

    private static final String HOST = "127.0.0.1";
    private static final int    PORT = 9100;

    public static void main(String[] args) throws IOException {
        System.out.println("=== Message Log Client ===");
        System.out.println("Commands:");
        System.out.println("  HELLO <clientId>  – register (first command)");
        System.out.println("  <message>         – send a message to be logged");
        System.out.println("  QUIT              – end session");
        System.out.println();

        try (
            Socket         socket = new Socket(HOST, PORT);
            BufferedReader in     = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter    out    = new PrintWriter(socket.getOutputStream(), true);
            Scanner        sc     = new Scanner(System.in)
        ) {
            System.out.println("Connected to " + HOST + ":" + PORT);

            // Background thread: print server responses
            Thread reader = new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println("[Server] " + line);
                    }
                } catch (IOException e) {
                    System.out.println("[!] Server disconnected.");
                }
            });
            reader.setDaemon(true);
            reader.start();

            // Main thread: read user input
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                out.println(line);

                if (line.equalsIgnoreCase("QUIT")) {
                    // Give reader thread a moment to print server's BYE
                    try { Thread.sleep(300); } catch (InterruptedException ignored) {}
                    break;
                }
            }

            System.out.println("Disconnected.");
        }
    }
}