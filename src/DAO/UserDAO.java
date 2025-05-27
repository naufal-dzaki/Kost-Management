package DAO;

import config.DatabaseConnection;
import models.User;
import utils.HashUtil;

import java.sql.*;

public class UserDAO {
    private Connection conn;

    public UserDAO() {
        conn = DatabaseConnection.getConnection();
    }

    public User login(String username, String password) {
        try {
            String hashedPassword = HashUtil.hashPassword(password);
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean register(User user) {
        try {
            String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            String hashedPassword = HashUtil.hashPassword(user.getPassword());

            stmt.setString(1, user.getUsername());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, user.getRole());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Gagal registrasi: " + e.getMessage());
        }
        return false;
    }
}
