// dao/KamarDAO.java
package DAO;

import config.DatabaseConnection;
import models.Kamar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KamarDAO {
    private final Connection conn = DatabaseConnection.getConnection();

    // Create
    public boolean insertKamar(Kamar kamar) {
        try {
            String sql = "INSERT INTO kamar (nomor_kamar, harga, status) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, kamar.getNomorKamar());
            stmt.setInt(2, kamar.getHarga());
            stmt.setString(3, kamar.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal insert kamar: " + e.getMessage());
            return false;
        }
    }

    // Read All
    public List<Kamar> getAllKamar() {
        List<Kamar> kamarList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM kamar ORDER BY nomor_kamar";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Kamar kamar = new Kamar(
                    rs.getInt("id"),
                    rs.getString("nomor_kamar"),
                    rs.getInt("harga"),
                    rs.getString("status")
                );
                kamarList.add(kamar);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data kamar: " + e.getMessage());
        }
        return kamarList;
    }

    // Read by ID
    public Kamar getKamarById(int id) {
        try {
            String sql = "SELECT * FROM kamar WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Kamar(
                    rs.getInt("id"),
                    rs.getString("nomor_kamar"),
                    rs.getInt("harga"),
                    rs.getString("status")
                );
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil kamar: " + e.getMessage());
        }
        return null;
    }

    // Update
    public boolean updateKamar(Kamar kamar) {
        try {
            String sql = "UPDATE kamar SET nomor_kamar = ?, harga = ?, status = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, kamar.getNomorKamar());
            stmt.setInt(2, kamar.getHarga());
            stmt.setString(3, kamar.getStatus());
            stmt.setInt(4, kamar.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal update kamar: " + e.getMessage());
            return false;
        }
    }

    // Delete
    public boolean deleteKamar(int id) {
        try {
            // Cek apakah kamar sedang dihuni
            String checkSql = "SELECT COUNT(*) FROM penghuni WHERE id_kamar = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Tidak dapat menghapus kamar yang sedang dihuni!");
                return false;
            }

            String sql = "DELETE FROM kamar WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal hapus kamar: " + e.getMessage());
            return false;
        }
    }

    // Search kamar
    public List<Kamar> searchKamar(String keyword) {
        List<Kamar> kamarList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM kamar WHERE nomor_kamar LIKE ? OR status LIKE ? OR harga = ? ORDER BY nomor_kamar";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            
            // Coba parse keyword sebagai angka untuk harga
            try {
                int harga = Integer.parseInt(keyword);
                stmt.setInt(3, harga);
            } catch (NumberFormatException e) {
                stmt.setInt(3, -1); // Nilai yang tidak mungkin
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Kamar kamar = new Kamar(
                    rs.getInt("id"),
                    rs.getString("nomor_kamar"),
                    rs.getInt("harga"),
                    rs.getString("status")
                );
                kamarList.add(kamar);
            }
        } catch (SQLException e) {
            System.out.println("Gagal search kamar: " + e.getMessage());
        }
        return kamarList;
    }

    public List<Kamar> getAvailableKamar() {
        List<Kamar> kamarList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM kamar WHERE status = 'kosong' ORDER BY nomor_kamar";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Kamar kamar = new Kamar(
                    rs.getInt("id"),
                    rs.getString("nomor_kamar"),
                    rs.getInt("harga"),
                    rs.getString("status")
                );
                kamarList.add(kamar);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil kamar kosong: " + e.getMessage());
        }
        return kamarList;
    }

    public boolean updateStatusKamar(int idKamar, String status) {
        try {
            String sql = "UPDATE kamar SET status = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, idKamar);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal update status kamar: " + e.getMessage());
            return false;
        }
    }
}