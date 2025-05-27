package models;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;

    // Constructor tanpa parameter
    public User() {}

    // Constructor semua atribut
    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Constructor tanpa ID (digunakan saat registrasi)
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "User [ID=" + id + ", Username=" + username + ", Role=" + role + "]";
    }
}