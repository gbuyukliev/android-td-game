package bg.ittalents.tower_defense.utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GamePreferences {

    public static final String PREFERENCES = "tower-defense.prefs";

    public static final String TAG = GamePreferences.class.getName();

    public static final GamePreferences instance = new GamePreferences();

    private boolean sound;
    private boolean music;
    private float volSound;
    private float volMusic;
    private boolean showFpsCounter;
    private Map<String, Integer> levelScores;

    private Preferences prefs;

    // singleton: prevent instantiation from other classes
    private GamePreferences () {
        levelScores = new HashMap<>();
        prefs = Gdx.app.getPreferences(PREFERENCES);
    }

    public void load () {
        sound = prefs.getBoolean("sound", true);
        music = prefs.getBoolean("music", true);
        volSound = MathUtils.clamp(prefs.getFloat("volSound", 0.1f), 0.0f, 1.0f);
        volMusic = MathUtils.clamp(prefs.getFloat("volMusic", 0.5f), 0.0f, 1.0f);
        showFpsCounter = prefs.getBoolean("showFpsCounter", false);

        int levelCounter = 1;
        while (prefs.contains("" + levelCounter)) {
            levelScores.put("" + levelCounter, prefs.getInteger("" + levelCounter));
            levelCounter++;
        }
    }

    public void save () {
        prefs.putBoolean("sound", sound);
        prefs.putBoolean("music", music);
        prefs.putFloat("volSound", volSound);
        prefs.putFloat("volMusic", volMusic);
        prefs.putBoolean("showFpsCounter", showFpsCounter);
        for (String level : levelScores.keySet()) {
            prefs.putInteger(level, levelScores.get(level));
        }
        prefs.flush();
    }

    public Map<String, Integer> getLevelScores() {
        return Collections.unmodifiableMap(levelScores);
    }

    public void putLevelScore(int level, int score) {
        levelScores.put(""+level, score);
    }

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }

    public float getVolSound() {
        return volSound;
    }

    public void setVolSound(float volSound) {
        this.volSound = volSound;
    }

    public float getVolMusic() {
        return volMusic;
    }

    public void setVolMusic(float volMusic) {
        this.volMusic = volMusic;
    }

    public boolean isShowFpsCounter() {
        return showFpsCounter;
    }

    public void setShowFpsCounter(boolean showFpsCounter) {
        this.showFpsCounter = showFpsCounter;
    }
}