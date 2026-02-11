import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdukDAO {

    public void insert(Produk p) {
        String sql = "INSERT INTO produk(nama,harga,stok) VALUES(?,?,?)";

        try (Connection c = KoneksiDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, p.getNama());
            ps.setDouble(2, p.getHarga());
            ps.setInt(3, p.getStok());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Insert gagal: " + e.getMessage());
        }
    }

    public List<Produk> getAll() {
        List<Produk> list = new ArrayList<>();

        try (Connection c = KoneksiDB.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM produk")) {

            while (rs.next()) {
                list.add(new Produk(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getDouble("harga"),
                        rs.getInt("stok")
                ));
            }

        } catch (Exception e) {
            System.out.println("Read gagal: " + e.getMessage());
        }

        return list;
    }

    public void update(Produk p) {
        String sql = "UPDATE produk SET nama=?, harga=?, stok=? WHERE id=?";

        try (Connection c = KoneksiDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, p.getNama());
            ps.setDouble(2, p.getHarga());
            ps.setInt(3, p.getStok());
            ps.setInt(4, p.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Update gagal: " + e.getMessage());
        }
    }

    public void delete(int id) {
        try (Connection c = KoneksiDB.getConnection();
             PreparedStatement ps =
                     c.prepareStatement("DELETE FROM produk WHERE id=?")) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Delete gagal: " + e.getMessage());
        }
    }
}
