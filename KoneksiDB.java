import java.sql.Connection;
import java.sql.DriverManager;

public class KoneksiDB {

    private static final String URL =
            "jdbc:mysql://localhost:3306/kasir_toko";

    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL, USER, PASS);

        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
            return null;
        }
    }
}
