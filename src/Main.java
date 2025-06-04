// Main.java (update)
import controllers.AuthController;
import controllers.KamarController;
import controllers.PenghuniController;
import controllers.TagihanController;
import DAO.KamarDAO;
import DAO.PenghuniDAO;
import DAO.TagihanDAO;
import models.Penghuni;
import models.Tagihan;
// import controllers.KamarController;
// import controllers.TagihanController;
import models.User;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        AuthController authController = new AuthController();

        while (true) {
            System.out.println("\n=== SISTEM MANAJEMEN KOST ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");
            
            try {
                int pilihan = Integer.parseInt(scanner.nextLine());

                switch (pilihan) {
                    case 1:
                        handleLogin(authController);
                        break;
                    case 2:
                        authController.register();
                        break;
                    case 3:
                        System.out.println("Keluar dari aplikasi...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid!");
            }
        }
    }

    private static void handleLogin(AuthController authController) {
        User user = authController.login();
        if (user != null) {
            if (user.getRole().equals("pemilik")) {
                showPemilikMenu(user);
            } else {
                showPenghuniMenu(user);
            }
        }
    }

    private static void showPemilikMenu(User user) {
        PenghuniController penghuniController = new PenghuniController();
        KamarController kamarController = new KamarController();
        TagihanController tagihanController = new TagihanController();

        while (true) {
            System.out.println("\n=== MENU PEMILIK KOST ===");
            System.out.println("1. Kelola Penghuni");
            System.out.println("2. Kelola Kamar");
            System.out.println("3. Kelola Tagihan");
            System.out.println("4. Konfirmasi Pembayaran");
            System.out.println("5. Logout");
            System.out.print("Pilih menu: ");

            try {
                int pilihan = Integer.parseInt(scanner.nextLine());

                switch (pilihan) {
                    case 1:
                        showPenghuniSubMenu(penghuniController);
                        break;
                    case 2:
                        showKamarSubMenu(kamarController);
                        break;
                    case 3:
                        showTagihanSubMenu(tagihanController);
                        break;
                    case 4:
                        new TagihanController().confirmPayment();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid!");
            }
        }
    }

    private static void showPenghuniSubMenu(PenghuniController controller) {
        while (true) {
            System.out.println("\n=== KELOLA PENGHUNI ===");
            System.out.println("1. Lihat Semua Penghuni");
            System.out.println("2. Tambah Penghuni");
            System.out.println("3. Edit Penghuni");
            System.out.println("4. Hapus Penghuni");
            System.out.println("5. Cari Penghuni");
            System.out.println("6. Tempatkan di Kamar");
            System.out.println("7. Kembali");
            System.out.print("Pilih menu: ");

            try {
                int pilihan = Integer.parseInt(scanner.nextLine());

                switch (pilihan) {
                    case 1:
                        controller.showAllPenghuni();
                        break;
                    case 2:
                        controller.addPenghuni();
                        break;
                    case 3:
                        controller.editPenghuni();
                        break;
                    case 4:
                        controller.deletePenghuni();
                        break;
                    case 5:
                        controller.searchPenghuni();
                        break;
                    case 6:
                        controller.assignKamar();
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid!");
            }
        }
    }


    private static void showPenghuniMenu(User user) {
        PenghuniController penghuniController = new PenghuniController();
        TagihanController tagihanController = new TagihanController();
        
        // Dapatkan data penghuni
        Penghuni penghuni = new PenghuniDAO().getPenghuniById(user.getId());

        while (true) {
            System.out.println("\n=== MENU PENGHUNI ===");
            System.out.println("Halo " + penghuni.getNama() + "!");
            System.out.println("1. Lihat Profil");
            System.out.println("2. Lihat Tagihan");
            System.out.println("3. Daftar Kamar");
            System.out.println("4. Keluar Kamar");
            System.out.println("5. Logout");
            System.out.print("Pilih menu: ");

            try {
                int pilihan = Integer.parseInt(scanner.nextLine());

                switch (pilihan) {
                    case 1:
                        showProfile(penghuni);
                        break;
                    case 2:
                        showTagihanPenghuni(penghuni.getId());
                        break;
                    case 3:
                        penghuniController.assignKamar();
                        break;
                    case 4:
                        leaveRoom(penghuni);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid!");
            }
        }
    }

    private static void showProfile(Penghuni penghuni) {
        System.out.println("\n=== PROFIL PENGHUNI ===");
        System.out.println("Nama: " + penghuni.getNama());
        System.out.println("No KTP: " + penghuni.getNoKtp());
        System.out.println("Kamar: " + (penghuni.getIdKamar() != 0 ? "Kamar No. " + penghuni.getIdKamar() : "Belum memiliki kamar"));
    }

    private static void showTagihanPenghuni(int idPenghuni) {
        List<Tagihan> tagihanList = new TagihanDAO().getTagihanByPenghuniId(idPenghuni);
        if (tagihanList.isEmpty()) {
            System.out.println("\nTidak ada tagihan untuk Anda.");
            return;
        }

        System.out.println("\n=== TAGIHAN ANDA ===");
        System.out.printf("%-5s %-15s %-10s %-15s\n", 
            "ID", "Bulan", "Jumlah", "Status");
        for (Tagihan t : tagihanList) {
            System.out.printf("%-5d %-15s %-10d %-15s\n", 
                t.getId(), t.getBulan(), t.getJumlah(), t.getStatus());
        }
    }

    private static void leaveRoom(Penghuni penghuni) {
        if (penghuni.getIdKamar() == 0) {
            System.out.println("\nAnda belum memiliki kamar.");
            return;
        }

        System.out.print("\nYakin ingin keluar dari kamar? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (!confirm.equals("y")) {
            System.out.println("Proses dibatalkan.");
            return;
        }

        PenghuniDAO penghuniDAO = new PenghuniDAO();
        KamarDAO kamarDAO = new KamarDAO();
        
        boolean success = penghuniDAO.updateKamarPenghuni(penghuni.getId(), 0);
        if (success) {
            kamarDAO.updateStatusKamar(penghuni.getIdKamar(), "kosong");
            System.out.println("Anda berhasil keluar dari kamar.");
            penghuni.setIdKamar(0); // Update lokal
        } else {
            System.out.println("Gagal keluar dari kamar.");
        }
    }

    private static void showKamarSubMenu(KamarController controller) {
        while (true) {
            System.out.println("\n=== KELOLA KAMAR ===");
            System.out.println("1. Lihat Semua Kamar");
            System.out.println("2. Tambah Kamar");
            System.out.println("3. Edit Kamar");
            System.out.println("4. Hapus Kamar");
            System.out.println("5. Cari Kamar");
            System.out.println("6. Kembali");
            System.out.print("Pilih menu: ");

            try {
                int pilihan = Integer.parseInt(scanner.nextLine());

                switch (pilihan) {
                    case 1:
                        controller.showAllKamar();
                        break;
                    case 2:
                        controller.addKamar();
                        break;
                    case 3:
                        controller.editKamar();
                        break;
                    case 4:
                        controller.deleteKamar();
                        break;
                    case 5:
                        controller.searchKamar();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid!");
            }
        }
    }

    private static void showTagihanSubMenu(TagihanController controller) {
        while (true) {
            System.out.println("\n=== KELOLA TAGIHAN ===");
            System.out.println("1. Lihat Semua Tagihan");
            System.out.println("2. Tambah Tagihan");
            System.out.println("3. Edit Tagihan");
            System.out.println("4. Hapus Tagihan");
            System.out.println("5. Cari Tagihan");
            System.out.println("6. Kembali");
            System.out.print("Pilih menu: ");

            try {
                int pilihan = Integer.parseInt(scanner.nextLine());

                switch (pilihan) {
                    case 1:
                        controller.showAllTagihan();
                        break;
                    case 2:
                        controller.addTagihan();
                        break;
                    case 3:
                        controller.editTagihan();
                        break;
                    case 4:
                        controller.deleteTagihan();
                        break;
                    case 5:
                        controller.searchTagihan();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid!");
            }
        }
    }

    // private static void showTagihanSubMenu(TagihanController controller) {
    //     // Implementasi mirip dengan showPenghuniSubMenu
    // }
}