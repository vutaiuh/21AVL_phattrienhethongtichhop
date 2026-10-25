import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CALCServer {
    private static final int PORT = 1099;

    public static void main(String[] args) {
        try {
            CALCImplement cal = new CALCImplement();
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.rebind("ONLY Service", cal);
            System.out.println("  PORT: " + PORT);

        } catch (Exception e) {
            // TODO: handle exception
            System.err.println("Server: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
