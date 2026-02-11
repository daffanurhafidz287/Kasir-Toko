import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class KasirFrame extends JFrame {

    JTextField txtNama = new JTextField();
    JTextField txtHarga = new JTextField();
    JTextField txtStok = new JTextField();

    JTable table = new JTable();
    DefaultTableModel model;

    ProdukDAO dao = new ProdukDAO();
    int selectedId = -1;

    public KasirFrame() {

        setTitle("Kasir Toko");
        setSize(600,400);
        setLayout(null);

        JLabel l1 = new JLabel("Nama");
        l1.setBounds(20,20,100,25);
        add(l1);

        txtNama.setBounds(120,20,150,25);
        add(txtNama);

        JLabel l2 = new JLabel("Harga");
        l2.setBounds(20,50,100,25);
        add(l2);

        txtHarga.setBounds(120,50,150,25);
        add(txtHarga);

        JLabel l3 = new JLabel("Stok");
        l3.setBounds(20,80,100,25);
        add(l3);

        txtStok.setBounds(120,80,150,25);
        add(txtStok);

        JButton btnTambah = new JButton("Tambah");
        btnTambah.setBounds(300,20,100,25);
        add(btnTambah);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(300,50,100,25);
        add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(300,80,100,25);
        add(btnDelete);

        model = new DefaultTableModel(new String[]{"ID","Nama","Harga","Stok"},0);
        table.setModel(model);

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20,120,540,200);
        add(sp);

        loadData();

        // TAMBAH DATA
        btnTambah.addActionListener(e -> {
            try {

                if(txtNama.getText().isEmpty()){
                    JOptionPane.showMessageDialog(this,"Nama tidak boleh kosong");
                    return;
                }

                Produk p = new Produk();
                p.setNama(txtNama.getText());
                p.setHarga(Double.parseDouble(txtHarga.getText()));
                p.setStok(Integer.parseInt(txtStok.getText()));

                dao.insert(p);
                loadData();
                clearForm();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        // PILIH DATA
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();

                selectedId = (int) model.getValueAt(row,0);
                txtNama.setText(model.getValueAt(row,1).toString());
                txtHarga.setText(model.getValueAt(row,2).toString());
                txtStok.setText(model.getValueAt(row,3).toString());
            }
        });

        // UPDATE
        btnUpdate.addActionListener(e -> {
            try {
                Produk p = new Produk(
                        selectedId,
                        txtNama.getText(),
                        Double.parseDouble(txtHarga.getText()),
                        Integer.parseInt(txtStok.getText())
                );

                dao.update(p);
                loadData();
                clearForm();

            } catch(Exception ex){
                ex.printStackTrace();
            }
        });

        // DELETE
        btnDelete.addActionListener(e -> {
            dao.delete(selectedId);
            loadData();
            clearForm();
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    void loadData() {
        model.setRowCount(0);
        List<Produk> list = dao.getAll();

        for(Produk p : list){
            model.addRow(new Object[]{
                    p.getId(),
                    p.getNama(),
                    p.getHarga(),
                    p.getStok()
            });
        }
    }

    void clearForm(){
        txtNama.setText("");
        txtHarga.setText("");
        txtStok.setText("");
        selectedId = -1;
    }

    public static void main(String[] args) {
        new KasirFrame();
    }
}
