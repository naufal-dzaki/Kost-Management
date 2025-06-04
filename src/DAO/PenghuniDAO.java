// dao/PenghuniDAO.java (lengkap)
package dao;

import config.DatabaseConnection;
import models.Penghuni;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PenghuniDAO {
    private final Connection conn = DatabaseConnection.getConnection();

    // Create
    public boolean insertPenghuni(Penghuni penghuni) {
        try {
            String sql = "INSERT INTO penghuni (id, nama, no_ktp, id_kamar) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, penghuni.getId());
            stmt.setString(2, penghuni.getNama());
            stmt.setString(3, penghuni.getNoKtp());
            if (penghuni.getIdKamar() == 0) {
                stmt.setNull(4, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(4, penghuni.getIdKamar());
            }
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Gagal insert penghuni: " + e.getMessage());
            return false;
        }
    }

    public boolean isNoKtpExists(String noKtp) {
        try {
            String sql = "SELECT COUNT(*) FROM penghuni WHERE no_ktp = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, noKtp);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Gagal cek no KTP: " + e.getMessage());
        }
        return false;
    }

    // Read All
    public List<Penghuni> getAllPenghuni() {
        List<Penghuni> penghuniList = new ArrayList<>();
        try {
            String sql = "SELECT p.*, u.username FROM penghuni p JOIN users u ON p.id = u.id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Penghuni penghuni = new Penghuni(
                    rs.getInt("id"),
                    rs.getString("username"),
                    "", // password tidak diambil
                    "penghuni",
                    rs.getString("nama"),
                    rs.getString("no_ktp"),
                    rs.getInt("id_kamar")
                );
                if (rs.wasNull()) {
                    penghuni.setIdKamar(0);
                }
                penghuniList.add(penghuni);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data penghuni: " + e.getMessage());
        }
        return penghuniList;
    }

    // Read by ID
    public Penghuni getPenghuniById(int id) {
        try {
            String sql = "SELECT p.*, u.username FROM penghuni p JOIN users u ON p.id = u.id WHERE p.id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Penghuni penghuni = new Penghuni(
                    rs.getInt("id"),
                    rs.getString("username"),
                    "", // password tidak diambil
                    "penghuni",
                    rs.getString("nama"),
                    rs.getString("no_ktp"),
                    rs.getInt("id_kamar")
                );
                if (rs.wasNull()) {
                    penghuni.setIdKamar(0);
                }
                return penghuni;
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil penghuni: " + e.getMessage());
        }
        return null;
    }

    // Update
    public boolean updatePenghuni(Penghuni penghuni) {
        try {
            String sql = "UPDATE penghuni SET nama = ?, no_ktp = ?, id_kamar = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, penghuni.getNama());
            stmt.setString(2, penghuni.getNoKtp());
            if (penghuni.getIdKamar() == 0) {
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(3, penghuni.getIdKamar());
            }
            stmt.setInt(4, penghuni.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal update penghuni: " + e.getMessage());
            return false;
        }
    }

    // Delete
    public boolean deletePenghuni(int id) {
        try {
            // Hapus dulu tagihan yang terkait
            String deleteTagihanSql = "DELETE FROM tagihan WHERE id_penghuni = ?";
            PreparedStatement deleteTagihanStmt = conn.prepareStatement(deleteTagihanSql);
            deleteTagihanStmt.setInt(1, id);
            deleteTagihanStmt.executeUpdate();

            // Hapus penghuni
            String sql = "DELETE FROM penghuni WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal hapus penghuni: " + e.getMessage());
            return false;
        }
    }

    // Search penghuni
    public List<Penghuni> searchPenghuni(String keyword) {
        List<Penghuni> penghuniList = new ArrayList<>();
        try {
            String sql = """
                SELECT p.*, u.username 
                FROM penghuni p 
                JOIN users u ON p.id = u.id 
                WHERE p.nama LIKE ? OR p.no_ktp LIKE ? OR u.username LIKE ?
                ORDER BY p.nama
                """;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            stmt.setString(3, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Penghuni penghuni = new Penghuni(
                    rs.getInt("id"),
                    rs.getString("username"),
                    "", // password tidak diambil
                    "penghuni",
                    rs.getString("nama"),
                    rs.getString("no_ktp"),
                    rs.getInt("id_kamar")
                );
                if (rs.wasNull()) {
                    penghuni.setIdKamar(0);
                }
                penghuniList.add(penghuni);
            }
        } catch (SQLException e) {
            System.out.println("Gagal search penghuni: " + e.getMessage());
        }
        return penghuniList;
    }

    // Update kamar penghuni
    public boolean updateKamarPenghuni(int idPenghuni, int idKamar) {
        try {
            String sql = "UPDATE penghuni SET id_kamar = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            if (idKamar == 0) {
                stmt.setNull(1, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(1, idKamar);
            }
            stmt.setInt(2, idPenghuni);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal update kamar penghuni: " + e.getMessage());
            return false;
        }
    }
}