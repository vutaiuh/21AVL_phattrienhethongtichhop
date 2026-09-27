import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;

/**
 * Bai 6: Message Log Server
 *
 * Protocol:
 *   HELLO <clientId>   – register client (must be first command)
 *   <message>          – log a message line
 *   QUIT               – end session cleanly
 *
 * Rules:
 *   - clientId: letters, digits, hyphens, underscores only
 *   - Log file: data/logs/<clientId>.txt
 *   - Each log line: [timestamp] [remoteAddr] <message>
 */
public class LogServer {

    private static final int    PORT        = 9100;
    private static final String LOG_DIR     = "data/logs";
    private static final String CLIENT_ID_PATTERN = "^[A-Za-z0-9_-]+$";

    public static void main(String[] args) throws IOException {
        // Ensure log directory exists
        Files.createDirectories(Paths.get(LOG_DIR));

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[SERVER] Message Log Server listening on port " + PORT);
            System.out.println("[SERVER] Log directory: " + Paths.get(LOG_DIR).toAbsolutePath());

            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("[+] New connection: " + client.getRemoteSocketAddress());
                Thread t = new Thread(() -> handleClient(client));
                t.setDaemon(true);
                t.start();
            }
        }
    }

    private static void handleClient(Socket socket) {
        String remoteAddr = socket.getRemoteSocketAddress().toString();

        try (
            socket;
            BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter    out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println("INFO Welcome! Send: HELLO <clientId>");

            String clientId = null;
            PrintWriter logWriter = null;

            try {
                String line;
                while ((line = in.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    // ---------- Not yet registered ----------
                    if (clientId == null) {
                        if (line.toUpperCase().startsWith("HELLO ")) {
                            String proposed = line.substring(6).trim();

                            if (!proposed.matches(CLIENT_ID_PATTERN)) {
                                out.println("ERR INVALID_CLIENT_ID - only letters, digits, hyphens and underscores allowed");
                                continue;
                            }

                            clientId  = proposed;
                            logWriter = openLogWriter(clientId);
                            out.println("OK Registered as [" + clientId + "]. Send messages or QUIT.");
                            System.out.println("[+] Client registered: " + clientId + " from " + remoteAddr);

                        } else {
                            out.println("ERR Send HELLO <clientId> first");
                        }
                        continue;
                    }

                    // ---------- Already registered ----------
                    if (line.equalsIgnoreCase("QUIT")) {
                        out.println("BYE Session closed for [" + clientId + "]");
                        System.out.println("[-] Client quit: " + clientId);
                        break;
                    }

                    // Log the message
                    String entry = buildLogEntry(remoteAddr, line);
                    logWriter.println(entry);
                    logWriter.flush();
                    out.println("OK Message logged");
                    System.out.println("[" + clientId + "] " + entry);
                }
            } finally {
                if (logWriter != null) logWriter.close();
            }

        } catch (IOException e) {
            System.out.println("[!] Connection error (" + remoteAddr + "): " + e.getMessage());
        } finally {
            System.out.println("[-] Disconnected: " + remoteAddr);
        }
    }

    /** Open (or append to) the log file for a given clientId. */
    private static PrintWriter openLogWriter(String clientId) throws IOException {
        Path logPath = Paths.get(LOG_DIR, clientId + ".txt");
        // Append mode: true
        return new PrintWriter(new FileWriter(logPath.toFile(), true));
    }

    /** Build one log line: [yyyy-MM-dd HH:mm:ss] [remoteAddr] message */
    private static String buildLogEntry(String remoteAddr, String message) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return "[" + timestamp + "] [" + remoteAddr + "] " + message;
    }
}