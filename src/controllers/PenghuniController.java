package controllers;

import DAO.PenghuniDAO;
import DAO.UserDAO;
import DAO.KamarDAO;
import models.Penghuni;
import models.User;
import models.Kamar;
import views.PenghuniView;

import java.util.List;

public class PenghuniController {
    private final PenghuniDAO penghuniDAO;
    private final KamarDAO kamarDAO;
    private final PenghuniView penghuniView;

    public PenghuniController() {
        penghuniDAO = new PenghuniDAO();
        kamarDAO = new KamarDAO();
        penghuniView = new PenghuniView();
    }

    public void showAllPenghuni() {
        List<Penghuni> penghuniList = penghuniDAO.getAllPenghuni();
        penghuniView.showAllPenghuni(penghuniList);
    }

    public void addPenghuni() {
        String[] penghuniData = penghuniView.getPenghuniData();
        String nama = penghuniData[0];
        String noKtp = penghuniData[1];
        String username = penghuniData[2];
        String password = penghuniData[3];

        if (nama.isEmpty() || noKtp.isEmpty() || username.isEmpty() || password.isEmpty()) {
            penghuniView.showMessage("Semua field harus diisi!");
            return;
        }

        User newUser = new User(0, username, password, "penghuni");
        UserDAO userDAO = new UserDAO();
        User registeredUser = userDAO.register(newUser);
        
        if (registeredUser == null) {
            penghuniView.showMessage("Gagal membuat akun penghuni. Username mungkin sudah digunakan.");
            return;
        }

        Penghuni newPenghuni = new Penghuni(
            registeredUser.getId(), 
            username, 
            password, 
            "penghuni", 
            nama, 
            noKtp, 
            0
        );
        
        if (penghuniDAO.isNoKtpExists(noKtp)) {
            penghuniView.showMessage("No KTP sudah terdaftar!");
            userDAO.deleteUser(registeredUser.getId());
            return;
        }

        boolean success = penghuniDAO.insertPenghuni(newPenghuni);
        penghuniView.showActionSuccess(success, "tambahkan");
    }

    public void editPenghuni() {
        showAllPenghuni();
        int id = penghuniView.getPenghuniId("diedit");

        Penghuni penghuni = penghuniDAO.getPenghuniById(id);
        if (penghuni == null) {
            penghuniView.showMessage("Penghuni tidak ditemukan!");
            return;
        }

        String[] updateData = penghuniView.getPenghuniUpdateData(penghuni);
        String nama = updateData[0];
        String noKtp = updateData[1];
        String idKamarStr = updateData[2];

        if (!nama.isEmpty()) {
            penghuni.setNama(nama);
        }

        if (!noKtp.isEmpty()) {
            penghuni.setNoKtp(noKtp);
        }

        if (!idKamarStr.isEmpty()) {
            penghuni.setIdKamar(Integer.parseInt(idKamarStr));
        }

        boolean success = penghuniDAO.updatePenghuni(penghuni);
        penghuniView.showActionSuccess(success, "update");
    }

    public void deletePenghuni() {
        showAllPenghuni();
        int id = penghuniView.getPenghuniId("dihapus");

        if (!penghuniView.confirmAction("menghapus")) {
            penghuniView.showMessage("Penghapusan dibatalkan.");
            return;
        }

        boolean success = penghuniDAO.deletePenghuni(id);
        penghuniView.showActionSuccess(success, "hapus");
    }

    public void searchPenghuni() {
        String keyword = penghuniView.getSearchKeyword();
        List<Penghuni> results = penghuniDAO.searchPenghuni(keyword);
        penghuniView.showAllPenghuni(results);
    }

    public void assignKamar() {
        showAllPenghuni();
        int idPenghuni = penghuniView.getPenghuniId("ditempatkan");

        Penghuni penghuni = penghuniDAO.getPenghuniById(idPenghuni);
        if (penghuni == null) {
            penghuniView.showMessage("Penghuni tidak ditemukan!");
            return;
        }

        List<Kamar> availableRooms = kamarDAO.getAvailableKamar();
        penghuniView.showAvailableRooms(availableRooms);

        int idKamar = penghuniView.getRoomAssignmentChoice();
        if (idKamar == 0) {
            penghuniView.showMessage("Proses dibatalkan.");
            return;
        }

        boolean success = penghuniDAO.updateKamarPenghuni(idPenghuni, idKamar);
        if (success) {
            kamarDAO.updateStatusKamar(idKamar, "terisi");
            penghuniView.showMessage("Penghuni berhasil ditempatkan di kamar!");
        } else {
            penghuniView.showMessage("Gagal menempatkan penghuni di kamar.");
        }
    }

    public void assignKamarPenghuni(Penghuni penghuni) {
        int idPenghuni = penghuni.getId();
        List<Kamar> availableRooms = kamarDAO.getAvailableKamar();
        penghuniView.showAvailableRooms(availableRooms);

        int idKamar = penghuniView.getRoomAssignmentChoice();
        if (idKamar == 0) {
            penghuniView.showMessage("Proses dibatalkan.");
            return;
        }

        boolean success = penghuniDAO.updateKamarPenghuni(idPenghuni, idKamar);
        if (success) {
            kamarDAO.updateStatusKamar(idKamar, "terisi");
            penghuni.setIdKamar(idKamar);
            penghuniView.showMessage("Anda berhasil ditempatkan di kamar!");
        } else {
            penghuniView.showMessage("Gagal menempatkan penghuni di kamar.");
        }
    }
}