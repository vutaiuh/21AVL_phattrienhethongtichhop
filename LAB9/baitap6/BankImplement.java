import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BankImplement extends UnicastRemoteObject implements IBank {

    private double balance = 1_000_000;

    public BankImplement() throws RemoteException {
        super();
    }

    @Override
    public double getBalance() throws RemoteException {
        System.out.println(
                "[" + Thread.currentThread().getName() + "] Kiem tra so du");

        return balance;
    }

    @Override
    public void deposit(double amount) throws RemoteException {
        if (amount <= 0) {
            System.out.println(
                    "[" + Thread.currentThread().getName() + "] Nap tien khong hop le: " + amount);
            return;
        }

        balance += amount;

        System.out.println(
                "[" + Thread.currentThread().getName() + "] Nap: " + amount
                        + " | So du: " + balance);
    }

    @Override
    public boolean withdraw(double amount) throws RemoteException {
        if (amount <= 0) {
            System.out.println(
                    "[" + Thread.currentThread().getName() + "] Rut tien khong hop le: " + amount);
            return false;
        }

        if (amount > balance) {
            System.out.println(
                    "[" + Thread.currentThread().getName() + "] Rut that bai, so du khong du");
            return false;
        }

        balance -= amount;

        System.out.println(
                "[" + Thread.currentThread().getName() + "] Rut: " + amount
                        + " | So du: " + balance);

        return true;
    }
}
