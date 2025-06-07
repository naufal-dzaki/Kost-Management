// views/AuthView.java
package views;

import java.util.Scanner;

public class AuthView {
    private final Scanner scanner;

    public AuthView() {
        scanner = new Scanner(System.in);
    }

    public String[] getLoginCredentials() {
        System.out.println("=== LOGIN ===");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        return new String[]{username, password};
    }

    public void showLoginError() {
        System.out.println("Username dan password tidak boleh kosong!");
    }

    public void showLoginSuccess(String role) {
        System.out.println("Login berhasil sebagai " + role);
    }

    public void showLoginFailed() {
        System.out.println("Login gagal! Username atau password salah.");
    }

    public String[] getRegisterData() {
        System.out.println("=== REGISTER ===");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Role (penghuni/pemilik): ");
        String role = scanner.nextLine().trim().toLowerCase();
        return new String[]{username, password, role};
    }

    public void showRegisterError(String message) {
        System.out.println(message);
    }

    public String[] getPenghuniData() {  // Changed return type from String to String[]
        System.out.print("Nama lengkap: ");
        String nama = scanner.nextLine().trim();
        System.out.print("No KTP: ");
        String noKtp = scanner.nextLine().trim();
        return new String[]{nama, noKtp};
    }

    public String[] getPemilikData() {  // Changed return type from String to String[]
        System.out.print("Nama lengkap: ");
        String nama = scanner.nextLine().trim();
        System.out.print("No HP: ");
        String noHp = scanner.nextLine().trim();
        return new String[]{nama, noHp};
    }

    public void showRegisterSuccess(String role) {
        System.out.println("Registrasi " + role + " berhasil.");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}