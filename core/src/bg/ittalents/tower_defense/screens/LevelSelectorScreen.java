package bg.ittalents.tower_defense.screens;

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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import bg.ittalents.tower_defense.network.INetwork;

public class LevelSelectorScreen extends AbstractGameScreen {

    private Stage stage;
    private Skin skin;
    private Label lblStatus;
    private Texture background;
    private Batch batch;
    private INetwork network;

    public LevelSelectorScreen(Game game, INetwork network) {
        super(game);
        this.network = network;
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(800f, 480f));
        Gdx.input.setInputProcessor(stage);
        background = new Texture(Gdx.files.internal("menus_background.jpg"));
        batch = stage.getBatch();
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
//        BitmapFont bitmapFont = Assets.instance.fonts.defaultFont;

        lblStatus = new Label("", skin);
        lblStatus.setColor(Color.RED);
        lblStatus.setPosition(100, 50);
        lblStatus.setSize(600, 30);
        lblStatus.setAlignment(Align.center);
        stage.addActor(lblStatus);

        TextButton btnPlay = new TextButton("Play", skin);
        btnPlay.setPosition(340, 210);
        btnPlay.setSize(120, 60);
        btnPlay.setColor(255 / 255.0f, 255 / 255.0f, 255 / 255.0f, 150 / 255.0f);
        btnPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getGame().setScreen(new GameScreen(getGame()));
            }
        });
        stage.addActor(btnPlay);

        TextButton btnLogout = new TextButton("Logout", skin);
        btnLogout.setPosition(20, 430);
        btnLogout.setSize(80, 30);
        btnLogout.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getGame().setScreen(new LoginScreen(getGame()));
            }
        });
        stage.addActor(btnLogout);

        TextButton btnEditAccount = new TextButton("Edit Account", skin);
        btnEditAccount.setPosition(640, 430);
        btnEditAccount.setSize(120, 30);
        btnEditAccount.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        stage.addActor(btnEditAccount);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void hide() {
        background.dispose();
        stage.dispose();
        skin.dispose();
    }
}
