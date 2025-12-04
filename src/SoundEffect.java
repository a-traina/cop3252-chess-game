import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class SoundEffect {
    private Clip soundClip;
    private boolean loaded;

    public SoundEffect(URL url) {
        try(AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url)) {
            soundClip = AudioSystem.getClip();
            soundClip.open(audioInputStream);
            audioInputStream.close();
            loaded = true;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {}
    }

    public void play() {
        if (soundClip != null && loaded) {
            if (soundClip.isRunning()) {
                soundClip.stop();
            }
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
