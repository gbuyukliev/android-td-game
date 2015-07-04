package bg.ittalents.tower_defense.screens.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class LevelSelectorWindow extends Window {

    public static final float PADDING = 10f;
    public static final float BUTTON_WIDTH = 100f;
    public static final float IMAGE_WIDTH = 160f;
    public static final float IMAGE_HEIGHT = 90f;
    public static final float WINDOW_TRANSPARENCY = 0.7f;
    public static final String LEVELS_PATH = "levels/level";
    public static final String BLACK_WHITE = "_bw";
    public static final String LEVELS_PNG_EXTENSION = ".png";
    public static final int LEVELS_PER_ROW = 4;

    private Array<Label> score;
    private INetworkScreenListener networkScreen;

    public LevelSelectorWindow(Skin skin, INetworkScreenListener networkScreen) {
        super("Pick a level", skin);
        this.networkScreen = networkScreen;
        build();
    }

    public void build() {
        this.setColor(1, 1, 1, WINDOW_TRANSPARENCY);

        int levelCounter = 1;
        while (doesLevelExist(levelCounter)) {
            addButton(levelCounter);

            if (levelCounter % LEVELS_PER_ROW == 0) {
                this.row();
            }

            levelCounter++;
        }
    }

    private boolean doesLevelExist(int level) {
        return Gdx.files.internal(LEVELS_PATH + level + LEVELS_PNG_EXTENSION).exists();
    }

    private void addButton(final int level) {
        Table table = new Table();
        Texture up = new Texture(Gdx.files.internal(LEVELS_PATH + level + LEVELS_PNG_EXTENSION));
        Texture down = new Texture(Gdx.files.internal(LEVELS_PATH + level + BLACK_WHITE + LEVELS_PNG_EXTENSION));

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new SpriteDrawable(new Sprite(up));
        style.imageDown = new SpriteDrawable(new Sprite(down));
        style.imageDisabled = new SpriteDrawable(new Sprite(down));

        ImageButton levelSelection = new ImageButton(style);

        levelSelection.addListener(new ChangeListener() {
                                       @Override
                                       public void changed(ChangeEvent event, Actor actor) {
                                           networkScreen.play(level);
                                       }
        });
        table.add(levelSelection).width(IMAGE_WIDTH).height(IMAGE_HEIGHT).pad(PADDING);
//        table.row();
//        table.add(new Label("Test", getSkin())).pad(PADDING);
        this.add(table);
    }
}