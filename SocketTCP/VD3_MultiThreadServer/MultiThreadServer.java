import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer {
	public final static int serverPort = 7000;

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(serverPort);
			System.out.println("Server da luong dang lang nghe tai cong " + serverPort + " ...");

			while (true) {
				// B2: accept() - chi accept, khong xu ly du lieu o day
				Socket socket = serverSocket.accept();

				// B3: tao 1 Thread rieng cho client nay va cho no chay ngay
				ClientHandler handler = new ClientHandler(socket);
				handler.start();
			}

		} catch (IOException ie) {
			System.out.println("Server Creation Error: " + ie);
		}
	}
}
