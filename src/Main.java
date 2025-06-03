import controllers.AuthController;
import models.User;

public class Main {
    public static void main(String[] args) {
        AuthController authController = new AuthController();

        while (true) {
            System.out.println("\n1. Login\n2. Register\n3. Keluar");
            System.out.print("Pilih menu: ");
            int pilihan = new java.util.Scanner(System.in).nextInt();

            if (pilihan == 1) {
                User user = authController.login();
                if (user != null) {
                    if (user.getRole().equals("penghuni")) {
                        System.out.println("Login berhasil sebagai Penghuni");
                    }
                    break;
                } else {
                    System.out.println("Login gagal, silakan coba lagi.");
                }
            } else if (pilihan == 2) {
                authController.register();
            } else {
                System.out.println("Keluar...");
                break;
            }
        }
    }
}
