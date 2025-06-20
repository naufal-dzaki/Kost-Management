package views;

import java.util.List;
import java.util.Scanner;
import models.Penghuni;
import utils.TableFormatter;
import models.Kamar;

public class PenghuniView {
    private final Scanner scanner;

    public PenghuniView() {
        scanner = new Scanner(System.in);
    }

    public void showAllPenghuni(List<Penghuni> penghuniList) {
        System.out.println("\n=== DAFTAR PENGHUNI ===");
        if (penghuniList.isEmpty()) {
            System.out.println("Tidak ada data penghuni.");
            return;
        }

        String[] headers = {"ID", "Nama", "No KTP", "Username", "Kamar"};
        String[][] data = penghuniList.stream()
            .map(p -> new String[]{
                String.valueOf(p.getId()),
                p.getNama(),
                p.getNoKtp(),
                p.getUsername(),
                (p.getIdKamar() != 0) ? "Kmr-" + p.getIdKamar() : "-"
            })
            .toArray(String[][]::new);
        
        System.out.println(TableFormatter.formatTable(headers, data));
    }

    public String[] getPenghuniData() {
        System.out.println("\n=== TAMBAH PENGHUNI ===");
        System.out.print("Nama: ");
        String nama = scanner.nextLine().trim();
        System.out.print("No KTP: ");
        String noKtp = scanner.nextLine().trim();
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        return new String[]{nama, noKtp, username, password};
    }

    public int getPenghuniId(String action) {
        System.out.print("\nMasukkan ID penghuni yang akan " + action + ": ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public String[] getPenghuniUpdateData(Penghuni penghuni) {
        System.out.println("\nData saat ini:");
        System.out.println("Nama: " + penghuni.getNama());
        System.out.println("No KTP: " + penghuni.getNoKtp());
        System.out.println("Kamar: " + (penghuni.getIdKamar() != 0 ? penghuni.getIdKamar() : "-"));

        System.out.print("\nNama baru (kosongkan jika tidak ingin mengubah): ");
        String nama = scanner.nextLine().trim();
        System.out.print("No KTP baru (kosongkan jika tidak ingin mengubah): ");
        String noKtp = scanner.nextLine().trim();
        System.out.print("ID Kamar baru (0 untuk kosongkan kamar): ");
        String idKamarStr = scanner.nextLine().trim();
        return new String[]{nama, noKtp, idKamarStr};
    }

    public boolean confirmAction(String action) {
        System.out.print("Yakin ingin " + action + " penghuni ini? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        return confirm.equals("y");
    }

    public String getSearchKeyword() {
        System.out.print("\nMasukkan kata kunci pencarian (nama/no KTP/username): ");
        return scanner.nextLine().trim();
    }

    public void showActionSuccess(boolean success, String action) {
        System.out.println(success ? "Penghuni berhasil di" + action + "!" : "Gagal " + action + " penghuni.");
    }

    public void showAvailableRooms(List<Kamar> availableRooms) {
        System.out.println("\n=== KAMAR TERSEDIA ===");
        if (availableRooms.isEmpty()) {
            System.out.println("Tidak ada kamar yang tersedia.");
            return;
        }

        String[] headers = {"ID", "Nomor", "Harga"};
        String[][] data = availableRooms.stream()
            .map(k -> new String[]{
                String.valueOf(k.getId()),
                k.getNomorKamar(),
                "Rp" + String.format("%,d", k.getHarga())
            })
            .toArray(String[][]::new);
        
        System.out.println(TableFormatter.formatTable(headers, data));
    }

    public int getRoomAssignmentChoice() {
        System.out.print("\nMasukkan ID kamar yang akan ditempati (0 untuk batal): ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}