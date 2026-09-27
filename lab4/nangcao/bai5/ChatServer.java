import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {

    private static final int PORT        = 7000;
    private static final int POOL_SIZE   = 10;

    // Thread-safe map: nickname -> ClientHandler
    private static final ConcurrentHashMap<String, ClientHandler> clients
            = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[SERVER] Chat server đang chạy tại port " + PORT);
            System.out.println("[SERVER] Thread pool size: " + POOL_SIZE);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("[+] Kết nối mới: " + socket.getRemoteSocketAddress());
                pool.execute(new ClientHandler(socket));
            }
        }
    }

    /** send all message expect user who was sended */
    static void broadcast(String message, String excludeNick) {
        for (Map.Entry<String, ClientHandler> entry : clients.entrySet()) {
            if (!entry.getKey().equals(excludeNick)) {
                entry.getValue().send(message);
            }
        }
    }

    /** Tadd client into list. */
    static boolean register(String nick, ClientHandler handler) {
        return clients.putIfAbsent(nick, handler) == null;
    }

    /** remove client out of list. */
    static void unregister(String nick) {
        if (nick != null) clients.remove(nick);
    }

    /** get list nickname */
    static Set<String> getOnlineUsers() {
        return clients.keySet();
    }

    // ------------------------------------------------------------------ //

    static class ClientHandler implements Runnable {

        private final Socket socket;
        private PrintWriter  out;
        private String       nick = null; // null 
        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String addr = socket.getRemoteSocketAddress().toString();
            try (
                socket;
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
            ) {
                out = new PrintWriter(socket.getOutputStream(), true);
                send("Hay dat nickname: NICK <tên>");

                String line;
                while ((line = in.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    System.out.println("[" + (nick != null ? nick : addr) + "] " + line);
                    handleCommand(line);
                }
            } catch (IOException e) {
                System.out.println("[!] Error: " + addr);
            } finally {
                disconnect();
            }
        }

        private void handleCommand(String line) {
            // ---- Chưa có nick: chỉ cho phép lệnh NICK ----
            if (nick == null) {
                if (line.toUpperCase().startsWith("NICK ")) {
                    String proposed = line.substring(5).trim();
                    if (proposed.isEmpty()) {
                        send("ERR empty field.");
                        return;
                    }
                    if (register(proposed, this)) {
                        nick = proposed;
                        send("OK Hello " + nick + "! ENter USERS, MSG <nd>, or QUIT.");
                        broadcast("[SERVER] " + nick + " tham gia chat.", nick);
                        System.out.println("[+] Register: " + nick);
                    } else {
                        send("ERR Nickname '" + proposed + "' da su dung.");
                    }
                } else {
                    send("ERR : NICK <tên>");
                }
                return;
            }

            // ---- Đã có nick ----
            String upper = line.toUpperCase();

            if (upper.equals("USERS")) {
                send("USERS " + String.join(", ", getOnlineUsers()));

            } else if (upper.startsWith("MSG ")) {
                String content = line.substring(4).trim();
                if (content.isEmpty()) {
                    send("ERR Empty.");
                } else {
                    String msg = "[" + nick + "] " + content;
                    broadcast(msg, nick);   // gửi cho người khác
                    send("OK Sending.");     // báo lại cho người gửi
                }

            } else if (upper.equals("QUIT")) {
                send("BYE " + nick + "!");
                disconnect();

            } else {
                send("ERR . Dùng: USERS | MSG <nd> | QUIT");
            }
        }

        void send(String message) {
            if (out != null) out.println(message);
        }

        private void disconnect() {
            if (nick != null) {
                System.out.println("[-] Out room: " + nick);
                unregister(nick);
                broadcast("[SERVER] " + nick + " out of room.", nick);
                nick = null;
            }
            try { socket.close(); } catch (IOException ignored) {}
        }
    }
}