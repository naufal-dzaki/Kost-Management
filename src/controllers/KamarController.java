// controllers/KamarController.java (updated)
package controllers;

import DAO.KamarDAO;
import models.Kamar;
import views.KamarView;

import java.util.List;

public class KamarController {
    private final KamarDAO kamarDAO;
    private final KamarView kamarView;

    public KamarController() {
        kamarDAO = new KamarDAO();
        kamarView = new KamarView();
    }

    public void showAllKamar() {
        List<Kamar> kamarList = kamarDAO.getAllKamar();
        kamarView.showAllKamar(kamarList);
    }

    public void addKamar() {
        String[] kamarData = kamarView.getKamarData();
        String nomor = kamarData[0];
        int harga = Integer.parseInt(kamarData[1]);

        if (nomor.isEmpty()) {
            kamarView.showMessage("Nomor kamar tidak boleh kosong!");
            return;
        }

        Kamar newKamar = new Kamar(0, nomor, harga, "kosong");
        boolean success = kamarDAO.insertKamar(newKamar);
        kamarView.showActionSuccess(success, "tambahkan");
    }

    public void editKamar() {
        showAllKamar();
        int id = kamarView.getKamarId("diedit");

        Kamar kamar = kamarDAO.getKamarById(id);
        if (kamar == null) {
            kamarView.showMessage("Kamar tidak ditemukan!");
            return;
        }

        String[] updateData = kamarView.getKamarUpdateData(kamar);
        String nomor = updateData[0];
        String hargaStr = updateData[1];

        if (!nomor.isEmpty()) {
            kamar.setNomorKamar(nomor);
        }

        if (!hargaStr.isEmpty()) {
            kamar.setHarga(Integer.parseInt(hargaStr));
        }

        boolean success = kamarDAO.updateKamar(kamar);
        kamarView.showActionSuccess(success, "update");
    }

    public void deleteKamar() {
        showAllKamar();
        int id = kamarView.getKamarId("dihapus");

        if (!kamarView.confirmAction("menghapus")) {
            kamarView.showMessage("Penghapusan dibatalkan.");
            return;
        }

        boolean success = kamarDAO.deleteKamar(id);
        kamarView.showActionSuccess(success, "hapus");
    }

    public void searchKamar() {
        String keyword = kamarView.getSearchKeyword();
        List<Kamar> results = kamarDAO.searchKamar(keyword);
        kamarView.showAllKamar(results);
    }
}