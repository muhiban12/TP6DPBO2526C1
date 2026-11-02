import javax.sound.sampled.*;
import java.io.InputStream;

/**
 * Kelas SoundEffect mengelola pemutaran audio digital (.wav) dalam game.
 * Menggunakan sistem pemutaran asynchronous (Opsi 2) agar efek suara (SFX) 
 * dapat diputar secara bersamaan (overlapping) saat tombol ditekan berturut-turut.
 */
public class SoundEffect {
    private String filename;
    private Clip bgmClip; // Penampung khusus untuk audio yang membutuhkan penghentian/looping (BGM) 

    /**
     * Konstruktor SoundEffect untuk menyimpan lokasi berkas audio.
     * 
     * @param filepath Nama berkas suara di dalam direktori assets (contoh: "jump.wav")
     */
    public SoundEffect(String filepath) {
        this.filename = filepath;
    }

    /**
     * Memutar efek suara pendek (SFX) dalam thread terpisah.
     * Metode ini memungkinkan audio baru diputar tanpa memotong audio sebelumnya yang belum selesai.
     */
    public void play() {
        new Thread(() -> {
            try {
                // Memuat stream audio dari folder assets di classpath
                InputStream is = getClass().getResourceAsStream("/assets/" + filename);
                if (is != null) {
                    AudioInputStream ais = AudioSystem.getAudioInputStream(is);
                    Clip sfxClip = AudioSystem.getClip();
                    sfxClip.open(ais);
                    sfxClip.start();

                    // Listener untuk menutup resource Clip secara otomatis saat suara selesai diputar
                    sfxClip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            sfxClip.close();
                        }
                    });
                }
            } catch (Exception e) {
                System.err.println("Gagal memutar SFX: " + filename);
            }
        }).start();
    }

    /**
     * Memutar audio secara berulang tanpa henti (khusus musik latar / BGM).
     */
    public void loop() {
        try {
            if (bgmClip != null && bgmClip.isRunning()) {
                bgmClip.stop();
            }
            InputStream is = getClass().getResourceAsStream("/assets/" + filename);
            if (is != null) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(is);
                bgmClip = AudioSystem.getClip();
                bgmClip.open(ais);
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception e) {
            System.err.println("Gagal memutar BGM: " + filename);
        }
    }

    /**
     * Menghentikan pemutaran BGM yang sedang berjalan.
     */
    public void stop() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
            bgmClip.close();
        }
    }
}