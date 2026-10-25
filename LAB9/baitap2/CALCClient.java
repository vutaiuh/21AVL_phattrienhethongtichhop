import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class CALCClient {

    public static void main(String[] args) {

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            CALC calc = (CALC) registry.lookup("ONLY Service");

            Scanner scanner = new Scanner(System.in);

            System.out.print("Nhap a: ");
            double a = scanner.nextDouble();

            System.out.print("Nhap b: ");
            double b = scanner.nextDouble();

            double result = calc.add(a, b);

            System.out.println(
                    "Ket qua: " + a + " + " + b + " = " + result);

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}