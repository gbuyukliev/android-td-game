package bg.ittalents.tower_defense.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
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

    public LoginScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(800f, 480f));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
//        BitmapFont bitmapFont = Assets.instance.fonts.defaultFont;

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
        btnLogin.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                Gdx.app.debug("Login", "Username: " + txtUsername.getText() + ", Pasword" + txtPassword.getText());
//                getGame().setScreen(new GameScreen(getGame()));

//                WaveManager waveManager = WaveManager.testWaves();
//                Json json = new Json();
//                json.setElementType(WaveManager.class, "waves", WaveManager.CreepCount.class);
//                Gdx.app.debug("JSON", json.prettyPrint(waveManager));

//                Json json = new Json();
//                json.setTypeName(null);
//                json.setUsePrototypes(false);
//                json.setIgnoreUnknownFields(true);
//                json.setOutputType(JsonWriter.OutputType.json);
//                LevelData levelData = json.fromJson(LevelData.class, new Offline().getLevelData(1));
//
//                Gdx.app.debug("JSON", levelData.toString());

                JsonObject json = new JsonObject();
                json.add("userName", new JsonPrimitive(txtUsername.getText()));
                json.add("password", new JsonPrimitive(txtPassword.getText()));

                Gdx.app.debug("URL", Network.URL + "LoginManager");
                Gdx.app.debug("JSON", json.toString());

                Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
                httpRequest.setUrl(Network.URL + "LoginManager");
                httpRequest.setContent(json.toString());
                Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
                    @Override
                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        if (httpResponse.getStatus().getStatusCode() == 200) {
                            getGame().setScreen(new GameScreen(getGame()));
                        } else {
                            Gdx.app.debug("Login POST", "" + httpResponse.getStatus().getStatusCode());
                        }
                    }

                    @Override
                    public void failed(Throwable t) {
                        Gdx.app.debug("Login POST", "" + "FAILED");
                    }

                    @Override
                    public void cancelled() {
                        Gdx.app.debug("Login POST", "" + "CANCELLED");
                    }
                });

//                JsonValue jsonValue = new JsonValue();
            }
        });
        stage.addActor(btnLogin);

        TextButton btnRegister = new TextButton("Register", skin);
        btnRegister.setPosition(400, 200);
        btnRegister.setSize(80, 30);
        btnRegister.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.debug("Login", "Username: " + txtUsername.getText() + ", Pasword" + txtPassword.getText());
                getGame().setScreen(new RegisterScreen(getGame()));
            }
        });
        stage.addActor(btnRegister);

        TextButton btnOffline = new TextButton("Play offline", skin);
        btnOffline.setPosition(500, 100);
        btnOffline.setSize(120, 30);
        btnOffline.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.debug("Login", "Username: " + txtUsername.getText() + ", Pasword" + txtPassword.getText());
                getGame().setScreen(new GameScreen(getGame()));
            }
        });
        stage.addActor(btnOffline);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        stage.dispose();
        skin.dispose();
    }
}
