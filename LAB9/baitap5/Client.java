import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            IChat chat = (IChat) registry.lookup("Service");

            Scanner scanner = new Scanner(System.in);

            System.out.println("===== CHAT RMI (go 'exit' de thoat) =====");

            String line;
            while (true) {
                System.out.print("Ban: ");
                line = scanner.nextLine();

                if (line.equalsIgnoreCase("exit")) {
                    break;
                }

                chat.sendMessage(line);
                System.out.println("Server phan hoi: " + line);
            }

            System.out.println("Thoat chat.");
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
