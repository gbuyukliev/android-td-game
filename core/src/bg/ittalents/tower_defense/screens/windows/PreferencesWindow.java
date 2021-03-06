package bg.ittalents.tower_defense.screens.windows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import bg.ittalents.tower_defense.game.ui.Gui;
import bg.ittalents.tower_defense.utils.AudioManager;
import bg.ittalents.tower_defense.utils.GamePreferences;

public class PreferencesWindow extends Window {

    public static final int PADDING = 10;
    public static final float WINDOW_TRANSPARENCY = 0.7f;

    private CheckBox chkSound;
    private CheckBox chkMusic;
    private Slider sldSound;
    private Slider sldMusic;

    public PreferencesWindow(Skin skin, final IParent parent) {
        super("Preferences", skin);
        this.setMovable(false);
        this.center();
        this.align(Align.center);

        this.setColor(1, 1, 1, WINDOW_TRANSPARENCY);
        this.add(buildOptWinAudioSettings()).colspan(2);
        loadSettings();
        this.row();
        TextButton btnSave = new TextButton("Save", skin);
        btnSave.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                saveSettings();
                parent.back();
            }
        });
        this.add(btnSave).pad(PADDING);
        TextButton btnClose = new TextButton("Close", skin);
        btnClose.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gui.isMenu = false;
                parent.back();
            }
        });
        this.add(btnClose).pad(PADDING);
    }

    private Table buildOptWinAudioSettings() {
        Table tbl = new Table();
        // + Title: "Audio"
        tbl.pad(10, 10, 0, 10);
        tbl.add(new Label("Audio", getSkin(), "default-font", Color.ORANGE))
                .colspan(3);
        tbl.row();
        tbl.columnDefaults(0).padRight(10);
        tbl.columnDefaults(1).padRight(10);
        // + Checkbox, "Sound" label, sound volume slider
        chkSound = new CheckBox("", getSkin());
        tbl.add(chkSound);
        tbl.add(new Label("Sound", getSkin()));
        sldSound = new Slider(0.0f, 1.0f, 0.1f, false, getSkin());
        tbl.add(sldSound);
        tbl.row();
        // + Checkbox, "Music" label, music volume slider
        chkMusic = new CheckBox("", getSkin());
        tbl.add(chkMusic);
        tbl.add(new Label("Music", getSkin()));
        sldMusic = new Slider(0.0f, 1.0f, 0.1f, false, getSkin());
        tbl.add(sldMusic);
        tbl.row();

        return tbl;
    }

    private void loadSettings() {
        GamePreferences prefs = GamePreferences.instance;
        prefs.load();
        chkSound.setChecked(prefs.isSound());
        sldSound.setValue(prefs.getVolSound());
        chkMusic.setChecked(prefs.isMusic());
        sldMusic.setValue(prefs.getVolMusic());
//        chkShowFpsCounter.setChecked(prefs.showFpsCounter);
    }

    private void saveSettings() {
        GamePreferences prefs = GamePreferences.instance;
        prefs.setSound(chkSound.isChecked());
        prefs.setVolSound(sldSound.getValue());
        prefs.setMusic(chkMusic.isChecked());
        prefs.setVolMusic(sldMusic.getValue());
        prefs.save();
        AudioManager.instance.onSettingsUpdated();
    }
}
