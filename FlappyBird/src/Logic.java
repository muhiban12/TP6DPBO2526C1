import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Kelas Logic bertindak sebagai pengatur utama (Controller/Engine) logika permainan Flappy Bird.
 * Mengimplementasikan ActionListener untuk game loop dan KeyListener untuk menerima input tombol keyboard.
 */
public class Logic implements ActionListener, KeyListener {
    // Dimensi area permainan (sama dengan ukuran JFrame)
    int frameWidth = 360;
    int frameHeight = 640;

    // Koordinat dan dimensi standar untuk Player dan Pipa
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;

    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    // Pengatur waktu (Timer) dan variabel fisika game
    Timer gameLoop;       // Timer pembentuk FPS game (60 update per detik)
    Timer pipesCooldown;  // Timer pembawa jeda pembentukan pipa baru secara rutin
    int gravity = 1;      // Akselerasi gravitasi yang ditarik ke bawah setiap frame
    int pipeVelocityX = -2;// Kecepatan horizontal pergerakan pipa ke kiri

    // Status permainan dan skor
    private boolean gameOver = false; // Flag penanda kondisi Game Over
    private int score = 0;            // Variabel akumulasi poin pemain

    // Komponen UI, Gambar Aset, dan Entitas Objek
    View view;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;
    Player player;
    ArrayList<Pipe> pipes;

    // Aset Efek Suara (Bonus Spesifikasi Audio)
    SoundEffect jumpSound;
    SoundEffect scoreSound;
    SoundEffect hitSound;

    /**
     * Konstruktor Logic untuk memuat aset, menginstansiasi player, list rintangan, dan mengonfigurasi Timer.
     */
    public Logic() {
        // Memuat gambar visual dari folder assets
        birdImage = new ImageIcon(getClass().getResource("/assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("/assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("/assets/upperPipe.png")).getImage();

        // Inisialisasi objek Player dan koleksi daftar pipa
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<>();

        // Inisialisasi berkas audio SFX
        jumpSound = new SoundEffect("jump.wav");
        scoreSound = new SoundEffect("score.wav");
        hitSound = new SoundEffect("hit.wav");

        // Timer peluncuran pipa otomatis setiap 1,5 detik (1500 milidetik)
        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameOver) {
                    placePipes(); // Memunculkan pasangan pipa baru jika game masih berjalan
                }
            }
        });

        // Timer pembaru logika utama (60 frame per detik)
        gameLoop = new Timer(1000 / 60, this);
    }

    /**
     * Memulai siklus permainan dari awal.
     */
    public void startGame() {
        resetGame();
        pipesCooldown.start();
        gameLoop.start();
    }

    /**
     * Menghitung posisi acak dan menambahkan sepasang pipa (pipa atas & pipa bawah) ke dalam daftar rintangan.
     */
    public void placePipes() {
        // Perhitungan nilai acak posisi Y pipa atas agar celah selalu bervariasi secara dinamis
        int randomPosY = (int) (pipeStartPosY - (pipeHeight / 4) - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight / 4; // Celah jarak vertikal yang dapat dilewati oleh burung

        // Membuat objek Pipa Atas dan memasukkannya ke dalam list
        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        // Membuat objek Pipa Bawah berjarak sejauh openingSpace dan memasukkannya ke dalam list
        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPosY + pipeHeight + openingSpace, pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    /**
     * Memperbarui posisi fisika objek Player, pipa, serta menangani penskoran dan deteksi tabrakan.
     */
    public void move() {
        if (gameOver) return; // Hentikan pembaruan pergerakan jika game sudah tamat

        // 1. Pembaruan Fisika Burung (Pengaruh Gravitasi)
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0)); // Mencegah burung terbang menembus langit-langit atas kanvas

        // 2. Cek Apakah Burung Jatuh Menyentuh Batas Bawah Layar (Spesifikasi Wajib 1)
        if (player.getPosY() + player.getHeight() >= frameHeight) {
            triggerGameOver();
        }

        // 3. Pembaruan Pergerakan Seluruh Pipa dan Pengecekan Interaksi
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipeVelocityX); // Geser pipa ke kiri

            // Tambah skor jika Player berhasil melewati koordinat X pipa (Spesifikasi Wajib 4)
            if (!pipe.isPassed() && player.getPosX() > pipe.getPosX() + pipe.getWidth()) {
                pipe.setPassed(true);
                score += 1;
                scoreSound.play(); // Putar efek suara saat poin bertambah
            }

            // Cek tabrakan antara Player dan pipa (Spesifikasi Wajib 1)
            if (checkCollision(player, pipe)) {
                triggerGameOver();
            }
        }
    }

    /**
     * Memeriksa interseksi interseksional antara objek Player dan objek Pipe.
     */
    private boolean checkCollision(Player a, Pipe b) {
        return a.getBounds().intersects(b.getBounds());
    }

    /**
     * Memicu status Game Over, menghentikan seluruh Timer, dan memutar efek suara benturan.
     */
    private void triggerGameOver() {
        if (!gameOver) {
            gameOver = true;
            hitSound.play();       // Memutar audio kecelakaan
            gameLoop.stop();       // Menghentikan pergerakan frame
            pipesCooldown.stop();  // Menghentikan pemunculan pipa baru
        }
    }

    /**
     * Mereset seluruh parameter posisi Player, mengosongkan pipa, mereset skor, dan memulai kembali siklus game (Spesifikasi Wajib 2).
     */
    public void resetGame() {
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);
        pipes.clear();
        score = 0;
        gameOver = false;
        gameLoop.start();
        pipesCooldown.start();
    }

    /**
     * Listener callback yang dipanggil oleh gameLoop Timer setiap 1/60 detik.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        if (view != null) {
            view.repaint(); // Memicu proses menggambar ulang visual di kelas View
        }
    }

    /**
     * Listener tombol keyboard untuk merespons masukan pemain (Spesifikasi Wajib 2 & Kontrol Permainan).
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Tombol SPASI: Membuat burung melompat ke atas
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver) {
            player.setVelocityY(-10); // Memberikan dorongan kecepatan instan ke atas sebesar -10
            jumpSound.play();         // Putar efek suara lompatan
        }

        // Tombol 'R': Mengulang permainan dari awal ketika Game Over
        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            resetGame();
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    // --- Getter dan Setter ---

    public void setView(View view) { this.view = view; }
    public Player getPlayer() { return player; }
    public ArrayList<Pipe> getPipes() { return pipes; }
    public boolean isGameOver() { return gameOver; }
    public int getScore() { return score; }
}