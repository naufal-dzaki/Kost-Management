// controllers/KamarController.java
package controllers;

import dao.KamarDAO;
import models.Kamar;

import java.util.List;
import java.util.Scanner;

public class KamarController {
    private final KamarDAO kamarDAO;
    private final Scanner scanner;

    public KamarController() {
        kamarDAO = new KamarDAO();
        scanner = new Scanner(System.in);
    }

    public void showAllKamar() {
        System.out.println("\n=== DAFTAR KAMAR ===");
        List<Kamar> kamarList = kamarDAO.getAllKamar();
        if (kamarList.isEmpty()) {
            System.out.println("Tidak ada data kamar.");
            return;
        }

        System.out.printf("%-5s %-10s %-15s %-10s\n", 
            "ID", "Nomor", "Harga", "Status");
        for (Kamar k : kamarList) {
            System.out.printf("%-5d %-10s %-15d %-10s\n", 
                k.getId(), k.getNomorKamar(), k.getHarga(), k.getStatus());
        }
    }

    public void addKamar() {
        System.out.println("\n=== TAMBAH KAMAR ===");
        System.out.print("Nomor Kamar: ");
        String nomor = scanner.nextLine().trim();
        System.out.print("Harga: ");
        int harga = Integer.parseInt(scanner.nextLine().trim());

        if (nomor.isEmpty()) {
            System.out.println("Nomor kamar tidak boleh kosong!");
            return;
        }

        Kamar newKamar = new Kamar(0, nomor, harga, "kosong");
        boolean success = kamarDAO.insertKamar(newKamar);
        System.out.println(success ? "Kamar berhasil ditambahkan!" : "Gagal menambahkan kamar.");
    }

    public void editKamar() {
        showAllKamar();
        System.out.print("\nMasukkan ID kamar yang akan diedit: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        Kamar kamar = kamarDAO.getKamarById(id);
        if (kamar == null) {
            System.out.println("Kamar tidak ditemukan!");
            return;
        }

        System.out.println("\nData saat ini:");
        System.out.println("Nomor: " + kamar.getNomorKamar());
        System.out.println("Harga: " + kamar.getHarga());
        System.out.println("Status: " + kamar.getStatus());

        System.out.print("\nNomor baru (kosongkan jika tidak ingin mengubah): ");
        String nomor = scanner.nextLine().trim();
        if (!nomor.isEmpty()) {
            kamar.setNomorKamar(nomor);
        }

        System.out.print("Harga baru (kosongkan jika tidak ingin mengubah): ");
        String hargaStr = scanner.nextLine().trim();
        if (!hargaStr.isEmpty()) {
            kamar.setHarga(Integer.parseInt(hargaStr));
        }

        boolean success = kamarDAO.updateKamar(kamar);
        System.out.println(success ? "Kamar berhasil diupdate!" : "Gagal mengupdate kamar.");
    }

    public void deleteKamar() {
        showAllKamar();
        System.out.print("\nMasukkan ID kamar yang akan dihapus: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Yakin ingin menghapus kamar ini? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (!confirm.equals("y")) {
            System.out.println("Penghapusan dibatalkan.");
            return;
        }

        boolean success = kamarDAO.deleteKamar(id);
        System.out.println(success ? "Kamar berhasil dihapus!" : "Gagal menghapus kamar.");
    }

    public void searchKamar() {
        System.out.print("\nMasukkan kata kunci pencarian (nomor/status/harga): ");
        String keyword = scanner.nextLine().trim();

        List<Kamar> results = kamarDAO.searchKamar(keyword);
        if (results.isEmpty()) {
            System.out.println("Tidak ditemukan kamar dengan kata kunci '" + keyword + "'");
            return;
        }

        System.out.println("\nHasil pencarian:");
        System.out.printf("%-5s %-10s %-15s %-10s\n", 
            "ID", "Nomor", "Harga", "Status");
        for (Kamar k : results) {
            System.out.printf("%-5d %-10s %-15d %-10s\n", 
                k.getId(), k.getNomorKamar(), k.getHarga(), k.getStatus());
        }
    }
}