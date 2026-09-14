import java.io.*;
import java.net.*;
public class TongServer {
    public static void main(String[] args) {
        int port = 6000;

        try {
            // B1: socket() + bind() + listen()
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server tinh tong dang cho Client tai cong " + port);

            while (true) {
                // accept()
                Socket socket = serverSocket.accept();
                System.out.println("Client ket noi: " + socket.getInetAddress());

                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                // B3: doc 2 so tu Client (read), xu ly (process request), gui ket qua (write)
                double a = in.readDouble();
                double b = in.readDouble();
                System.out.println("Nhan duoc: a = " + a + ", b = " + b);

                double tong = a + b;
                out.writeDouble(tong);
                System.out.println("Da gui ket qua: " + tong);

                // B4: dong ket noi phien lam viec nay
                in.close();
                out.close();
                socket.close();
                System.out.println("Da dong ket noi voi Client.\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
