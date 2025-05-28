package models;

public class Penghuni extends User {
    private String nama;
    private String noKtp;
    private int idKamar;

    public Penghuni() {}

    public Penghuni(int id, String username, String password, String role, String nama, String noKtp, int idKamar) {
        super(id, username, password, role);
        this.nama = nama;
        this.noKtp = noKtp;
        this.idKamar = idKamar;
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNoKtp() { return noKtp; }
    public void setNoKtp(String noKtp) { this.noKtp = noKtp; }

    public int getIdKamar() { return idKamar; }
    public void setIdKamar(int idKamar) { this.idKamar = idKamar; }
}
