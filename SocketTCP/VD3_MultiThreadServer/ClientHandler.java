import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
	Socket channel; // socket

	public ClientHandler(Socket s) {
		channel = s;
	}

	public void run() {
		try (
			BufferedReader in = new BufferedReader(
					new InputStreamReader(channel.getInputStream()));
			PrintWriter out = new PrintWriter(channel.getOutputStream(), true)
		) {
			System.out.println("[" + Thread.currentThread().getName() + "] Da ket noi voi Client: "
					+ channel.getInetAddress());

			String requestLine;
			while ((requestLine = in.readLine()) != null) {
				System.out.println("[" + Thread.currentThread().getName() + "] Nhan tu Client: " + requestLine);

				if (requestLine.equalsIgnoreCase("exit")) {
					break;
				}

				// process request: chuyen thanh chu in hoa
				String reply = requestLine.toUpperCase();
				out.println(reply);
			}

		} catch (IOException ie) {
			System.out.println("Request Processing Error: " + ie);
		} finally {
			try {
				channel.close();
			} catch (IOException ignored) {
			}
			System.out.println("[" + Thread.currentThread().getName() + "] Da dong ket noi voi Client.\n");
		}
	}
}
