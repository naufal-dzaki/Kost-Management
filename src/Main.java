// Main.java (updated)
import controllers.AuthController;
import controllers.KamarController;
import controllers.PenghuniController;
import controllers.TagihanController;
import DAO.KamarDAO;
import DAO.PenghuniDAO;
import DAO.TagihanDAO;
import models.Penghuni;
import models.Tagihan;
import models.User;
import views.MainView;

import java.util.List;

public class Main {
    private static final MainView mainView = new MainView();

    public static void main(String[] args) {
        AuthController authController = new AuthController();

        while (true) {
            int pilihan = mainView.showMainMenu();

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
            int pilihan = mainView.showPemilikMenu();

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
                    tagihanController.confirmPayment();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void showPenghuniSubMenu(PenghuniController controller) {
        while (true) {
            int pilihan = mainView.showPenghuniSubMenu();

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
        }
    }

    private static void showPenghuniMenu(User user) {
        PenghuniController penghuniController = new PenghuniController();
        TagihanController tagihanController = new TagihanController();
        Penghuni penghuni = new PenghuniDAO().getPenghuniById(user.getId());

        while (true) {
            int pilihan = mainView.showPenghuniMenu(penghuni.getNama());

            switch (pilihan) {
                case 1:
                    mainView.showProfile(penghuni.getNama(), penghuni.getNoKtp(), penghuni.getIdKamar());
                    break;
                case 2:
                    List<Tagihan> tagihanList = new TagihanDAO().getTagihanByPenghuniId(penghuni.getId());
                    mainView.showTagihanPenghuni(tagihanList);
                    break;
                case 3:
                    penghuniController.assignKamarPenghuni(penghuni);
                    break;
                case 4:
                    if (penghuni.getIdKamar() == 0) {
                        mainView.showMessage("\nAnda belum memiliki kamar.");
                        continue;
                    }

                    if (mainView.confirmLeaveRoom()) {
                        PenghuniDAO penghuniDAO = new PenghuniDAO();
                        KamarDAO kamarDAO = new KamarDAO();
                        
                        boolean success = penghuniDAO.updateKamarPenghuni(penghuni.getId(), 0);
                        if (success) {
                            kamarDAO.updateStatusKamar(penghuni.getIdKamar(), "kosong");
                            mainView.showMessage("Anda berhasil keluar dari kamar.");
                            penghuni.setIdKamar(0);
                        } else {
                            mainView.showMessage("Gagal keluar dari kamar.");
                        }
                    } else {
                        mainView.showMessage("Proses dibatalkan.");
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void showKamarSubMenu(KamarController controller) {
        while (true) {
            int pilihan = mainView.showKamarSubMenu();

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
        }
    }

    private static void showTagihanSubMenu(TagihanController controller) {
        while (true) {
            int pilihan = mainView.showTagihanSubMenu();

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
        }
    }
}