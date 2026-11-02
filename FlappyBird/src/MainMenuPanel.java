import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Kelas MainMenuPanel merepresentasikan tampilan menu awal permainan sebelum game dimulai (Spesifikasi Wajib 5).
 * Berisi tombol "Start Game" untuk memulai dan "Exit" untuk keluar dari aplikasi.
 */
public class MainMenuPanel extends JPanel {

    /**
     * Konstruktor untuk membentuk tampilan antarmuka Main Menu.
     * 
     * @param onStart Event listener saat tombol Start Game ditekan
     * @param onExit  Event listener saat tombol Exit ditekan
     */
    public MainMenuPanel(ActionListener onStart, ActionListener onExit) {
        setPreferredSize(new Dimension(360, 640));
        setLayout(new GridBagLayout()); // Menggunakan layout GridBagLayout agar posisi komponen rapi di tengah
        setBackground(new Color(70, 130, 180)); // Warna latar belakang biru steel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Jarak/margin antar komponen
        gbc.gridx = 0;

        // 1. Menggambar Label Judul Game
        JLabel titleLabel = new JLabel("FLAPPY BIRD");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        add(titleLabel, gbc);

        // 2. Menggambar Tombol "Start Game"
        JButton startBtn = new JButton("Start Game");
        startBtn.setFont(new Font("Arial", Font.BOLD, 18));
        startBtn.setPreferredSize(new Dimension(160, 45));
        startBtn.addActionListener(onStart); // Daftarkan aksi mulai permainan
        gbc.gridy = 1;
        add(startBtn, gbc);

        // 3. Menggambar Tombol "Exit"
        JButton exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Arial", Font.BOLD, 18));
        exitBtn.setPreferredSize(new Dimension(160, 45));
        exitBtn.addActionListener(onExit); // Daftarkan aksi keluar aplikasi
        gbc.gridy = 2;
        add(exitBtn, gbc);
    }
}