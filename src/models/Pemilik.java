package models;

public class Pemilik extends User {
    private String nama;
    private String noHp;
    private String alamatKost;

    public Pemilik() {}

    public Pemilik(int id, String username, String password, String role, String nama, String noHp, String alamatKost) {
        super(id, username, password, role);
        this.nama = nama;
        this.noHp = noHp;
        this.alamatKost = alamatKost;
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }

    public String getAlamatKost() { return alamatKost; }
    public void setAlamatKost(String alamatKost) { this.alamatKost = alamatKost; }
}
