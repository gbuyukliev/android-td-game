package bg.ittalents.tower_defense.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import bg.ittalents.tower_defense.network.Network;

public class RegisterScreen extends AbstractGameScreen{

    private Stage stage;
    private Skin skin;
    private TextField txtNickname;
    private TextField txtUsername;
    private TextField txtPassword;
    private TextField txtPassword2;
    private TextField txtEmail;
    private CheckBox checkBox;

    public RegisterScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(800f, 480f));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
//        BitmapFont bitmapFont = Assets.instance.fonts.defaultFont;

        Label lblNickname = new Label("Nickname: ", skin);
        lblNickname.setPosition(300, 400);
        lblNickname.setSize(80, 30);
        stage.addActor(lblNickname);

        txtNickname = new TextField("", skin);
        txtNickname.setPosition(400, 400);
        txtNickname.setSize(120, 30);
        stage.addActor(txtNickname);

        Label lblUsername = new Label("Username: ", skin);
        lblUsername.setPosition(300, 350);
        lblUsername.setSize(80, 30);
        stage.addActor(lblUsername);

        txtUsername = new TextField("", skin);
        txtUsername.setPosition(400, 350);
        txtUsername.setSize(120, 30);
        stage.addActor(txtUsername);

        Label lblPassword = new Label("Password: ", skin);
        lblPassword.setPosition(300, 300);
        lblPassword.setSize(80, 30);
        stage.addActor(lblPassword);

        txtPassword = new TextField("", skin);
        txtPassword.setPosition(400, 300);
        txtPassword.setSize(120, 30);
        txtPassword.setPasswordMode(true);
        txtPassword.setPasswordCharacter('*');
        stage.addActor(txtPassword);

        Label lblPassword2 = new Label("Password: ", skin);
        lblPassword2.setPosition(300, 250);
        lblPassword2.setSize(80, 30);
        stage.addActor(lblPassword2);

        txtPassword2 = new TextField("", skin);
        txtPassword2.setPosition(400, 250);
        txtPassword2.setSize(120, 30);
        txtPassword2.setPasswordMode(true);
        txtPassword2.setPasswordCharacter('*');
        stage.addActor(txtPassword2);

        Label lblEmail = new Label("Email: ", skin);
        lblEmail.setPosition(300, 200);
        lblEmail.setSize(80, 30);
        stage.addActor(lblEmail);

        txtEmail = new TextField("", skin);
        txtEmail.setPosition(400, 200);
        txtEmail.setSize(120, 30);
        stage.addActor(txtEmail);

        checkBox = new CheckBox("Send notification when\nsomeone beats your record! ", skin);
        checkBox.setPosition(300, 150);
        checkBox.setSize(240, 60);
        checkBox.setChecked(true);
        stage.addActor(checkBox);

        TextButton btnRegister = new TextButton("Register", skin);
        btnRegister.setPosition(300, 100);
        btnRegister.setSize(80, 30);
        btnRegister.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("Login", "Username: " + txtUsername.getText() + ", Password" + txtPassword.getText());
                register();
            }
        });
        stage.addActor(btnRegister);

        TextButton btnCancel = new TextButton("Cancel", skin);
        btnCancel.setPosition(400, 100);
        btnCancel.setSize(80, 30);
        btnCancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getGame().setScreen(new LoginScreen(getGame()));
            }
        });
        stage.addActor(btnCancel);
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
                            Gdx.net.cancelHttpRequest(httpRequest);
                            getGame().setScreen(new LoginScreen(getGame()));
                        }
                    });
                } else {
                    Gdx.app.debug("Login POST", "" + httpResponse.getHeaders().toString());
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
        Gdx.input.setCatchBackKey(false);
    }
}
