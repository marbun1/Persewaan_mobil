package models;

import java.util.Date;

public class Rental {
    private int id;
    private int userId;
    private int carId;
    private Date tanggalMulai;
    private Date tanggalSelesai;
    private double biayaTotal;

    // Constructor
    public Rental() {}

    public Rental(int userId, int carId, Date tanggalMulai, Date tanggalSelesai, double biayaTotal) {
        this.userId = userId;
        this.carId = carId;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.biayaTotal = biayaTotal;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }
    public Date getTanggalMulai() { return tanggalMulai; }
    public void setTanggalMulai(Date tanggalMulai) { this.tanggalMulai = tanggalMulai; }
    public Date getTanggalSelesai() { return tanggalSelesai; }
    public void setTanggalSelesai(Date tanggalSelesai) { this.tanggalSelesai = tanggalSelesai; }
    public double getBiayaTotal() { return biayaTotal; }
    public void setBiayaTotal(double biayaTotal) { this.biayaTotal = biayaTotal; }
}
