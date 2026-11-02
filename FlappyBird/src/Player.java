import java.awt.*;

/**
 * Kelas Player merepresentasikan objek burung (karakter utama) dalam game Flappy Bird.
 * Menyimpan data koordinat posisi, ukuran fisik, kecepatan vertikal (velocityY), dan gambar aset.
 */
public class Player {
    private int posX;       // Posisi horizontal (koordinat X) burung di kanvas
    private int posY;       // Posisi vertikal (koordinat Y) burung di kanvas
    private int width;      // Lebar fisik karakter burung
    private int height;     // Tinggi fisik karakter burung
    private Image image;    // Aset gambar visual karakter burung
    private int velocityY;  // Kecepatan pergerakan vertikal (pengaruh gravitasi dan lompatan)

    /**
     * Konstruktor untuk menginisialisasi objek Player baru.
     * 
     * @param posX   Posisi X awal burung
     * @param posY   Posisi Y awal burung
     * @param width  Lebar karakter
     * @param height Tinggi karakter
     * @param image  Objek gambar karakter burung
     */
    public Player(int posX, int posY, int width, int height, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
        this.velocityY = 0; // Kecepatan awal diatur ke 0 saat burung diinstansiasi
    }

    /**
     * Mengambil area batas fisik (bounding box) karakter.
     * Digunakan oleh sistem deteksi tabrakan (collision detection).
     * 
     * @return Rectangle bentuk persegi panjang sesuai ukuran dan posisi burung saat ini
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
    public void setImage(Image image) { this.image = image; }

    public int getVelocityY() { return velocityY; }
    public void setVelocityY(int velocityY) { this.velocityY = velocityY; }
}