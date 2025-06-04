// dao/TagihanDAO.java
package DAO;

import config.DatabaseConnection;
import models.Tagihan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TagihanDAO {
    private final Connection conn = DatabaseConnection.getConnection();

    // Create
    public boolean insertTagihan(Tagihan tagihan) {
        try {
            String sql = "INSERT INTO tagihan (id_penghuni, bulan, jumlah, status) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tagihan.getIdPenghuni());
            stmt.setString(2, tagihan.getBulan());
            stmt.setInt(3, tagihan.getJumlah());
            stmt.setString(4, tagihan.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal insert tagihan: " + e.getMessage());
            return false;
        }
    }

    // Read All
    public List<Tagihan> getAllTagihan() {
        List<Tagihan> tagihanList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tagihan ORDER BY bulan DESC";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Tagihan tagihan = new Tagihan(
                    rs.getInt("id"),
                    rs.getInt("id_penghuni"),
                    rs.getString("bulan"),
                    rs.getInt("jumlah"),
                    rs.getString("status")
                );
                tagihanList.add(tagihan);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data tagihan: " + e.getMessage());
        }
        return tagihanList;
    }

    // Read by ID
    public Tagihan getTagihanById(int id) {
        try {
            String sql = "SELECT * FROM tagihan WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Tagihan(
                    rs.getInt("id"),
                    rs.getInt("id_penghuni"),
                    rs.getString("bulan"),
                    rs.getInt("jumlah"),
                    rs.getString("status")
                );
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil tagihan: " + e.getMessage());
        }
        return null;
    }

    // Read by Penghuni ID
    public List<Tagihan> getTagihanByPenghuniId(int idPenghuni) {
        List<Tagihan> tagihanList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tagihan WHERE id_penghuni = ? ORDER BY bulan DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idPenghuni);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Tagihan tagihan = new Tagihan(
                    rs.getInt("id"),
                    rs.getInt("id_penghuni"),
                    rs.getString("bulan"),
                    rs.getInt("jumlah"),
                    rs.getString("status")
                );
                tagihanList.add(tagihan);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil tagihan penghuni: " + e.getMessage());
        }
        return tagihanList;
    }

    // Update
    public boolean updateTagihan(Tagihan tagihan) {
        try {
            String sql = "UPDATE tagihan SET id_penghuni = ?, bulan = ?, jumlah = ?, status = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tagihan.getIdPenghuni());
            stmt.setString(2, tagihan.getBulan());
            stmt.setInt(3, tagihan.getJumlah());
            stmt.setString(4, tagihan.getStatus());
            stmt.setInt(5, tagihan.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal update tagihan: " + e.getMessage());
            return false;
        }
    }

    // Delete
    public boolean deleteTagihan(int id) {
        try {
            String sql = "DELETE FROM tagihan WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal hapus tagihan: " + e.getMessage());
            return false;
        }
    }

    // Update status tagihan (untuk konfirmasi pembayaran)
    public boolean updateStatusTagihan(int id, String status) {
        try {
            String sql = "UPDATE tagihan SET status = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal update status tagihan: " + e.getMessage());
            return false;
        }
    }

    // Search tagihan dengan join ke tabel penghuni untuk nama
    public List<Object[]> searchTagihan(String keyword) {
        List<Object[]> results = new ArrayList<>();
        try {
            String sql = """
                SELECT t.*, p.nama as nama_penghuni 
                FROM tagihan t 
                JOIN penghuni p ON t.id_penghuni = p.id 
                WHERE t.bulan LIKE ? OR p.nama LIKE ? OR t.status LIKE ? OR t.jumlah = ?
                ORDER BY t.bulan DESC
                """;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            stmt.setString(3, "%" + keyword + "%");
            
            // Coba parse keyword sebagai angka untuk jumlah
            try {
                int jumlah = Integer.parseInt(keyword);
                stmt.setInt(4, jumlah);
            } catch (NumberFormatException e) {
                stmt.setInt(4, -1); // Nilai yang tidak mungkin
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] row = {
                    new Tagihan(
                        rs.getInt("id"),
                        rs.getInt("id_penghuni"),
                        rs.getString("bulan"),
                        rs.getInt("jumlah"),
                        rs.getString("status")
                    ),
                    rs.getString("nama_penghuni")
                };
                results.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Gagal search tagihan: " + e.getMessage());
        }
        return results;
    }

    // Get tagihan dengan nama penghuni (untuk display)
    public List<Object[]> getTagihanWithPenghuniName() {
        List<Object[]> results = new ArrayList<>();
        try {
            String sql = """
                SELECT t.*, p.nama as nama_penghuni 
                FROM tagihan t 
                JOIN penghuni p ON t.id_penghuni = p.id 
                ORDER BY t.bulan DESC
                """;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] row = {
                    new Tagihan(
                        rs.getInt("id"),
                        rs.getInt("id_penghuni"),
                        rs.getString("bulan"),
                        rs.getInt("jumlah"),
                        rs.getString("status")
                    ),
                    rs.getString("nama_penghuni")
                };
                results.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil tagihan dengan nama: " + e.getMessage());
        }
        return results;
    }
}