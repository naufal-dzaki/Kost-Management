// controllers/AuthController.java (updated)
package controllers;

import models.Pemilik;
import models.Penghuni;
import models.User;
import DAO.PemilikDAO;
import DAO.PenghuniDAO;
import DAO.UserDAO;
import views.AuthView;

public class AuthController {
    private final UserDAO userDAO;
    private final AuthView authView;

    public AuthController() {
        userDAO = new UserDAO();
        authView = new AuthView();
    }

    public User login() {
        String[] credentials = authView.getLoginCredentials();
        String username = credentials[0];
        String password = credentials[1];

        if (username.isEmpty() || password.isEmpty()) {
            authView.showLoginError();
            return null;
        }

        User user = userDAO.login(username, password);

        if (user != null) {
            authView.showLoginSuccess(user.getRole());
        } else {
            authView.showLoginFailed();
        }

        return user;
    }

    public void register() {
        String[] registerData = authView.getRegisterData();
        String username = registerData[0];
        String password = registerData[1];
        String role = registerData[2];

        if (username.isEmpty()) {
            authView.showRegisterError("Username tidak boleh kosong!");
            return;
        }

        if (password.isEmpty()) {
            authView.showRegisterError("Password tidak boleh kosong!");
            return;
        }

        if (!role.equals("penghuni") && !role.equals("pemilik")) {
            authView.showRegisterError("Role tidak valid. Harus 'penghuni' atau 'pemilik'.");
            return;
        }

        User newUser = new User(0, username, password, role);
        User registeredUser = userDAO.register(newUser);

        if (registeredUser != null) {
            if (role.equals("penghuni")) {
                registerPenghuni(registeredUser);
            } else if (role.equals("pemilik")) {
                registerPemilik(registeredUser);
            }
            authView.showRegisterSuccess(role);
        } else {
            authView.showRegisterError("Registrasi gagal. Username mungkin sudah digunakan.");
        }
    }

    private void registerPenghuni(User user) {
        String[] penghuniData = authView.getPenghuniData();
        String nama = penghuniData[0];
        String noKtp = penghuniData[1];

        Penghuni penghuni = new Penghuni(
            user.getId(),
            user.getUsername(), user.getPassword(), user.getRole(),
            nama, noKtp, 0
        );

        boolean success = new PenghuniDAO().insertPenghuni(penghuni);
        if (!success) {
            authView.showRegisterError("Gagal simpan data penghuni.");
        }
    }

    private void registerPemilik(User user) {
        String[] pemilikData = authView.getPemilikData();
        String nama = pemilikData[0];
        String noHp = pemilikData[1];

        Pemilik pemilik = new Pemilik(
            user.getId(),
            user.getUsername(), user.getPassword(), user.getRole(),
            nama, noHp
        );

        boolean success = new PemilikDAO().insertPemilik(pemilik);
        if (!success) {
            authView.showRegisterError("Gagal simpan data pemilik.");
        }
    }
}