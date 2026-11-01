import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChat extends Remote {

    void sendMessage(String message) throws RemoteException;
}
