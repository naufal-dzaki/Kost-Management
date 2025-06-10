// controllers/TagihanController.java (updated)
package controllers;

import DAO.PenghuniDAO;
import DAO.TagihanDAO;
import models.Penghuni;
import models.Tagihan;
import views.TagihanView;
import views.PenghuniView;

import java.util.List;

public class TagihanController {
    private final TagihanDAO tagihanDAO;
    private final PenghuniDAO penghuniDAO;
    private final TagihanView tagihanView;
    private final PenghuniView penghuniView;

    public TagihanController() {
        tagihanDAO = new TagihanDAO();
        penghuniDAO = new PenghuniDAO();
        tagihanView = new TagihanView();
        penghuniView = new PenghuniView();
    }

    public void showAllTagihan() {
        List<Object[]> results = tagihanDAO.getTagihanWithPenghuniName();
        tagihanView.showAllTagihan(results);
    }

    public void addTagihan() {
        List<Penghuni> penghuniList = penghuniDAO.getAllPenghuni();
        penghuniView.showAllPenghuni(penghuniList);
        String[] tagihanData = tagihanView.getTagihanData();
        int idPenghuni = Integer.parseInt(tagihanData[0]);
        String bulan = tagihanData[1];
        int jumlah = Integer.parseInt(tagihanData[2]);

        if (bulan.isEmpty()) {
            tagihanView.showMessage("Bulan tidak boleh kosong!");
            return;
        }

        Tagihan newTagihan = new Tagihan(0, idPenghuni, bulan, jumlah, "belum lunas");
        boolean success = tagihanDAO.insertTagihan(newTagihan);
        tagihanView.showActionSuccess(success, "tambahkan");
    }

    public void editTagihan() {
        showAllTagihan();
        int id = tagihanView.getTagihanId("diedit");

        Tagihan tagihan = tagihanDAO.getTagihanById(id);
        if (tagihan == null) {
            tagihanView.showMessage("Tagihan tidak ditemukan!");
            return;
        }

        String[] updateData = tagihanView.getTagihanUpdateData(tagihan);
        String bulan = updateData[0];
        String jumlahStr = updateData[1];

        if (!bulan.isEmpty()) {
            tagihan.setBulan(bulan);
        }

        if (!jumlahStr.isEmpty()) {
            tagihan.setJumlah(Integer.parseInt(jumlahStr));
        }

        boolean success = tagihanDAO.updateTagihan(tagihan);
        tagihanView.showActionSuccess(success, "update");
    }

    public void deleteTagihan() {
        showAllTagihan();
        int id = tagihanView.getTagihanId("dihapus");

        if (!tagihanView.confirmAction("menghapus")) {
            tagihanView.showMessage("Penghapusan dibatalkan.");
            return;
        }

        boolean success = tagihanDAO.deleteTagihan(id);
        tagihanView.showActionSuccess(success, "hapus");
    }

    public void searchTagihan() {
        String keyword = tagihanView.getSearchKeyword();
        List<Object[]> results = tagihanDAO.searchTagihan(keyword);
        tagihanView.showAllTagihan(results);
    }

// controllers/TagihanController.java
    public void confirmPayment() {
        List<Object[]> unpaidBills = tagihanDAO.searchTagihan("belum lunas");
        tagihanView.showUnpaidBills(unpaidBills);

        if (unpaidBills.isEmpty()) {
            return;
        }

        int id = tagihanView.getTagihanId("dikonfirmasi");
        if (!tagihanView.confirmPayment()) {
            tagihanView.showMessage("Konfirmasi dibatalkan.");
            return;
        }

        boolean success = tagihanDAO.updateStatusTagihan(id, "lunas");
        tagihanView.showMessage(success ? "Status tagihan berhasil diupdate!" : "Gagal mengupdate status tagihan.");
    }
}