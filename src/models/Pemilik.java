package models;

public class Pemilik extends User {
    private String nama;
    private String noHp;

    public Pemilik() {}

    public Pemilik(int id, String username, String password, String role, String nama, String noHp) {
        super(id, username, password, role);
        this.nama = nama;
        this.noHp = noHp;
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }
}