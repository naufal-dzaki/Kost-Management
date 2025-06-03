package dao;

import config.DatabaseConnection;
import models.Pemilik;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PemilikDAO {
    private final Connection conn = DatabaseConnection.getConnection();

    public boolean insertPemilik(Pemilik pemilik) {
        try {
            String sql = "INSERT INTO pemilik (id, nama, no_hp) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pemilik.getId());
            stmt.setString(2, pemilik.getNama());
            stmt.setString(3, pemilik.getNoHp());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Gagal insert pemilik: " + e.getMessage());
            return false;
        }
    }
}
