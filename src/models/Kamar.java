package models;

public class Kamar {
    private int id;
    private String nomorKamar;
    private int harga;
    private String status; // kosong / terisi

    public Kamar() {}

    public Kamar(int id, String nomorKamar, int harga, String status) {
        this.id = id;
        this.nomorKamar = nomorKamar;
        this.harga = harga;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomorKamar() { return nomorKamar; }
    public void setNomorKamar(String nomorKamar) { this.nomorKamar = nomorKamar; }

    public int getHarga() { return harga; }
    public void setHarga(int harga) { this.harga = harga; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
