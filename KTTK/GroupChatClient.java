import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Group Chat Client
 */
public class GroupChatClient {

    public static void main(String[] args) throws IOException {
        System.out.println("=== Group Chat Client ===");

        Socket socket = new Socket("127.0.0.1", 9200);
        System.out.println("Connected to server");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner sc = new Scanner(System.in);

        // Thread nhận tin từ server
        new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("[Server] " + line);
                }
            } catch (IOException e) {}
        }).start();

        // Thread send to server
        System.out.println("\nCommands: HELLO <name>, LIST, JOIN <room>, /quit, /exit\n");
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!line.isEmpty()) {
                out.println(line);
                if (line.equalsIgnoreCase("/EXIT")) break;
            }
        }

        socket.close();
        System.out.println("Disconnected");
    }
}