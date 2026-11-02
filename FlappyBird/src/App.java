import javax.swing.*;
import java.awt.*;

/**
 * Kelas App berfungsi sebagai titik masuk utama (Main Entry Point) aplikasi Flappy Bird.
 * Menyiapkan window utama (JFrame) dan mengelola navigasi panel menggunakan CardLayout.
 */
public class App {
    public static void main(String[] args) {
        // Inisialisasi jendela JFrame utama aplikasi
        JFrame frame = new JFrame("FlappyBird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null); // Menampilkan jendela di tengah layar monitor
        frame.setResizable(false);            // Mencegah perubahan ukuran jendela oleh pengguna

        // Menggunakan CardLayout untuk mengelola peralihan antarmuka antara Main Menu dan Layar Permainan
        CardLayout cardLayout = new CardLayout();
        JPanel mainContainer = new JPanel(cardLayout);

        // Inisialisasi komponen arsitektur engine game
        Logic logika = new Logic();
        View gameView = new View(logika);
        logika.setView(gameView);

        // Inisialisasi Main Menu beserta aksi navigasi tombolnya (Spesifikasi Wajib 5)
        MainMenuPanel menuPanel = new MainMenuPanel(
            e -> {
                // Aksi saat tombol "Start Game" ditekan: Pindah ke layar game dan jalankan siklus permainan
                cardLayout.show(mainContainer, "GAME");
                gameView.requestFocusInWindow(); // Mengarahkan fokus tombol keyboard ke layar kanvas game
                logika.startGame();
            },
            e -> System.exit(0) // Aksi saat tombol "Exit" ditekan: Menutup aplikasi
        );

        // Memasukkan panel menu dan panel game ke dalam kontainer utama
        mainContainer.add(menuPanel, "MENU");
        mainContainer.add(gameView, "GAME");

        // Menambahkan kontainer utama ke JFrame dan menampilkannya
        frame.add(mainContainer);
        frame.pack();
        frame.setVisible(true);
    }
}