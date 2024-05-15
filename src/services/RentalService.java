package services;

import db.DBConnection;
import models.Car;
import models.Rental;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalService {
    public void rentCar(Rental rental) throws SQLException {
        String query = "INSERT INTO rentals (user_id, car_id, tanggal_mulai, tanggal_selesai, biaya_total) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rental.getUserId());
            stmt.setInt(2, rental.getCarId());
            stmt.setDate(3, new java.sql.Date(rental.getTanggalMulai().getTime()));
            stmt.setDate(4, new java.sql.Date(rental.getTanggalSelesai().getTime()));
            stmt.setDouble(5, rental.getBiayaTotal());
            stmt.executeUpdate();
        }

        // Update ketersediaan mobil
        String updateCarQuery = "UPDATE cars SET tersedia = false WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateCarQuery)) {
            stmt.setInt(1, rental.getCarId());
            stmt.executeUpdate();
        }
    }

    public List<Rental> getRentalsByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM rentals WHERE user_id = ?";
        List<Rental> rentals = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Rental rental = new Rental();
                    rental.setId(rs.getInt("id"));
                    rental.setUserId(rs.getInt("user_id"));
                    rental.setCarId(rs.getInt("car_id"));
                    rental.setTanggalMulai(rs.getDate("tanggal_mulai"));
                    rental.setTanggalSelesai(rs.getDate("tanggal_selesai"));
                    rental.setBiayaTotal(rs.getDouble("biaya_total"));
                    rentals.add(rental);
                }
            }
        }
        return rentals;
    }

    public void returnCar(String nomorPlat, int userId) throws SQLException {
        String getCarQuery = "SELECT id FROM cars WHERE nomor_plat = ?";
        String getRentalQuery = "SELECT * FROM rentals WHERE car_id = ? AND user_id = ? AND tanggal_selesai >= CURDATE()";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement getCarStmt = conn.prepareStatement(getCarQuery);
             PreparedStatement getRentalStmt = conn.prepareStatement(getRentalQuery)) {
            // Get car ID
            getCarStmt.setString(1, nomorPlat);
            int carId;
            try (ResultSet carRs = getCarStmt.executeQuery()) {
                if (carRs.next()) {
                    carId = carRs.getInt("id");
                } else {
                    throw new SQLException("Car not found");
                }
            }

            // Get rental
            getRentalStmt.setInt(1, carId);
            getRentalStmt.setInt(2, userId);
            int rentalId;
            try (ResultSet rentalRs = getRentalStmt.executeQuery()) {
                if (rentalRs.next()) {
                    rentalId = rentalRs.getInt("id");
                } else {
                    throw new SQLException("Rental not found");
                }
            }

            // Mark rental as finished and update car availability
            String updateRentalQuery = "UPDATE rentals SET tanggal_selesai = CURDATE() WHERE id = ?";
            String updateCarQuery = "UPDATE cars SET tersedia = true WHERE id = ?";
            try (PreparedStatement updateRentalStmt = conn.prepareStatement(updateRentalQuery);
                 PreparedStatement updateCarStmt = conn.prepareStatement(updateCarQuery)) {
                updateRentalStmt.setInt(1, rentalId);
                updateRentalStmt.executeUpdate();

                updateCarStmt.setInt(1, carId);
                updateCarStmt.executeUpdate();
            }
        }
    }
}
