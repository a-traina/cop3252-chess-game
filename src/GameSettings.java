import java.awt.Color;

public class GameSettings {
    private Color lightBoardColor;
    private Color darkBoardColor;
    private boolean toggleTimer;
    private boolean toggleEvalBar;
    private boolean toggleSoundFX;
    private boolean toggleMusic;

    public GameSettings() {
        lightBoardColor = new Color(236, 214, 177);
        darkBoardColor = new Color(185, 134, 99);
        toggleTimer = true;
        toggleEvalBar = true;
        toggleSoundFX = true;
        toggleMusic = true;
    }

    public void setLightBoardColor(Color light) {
        lightBoardColor = light;
    }

    public void setDarkBoardColor(Color dark) {
        darkBoardColor = dark;
    }

    public void setToggleTimer(boolean flag) {
        toggleTimer = flag;
    }

    public void setToggleEvalBar(boolean flag) {
        toggleEvalBar = flag;
    }

    public void setToggleSoundFX(boolean flag) {
        toggleSoundFX = flag;
    }

    public void setToggleMusic(boolean flag) {
        toggleMusic = flag;
    }

    public Color getLightBoardColor() {
        return lightBoardColor;
    }

    public Color getDarkBoardColor() {
        return darkBoardColor;
    }

    public boolean getToggleTimer() {
        return toggleTimer;
    }

    public boolean getToggleEvalBar() {
        return toggleEvalBar;
    }

    public boolean getToggleSoundFX() {
        return toggleSoundFX;
    }

    public boolean getToggleMusic() {
        return toggleMusic;
    }
}

