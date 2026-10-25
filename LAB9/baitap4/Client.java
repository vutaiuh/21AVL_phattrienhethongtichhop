import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            IContact manager = (IContact) registry.lookup("Service");

            Scanner scanner = new Scanner(System.in);

            int choice;

            do {

                System.out.println("\n===== QUAN LY DANH BA =====");
                System.out.println("1. Them lien he");
                System.out.println("2. Tim lien he");
                System.out.println("3. Xoa lien he");
                System.out.println("0. Thoat");
                System.out.print("Chon: ");

                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {

                    case 1:

                        System.out.print("Nhap ten: ");
                        String addName = scanner.nextLine();

                        System.out.print("Nhap so dien thoai: ");
                        String phone = scanner.nextLine();

                        manager.addContact(addName, phone);

                        System.out.println(
                                "Da them: " + addName + " - " + phone);

                        break;

                    case 2:

                        System.out.print("Nhap ten can tim: ");
                        String findName = scanner.nextLine();

                        String result = manager.findContact(findName);

                        if (result != null) {
                            System.out.println(
                                    "So dien thoai: " + result);
                        } else {
                            System.out.println(
                                    "Khong tim thay lien he: "
                                            + findName);
                        }

                        break;

                    case 3:

                        System.out.print("Nhap ten can xoa: ");
                        String delName = scanner.nextLine();

                        boolean deleted = manager.deleteContact(delName);

                        if (deleted) {
                            System.out.println(
                                    "Da xoa: " + delName);
                        } else {
                            System.out.println(
                                    "Khong tim thay lien he: "
                                            + delName);
                        }

                        break;

                    case 0:

                        System.out.println(
                                "Thoat chuong trinh.");

                        break;

                    default:

                        System.out.println(
                                "Lua chon khong hop le!");
                }

            } while (choice != 0);

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}