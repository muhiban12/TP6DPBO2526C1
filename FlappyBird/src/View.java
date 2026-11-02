import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Kelas View bertindak sebagai kanvas render visual (View/GUI) berbasis JPanel.
 * Bertanggung jawab menggambar seluruh aset latar belakang, burung, pipa, skor, serta layar Game Over.
 */
public class View extends JPanel {
    int width = 360;  // Lebar kanvas
    int height = 640; // Tinggi kanvas

    private Logic logic;     // Referensi ke objek pengendali logika permainan
    private Image bgImage;   // Gambar visual latar belakang game

    /**
     * Konstruktor View untuk mengonfigurasi dimensi kanvas dan mendaftarkan listener keyboard.
     * 
     * @param logic Objek controller Logic
     */
    public View(Logic logic) {
        this.logic = logic;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.cyan);

        // Memuat berkas gambar background dari lokasi aset
        bgImage = new ImageIcon(getClass().getResource("/assets/background.png")).getImage();

        // Mengatur agar panel dapat menerima fokus input tombol keyboard
        setFocusable(true);
        addKeyListener(logic);
    }

    /**
     * Override metode bawaan Swing untuk melakukan penggambaran ulang pada komponen.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g); // Memanggil metode kustom untuk render entitas game
    }

    /**
     * Menggambar seluruh elemen grafis game ke layar kanvas.
     * 
     * @param g Objek konteks grafis Graphics
     */
    public void draw(Graphics g) {
        // 1. Menggambar Latar Belakang (Background Image)
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, width, height, null);
        }

        // 2. Menggambar Karakter Burung (Player)
        Player player = logic.getPlayer();
        if (player != null) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(),
                    player.getWidth(), player.getHeight(), null);
        }

        // 3. Menggambar Seluruh Pipa Rintangan (Pipes)
        ArrayList<Pipe> pipes = logic.getPipes();
        if (pipes != null) {
            for (Pipe pipe : pipes) {
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(),
                        pipe.getWidth(), pipe.getHeight(), null);
            }
        }

        // 4. Menggambar Teks Skor dan Tampilan Overlay Game Over (Spesifikasi Wajib 1, 2, 3, & 4)
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 32));
        
        if (!logic.isGameOver()) {
            // Tampilkan nilai skor aktif di pojok kiri atas (dibagi 2 karena 1 pasangan terdiri dari 2 pipa)
            g.drawString(String.valueOf(logic.getScore() / 2), 10, 35);
        } else {
            // Menggambar lapisan gelap transparan (overlay) saat terjadi Game Over
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, width, height);

            // Menggambar Judul Teks "GAME OVER"
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("GAME OVER", 75, 260);

            // Menggambar Hasil Skor Akhir
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Skor Akhir: " + (logic.getScore() / 2), 120, 310);

            // Menggambar Instruksi Restart Permainan
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Tekan 'R' untuk Restart", 90, 360);
        }
    }
}