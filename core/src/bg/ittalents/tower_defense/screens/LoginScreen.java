package bg.ittalents.tower_defense.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LoginScreen extends AbstractGameScreen {

    Stage stage;

    TextField txtUsername;
    TextField txtPassword;

    public LoginScreen(Game game) {
        super(game);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));

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
        stage.addActor(txtPassword);

        TextButton btnLogin = new TextButton("Login", skin);
        btnLogin.setPosition(300, 200);
        btnLogin.setSize(80, 30);
        btnLogin.addListener(new ClickListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.debug("Login", "Username: " + txtUsername.getText() +  ", Pasword" + txtPassword.getText());
            }
        });
        stage.addActor(btnLogin);

        TextButton btnRegister = new TextButton("Register", skin);
        btnRegister.setPosition(400, 200);
        btnRegister.setSize(80, 30);
        stage.addActor(btnRegister);
    }

    @Override
    public void show() {
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

    }

    @Override
    public void dispose() {

        stage.dispose();
        super.dispose();
    }
}
