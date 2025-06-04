// controllers/AuthController.java
package controllers;

import models.Pemilik;
import models.Penghuni;
import models.User;

import java.util.Scanner;

import DAO.PemilikDAO;
import DAO.PenghuniDAO;
import DAO.UserDAO;

public class AuthController {
    private final UserDAO userDAO;
    private final Scanner scanner;

    public AuthController() {
        userDAO = new UserDAO();
        scanner = new Scanner(System.in);
    }

    public User login() {
        System.out.println("=== LOGIN ===");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username dan password tidak boleh kosong!");
            return null;
        }

        User user = userDAO.login(username, password);

        if (user != null) {
            System.out.println("Login berhasil sebagai " + user.getRole());
        } else {
            System.out.println("Login gagal! Username atau password salah.");
        }

        return user;
    }

    public void register() {
        System.out.println("=== REGISTER ===");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        
        if (username.isEmpty()) {
            System.out.println("Username tidak boleh kosong!");
            return;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        
        if (password.isEmpty()) {
            System.out.println("Password tidak boleh kosong!");
            return;
        }

        System.out.print("Role (penghuni/pemilik): ");
        String role = scanner.nextLine().trim().toLowerCase();

        if (!role.equals("penghuni") && !role.equals("pemilik")) {
            System.out.println("Role tidak valid. Harus 'penghuni' atau 'pemilik'.");
            return;
        }

        User newUser = new User(0, username, password, role);
        User registeredUser = userDAO.register(newUser);

        if (registeredUser != null) {
            if (role.equals("penghuni")) {
                registerPenghuni(registeredUser);
            } else if (role.equals("pemilik")) {
                registerPemilik(registeredUser);
            }
        } else {
            System.out.println("Registrasi gagal. Username mungkin sudah digunakan.");
        }
    }

    private void registerPenghuni(User user) {
        System.out.print("Nama lengkap: ");
        String nama = scanner.nextLine().trim();
        
        if (nama.isEmpty()) {
            System.out.println("Nama tidak boleh kosong!");
            return;
        }

        System.out.print("No KTP: ");
        String noKtp = scanner.nextLine().trim();
        
        if (noKtp.isEmpty()) {
            System.out.println("No KTP tidak boleh kosong!");
            return;
        }

        Penghuni penghuni = new Penghuni(
            user.getId(),
            user.getUsername(), user.getPassword(), user.getRole(),
            nama, noKtp, 0  // idKamar = 0 (belum ada kamar)
        );

        boolean success = new PenghuniDAO().insertPenghuni(penghuni);
        System.out.println(success ? "Registrasi penghuni berhasil." : "Gagal simpan data penghuni.");
    }

    private void registerPemilik(User user) {
        System.out.print("Nama lengkap: ");
        String nama = scanner.nextLine().trim();
        
        if (nama.isEmpty()) {
            System.out.println("Nama tidak boleh kosong!");
            return;
        }

        System.out.print("No HP: ");
        String noHp = scanner.nextLine().trim();
        
        if (noHp.isEmpty()) {
            System.out.println("No HP tidak boleh kosong!");
            return;
        }

        Pemilik pemilik = new Pemilik(
            user.getId(),
            user.getUsername(), user.getPassword(), user.getRole(),
            nama, noHp
        );

        boolean success = new PemilikDAO().insertPemilik(pemilik);
        System.out.println(success ? "Registrasi pemilik berhasil." : "Gagal simpan data pemilik.");
    }
}