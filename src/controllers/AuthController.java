package controllers;

import models.User;

import java.util.Scanner;

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

        User user = new User(username, password, role);

        if (userDAO.register(user)) {
            System.out.println("Registrasi berhasil!");
        } else {
            System.out.println("Registrasi gagal.");
        }
    }
}
