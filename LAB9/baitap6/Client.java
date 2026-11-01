import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            IBank bank = (IBank) registry.lookup("Service");

            Scanner scanner = new Scanner(System.in);

            int choice;

            do {

                System.out.println("\n===== QUAN LY TAI KHOAN =====");
                System.out.println("1. Kiem tra so du");
                System.out.println("2. Nap tien");
                System.out.println("3. Rut tien");
                System.out.println("0. Thoat");
                System.out.print("Chon: ");

                choice = scanner.nextInt();

                switch (choice) {

                    case 1:
                        System.out.println("So du: " + bank.getBalance());
                        break;

                    case 2:
                        System.out.print("So tien nap: ");
                        double depositAmount = scanner.nextDouble();
                        bank.deposit(depositAmount);
                        System.out.println("Da nap tien.");
                        break;

                    case 3:
                        System.out.print("So tien rut: ");
                        double withdrawAmount = scanner.nextDouble();
                        boolean ok = bank.withdraw(withdrawAmount);
                        if (ok) {
                            System.out.println("Rut tien thanh cong.");
                        } else {
                            System.out.println("Rut tien that bai.");
                        }
                        break;

                    case 0:
                        System.out.println("Thoat chuong trinh.");
                        break;

                    default:
                        System.out.println("Lua chon khong hop le!");
                }

            } while (choice != 0);

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
