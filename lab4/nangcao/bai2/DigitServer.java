
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class DigitServer {

    private static final String[] DIGIT_WORDS = {
            "khong", "mot", "hai", "ba", "bon", "nam", "sau", "bay", "tam", "chin"
    };

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[DigitServer] Dang lang nghe tai port " + port + " ...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[DigitServer] Ket noi moi tu: " + clientSocket.getRemoteSocketAddress());
                // Moi client mot thread rieng de phuc vu nhieu client cung luc
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
                System.out.println("[DigitServer] Nhan tu " + socket.getRemoteSocketAddress()
                        + ": \"" + line + "\"");

                if (line.equals("QUIT")) {
                    out.println("BYE");
                    break;
                }

                String response = process(line);
                out.println(response);
                System.out.println("[DigitServer] Tra ve: \"" + response + "\"");
            }
        } catch (IOException e) {
            System.out.println("[DigitServer] Client " + socket.getRemoteSocketAddress()
                    + " ngat ket noi dot ngot: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
            System.out.println("[DigitServer] Da dong ket noi voi " + socket.getRemoteSocketAddress());
        }
    }
    private static String process(String data) {
        if (data.length() != 1 || !Character.isDigit(data.charAt(0))) {
            return "ERR INVALID_DIGIT";
        }
        int digit = data.charAt(0) - '0';
        return DIGIT_WORDS[digit];
    }
}
