package services;

import db.DBConnection;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    public void registerUser(User user) throws SQLException {
        String query = "INSERT INTO users (nama, alamat, nomor_telepon, nomor_sim) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getNama());
            stmt.setString(2, user.getAlamat());
            stmt.setString(3, user.getNomorTelepon());
            stmt.setString(4, user.getNomorSim());
            stmt.executeUpdate();
        }
    }

    public User getUserById(int id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setNama(rs.getString("nama"));
                    user.setAlamat(rs.getString("alamat"));
                    user.setNomorTelepon(rs.getString("nomor_telepon"));
                    user.setNomorSim(rs.getString("nomor_sim"));
                    return user;
                }
            }
        }
        return null;
    }
}
