import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            IImplement primeChecker = new IImplement();

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Service", primeChecker);

            System.out.println("Server da khoi dong, cho client ket noi...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
