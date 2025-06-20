
package views;

import java.util.List;
import java.util.Scanner;
import models.Kamar;
import utils.TableFormatter;

public class KamarView {
    private final Scanner scanner;

    public KamarView() {
        scanner = new Scanner(System.in);
    }

    public void showAllKamar(List<Kamar> kamarList) {
        System.out.println("\n=== DAFTAR KAMAR ===");
        if (kamarList.isEmpty()) {
            System.out.println("Tidak ada data kamar.");
            return;
        }

        String[] headers = {"ID", "Nomor", "Harga", "Status"};
        String[][] data = kamarList.stream()
            .map(k -> new String[]{
                String.valueOf(k.getId()),
                k.getNomorKamar(),
                "Rp" + String.format("%,d", k.getHarga()),
                k.getStatus()
            })
            .toArray(String[][]::new);
        
        System.out.println(TableFormatter.formatTable(headers, data));
    }

    public String[] getKamarData() {
        System.out.println("\n=== TAMBAH KAMAR ===");
        System.out.print("Nomor Kamar: ");
        String nomor = scanner.nextLine().trim();
        System.out.print("Harga: ");
        String harga = scanner.nextLine().trim();
        return new String[]{nomor, harga};
    }

    public int getKamarId(String action) {
        System.out.print("\nMasukkan ID kamar yang akan " + action + ": ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public String[] getKamarUpdateData(Kamar kamar) {
        System.out.println("\nData saat ini:");
        System.out.println("Nomor: " + kamar.getNomorKamar());
        System.out.println("Harga: " + kamar.getHarga());
        System.out.println("Status: " + kamar.getStatus());

        System.out.print("\nNomor baru (kosongkan jika tidak ingin mengubah): ");
        String nomor = scanner.nextLine().trim();
        System.out.print("Harga baru (kosongkan jika tidak ingin mengubah): ");
        String hargaStr = scanner.nextLine().trim();
        return new String[]{nomor, hargaStr};
    }

    public boolean confirmAction(String action) {
        System.out.print("Yakin ingin " + action + " kamar ini? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        return confirm.equals("y");
    }

    public String getSearchKeyword() {
        System.out.print("\nMasukkan kata kunci pencarian (nomor/status/harga): ");
        return scanner.nextLine().trim();
    }

    public void showActionSuccess(boolean success, String action) {
        System.out.println(success ? "Kamar berhasil di" + action + "!" : "Gagal " + action + " kamar.");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}