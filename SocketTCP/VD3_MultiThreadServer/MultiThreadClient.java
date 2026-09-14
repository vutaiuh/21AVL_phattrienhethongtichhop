import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class MultiThreadClient {
	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 7000;

		try (
			// B2: socket() + connect()
			Socket socket = new Socket(host, port);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			BufferedReader keyboard = new BufferedReader(
					new InputStreamReader(System.in))
		) {
			System.out.println("Da ket noi toi Server " + host + ":" + port);
			String message;

			while (true) {
				System.out.print("Nhap chuoi can gui (go 'exit' de thoat): ");
				message = keyboard.readLine();

				// write(): gui du lieu cho Server
				out.println(message);

				if (message.equalsIgnoreCase("exit")) {
					break;
				}

				// read(): nhan du lieu phan hoi tu Server
				String reply = in.readLine();
				System.out.println("Server tra ve: " + reply);
			}

		} catch (UnknownHostException e) {
			System.out.println("Khong tim thay Server: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// B4: try-with-resources tu dong close() socket khi ket thuc
	}
}
