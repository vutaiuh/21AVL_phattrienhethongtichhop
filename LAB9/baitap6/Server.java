import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    public static void main(String[] args) {

        try {
            BankImplement bank = new BankImplement();

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Service", bank);

            System.out.println("Server ngan hang da khoi dong, so du ban dau: 1_000_000");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
