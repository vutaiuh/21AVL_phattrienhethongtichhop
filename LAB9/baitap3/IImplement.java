import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class IImplement extends UnicastRemoteObject implements Irmi {
    public IImplement() throws RemoteException {
        super();
    }

    private boolean checkPrime(int n) {
        if (n < 2)
            return false;
        if (n == 2)
            return true;
        if (n % 2 == 0)
            return false;
        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    @Override
    public boolean isPrime(int n) throws RemoteException {
        String thread = Thread.currentThread().getName();
        System.out.println("[" + thread + "] Kiem tra so: " + n);

        boolean result = checkPrime(n);

        System.out
                .println("[" + thread + "] Ket qua: " + n + (result ? " la so nguyen to" : " khong phai so nguyen to"));
        return result;
    }
}
