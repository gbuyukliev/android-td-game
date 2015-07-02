package bg.ittalents.tower_defense.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import bg.ittalents.tower_defense.network.Network;
import bg.ittalents.tower_defense.network.UserInfo;
import bg.ittalents.tower_defense.screens.windows.*;

public class LoginScreen extends AbstractGameScreen implements INetworkScreenListener {

    public static final float PADDING = 10f;
    public static final float BUTTON_WIDTH = 100f;
    public static final float SCREEN_WIDTH = 800f;
    public static final float SCREEN_HEIGHT = 480f;
    public static final float WINDOW_TRANSPARENCY = 0.7f;
    public static final float STATUS_MESSAGE_DELAY = 5f;


    private Stage stage;
    private Skin skin;
    private Label lblStatus;
    private Label lblLoggedStatus;
    private Table mainTable;
    private Table loggedTable;
    private Table loggedOutTable;
    private Window registerWindow;
    private Window loginWindow;
    private Table levelSelector;
    private Texture background;
    private Batch batch;
    private UserInfo userInfo;

    public LoginScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        background = new Texture(Gdx.files.internal("menus_background.jpg"));
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        buildStage();
    }

    private void buildStage() {
        stage = new Stage(new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        batch = stage.getBatch();
        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);
        loginWindow = new LoginWindow(skin, this);
        registerWindow = new RegisterWindow(skin, this);
        levelSelector = new bg.ittalents.tower_defense.screens.windows.LevelSelectorTable(skin, this);
        buildStatusTable();
//        mainTable.add(loginWindow).center();

        switchToWindow(SCREEN.LOGIN);
    }

    private void buildStatusTable() {
        buildLoggedOutTable();
        buildLoggedTable();
    }

    private void buildLoggedOutTable() {
        loggedOutTable = new Table();
        loggedOutTable.setFillParent(true);
        loggedOutTable.top();
        loggedOutTable.setColor(1, 1, 1, WINDOW_TRANSPARENCY);
        TextButton btnQuit = new TextButton("Quit", skin);
        btnQuit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        loggedOutTable.add(btnQuit).width(BUTTON_WIDTH).pad(PADDING).left();

        lblStatus = new Label("", skin);
        lblStatus.setColor(Color.RED);
        lblStatus.setAlignment(Align.center);
        loggedOutTable.add(lblStatus).expandX().pad(PADDING);

        TextButton btnOffline = new TextButton("Play offline", skin);
        btnOffline.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                offline();
            }
        });
        loggedOutTable.add(btnOffline).width(BUTTON_WIDTH).pad(PADDING).right();

    }

    private void buildLoggedTable() {
        loggedTable = new Table();
        loggedTable.setFillParent(true);
        loggedTable.top();
        loggedTable.setColor(1, 1, 1, WINDOW_TRANSPARENCY);

        TextButton btnLogout = new TextButton("Logout", skin);
        btnLogout.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchToWindow(SCREEN.LOGIN);
            }
        });
        loggedTable.add(btnLogout).width(BUTTON_WIDTH).pad(PADDING).left();

        lblLoggedStatus = new Label("", skin);
        lblLoggedStatus.setColor(Color.RED);
        lblLoggedStatus.setAlignment(Align.center);
        loggedTable.add(lblLoggedStatus).expandX().pad(PADDING);

        TextButton btnEditAccount = new TextButton("Edit Account", skin);
        btnEditAccount.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchToWindow(SCREEN.ACCOUNT_INFO);
            }
        });
        loggedTable.add(btnEditAccount).width(BUTTON_WIDTH).pad(PADDING).right();
    }

    private void offline() {
//        Json json = new Json();
//        json.setTypeName(null);
//        json.setUsePrototypes(false);
//        json.setIgnoreUnknownFields(true);
//        json.setOutputType(JsonWriter.OutputType.json);
//        LevelData levelData = json.fromJson(LevelData.class, new Offline().getLevelData(1));

//        Gdx.app.debug("JSON", levelData.toString());

//        getGame().setScreen(new LevelSelectorScreen(getGame(), UserInfo.createGuessUser(), new Offline()));
        Network.setOnline(false);

        switchToWindow(SCREEN.LEVEL_SELECTOR);
    }

    @Override
    public void switchToWindow(SCREEN window) {
        mainTable.clearChildren();
        loggedTable.remove();
        loggedOutTable.remove();
        switch (window) {
            case REGISTER :
                mainTable.add(registerWindow);
                stage.addActor(loggedOutTable);
                break;
            case LEVEL_SELECTOR:
                mainTable.add(levelSelector);
                stage.addActor(loggedTable);
                break;
            case ACCOUNT_INFO:
            case LOGIN :
            default:
                mainTable.add(loginWindow);
                stage.addActor(loggedOutTable);
                break;
        }
    }

    @Override
    public void setStatus(String message) {
        setStatusToLabel(message, lblStatus);
        setStatusToLabel(message, lblLoggedStatus);
    }

    public static void setStatusToLabel(String message, final Label label) {
        label.setText(message);
        // clear status message after STATUS_MESSAGE_DELAY seconds
        label.addAction(Actions.sequence(Actions.delay(STATUS_MESSAGE_DELAY), Actions.run(new Runnable() {
            @Override
            public void run() {
                label.setText("");
            }
        })));
    }

    @Override
    public void setPlayerInfo(String userInfoJSON) {

        Json json = new Json();
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setIgnoreUnknownFields(true);
        json.setOutputType(JsonWriter.OutputType.json);
        userInfo = json.fromJson(UserInfo.class, userInfoJSON);

        Gdx.app.debug("JSON", userInfo.toString());
    }

    @Override
    public void play() {
        getGame().setScreen(new GameScreen(getGame()));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        buildStage();
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
