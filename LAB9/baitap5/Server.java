import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    public static void main(String[] args) {

        try {
            ChatImplement chat = new ChatImplement();

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Service", chat);

            System.out.println("Server chat da khoi dong, cho client ket noi...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
