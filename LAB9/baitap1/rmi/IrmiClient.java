import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class IrmiClient {

    public static void main(String[] args) {
        try {

            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Irmi stub = (Irmi) registry.lookup("HelloService");
            System.out.println("╔══════════════════════════════════╗");
            String response = stub.sayHello();
            System.out.println("[Client] Server: \"" + response + "\"");
        } catch (Exception e) {
            System.err.println("[Client] ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
