import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatImplement extends UnicastRemoteObject implements IChat {

    public ChatImplement() throws RemoteException {
        super();
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println(
                "[" + Thread.currentThread().getName() + "] Client gui: " + message);

        String reply = "Server phan hoi: " + message;

        System.out.println(
                "[" + Thread.currentThread().getName() + "] " + reply);
    }
}
