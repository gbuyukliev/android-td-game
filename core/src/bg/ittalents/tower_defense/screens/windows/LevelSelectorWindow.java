package bg.ittalents.tower_defense.screens.windows;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import bg.ittalents.tower_defense.network.INetwork;
import bg.ittalents.tower_defense.network.UserInfo;
import bg.ittalents.tower_defense.screens.windows.INetworkScreenListener;

public class LevelSelectorWindow extends Window {

    public static final float PADDING = 10f;
    public static final float BUTTON_WIDTH = 100f;
    public static final float IMAGE_WIDTH = 160f;
    public static final float IMAGE_HEIGHT = 90f;
    public static final float WINDOW_TRANSPARENCY = 0.7f;
    public static final String LEVELS_PATH = "levels/level";
    public static final String LEVELS_PNG_EXTENTIONS = ".png";


    private INetworkScreenListener networkScreen;

    public LevelSelectorWindow(Skin skin, INetworkScreenListener networkScreen) {
        super("Pick Level", skin);
        this.networkScreen = networkScreen;
        show();
    }

    public void show() {
        setColor(1, 1, 1, WINDOW_TRANSPARENCY);

        int levelCounter = 1;
        while (isLevelExist(levelCounter)) {
            addButton(levelCounter);
            if (levelCounter % 3 == 0) {
                row();
            }
            levelCounter++;
        }
    }

    private boolean isLevelExist(int level) {
        return Gdx.files.internal(LEVELS_PATH + level + LEVELS_PNG_EXTENTIONS).exists();
    }

    private void addButton(final int level) {
        Image levelSelection = new Image(new Texture(Gdx.files.internal(LEVELS_PATH + level + LEVELS_PNG_EXTENTIONS)));
        levelSelection.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                networkScreen.play(level);
                return true;
            }
        });
        add(levelSelection).width(IMAGE_WIDTH).height(IMAGE_HEIGHT).pad(PADDING);
    }
}
