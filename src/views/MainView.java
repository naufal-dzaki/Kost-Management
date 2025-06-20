package views;

import models.Tagihan;
import utils.TableFormatter;

import java.util.List;
import java.util.Scanner;

public class MainView {
    private final Scanner scanner;

    public MainView() {
        scanner = new Scanner(System.in);
    }

    public int showMainMenu() {
        System.out.println("\n=== SISTEM MANAJEMEN KOST ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Keluar");
        System.out.print("Pilih menu: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public int showPemilikMenu() {
        System.out.println("\n=== MENU PEMILIK KOST ===");
        System.out.println("1. Kelola Penghuni");
        System.out.println("2. Kelola Kamar");
        System.out.println("3. Kelola Tagihan");
        System.out.println("4. Konfirmasi Pembayaran");
        System.out.println("5. Logout");
        System.out.print("Pilih menu: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public int showPenghuniSubMenu() {
        System.out.println("\n=== KELOLA PENGHUNI ===");
        System.out.println("1. Lihat Semua Penghuni");
        System.out.println("2. Tambah Penghuni");
        System.out.println("3. Edit Penghuni");
        System.out.println("4. Hapus Penghuni");
        System.out.println("5. Cari Penghuni");
        System.out.println("6. Tempatkan di Kamar");
        System.out.println("7. Kembali");
        System.out.print("Pilih menu: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public int showKamarSubMenu() {
        System.out.println("\n=== KELOLA KAMAR ===");
        System.out.println("1. Lihat Semua Kamar");
        System.out.println("2. Tambah Kamar");
        System.out.println("3. Edit Kamar");
        System.out.println("4. Hapus Kamar");
        System.out.println("5. Cari Kamar");
        System.out.println("6. Kembali");
        System.out.print("Pilih menu: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public int showTagihanSubMenu() {
        System.out.println("\n=== KELOLA TAGIHAN ===");
        System.out.println("1. Lihat Semua Tagihan");
        System.out.println("2. Tambah Tagihan");
        System.out.println("3. Edit Tagihan");
        System.out.println("4. Hapus Tagihan");
        System.out.println("5. Cari Tagihan");
        System.out.println("6. Kembali");
        System.out.print("Pilih menu: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public int showPenghuniMenu(String nama) {
        System.out.println("\n=== MENU PENGHUNI ===");
        System.out.println("Halo " + nama + "!");
        System.out.println("1. Lihat Profil");
        System.out.println("2. Lihat Tagihan");
        System.out.println("3. Daftar Kamar");
        System.out.println("4. Keluar Kamar");
        System.out.println("5. Logout");
        System.out.print("Pilih menu: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public void showProfile(String nama, String noKtp, int idKamar) {
        System.out.println("\n=== PROFIL PENGHUNI ===");
        System.out.println("Nama: " + nama);
        System.out.println("No KTP: " + noKtp);
        System.out.println("Kamar: " + (idKamar != 0 ? "Kmr-" + idKamar : "Belum memiliki kamar"));
    }

    public void showTagihanPenghuni(List<Tagihan> tagihanList) {
        System.out.println("\n=== TAGIHAN ANDA ===");
        if (tagihanList.isEmpty()) {
            System.out.println("Tidak ada tagihan untuk Anda.");
            return;
        }

        String[] headers = {"ID", "Bulan", "Jumlah", "Status"};
        String[][] data = tagihanList.stream()
            .map(t -> new String[]{
                String.valueOf(t.getId()),
                t.getBulan(),
                "Rp" + String.format("%,d", t.getJumlah()),
                t.getStatus()
            })
            .toArray(String[][]::new);
        
        System.out.println(TableFormatter.formatTable(headers, data));
    }
    
    public boolean confirmLeaveRoom() {
        System.out.print("\nYakin ingin keluar dari kamar? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        return confirm.equals("y");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}