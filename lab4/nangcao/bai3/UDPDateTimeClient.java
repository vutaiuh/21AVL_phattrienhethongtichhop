import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class UDPDateTimeClient {

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 6001;

        try (
                DatagramSocket socket = new DatagramSocket();
                BufferedReader stdIn = new BufferedReader(
                        new InputStreamReader(System.in, StandardCharsets.UTF_8))
        ) {
            socket.setSoTimeout(3000); // timeout 3s de phat hien server khong phan hoi
            InetAddress address = InetAddress.getByName(host);

            System.out.println("UDP client se goi toi " + host + ":" + port);
            System.out.println("Lenh: DATE | TIME | DATETIME | EXIT (thoat client, cuc bo)");

            String userInput;
            while (true) {
                System.out.print("> ");
                userInput = stdIn.readLine();
                if (userInput == null || userInput.equalsIgnoreCase("EXIT")) {
                    break;
                }

                byte[] sendData = userInput.getBytes(StandardCharsets.UTF_8);
                DatagramPacket request = new DatagramPacket(sendData, sendData.length, address, port);
                socket.send(request);

                byte[] buffer = new byte[1024];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(response);
                    String reply = new String(response.getData(), 0, response.getLength(), StandardCharsets.UTF_8);
                    System.out.println("Server: " + reply);
                } catch (SocketTimeoutException e) {
                    // Khong co dam bao trong UDP -> khong biet chac la mat goi tin
                    // hay la server da dung; chi biet la KHONG NHAN duoc phan hoi.
                    System.out.println("[TIMEOUT] Khong nhan duoc phan hoi sau 3s "
                            + "(server co the da dung, hoac goi tin bi that lac).");
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("[LOI] Khong tim thay host: " + host);
        } catch (IOException e) {
            System.err.println("[LOI] Loi socket UDP: " + e.getMessage());
        }
    }
}
