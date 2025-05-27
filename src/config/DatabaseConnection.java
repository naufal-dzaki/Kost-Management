package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/kost_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Koneksi ke database berhasil!");
        } catch (SQLException e) {
            System.out.println("❌ Koneksi gagal: " + e.getMessage());
        }
        return conn;
    }

    // Untuk testing koneksi
    public static void main(String[] args) {
        connect();
    }
}
