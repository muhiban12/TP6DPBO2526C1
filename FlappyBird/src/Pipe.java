import java.awt.*;

/**
 * Kelas Pipe merepresentasikan objek rintangan pipa dalam permainan.
 * Setiap pipa bergerak secara horizontal ke arah kiri dan memiliki penanda status dilewati (passed).
 */
public class Pipe {
    private int posX;        // Posisi X pipa di kanvas
    private int posY;        // Posisi Y pipa di kanvas
    private int width;       // Lebar rintangan pipa
    private int height;      // Tinggi rintangan pipa
    private Image image;     // Aset gambar rintangan pipa (pipa atas atau bawah)
    private int velocityX;   // Kecepatan pergerakan pipa ke arah kiri (nilai negatif)
    private boolean passed;  // Penanda apakah pipa ini sudah berhasil dilewati oleh Player (untuk penskoran)

    /**
     * Konstruktor untuk membentuk objek Pipe baru.
     * 
     * @param posX   Posisi X awal pipa
     * @param posY   Posisi Y awal pipa
     * @param width  Lebar pipa
     * @param height Tinggi pipa
     * @param image  Objek gambar rintangan pipa
     */
    public Pipe(int posX, int posY, int width, int height, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
        this.velocityX = -2; // Pipa secara bertahap bergerak ke kiri sebesar 2 piksel per frame
        this.passed = false; // Status awal pipa diatur belum dilewati
    }

    /**
     * Mengambil area batas fisik (bounding box) rintangan pipa.
     * Digunakan untuk mengecek interseksi tabrakan dengan Player.
     * 
     * @return Rectangle persegi panjang dari rintangan pipa
     */
    public Rectangle getBounds() {
        return new Rectangle(posX, posY, width, height);
    }

    // --- Getter dan Setter Atribut ---

    public int getPosX() { return posX; }
    public void setPosX(int posX) { this.posX = posX; }

    public int getPosY() { return posY; }
    public void setPosY(int posY) { this.posY = posY; }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public Image getImage() { return image; }

    public int getVelocityX() { return velocityX; }
    public void setVelocityX(int velocityX) { this.velocityX = velocityX; }

    public boolean isPassed() { return passed; }
    public void setPassed(boolean passed) { this.passed = passed; }
}