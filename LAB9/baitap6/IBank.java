import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBank extends Remote {

    double getBalance() throws RemoteException;

    void deposit(double amount) throws RemoteException;

    boolean withdraw(double amount) throws RemoteException;
}
