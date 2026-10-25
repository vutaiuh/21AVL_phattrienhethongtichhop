import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Irmi checker = (Irmi) registry.lookup("Service");

            Scanner scanner = new Scanner(System.in);

            System.out.print("Nhap so can kiem tra: ");
            int n = scanner.nextInt();

            boolean result = checker.isPrime(n);

            if (result) {
                System.out.println(n + " la so nguyen to.");
            } else {
                System.out.println(n + " khong phai so nguyen to.");
            }

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
