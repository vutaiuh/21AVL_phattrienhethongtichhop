import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class IrmiImpl extends UnicastRemoteObject implements Irmi {

    protected IrmiImpl() throws RemoteException {
        super();
    }

    @Override
    public String sayHello() throws RemoteException {
        System.out.println("[Server] Client call sayHello()");
        return "Hello, World!";
    }

    public static void main(String[] args) {
        try {
            IrmiImpl server = new IrmiImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("HelloService", server);
            System.out.println("╔══════════════════════════════════╗");

        } catch (Exception e) {
            System.err.println("[Server]: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
