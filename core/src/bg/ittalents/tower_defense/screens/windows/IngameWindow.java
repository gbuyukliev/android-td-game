package bg.ittalents.tower_defense.screens.windows;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import bg.ittalents.tower_defense.game.ui.Gui;

public class IngameWindow extends Window {
    public static final float WINDOW_TRANSPARENCY = 0.7f;
    public static final float PADDING = 10f;
    public static final float BUTTON_WIDTH = 100f;

    public IngameWindow(Skin skin, final Gui gui) {
        super("", skin);
        this.setMovable(false);
        this.setColor(1, 1, 1, WINDOW_TRANSPARENCY);
        this.center();
        this.align(Align.center);

        TextButton btnOptions = new TextButton("Options", skin);
        btnOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gui.switchToOptions();
            }
        });
        this.add(btnOptions).pad(PADDING).width(BUTTON_WIDTH);

        this.row();

        TextButton btnEnd = new TextButton("End Game", skin);
        btnEnd.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gui.goToLogin();
            }
        });
        this.add(btnEnd).pad(PADDING).width(BUTTON_WIDTH);

        this.row();

        TextButton btnClose = new TextButton("Close", skin);
        btnClose.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                IngameWindow.this.remove();
                gui.unpause();
            }
        });
        this.add(btnClose).pad(PADDING).width(BUTTON_WIDTH);
    }
}
