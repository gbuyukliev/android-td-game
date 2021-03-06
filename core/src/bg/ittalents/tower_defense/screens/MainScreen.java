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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import bg.ittalents.tower_defense.game.Assets;
import bg.ittalents.tower_defense.network.Network;
import bg.ittalents.tower_defense.network.UserInfo;
import bg.ittalents.tower_defense.screens.windows.*;
import bg.ittalents.tower_defense.utils.AudioManager;

public class MainScreen extends AbstractGameScreen implements INetworkScreenListener, IParent, ILevelSelect {

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
    private Label lblLogged;
    private Table mainTable;
    private Table loggedTable;
    private Table loggedOutTable;
    private Table registerWindow;
    private Table loginWindow;
    private Table optionsWindow;
    private Table editAccountInfo;
    private LevelSelectorWindow levelSelectorWindow;
    private Table topPlayersWindow;
    private TextButton btnTopPlayers;
    private TextButton btnEditAccount;
    private Texture background;
    private Batch batch;

    public MainScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        background = new Texture(Gdx.files.internal("menus_background.png"));
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
        levelSelectorWindow = new LevelSelectorWindow(skin, this);
        topPlayersWindow = new TopPlayersWindow(skin, this);
        optionsWindow = new PreferencesWindow(skin, this);
        editAccountInfo = new EditAccountInfoWindow(skin, this);
        buildStatusTable();
//        mainTable.add(loginWindow).center();

        if (UserInfo.isLogged()) {
            switchToWindow(SCREEN.LEVEL_SELECTOR);
            if(!Network.isOnline()) {
                hideButtons(true);
            }
        } else {
            switchToWindow(SCREEN.LOGIN);
        }
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

        lblLogged= new Label("", skin);
        lblLogged.setColor(Color.GREEN);
        lblLogged.setAlignment(Align.right);
        loggedTable.add(lblLogged).expandX().pad(PADDING);

        TextButton btnOptions = new TextButton("Options", skin);
        btnOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchToWindow(SCREEN.OPTIONS);
            }
        });
        loggedTable.add(btnOptions).width(BUTTON_WIDTH).pad(PADDING).right();

        loggedTable.row();

        btnEditAccount = new TextButton("Edit Account", skin);
        btnEditAccount.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchToWindow(SCREEN.ACCOUNT_INFO);
            }
        });
        loggedTable.add(btnEditAccount).width(BUTTON_WIDTH).pad(PADDING).left();

        lblLoggedStatus = new Label("", skin);
        lblLoggedStatus.setColor(Color.RED);
        lblLogged.setAlignment(Align.center);
        loggedTable.add(lblLoggedStatus).expandX().pad(PADDING);

        btnTopPlayers = new TextButton("Top Players", skin);
        btnTopPlayers.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchToWindow(SCREEN.TOP_PLAYERS);
            }
        });
        loggedTable.add(btnTopPlayers).width(BUTTON_WIDTH).pad(PADDING).right();
    }

    private void offline() {
        Network.setOnline(false);
        UserInfo.logInAsGuest();

        hideButtons(true);

        switchToWindow(SCREEN.LEVEL_SELECTOR);
    }

    private void hideButtons(boolean hide) {
//        btnTopPlayers.setDisabled(true);
        btnTopPlayers.setVisible(!hide);
        btnEditAccount.setVisible(!hide);
    }

    @Override
    public void switchToWindow(SCREEN window) {
        clearStatus();
        mainTable.clearChildren();
        loggedTable.remove();
        loggedOutTable.remove();
        switch (window) {
            case REGISTER :
                mainTable.add(registerWindow);
                stage.addActor(loggedOutTable);
                break;
            case LEVEL_SELECTOR:
                levelSelectorWindow.updateWindow();
                lblLogged.setText("Welcome " + UserInfo.getNickName());
                mainTable.add(levelSelectorWindow);
                stage.addActor(loggedTable);
                break;
            case TOP_PLAYERS:
                mainTable.add(topPlayersWindow);
                stage.addActor(loggedTable);
                break;
            case OPTIONS:
                mainTable.add(optionsWindow);
                stage.addActor(loggedTable);
                break;
            case ACCOUNT_INFO:
                mainTable.add(editAccountInfo);
                stage.addActor(loggedTable);
                break;
            case LOGIN :
                hideButtons(false);
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

    public void clearStatus() {
        lblStatus.setText("");
        lblLoggedStatus.setText("");
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
    public void setPlayerInfo(String userJson) {
        UserInfo.logAs(userJson);
        Gdx.app.debug("JSON", userJson);
        Gdx.app.debug("JSON", UserInfo.getAsString());
    }

    @Override
    public void back() {
        switchToWindow(INetworkScreenListener.SCREEN.LEVEL_SELECTOR);
    }

    @Override
    public void play(int level) {
        UserInfo.setLevel(level);
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
        stage.getViewport().update(width, height);
//        buildStage();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void hide() {
        background.dispose();
        stage.dispose();
        skin.dispose();
    }
}
