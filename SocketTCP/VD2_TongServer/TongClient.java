import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * VÍ DỤ 2: TCP Client tinh tong 2 so
 * Theo sơ đồ "Lập trình socket với TCP":
 *   B2: Client tạo Socket, yêu cầu thiết lập một kết nối với Server
 *   B3: Trao đổi thông tin giữa client và server (gui 2 so, nhan ket qua)
 *   B4: Kết thúc phiên làm việc
 */
public class TongClient {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 6000;
        Scanner scanner = new Scanner(System.in);

        try (
            // B2: socket() + connect()
            Socket socket = new Socket(host, port);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream())
        ) {
            System.out.println("Da ket noi toi Server " + host + ":" + port);

            System.out.print("Nhap so thu nhat: ");
            double a = scanner.nextDouble();
            System.out.print("Nhap so thu hai: ");
            double b = scanner.nextDouble();

            // write(): gui 2 so cho Server
            out.writeDouble(a);
            out.writeDouble(b);

            // read(): nhan ket qua tong tu Server
            double ketQua = in.readDouble();
            System.out.println("Tong " + a + " + " + b + " = " + ketQua);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
        // B4: try-with-resources tu dong close() socket khi ket thuc
    }
}
