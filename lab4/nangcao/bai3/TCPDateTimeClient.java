import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPDateTimeClient {

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 6000;

        try (
                Socket socket = new Socket(host, port);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
                BufferedReader stdIn = new BufferedReader(
                        new InputStreamReader(System.in, StandardCharsets.UTF_8))
        ) {
            System.out.println("Da ket noi toi TCP DateTimeServer " + host + ":" + port);
            System.out.println("Lenh: DATE | TIME | DATETIME | QUIT");

            String userInput;
            while (true) {
                System.out.print("> ");
                userInput = stdIn.readLine();
                if (userInput == null) {
                    break;
                }

                out.println(userInput);

                String response;
                try {
                    response = in.readLine();
                } catch (IOException e) {
                    // Server dong ket noi dot ngot (vd bi Ctrl+C) -> loi khi doc socket
                    System.out.println("[MAT KET NOI] Server co ve da bi dung dot ngot: " + e.getMessage());
                    break;
                }

                if (response == null) {
                    // Server dong ket noi mot cach "sach" (EOF) truoc khi tra loi
                    System.out.println("[MAT KET NOI] Server da dong ket noi (khong con du lieu / EOF).");
                    break;
                }

                System.out.println("Server: " + response);

                if (userInput.trim().equalsIgnoreCase("QUIT")) {
                    break;
                }
            }
        } catch (ConnectException e) {
            System.err.println("[LOI] Khong the ket noi den server (server chua chay hoac sai port): "
                    + e.getMessage());
        } catch (IOException e) {
            System.err.println("[LOI] Loi IO khi giao tiep voi server: " + e.getMessage());
        }
    }
}
