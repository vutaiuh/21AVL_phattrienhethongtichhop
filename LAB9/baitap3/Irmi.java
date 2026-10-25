import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Irmi extends Remote {
    boolean isPrime(int n) throws RemoteException;
}