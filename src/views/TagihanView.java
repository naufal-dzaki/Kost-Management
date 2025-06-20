package views;

import java.util.List;
import java.util.Scanner;
import models.Tagihan;
import utils.TableFormatter;

public class TagihanView {
    private final Scanner scanner;

    public TagihanView() {
        scanner = new Scanner(System.in);
    }

    public void showAllTagihan(List<Object[]> results) {
        System.out.println("\n=== DAFTAR TAGIHAN ===");
        if (results.isEmpty()) {
            System.out.println("Tidak ada data tagihan.");
            return;
        }

        String[] headers = {"ID", "Nama Penghuni", "Bulan", "Jumlah", "Status"};
        String[][] data = results.stream()
            .map(row -> {
                Tagihan t = (Tagihan) row[0];
                String nama = (String) row[1];
                return new String[]{
                    String.valueOf(t.getId()),
                    nama,
                    t.getBulan(),
                    "Rp" + String.format("%,d", t.getJumlah()),
                    t.getStatus()
                };
            })
            .toArray(String[][]::new);
        
        System.out.println(TableFormatter.formatTable(headers, data));
    }

    public String[] getTagihanData() {
        System.out.println("\n=== TAMBAH TAGIHAN ===");
        System.out.print("ID Penghuni: ");
        String idPenghuni = scanner.nextLine().trim();
        System.out.print("Bulan (Format: YYYY-MM): ");
        String bulan = scanner.nextLine().trim();
        System.out.print("Jumlah: ");
        String jumlah = scanner.nextLine().trim();
        return new String[]{idPenghuni, bulan, jumlah};
    }

    public int getTagihanId(String action) {
        System.out.print("\nMasukkan ID tagihan yang akan " + action + ": ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public String[] getTagihanUpdateData(Tagihan tagihan) {
        System.out.println("\nData saat ini:");
        System.out.println("ID Penghuni: " + tagihan.getIdPenghuni());
        System.out.println("Bulan: " + tagihan.getBulan());
        System.out.println("Jumlah: " + tagihan.getJumlah());
        System.out.println("Status: " + tagihan.getStatus());

        System.out.print("\nBulan baru (kosongkan jika tidak ingin mengubah): ");
        String bulan = scanner.nextLine().trim();
        System.out.print("Jumlah baru (kosongkan jika tidak ingin mengubah): ");
        String jumlahStr = scanner.nextLine().trim();
        return new String[]{bulan, jumlahStr};
    }

    public boolean confirmAction(String action) {
        System.out.print("Yakin ingin " + action + " tagihan ini? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        return confirm.equals("y");
    }

    public String getSearchKeyword() {
        System.out.print("\nMasukkan kata kunci pencarian (bulan/nama/status/jumlah): ");
        return scanner.nextLine().trim();
    }

    public void showActionSuccess(boolean success, String action) {
        System.out.println(success ? "Tagihan berhasil di" + action + "!" : "Gagal " + action + " tagihan.");
    }

    public void showUnpaidBills(List<Object[]> unpaidBills) {
        System.out.println("\n=== KONFIRMASI PEMBAYARAN ===");
        if (unpaidBills.isEmpty()) {
            System.out.println("Tidak ada tagihan yang belum lunas.");
            return;
        }

        String[] headers = {"ID", "Nama Penghuni", "Bulan", "Jumlah"};
        String[][] data = unpaidBills.stream()
            .map(row -> {
                Tagihan t = (Tagihan) row[0];
                String nama = (String) row[1];
                return new String[]{
                    String.valueOf(t.getId()),
                    nama,
                    t.getBulan(),
                    "Rp" + String.format("%,d", t.getJumlah())
                };
            })
            .toArray(String[][]::new);
        
        System.out.println(TableFormatter.formatTable(headers, data));
    }

    public boolean confirmPayment() {
        System.out.print("Konfirmasi pembayaran sudah lunas? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        return confirm.equals("y");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}