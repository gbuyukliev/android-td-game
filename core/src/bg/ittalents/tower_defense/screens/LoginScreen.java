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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
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

    private Stage stage;
    private Skin skin;
    private TextField txtUsername;
    private TextField txtPassword;
    private Label lblStatus;
    private Texture background;
    private Batch batch;

    public LoginScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(800f, 480f));
        Gdx.input.setInputProcessor(stage);
        background = new Texture(Gdx.files.internal("menus_background.jpg"));

        batch = stage.getBatch();
        Table table = new Table();
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
//        BitmapFont bitmapFont = Assets.instance.fonts.defaultFont;

        lblStatus = new Label("", skin);
        lblStatus.setColor(Color.RED);
        lblStatus.setPosition(100, 50);
        lblStatus.setSize(600, 30);
        lblStatus.setAlignment(Align.center);
        stage.addActor(lblStatus);

        Label lblUsername = new Label("Username: ", skin);
        lblUsername.setPosition(300, 300);
        lblUsername.setSize(80, 30);
        stage.addActor(lblUsername);

        txtUsername = new TextField("", skin);
        txtUsername.setPosition(400, 300);
        txtUsername.setSize(120, 30);
        stage.addActor(txtUsername);

        Label lblPassword = new Label("Password: ", skin);
        lblPassword.setPosition(300, 250);
        lblPassword.setSize(80, 30);
        stage.addActor(lblPassword);

        txtPassword = new TextField("", skin);
        txtPassword.setPosition(400, 250);
        txtPassword.setSize(120, 30);
        txtPassword.setPasswordMode(true);
        txtPassword.setPasswordCharacter('*');
        stage.addActor(txtPassword);

        TextButton btnLogin = new TextButton("Login", skin);
        btnLogin.setPosition(300, 200);
        btnLogin.setSize(80, 30);
        btnLogin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("Login", "Username: " + txtUsername.getText() + ", Password" + txtPassword.getText());
//                getGame().setScreen(new GameScreen(getGame()));

//                Json json = new Json();
//                json.setTypeName(null);
//                json.setUsePrototypes(false);
//                json.setIgnoreUnknownFields(true);
//                json.setOutputType(JsonWriter.OutputType.json);
//                LevelData levelData = json.fromJson(LevelData.class, new Offline().getLevelData(1));
//
//                Gdx.app.debug("JSON", levelData.toString());

                login();
//

//                JsonValue jsonValue = new JsonValue();
            }
        });
        stage.addActor(btnLogin);

        TextButton btnRegister = new TextButton("Register", skin);
        btnRegister.setPosition(400, 200);
        btnRegister.setSize(80, 30);
        btnRegister.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                Gdx.app.debug("Login", "Username: " + txtUsername.getText() + ", Pasword" + txtPassword.getText());
                getGame().setScreen(new RegisterScreen(getGame()));
            }
        });
        stage.addActor(btnRegister);

        TextButton btnOffline = new TextButton("Play offline", skin);
        btnOffline.setPosition(550, 430);
        btnOffline.setSize(120, 30);
        btnOffline.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                Json json = new Json();
                json.setTypeName(null);
                json.setUsePrototypes(false);
                json.setIgnoreUnknownFields(true);
                json.setOutputType(JsonWriter.OutputType.json);
                LevelData levelData = json.fromJson(LevelData.class, new Offline().getLevelData(1));

                Gdx.app.debug("JSON", levelData.toString());

                getGame().setScreen(new LevelSelectorScreen(getGame(), new Network(getGame())));
            }
        });
        stage.addActor(btnOffline);

        TextButton btnQuit = new TextButton("Quit", skin);
        btnQuit.setPosition(700, 430);
        btnQuit.setSize(80, 30);
        btnQuit.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        stage.addActor(btnQuit);
    }

    private void login() {
//        btnLogin.setDisabled(true);
//                btnLogin.setTouchable(Touchable.disabled);
        JsonObject json = new JsonObject();
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
                            getGame().setScreen(new LevelSelectorScreen(getGame(), new Offline()));
                        }
                    });
                } else {
                    lblStatus.setText(httpResponse.getHeader(null));
                    Gdx.app.debug("Login POST", "" + httpResponse.getHeader(null));
//                    Gdx.net.cancelHttpRequest(httpRequest);
                }
            }

            @Override
            public void failed(Throwable t) {
                lblStatus.setText("Server is not responding");
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
