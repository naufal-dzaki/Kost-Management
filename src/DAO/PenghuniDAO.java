package dao;

import config.DatabaseConnection;
import models.Penghuni;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PenghuniDAO {
    private final Connection conn = DatabaseConnection.getConnection();

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
}
