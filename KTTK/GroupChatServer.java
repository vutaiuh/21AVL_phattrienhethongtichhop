import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Group Chat Server
 *
 * Main:
 *   - manage rooms
 *   - Broadcast message in room
 *   - save history room chatting
 *   - remove chat => clean history
 *   - Hien thi so phong hien co
 */
public class GroupChatServer {

    static final int PORT = 9200;
    static final int MAX_HISTORY = 200; // limit row history 
    static final HashMap<String, ChatRoom> rooms = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("[Server] Ket noi tai port " + PORT);

        while (true) {
            Socket socket = server.accept();
            System.out.println("[+] New client: " + socket.getInetAddress());
            new ClientThread(socket).start();
        }
    }
    static int roomCount() {
        synchronized (rooms) {
            return rooms.size();
        }
    }

    static ChatRoom joinOrCreate(String roomName, ClientThread client) {
        synchronized (rooms) {
            ChatRoom room = rooms.get(roomName);
            if (room == null) {
                room = new ChatRoom(roomName);
                rooms.put(roomName, room);
                System.out.println("[+] Room created: " + roomName + " | Tong so phong: " + rooms.size());
            }
            room.members.add(client);
            return room;
        }
    }

    static void leave(ChatRoom room, ClientThread client) {
        synchronized (rooms) {
            room.members.remove(client);
            // empty room => remove history,...
            if (room.members.isEmpty()) {
                room.clearHistory();
                rooms.remove(room.name);
                System.out.println("[-] Room deleted: " + room.name + " (da xoa lich su chat)"
                        + " | Tong so phong: " + rooms.size());
            }
        }
    }

    // Thread handle client
    static class ClientThread extends Thread {
        Socket socket;
        PrintWriter out;
        BufferedReader in;
        String clientName = null;
        ChatRoom currentRoom = null;

        ClientThread(Socket socket) throws IOException {
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        }

        public void run() {
            try {
                out.println("Hello! Welcome please send: HELLO <name>");

                String line;
                while ((line = in.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    // No register
                    if (clientName == null) {
                        if (line.toUpperCase().startsWith("HELLO ")) {
                            clientName = line.substring(6).trim();
                            out.println("OK Dang ki tai [" + clientName + "]");
                            listRooms();
                            System.out.println("[+] " + clientName + " registered");
                        } else {
                            out.println("ERR Send HELLO <name> first");
                        }
                        continue;
                    }

                    // registered but not in room
                    if (currentRoom == null) {
                        if (line.equalsIgnoreCase("LIST")) {
                            listRooms();
                        } else if (line.toUpperCase().startsWith("JOIN ")) {
                            String roomName = line.substring(5).trim();
                            if (roomName.isEmpty()) {
                                out.println("ERR Send JOIN <roomName>");
                            } else {
                                joinRoom(roomName);
                            }
                        } else if (line.equalsIgnoreCase("/EXIT")) {
                            out.println("BYE Goodbye!");
                            break;
                        } else {
                            out.println("ERR Send LIST or JOIN <roomName>");
                        }
                        continue;
                    }

                    // joined room
                    if (line.equalsIgnoreCase("/QUIT")) {
                        leaveRoom();
                        out.println("OK Roi khoi phong");
                        listRooms();
                    } else if (line.equalsIgnoreCase("/EXIT")) {
                        leaveRoom();
                        out.println("BYE Goodbye!");
                        break;
                    } else {
                        // send message (save history and send user)
                        String msg = "[" + clientName + "]: " + line;
                        currentRoom.sendMessage(msg, this);
                        out.println("Da gui tin nhan");
                    }
                }
            } catch (IOException e) {
                System.out.println("[!] Error: " + e.getMessage());
            } finally {
                try {
                    if (currentRoom != null) leaveRoom();
                    socket.close();
                } catch (IOException e) {}
                System.out.println("[-] " + clientName + " disconnected");
            }
        }

        void listRooms() {
            synchronized (rooms) {
                if (rooms.isEmpty()) {
                    out.println("INFO======= Hien chua co room nao. Please send: JOIN <roomName>");
                } else {
                    out.println("INFO======= Hien co nhung room sau:");
                    for (ChatRoom r : rooms.values()) {
                        out.println("  - " + r.name + " (" + r.members.size() + " members)");
                    }
                }
            }
        }

        void joinRoom(String roomName) {
            currentRoom = joinOrCreate(roomName, this);
            out.println("OK Joined [" + roomName + "]");

            // send history chat room
            List<String> old = currentRoom.getHistory();
            if (!old.isEmpty()) {
                out.println("INFO======= Lich su chat cua phong [" + roomName + "]:");
                for (String h : old) {
                    out.println(h);
                }
                out.println("INFO======= Het lich su");
            }

            currentRoom.sendMessage("[" + clientName + "] joined", this);
            System.out.println("[+] " + clientName + " joined " + roomName);
        }

        void leaveRoom() {
            if (currentRoom != null) {
                ChatRoom room = currentRoom;
                currentRoom = null;
                room.sendMessage("[" + clientName + "] left", this);
                leave(room, this); // neu phong rong thi xoa phong + lich su
            }
        }
    }

    static class ChatRoom {
        String name;
        CopyOnWriteArrayList<ClientThread> members = new CopyOnWriteArrayList<>();
        private final LinkedList<String> history = new LinkedList<>();

        ChatRoom(String name) {
            this.name = name;
        }

        void sendMessage(String msg, ClientThread sender) {
            addHistory(msg);
            for (ClientThread client : members) {
                if (client != sender) {
                    client.out.println(msg);
                }
            }
        }

        synchronized void addHistory(String msg) {
            history.add(msg);
            if (history.size() > MAX_HISTORY) {
                history.removeFirst();
            }
        }

        synchronized List<String> getHistory() {
            return new ArrayList<>(history);
        }

        synchronized void clearHistory() {
            history.clear();
        }
    }
}