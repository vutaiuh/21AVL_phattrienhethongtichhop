import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UDPDateTimeServer {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd MM yyyy");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH mm ss");
    private static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern("dd MM yyyy HH mm ss");

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 6001;

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("[UDP DateTimeServer] Dang lang nghe tai port " + port + " ...");
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request); // Cho toi khi co goi tin den

                SocketAddress clientAddr = request.getSocketAddress();
                String cmd = new String(request.getData(), request.getOffset(),
                        request.getLength(), StandardCharsets.UTF_8).trim().toUpperCase();

                System.out.println("[UDP DateTimeServer] Nhan tu " + clientAddr + ": " + cmd);

                String reply = process(cmd);
                byte[] replyData = reply.getBytes(StandardCharsets.UTF_8);
                DatagramPacket response = new DatagramPacket(
                        replyData, replyData.length, request.getAddress(), request.getPort());
                socket.send(response);

                System.out.println("[UDP DateTimeServer] Da gui lai " + clientAddr + ": " + reply);
            }
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
            case "QUIT":
                // Khong co y nghia trong UDP vi khong co phien ket noi de dong
                return "ERR QUIT khong duoc ho tro tren UDP (khong co khai niem phien)";
            default:
                return "ERR UNKNOWN_COMMAND";
        }
    }
}
