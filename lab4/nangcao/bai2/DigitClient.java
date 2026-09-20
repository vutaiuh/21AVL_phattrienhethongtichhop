
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class DigitClient {

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5000;

        try (
                Socket socket = new Socket(host, port);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
                BufferedReader stdIn = new BufferedReader(
                        new InputStreamReader(System.in, StandardCharsets.UTF_8))
        ) {
            System.out.println("Da ket noi toi DigitServer tai " + host + ":" + port);
            System.out.println("Nhap mot chu so (0-9), hoac QUIT de thoat.");

            String userInput;
            while (true) {
                System.out.print("> ");
                userInput = stdIn.readLine();
                if (userInput == null) {
                    break;
                }

                out.println(userInput);

                String response = in.readLine();
                if (response == null) {
                    System.out.println("Server da dong ket noi bat ngo.");
                    break;
                }
                System.out.println("Server tra loi: " + response);

                if (userInput.equals("QUIT")) {
                    break;
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("[LOI] Khong tim thay host: " + host);
        } catch (IOException e) {
            System.err.println("[LOI] Khong ket noi duoc toi server: " + e.getMessage());
        }
    }
}
