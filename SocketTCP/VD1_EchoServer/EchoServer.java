import java.io.*;
import java.net.*;

public class EchoServer {
    public static void main(String[] args) {
        int port = 5000;

        try {
            // B1: socket() + bind() + listen()
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server dang lang nghe tai cong " + port + " ...");

            while (true) {
                // accept(): cho phep 1 ket noi moi
                Socket socket = serverSocket.accept();
                System.out.println("Da ket noi voi Client: " + socket.getInetAddress());

                // B3: doc / ghi du lieu qua Stream
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                String requestLine;
                while ((requestLine = in.readLine()) != null) {
                    System.out.println("Nhan tu Client: " + requestLine);

                    if (requestLine.equalsIgnoreCase("exit")) {
                        break;
                    }

                    // process request
                    String reply = requestLine.toUpperCase();
                    out.println(reply);
                }

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
