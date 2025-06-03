package controllers;

import models.Pemilik;
import models.Penghuni;
import models.User;


import java.util.Scanner;

import dao.PemilikDAO;
import dao.PenghuniDAO;
import dao.UserDAO;

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
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

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
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Role (penghuni/pemilik): ");
        String role = scanner.nextLine();

        if (!role.equals("penghuni") && !role.equals("pemilik")) {
            System.out.println("Role tidak valid.");
            return;
        }

        User newUser = new User(0, username, password, role);
        User registeredUser = userDAO.register(newUser);

        if (registeredUser != null) {
            if (role.equals("penghuni")) {
                System.out.print("Nama: ");
                String nama = scanner.nextLine();
                System.out.print("No KTP: ");
                String noKtp = scanner.nextLine();
                System.out.print("ID Kamar (opsional): ");
                String kamarInput = scanner.nextLine();
                int idKamar = kamarInput.isEmpty() ? 0 : Integer.parseInt(kamarInput);

                Penghuni penghuni = new Penghuni(
                    registeredUser.getId(),
                    username, password, role,
                    nama, noKtp, idKamar
                );

                boolean success = new PenghuniDAO().insertPenghuni(penghuni);
                System.out.println(success ? "Registrasi penghuni berhasil." : "Gagal simpan data penghuni.");

            } else if (role.equals("pemilik")) {
                System.out.print("Nama: ");
                String nama = scanner.nextLine();
                System.out.print("No HP: ");
                String noHp = scanner.nextLine();
                System.out.print("Alamat Kost: ");
                String alamatKost = scanner.nextLine();

                Pemilik pemilik = new Pemilik(
                    registeredUser.getId(),
                    username, password, role,
                    nama, noHp, alamatKost
                );

                boolean success = new PemilikDAO().insertPemilik(pemilik);
                System.out.println(success ? "Registrasi pemilik berhasil." : "Gagal simpan data pemilik.");
            }
        } else {
            System.out.println("Registrasi gagal.");
        }
    }
}
