package services;

import db.DBConnection;
import models.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarService {
    public void addCar(Car car) throws SQLException {
        String query = "INSERT INTO cars (merek, model, nomor_plat, tarif_sewa_per_hari, tersedia) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, car.getMerek());
            stmt.setString(2, car.getModel());
            stmt.setString(3, car.getNomorPlat());
            stmt.setDouble(4, car.getTarifSewaPerHari());
            stmt.setBoolean(5, car.isTersedia());
            stmt.executeUpdate();
        }
    }

    public List<Car> getAllAvailableCars() throws SQLException {
        String query = "SELECT * FROM cars WHERE tersedia = true";
        List<Car> cars = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt("id"));
                car.setMerek(rs.getString("merek"));
                car.setModel(rs.getString("model"));
                car.setNomorPlat(rs.getString("nomor_plat"));
                car.setTarifSewaPerHari(rs.getDouble("tarif_sewa_per_hari"));
                car.setTersedia(rs.getBoolean("tersedia"));
                cars.add(car);
            }
        }
        return cars;
    }

    public Car getCarById(int id) throws SQLException {
        String query = "SELECT * FROM cars WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Car car = new Car();
                    car.setId(rs.getInt("id"));
                    car.setMerek(rs.getString("merek"));
                    car.setModel(rs.getString("model"));
                    car.setNomorPlat(rs.getString("nomor_plat"));
                    car.setTarifSewaPerHari(rs.getDouble("tarif_sewa_per_hari"));
                    car.setTersedia(rs.getBoolean("tersedia"));
                    return car;
                }
            }
        }
        return null;
    }
}
