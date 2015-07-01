package bg.ittalents.tower_defense.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import bg.ittalents.tower_defense.game.waves.LevelData;
import bg.ittalents.tower_defense.network.Network;
import bg.ittalents.tower_defense.network.Offline;

public class LoginScreen extends AbstractGameScreen {

    public static final float PADDING = 10f;
    public static final float BUTTON_WIDTH = 100f;
    public static final float SCREEN_WIDTH = 800f;
    public static final float SCREEN_HEIGHT = 480f;
    public static final float WINDOW_TRANSPARENCY = 0.7f;
    public static final float STATUS_MESSAGE_DELAY = 5f;

    private Stage stage;
    private Skin skin;
    private TextField txtUsername;
    private TextField txtPassword;
    private TextField txtNickname;
    private TextField txtUsernameReg;
    private TextField txtPasswordReg;
    private TextField txtPasswordReg2;
    private TextField txtEmail;
    private CheckBox checkBox;
    private Label lblStatus;
    private Table mainTable;
    private Window registerWindow;
    private Window loginWindow;
    private Texture background;
    private Batch batch;

    public LoginScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        background = new Texture(Gdx.files.internal("menus_background.jpg"));
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        stage = new Stage(new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        batch = stage.getBatch();
        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);
        buildStatusTable();
        buildLoginWindow();
        buildRegisterWindow();
    }

    private void buildStage() {
        stage = new Stage(new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        batch = stage.getBatch();
        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);
        buildStatusTable();
        buildLoginWindow();
        buildRegisterWindow();
    }

    private void buildRegisterWindow() {
        registerWindow = new Window("Register", skin);
        registerWindow.setColor(1, 1, 1, WINDOW_TRANSPARENCY);
        registerWindow.center();
        Table table = new Table();
        ScrollPane scrollPane = new ScrollPane(table, skin);
        registerWindow.add(scrollPane).colspan(2);

        table.add(new Label("Nickname: ", skin)).pad(PADDING);
        txtNickname = new TextField("", skin);
        table.add(txtNickname).pad(PADDING);
        table.row();
        table.add(new Label("Username: ", skin)).pad(PADDING);
        txtUsernameReg = new TextField("", skin);
        table.add(txtUsernameReg).pad(PADDING);
        table.row();
        table.add(new Label("Password: ", skin));
        txtPasswordReg = new TextField("", skin);
        txtPasswordReg.setPasswordMode(true);
        txtPasswordReg.setPasswordCharacter('*');
        table.add(txtPasswordReg).pad(PADDING);
        table.row();
        table.add(new Label("Repeat Password: ", skin)).pad(PADDING);
        txtPasswordReg2 = new TextField("", skin);
        txtPasswordReg2.setPasswordMode(true);
        txtPasswordReg2.setPasswordCharacter('*');
        table.add(txtPasswordReg2).pad(PADDING);
        table.row();
        table.add(new Label("Email: ", skin)).pad(PADDING);
        txtEmail = new TextField("", skin);
        table.add(txtEmail).pad(PADDING);
        table.row();
        checkBox = new CheckBox("Send notification when\nsomeone beats your record! ", skin);
        checkBox.setChecked(true);
        table.add(checkBox).colspan(2).pad(PADDING);
        registerWindow.row();
        TextButton btnRegister = new TextButton("Register", skin);
        btnRegister.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("Login", "Username: " + txtUsernameReg.getText() + ", Password" + txtPasswordReg.getText());
                if (txtPasswordReg.getText().equals(txtPasswordReg2.getText())) {
                    register();
                } else {
                    setStatus("Passwords do not match");
                }
            }
        });
        registerWindow.add(btnRegister).pad(PADDING).width(BUTTON_WIDTH);

        TextButton btnCancel = new TextButton("Cancel", skin);
        btnCancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                registerWindow.remove();
                mainTable.add(loginWindow);
            }
        });
        registerWindow.add(btnCancel).pad(PADDING).width(BUTTON_WIDTH);
    }

    private void buildLoginWindow() {
        loginWindow = new Window("Login", skin);
        // Make options window slightly transparent
        loginWindow.setColor(1, 1, 1, WINDOW_TRANSPARENCY);
        loginWindow.center();
        loginWindow.add(new Label("Username: ", skin)).pad(PADDING);
        txtUsername = new TextField("", skin);
        loginWindow.add(txtUsername).pad(PADDING);
        loginWindow.row();
        loginWindow.add(new Label("Password: ", skin)).pad(PADDING);
        txtPassword = new TextField("", skin);
        txtPassword.setPasswordMode(true);
        txtPassword.setPasswordCharacter('*');
        loginWindow.add(txtPassword).pad(PADDING);
        loginWindow.row();

        TextButton btnLogin = new TextButton("Login", skin);
        btnLogin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                login();
            }
        });
        loginWindow.add(btnLogin).width(BUTTON_WIDTH).pad(PADDING);

        TextButton btnRegister = new TextButton("Register", skin);
        btnRegister.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                loginWindow.remove();
                mainTable.add(registerWindow);
            }
        });
        loginWindow.add(btnRegister).width(BUTTON_WIDTH).pad(PADDING);
        mainTable.add(loginWindow).center();
    }

    private void buildStatusTable() {
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.setColor(1, 1, 1, WINDOW_TRANSPARENCY);

        TextButton btnQuit = new TextButton("Quit", skin);
        btnQuit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        table.add(btnQuit).width(BUTTON_WIDTH).pad(PADDING).left();

        lblStatus = new Label("", skin);
        lblStatus.setColor(Color.RED);
        lblStatus.setAlignment(Align.center);
        table.add(lblStatus).expandX().pad(PADDING);

        TextButton btnOffline = new TextButton("Play offline", skin);
        btnOffline.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                offline();
            }
        });
        table.add(btnOffline).width(BUTTON_WIDTH).pad(PADDING).right();

        stage.addActor(table);
    }

    private void offline() {
        Json json = new Json();
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setIgnoreUnknownFields(true);
        json.setOutputType(JsonWriter.OutputType.json);
        LevelData levelData = json.fromJson(LevelData.class, new Offline().getLevelData(1));

        Gdx.app.debug("JSON", levelData.toString());

        getGame().setScreen(new LevelSelectorScreen(getGame(), new Offline()));
    }

    private void login() {
        JsonObject json = new JsonObject();
        if (txtUsername.getText().length() == 0 || txtUsername.getText().length() == 0) {
            setStatus("Enter valid username and password!");
            return;
        }
        json.add("userName", new JsonPrimitive(txtUsername.getText()));
        json.add("password", new JsonPrimitive(txtPassword.getText()));

        Gdx.app.debug("URL", Network.URL + Network.LOGIN_MANAGER);
        Gdx.app.debug("JSON", json.toString());

        final Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(Network.URL + Network.LOGIN_MANAGER);
        httpRequest.setHeader("Content-Type", "application/json");
        httpRequest.setContent(json.toString());
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(final Net.HttpResponse httpResponse) {
                if (httpResponse.getStatus().getStatusCode() == 200) {
                    Gdx.app.postRunnable(new Runnable() {

                        @Override
                        public void run() {
//                            Gdx.app.debug("Login POST", "" + httpResponse.getHeader("Reason"));
//                            Gdx.net.cancelHttpRequest(httpRequest);
                            getGame().setScreen(new LevelSelectorScreen(getGame(), new Network(getGame())));
                        }
                    });
                } else {
                    setStatus(httpResponse.getHeader(null));
                    Gdx.app.debug("Login POST", "" + httpResponse.getHeader(null));
//                    Gdx.net.cancelHttpRequest(httpRequest);
                }
            }

            @Override
            public void failed(Throwable t) {
                setStatus("Server is not responding");
//                Gdx.app.debug("Login POST", "FAILED" + t.getMessage());
            }

            @Override
            public void cancelled() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
//                                btnLogin.setDisabled(false);
//                                btnLogin.setTouchable(Touchable.enabled);
                        Gdx.app.debug("Login POST", "CANCELLED");
                    }
                });
            }
        });
    }

    private void register() {
        JsonObject json = new JsonObject();
        json.add("userName", new JsonPrimitive(txtUsername.getText()));
        json.add("nickName", new JsonPrimitive(txtNickname.getText()));
        json.add("password", new JsonPrimitive(txtPassword.getText()));
        json.add("email", new JsonPrimitive(txtEmail.getText()));
        json.add("spam", new JsonPrimitive(checkBox.isChecked()));

        Gdx.app.debug("URL", Network.URL + Network.REGISTER_MANAGER);
        Gdx.app.debug("JSON", json.toString());

        final Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(Network.URL + Network.REGISTER_MANAGER);
        httpRequest.setHeader("Content-Type", "application/json");
        httpRequest.setContent(json.toString());
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if (httpResponse.getStatus().getStatusCode() == 200) {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
//                            Gdx.net.cancelHttpRequest(httpRequest);
                            registerWindow.remove();
                            mainTable.add(loginWindow);
                        }
                    });
                } else {
                    setStatus(httpResponse.getHeader(null));
//                    Gdx.app.debug("Login POST", "" + httpResponse.getHeaders().toString());
//                    Gdx.app.debug("Login POST", "" + httpResponse.getStatus().getStatusCode());
                }
            }

            @Override
            public void failed(Throwable t) {
                setStatus("Server is not responding");
                Gdx.app.debug("Login POST", "" + "FAILED");
            }

            @Override
            public void cancelled() {
                Gdx.app.debug("Login POST", "" + "CANCELLED");
            }
        });
    }

    private void setStatus(String message) {
        lblStatus.setText(message);
        // clear status message after STATUS_MESSAGE_DELAY seconds
        lblStatus.addAction(Actions.sequence(Actions.delay(STATUS_MESSAGE_DELAY), Actions.run(new Runnable() {
            @Override
            public void run() {
                lblStatus.setText("");
            }
        })));
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
