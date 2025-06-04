// controllers/PenghuniController.java
package controllers;

import dao.PenghuniDAO;
import dao.UserDAO;
import dao.KamarDAO;
import models.Penghuni;
import models.User;
import models.Kamar;

import java.util.List;
import java.util.Scanner;

public class PenghuniController {
    private final PenghuniDAO penghuniDAO;
    private final KamarDAO kamarDAO;
    private final Scanner scanner;

    public PenghuniController() {
        penghuniDAO = new PenghuniDAO();
        kamarDAO = new KamarDAO();
        scanner = new Scanner(System.in);
    }

    public void showAllPenghuni() {
        System.out.println("\n=== DAFTAR PENGHUNI ===");
        List<Penghuni> penghuniList = penghuniDAO.getAllPenghuni();
        if (penghuniList.isEmpty()) {
            System.out.println("Tidak ada data penghuni.");
            return;
        }

        System.out.printf("%-5s %-20s %-20s %-15s %-10s\n", 
            "ID", "Nama", "No KTP", "Username", "Kamar");
        for (Penghuni p : penghuniList) {
            String kamar = (p.getIdKamar() != 0) ? "Kmr-" + p.getIdKamar() : "-";
            System.out.printf("%-5d %-20s %-20s %-15s %-10s\n", 
                p.getId(), p.getNama(), p.getNoKtp(), p.getUsername(), kamar);
        }
    }

    public void addPenghuni() {
        System.out.println("\n=== TAMBAH PENGHUNI ===");
        System.out.print("Nama: ");
        String nama = scanner.nextLine().trim();
        System.out.print("No KTP: ");
        String noKtp = scanner.nextLine().trim();
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if (nama.isEmpty() || noKtp.isEmpty() || username.isEmpty() || password.isEmpty()) {
            System.out.println("Semua field harus diisi!");
            return;
        }

        // 1. Pertama, buat user baru di tabel users
        User newUser = new User(0, username, password, "penghuni");
        UserDAO userDAO = new UserDAO();
        User registeredUser = userDAO.register(newUser);
        
        if (registeredUser == null) {
            System.out.println("Gagal membuat akun penghuni. Username mungkin sudah digunakan.");
            return;
        }

        // 2. Setelah user berhasil dibuat, buat data penghuni
        Penghuni newPenghuni = new Penghuni(
            registeredUser.getId(), 
            username, 
            password, 
            "penghuni", 
            nama, 
            noKtp, 
            0
        );
        
        // 3. Validasi no KTP unik
        if (penghuniDAO.isNoKtpExists(noKtp)) {
            System.out.println("No KTP sudah terdaftar!");
            // Hapus user yang sudah dibuat jika no KTP duplikat
            userDAO.deleteUser(registeredUser.getId());
            return;
        }

        boolean success = penghuniDAO.insertPenghuni(newPenghuni);
        System.out.println(success ? "Penghuni berhasil ditambahkan!" : "Gagal menambahkan penghuni.");
    }

    public void editPenghuni() {
        showAllPenghuni();
        System.out.print("\nMasukkan ID penghuni yang akan diedit: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        Penghuni penghuni = penghuniDAO.getPenghuniById(id);
        if (penghuni == null) {
            System.out.println("Penghuni tidak ditemukan!");
            return;
        }

        System.out.println("\nData saat ini:");
        System.out.println("Nama: " + penghuni.getNama());
        System.out.println("No KTP: " + penghuni.getNoKtp());
        System.out.println("Kamar: " + (penghuni.getIdKamar() != 0 ? penghuni.getIdKamar() : "-"));

        System.out.print("\nNama baru (kosongkan jika tidak ingin mengubah): ");
        String nama = scanner.nextLine().trim();
        if (!nama.isEmpty()) {
            penghuni.setNama(nama);
        }

        System.out.print("No KTP baru (kosongkan jika tidak ingin mengubah): ");
        String noKtp = scanner.nextLine().trim();
        if (!noKtp.isEmpty()) {
            penghuni.setNoKtp(noKtp);
        }

        System.out.print("ID Kamar baru (0 untuk kosongkan kamar): ");
        String idKamarStr = scanner.nextLine().trim();
        if (!idKamarStr.isEmpty()) {
            int idKamar = Integer.parseInt(idKamarStr);
            penghuni.setIdKamar(idKamar);
        }

        boolean success = penghuniDAO.updatePenghuni(penghuni);
        System.out.println(success ? "Penghuni berhasil diupdate!" : "Gagal mengupdate penghuni.");
    }

    public void deletePenghuni() {
        showAllPenghuni();
        System.out.print("\nMasukkan ID penghuni yang akan dihapus: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Yakin ingin menghapus penghuni ini? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (!confirm.equals("y")) {
            System.out.println("Penghapusan dibatalkan.");
            return;
        }

        boolean success = penghuniDAO.deletePenghuni(id);
        System.out.println(success ? "Penghuni berhasil dihapus!" : "Gagal menghapus penghuni.");
    }

    public void searchPenghuni() {
        System.out.print("\nMasukkan kata kunci pencarian (nama/no KTP/username): ");
        String keyword = scanner.nextLine().trim();

        List<Penghuni> results = penghuniDAO.searchPenghuni(keyword);
        if (results.isEmpty()) {
            System.out.println("Tidak ditemukan penghuni dengan kata kunci '" + keyword + "'");
            return;
        }

        System.out.println("\nHasil pencarian:");
        System.out.printf("%-5s %-20s %-20s %-15s %-10s\n", 
            "ID", "Nama", "No KTP", "Username", "Kamar");
        for (Penghuni p : results) {
            String kamar = (p.getIdKamar() != 0) ? "Kmr-" + p.getIdKamar() : "-";
            System.out.printf("%-5d %-20s %-20s %-15s %-10s\n", 
                p.getId(), p.getNama(), p.getNoKtp(), p.getUsername(), kamar);
        }
    }

    public void assignKamar() {
        showAllPenghuni();
        System.out.print("\nMasukkan ID penghuni: ");
        int idPenghuni = Integer.parseInt(scanner.nextLine().trim());

        Penghuni penghuni = penghuniDAO.getPenghuniById(idPenghuni);
        if (penghuni == null) {
            System.out.println("Penghuni tidak ditemukan!");
            return;
        }

        System.out.println("\n=== KAMAR TERSEDIA ===");
        List<Kamar> availableRooms = kamarDAO.getAvailableKamar();
        if (availableRooms.isEmpty()) {
            System.out.println("Tidak ada kamar yang tersedia.");
            return;
        }

        System.out.printf("%-5s %-10s %-10s\n", "ID", "Nomor", "Harga");
        for (Kamar k : availableRooms) {
            System.out.printf("%-5d %-10s %-10d\n", k.getId(), k.getNomorKamar(), k.getHarga());
        }

        System.out.print("\nMasukkan ID kamar yang akan ditempati (0 untuk batal): ");
        int idKamar = Integer.parseInt(scanner.nextLine().trim());
        if (idKamar == 0) {
            System.out.println("Proses dibatalkan.");
            return;
        }

        // Update kamar penghuni
        boolean success = penghuniDAO.updateKamarPenghuni(idPenghuni, idKamar);
        if (success) {
            // Update status kamar
            kamarDAO.updateStatusKamar(idKamar, "terisi");
            System.out.println("Penghuni berhasil ditempatkan di kamar!");
        } else {
            System.out.println("Gagal menempatkan penghuni di kamar.");
        }
    }
}