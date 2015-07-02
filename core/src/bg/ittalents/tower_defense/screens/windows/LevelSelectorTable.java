package bg.ittalents.tower_defense.screens.windows;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import bg.ittalents.tower_defense.network.INetwork;
import bg.ittalents.tower_defense.network.UserInfo;
import bg.ittalents.tower_defense.screens.windows.INetworkScreenListener;

public class LevelSelectorTable extends Table {

    public static final float PADDING = 10f;
    public static final float BUTTON_WIDTH = 100f;
    public static final float SCREEN_WIDTH = 800f;
    public static final float SCREEN_HEIGHT = 480f;
    public static final float WINDOW_TRANSPARENCY = 0.7f;


    private INetwork network;
//    private UserInfo userInfo;
    private INetworkScreenListener networkScreen;

    public LevelSelectorTable(Skin skin, INetworkScreenListener networkScreen) {
        super(skin);
        this.networkScreen = networkScreen;
        this.network = network;
//        this.userInfo = userInfo;
//        Gdx.app.log("UserInfo", userInfo.toString());

        show();
    }

    public void show() {
        TextButton btnPlay = new TextButton("Play", getSkin());
        btnPlay.setColor(1, 1, 1, WINDOW_TRANSPARENCY);
        btnPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
               networkScreen.play();
            }
        });
        add(btnPlay).width(120).height(60);
    }
}
