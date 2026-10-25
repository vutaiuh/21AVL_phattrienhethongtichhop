import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CALCImplement extends UnicastRemoteObject implements CALC {
    public CALCImplement() throws RemoteException {
        super();
    }

    @Override
    public double add(double a, double b) throws RemoteException {
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + threadName + "] SERVER: a=" + a + ", b=" + b);
        double result = a + b;
        System.out.println("[" + threadName + "] KQ: " + result);
        return result;
    }
}
