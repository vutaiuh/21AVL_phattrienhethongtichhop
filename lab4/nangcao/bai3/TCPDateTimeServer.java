import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TCPDateTimeServer {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd MM yyyy");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH mm ss");
    private static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern("dd MM yyyy HH mm ss");

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 6000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[TCP DateTimeServer] Dang lang nghe tai port " + port + " ...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[TCP DateTimeServer] Ket noi moi: " + clientSocket.getRemoteSocketAddress());
                new Thread(() -> handleClient(clientSocket)).start();
            }
        }
    }

    private static void handleClient(Socket socket) {
        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                String cmd = line.trim().toUpperCase();
                System.out.println("[TCP DateTimeServer] Nhan lenh: " + cmd);

                if (cmd.equals("QUIT")) {
                    out.println("BYE");
                    break;
                }

                out.println(process(cmd));
            }
        } catch (IOException e) {
            // Truong hop client dong dot ngot / mat mang trong luc server dang doc-ghi
            System.out.println("[TCP DateTimeServer] Mat ket noi voi " + socket.getRemoteSocketAddress()
                    + ": " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
            System.out.println("[TCP DateTimeServer] Da dong ket noi: " + socket.getRemoteSocketAddress());
        }
    }

    static String process(String cmd) {
        LocalDateTime now = LocalDateTime.now();
        switch (cmd) {
            case "DATE":
                return now.format(DATE_FMT);
            case "TIME":
                return now.format(TIME_FMT);
            case "DATETIME":
                return now.format(DATETIME_FMT);
            default:
                return "ERR UNKNOWN_COMMAND";
        }
    }
}
