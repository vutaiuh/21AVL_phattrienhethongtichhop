
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class HostURIInspector {

    public static void main(String[] args) {
        // ----- Xu ly thieu tham so -----
        if (args.length < 2) {
            System.err.println("[LOI] Thieu tham so dau vao.");
            System.err.println("Cach dung : java HostURIInspector <hostname> <uri>");
            System.err.println("Vi du     : java HostURIInspector www.google.com \"https://www.google.com:443/search?q=java#top\"");
            System.exit(1);
            return;
        }

        String hostname = args[0];
        String uriString = args[1];

        System.out.println("############################################");
        System.out.println("# BAI 1 - HOST VA URI INSPECTOR");
        System.out.println("############################################");

        System.out.println();
        System.out.println("=== [1] THONG TIN HOSTNAME: " + hostname + " ===");
        inspectHost(hostname);

        System.out.println();
        System.out.println("=== [2] THONG TIN URI: " + uriString + " ===");
        inspectURI(uriString);
    }

    /** In toan bo IP cua hostname: loai IPv4/IPv6, loopback, site-local. */
    private static void inspectHost(String hostname) {
        try {
            InetAddress[] addresses = InetAddress.getAllByName(hostname);
            System.out.println("Trang thai        : PHAN GIAI THANH CONG");
            System.out.println("So dia chi IP     : " + addresses.length);
            int i = 1;
            for (InetAddress addr : addresses) {
                String type;
                if (addr instanceof Inet4Address) {
                    type = "IPv4";
                } else if (addr instanceof Inet6Address) {
                    type = "IPv6";
                } else {
                    type = "Khong xac dinh";
                }
                System.out.println("--- Dia chi #" + (i++) + " ---");
                System.out.println("   Dia chi IP     : " + addr.getHostAddress());
                System.out.println("   Loai           : " + type);
                System.out.println("   Loopback       : " + addr.isLoopbackAddress());
                System.out.println("   Site local     : " + addr.isSiteLocalAddress());
                System.out.println("   Ten canonical  : " + addr.getCanonicalHostName());
            }
        } catch (UnknownHostException e) {
            System.out.println("Trang thai        : LOI");
            System.out.println("[LOI] Khong the phan giai hostname \"" + hostname + "\".");
            System.out.println("Chi tiet          : " + e.getMessage());
        }
    }

    /** Dung java.net.URI de in scheme, host, port, path, query, fragment. */
    private static void inspectURI(String uriString) {
        try {
            URI uri = new URI(uriString);
            System.out.println("Trang thai : URI HOP LE");
            System.out.println("Scheme     : " + display(uri.getScheme()));
            System.out.println("Host       : " + display(uri.getHost()));
            System.out.println("Port       : " + (uri.getPort() == -1 ? "(khong chi dinh / mac dinh)" : String.valueOf(uri.getPort())));
            System.out.println("Path       : " + display(uri.getPath()));
            System.out.println("Query      : " + display(uri.getQuery()));
            System.out.println("Fragment   : " + display(uri.getFragment()));
        } catch (URISyntaxException e) {
            // ----- Xu ly URI sai cu phap -----
            System.out.println("Trang thai : URI SAI CU PHAP");
            System.out.println("[LOI] " + e.getReason() + " (vi tri: " + e.getIndex() + ")");
        }
    }

    private static String display(String value) {
        return (value == null || value.isEmpty()) ? "(khong co)" : value;
    }
}
