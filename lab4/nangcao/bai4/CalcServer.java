import java.io.*;
import java.net.*;

public class CalcServer {

    private static final int PORT = 9000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("[SERVER] Listenner at port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("[+] Connect from: " + clientSocket.getRemoteSocketAddress());

            Thread t = new Thread(() -> handleClient(clientSocket));
            t.setDaemon(true);
            t.start();
        }
    }

    private static void handleClient(Socket socket) {
        String addr = socket.getRemoteSocketAddress().toString();
        try (
            socket;
            BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter    out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("[" + addr + "] << " + line);
                String response = handleCalc(line.trim());
                System.out.println("[" + addr + "] >> " + response);
                out.println(response);
            }
        } catch (IOException e) {
            System.out.println("[!] Err at client " + addr + ": " + e.getMessage());
        } finally {
            System.out.println("[-] Timeout connection: " + addr);
        }
    }

    static String handleCalc(String request) {
        if (request == null || request.isEmpty()) {
            return "ERR INVALID_FORMAT";
        }

        String[] parts = request.split("\\s+");
        if (parts.length != 4 || !parts[0].equalsIgnoreCase("CALC")) {
            return "ERR INVALID_FORMAT";
        }

        String operator = parts[1];
        String op1Str   = parts[2];
        String op2Str   = parts[3];
        if (!operator.equals("+") && !operator.equals("-")
                && !operator.equals("*") && !operator.equals("/")) {
            return "ERR UNSUPPORTED_OPERATOR";
        }

        double op1, op2;
        try {
            op1 = Double.parseDouble(op1Str);
            op2 = Double.parseDouble(op2Str);
        } catch (NumberFormatException e) {
            return "ERR INVALID_NUMBER";
        }

        double result;
        switch (operator) {
            case "+" -> result = op1 + op2;
            case "-" -> result = op1 - op2;
            case "*" -> result = op1 * op2;
            case "/" -> {
                if (op2 == 0) return "ERR DIVIDE_BY_ZERO";
                result = op1 / op2;
            }
            default -> { return "ERR UNSUPPORTED_OPERATOR"; }
        }

        if (result == Math.floor(result) && !Double.isInfinite(result)) {
            return "OK " + (long) result;
        }
        return "OK " + result;
    }
}