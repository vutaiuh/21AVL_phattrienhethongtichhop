import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    public static void main(String[] args) {

        try {
            ContactImplement manager = new ContactImplement();

            Registry registry = LocateRegistry.createRegistry(1099);

            registry.rebind("Service", manager);

            System.out.println(
                    "Server da khoi dong, cho client ket noi...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}