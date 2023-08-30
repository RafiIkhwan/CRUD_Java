package layout.form;

import javax.naming.InitialContext;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBarang {
    private JTextField jTextField_kode_barang;
    private JTextField jTextField_stok;
    private JTextField jTextField_nama_barang;
    private JTextField jTextField_harga;
    private JTextField jTextField_satuan;
    private JButton jButton_baru;
    private JButton jButton_simpan;
    private JButton jButton_edit;
    private JButton jButton_hapus;
    private JTable jTable_barang;
    private JPanel Form;
    private JPanel Button;
    private JPanel Tabel;
    private JPanel DataBarang;
    private JLabel kodeBarangLabel;
    private JLabel DAFTARDATABARANGLabel;
    private JLabel namaBarangLabel;
    private JLabel hargaLabel;
    private JLabel satuanLabel;
    private JLabel stokLabel;

    private DefaultTableModel defaultTableModel;
    private String SQL;

    public DataBarang() {
        jTable_barang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int baris = jTable_barang.getSelectedRow();
                jTextField_kode_barang.setText(defaultTableModel.getValueAt(baris, 0).toString());
                jTextField_nama_barang.setText(defaultTableModel.getValueAt(baris, 1).toString());
                jTextField_harga.setText(defaultTableModel.getValueAt(baris, 2).toString());
                jTextField_satuan.setText(defaultTableModel.getValueAt(baris, 3).toString());
                jTextField_stok.setText(defaultTableModel.getValueAt(baris, 4).toString());
            }
        });

        jButton_simpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Connection connection = config.connection.getConnection();
                    PreparedStatement statement = connection.prepareStatement(
                            "INSERT INTO barang (kode_barang, nama_barang, harga, satuan, stok)" +
                            "values(?,?,?,?,?)");
                    statement.setString(1,jTextField_kode_barang.getText());
                    statement.setString(2,jTextField_nama_barang.getText());
                    statement.setString(3,jTextField_harga.getText());
                    statement.setString(4,jTextField_satuan.getText());
                    statement.setString(5,jTextField_stok.getText());
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data berhasil disimpan","Pesan",
                    JOptionPane.INFORMATION_MESSAGE);
                    TampilData();
                } catch (SQLException exception){
                    System.out.println(exception.getMessage());
                }
            }
        });

        jButton_edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Connection connection = config.connection.getConnection();
                    PreparedStatement statement = connection.prepareStatement(
                            "UPDATE barang set nama_barang=?, harga=?, satuan=?, stok=? WHERE kode_barang=?");
                    statement.setString(1,jTextField_nama_barang.getText());
                    statement.setString(2,jTextField_harga.getText());
                    statement.setString(3,jTextField_satuan.getText());
                    statement.setString(4,jTextField_stok.getText());
                    statement.setString(5,jTextField_kode_barang.getText());
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data berhasil diubah","Pesan",
                            JOptionPane.INFORMATION_MESSAGE);
                    TampilData();
                } catch (SQLException exception){
                    System.out.println(exception.getMessage());
                }
            }
        });
        jButton_hapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    Connection connection = config.connection.getConnection();
                    int confirm = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus data tersebut?", "Konfirmasi",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (confirm == 0){
                        try{
                            java.sql.PreparedStatement statement = connection.prepareStatement(
                                    "DELETE FROM barang WHERE kode_barang = '" +
                                            jTextField_kode_barang.getText() + "'");
                            statement.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Data berhasil dihapus","Pesan",
                                    JOptionPane.INFORMATION_MESSAGE);
                            TampilData();
                            jTextField_kode_barang.setText("");
                            jTextField_nama_barang.setText("");
                            jTextField_harga.setText("");
                            jTextField_satuan.setText("");
                            jTextField_stok.setText("");
                            statement.executeUpdate();
                    } catch (SQLException exception){
                            System.out.println(exception.getMessage());
                        }
                }
            }
        });
    }

    public void TampilData(){
        defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Kode Barang");
        defaultTableModel.addColumn("Nama Barang");
        defaultTableModel.addColumn("Harga");
        defaultTableModel.addColumn("Satuan");
        defaultTableModel.addColumn("Stok");
        jTable_barang.setModel(defaultTableModel);
        Connection connection = config.connection.getConnection();
        try {
            java.sql.Statement statement = connection.createStatement();
            SQL = "SELECT * FROM BARANG";
            java.sql.ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                defaultTableModel.addRow(new Object[]{
                        resultSet.getString("kode_barang"),
                        resultSet.getString("nama_barang"),
                        resultSet.getString("harga"),
                        resultSet.getString("satuan"),
                        resultSet.getString("stok")
                });
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Data Barang");
        layout.form.DataBarang dataBarang = new DataBarang();
        dataBarang.TampilData();
        frame.setContentPane(dataBarang.DataBarang);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
