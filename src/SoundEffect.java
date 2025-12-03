import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SoundEffect {
    private Clip soundClip;
    private boolean loaded;

    public SoundEffect(URL url) {

        new Thread(() -> {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
                soundClip = AudioSystem.getClip();
                soundClip.open(audioInputStream);
                audioInputStream.close();
                loaded = true;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void play() {
        if (soundClip != null && loaded) {
            soundClip.setFramePosition(0);
            soundClip.start();
        }
    }

    public void stop() {
        if (soundClip != null) {
            soundClip.stop();
        }
    }

    public void close() {
        if (soundClip != null && soundClip.isOpen()) {
            soundClip.close();
        }
    }

}
