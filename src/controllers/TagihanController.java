// controllers/TagihanController.java
package controllers;

import dao.PenghuniDAO;
import dao.TagihanDAO;
import models.Tagihan;

import java.util.List;
import java.util.Scanner;

public class TagihanController {
    private final TagihanDAO tagihanDAO;
    private final PenghuniDAO penghuniDAO;
    private final Scanner scanner;

    public TagihanController() {
        tagihanDAO = new TagihanDAO();
        penghuniDAO = new PenghuniDAO();
        scanner = new Scanner(System.in);
    }

    public void showAllTagihan() {
        System.out.println("\n=== DAFTAR TAGIHAN ===");
        List<Object[]> results = tagihanDAO.getTagihanWithPenghuniName();
        if (results.isEmpty()) {
            System.out.println("Tidak ada data tagihan.");
            return;
        }

        System.out.printf("%-5s %-20s %-15s %-10s %-15s\n", 
            "ID", "Nama Penghuni", "Bulan", "Jumlah", "Status");
        for (Object[] row : results) {
            Tagihan t = (Tagihan) row[0];
            String nama = (String) row[1];
            System.out.printf("%-5d %-20s %-15s %-10d %-15s\n", 
                t.getId(), nama, t.getBulan(), t.getJumlah(), t.getStatus());
        }
    }

    public void addTagihan() {
        System.out.println("\n=== TAMBAH TAGIHAN ===");
        
        // Tampilkan daftar penghuni
        System.out.println("\nDaftar Penghuni:");
        new PenghuniController().showAllPenghuni();
        
        System.out.print("\nID Penghuni: ");
        int idPenghuni = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Bulan (Format: YYYY-MM): ");
        String bulan = scanner.nextLine().trim();
        System.out.print("Jumlah: ");
        int jumlah = Integer.parseInt(scanner.nextLine().trim());

        if (bulan.isEmpty()) {
            System.out.println("Bulan tidak boleh kosong!");
            return;
        }

        Tagihan newTagihan = new Tagihan(0, idPenghuni, bulan, jumlah, "belum lunas");
        boolean success = tagihanDAO.insertTagihan(newTagihan);
        System.out.println(success ? "Tagihan berhasil ditambahkan!" : "Gagal menambahkan tagihan.");
    }

    public void editTagihan() {
        showAllTagihan();
        System.out.print("\nMasukkan ID tagihan yang akan diedit: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        Tagihan tagihan = tagihanDAO.getTagihanById(id);
        if (tagihan == null) {
            System.out.println("Tagihan tidak ditemukan!");
            return;
        }

        System.out.println("\nData saat ini:");
        System.out.println("ID Penghuni: " + tagihan.getIdPenghuni());
        System.out.println("Bulan: " + tagihan.getBulan());
        System.out.println("Jumlah: " + tagihan.getJumlah());
        System.out.println("Status: " + tagihan.getStatus());

        System.out.print("\nBulan baru (kosongkan jika tidak ingin mengubah): ");
        String bulan = scanner.nextLine().trim();
        if (!bulan.isEmpty()) {
            tagihan.setBulan(bulan);
        }

        System.out.print("Jumlah baru (kosongkan jika tidak ingin mengubah): ");
        String jumlahStr = scanner.nextLine().trim();
        if (!jumlahStr.isEmpty()) {
            tagihan.setJumlah(Integer.parseInt(jumlahStr));
        }

        boolean success = tagihanDAO.updateTagihan(tagihan);
        System.out.println(success ? "Tagihan berhasil diupdate!" : "Gagal mengupdate tagihan.");
    }

    public void deleteTagihan() {
        showAllTagihan();
        System.out.print("\nMasukkan ID tagihan yang akan dihapus: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Yakin ingin menghapus tagihan ini? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (!confirm.equals("y")) {
            System.out.println("Penghapusan dibatalkan.");
            return;
        }

        boolean success = tagihanDAO.deleteTagihan(id);
        System.out.println(success ? "Tagihan berhasil dihapus!" : "Gagal menghapus tagihan.");
    }

    public void searchTagihan() {
        System.out.print("\nMasukkan kata kunci pencarian (bulan/nama/status/jumlah): ");
        String keyword = scanner.nextLine().trim();

        List<Object[]> results = tagihanDAO.searchTagihan(keyword);
        if (results.isEmpty()) {
            System.out.println("Tidak ditemukan tagihan dengan kata kunci '" + keyword + "'");
            return;
        }

        System.out.println("\nHasil pencarian:");
        System.out.printf("%-5s %-20s %-15s %-10s %-15s\n", 
            "ID", "Nama Penghuni", "Bulan", "Jumlah", "Status");
        for (Object[] row : results) {
            Tagihan t = (Tagihan) row[0];
            String nama = (String) row[1];
            System.out.printf("%-5d %-20s %-15s %-10d %-15s\n", 
                t.getId(), nama, t.getBulan(), t.getJumlah(), t.getStatus());
        }
    }

    public void confirmPayment() {
        System.out.println("\n=== KONFIRMASI PEMBAYARAN ===");
        List<Object[]> unpaidBills = tagihanDAO.searchTagihan("belum lunas");
        if (unpaidBills.isEmpty()) {
            System.out.println("Tidak ada tagihan yang belum lunas.");
            return;
        }

        System.out.printf("%-5s %-20s %-15s %-10s\n", 
            "ID", "Nama Penghuni", "Bulan", "Jumlah");
        for (Object[] row : unpaidBills) {
            Tagihan t = (Tagihan) row[0];
            String nama = (String) row[1];
            System.out.printf("%-5d %-20s %-15s %-10d\n", 
                t.getId(), nama, t.getBulan(), t.getJumlah());
        }

        System.out.print("\nMasukkan ID tagihan yang akan dikonfirmasi: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Konfirmasi pembayaran sudah lunas? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (!confirm.equals("y")) {
            System.out.println("Konfirmasi dibatalkan.");
            return;
        }

        boolean success = tagihanDAO.updateStatusTagihan(id, "lunas");
        System.out.println(success ? "Status tagihan berhasil diupdate!" : "Gagal mengupdate status tagihan.");
    }
}