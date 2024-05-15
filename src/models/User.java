package models;

public class User {
    private int id;
    private String nama;
    private String alamat;
    private String nomorTelepon;
    private String nomorSim;

    // Constructor
    public User() {}

    public User(String nama, String alamat, String nomorTelepon, String nomorSim) {
        this.nama = nama;
        this.alamat = alamat;
        this.nomorTelepon = nomorTelepon;
        this.nomorSim = nomorSim;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public String getNomorTelepon() { return nomorTelepon; }
    public void setNomorTelepon(String nomorTelepon) { this.nomorTelepon = nomorTelepon; }
    public String getNomorSim() { return nomorSim; }
    public void setNomorSim(String nomorSim) { this.nomorSim = nomorSim; }
}
