package models;

public class Tagihan {
    private int id;
    private int idPenghuni;
    private String bulan;
    private int jumlah;
    private String status;

    public Tagihan() {}

    public Tagihan(int id, int idPenghuni, String bulan, int jumlah, String status) {
        this.id = id;
        this.idPenghuni = idPenghuni;
        this.bulan = bulan;
        this.jumlah = jumlah;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdPenghuni() { return idPenghuni; }
    public void setIdPenghuni(int idPenghuni) { this.idPenghuni = idPenghuni; }

    public String getBulan() { return bulan; }
    public void setBulan(String bulan) { this.bulan = bulan; }

    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
