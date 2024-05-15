package models;

public class Car {
    private int id;
    private String merek;
    private String model;
    private String nomorPlat;
    private double tarifSewaPerHari;
    private boolean tersedia;

    // Constructor
    public Car() {}

    public Car(String merek, String model, String nomorPlat, double tarifSewaPerHari, boolean tersedia) {
        this.merek = merek;
        this.model = model;
        this.nomorPlat = nomorPlat;
        this.tarifSewaPerHari = tarifSewaPerHari;
        this.tersedia = tersedia;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMerek() { return merek; }
    public void setMerek(String merek) { this.merek = merek; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getNomorPlat() { return nomorPlat; }
    public void setNomorPlat(String nomorPlat) { this.nomorPlat = nomorPlat; }
    public double getTarifSewaPerHari() { return tarifSewaPerHari; }
    public void setTarifSewaPerHari(double tarifSewaPerHari) { this.tarifSewaPerHari = tarifSewaPerHari; }
    public boolean isTersedia() { return tersedia; }
    public void setTersedia(boolean tersedia) { this.tersedia = tersedia; }
}
