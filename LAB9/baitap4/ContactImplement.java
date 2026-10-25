import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ContactImplement extends UnicastRemoteObject implements IContact {

    private HashMap<String, String> contacts = new HashMap<>();

    public ContactImplement() throws RemoteException {
        super();
    }

    @Override
    public void addContact(String name, String phone) throws RemoteException {
        contacts.put(name, phone);

        System.out.println(
                "[" + Thread.currentThread().getName() + "] Them: "
                        + name + " - " + phone);
    }

    @Override
    public String findContact(String name) throws RemoteException {
        System.out.println(
                "[" + Thread.currentThread().getName() + "] Tim: " + name);

        return contacts.get(name);
    }

    @Override
    public boolean deleteContact(String name) throws RemoteException {
        System.out.println(
                "[" + Thread.currentThread().getName() + "] Xoa: " + name);

        return contacts.remove(name) != null;
    }
}