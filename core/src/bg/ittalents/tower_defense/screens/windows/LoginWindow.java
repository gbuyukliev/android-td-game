package bg.ittalents.tower_defense.screens.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import bg.ittalents.tower_defense.network.Network;

public class LoginWindow extends Window {

    public static final String TITLE = "Login";

    public static final float PADDING = 10f;
    public static final float BUTTON_WIDTH = 100f;
    public static final float WINDOW_TRANSPARENCY = 0.7f;

    private INetworkScreenListener networkScreen;

    private TextField txtUsername;
    private TextField txtPassword;

    public LoginWindow(Skin skin, INetworkScreenListener networkScreen) {
        super(TITLE, skin);
        this.networkScreen = networkScreen;
        buildLoginWindow();
    }

    private void buildLoginWindow() {
        // Make window slightly transparent
        this.setColor(1, 1, 1, WINDOW_TRANSPARENCY);
        this.center();

        this.add(new Label("Username: ", getSkin())).pad(PADDING);
        txtUsername = new TextField("", getSkin());
        this.add(txtUsername).pad(PADDING);
        this.row();
        this.add(new Label("Password: ", getSkin())).pad(PADDING);
        txtPassword = new TextField("", getSkin());
        txtPassword.setPasswordMode(true);
        txtPassword.setPasswordCharacter('*');
        this.add(txtPassword).pad(PADDING);
        this.row();

        TextButton btnLogin = new TextButton("Login", getSkin());
        btnLogin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                networkScreen.switchToWindow(INetworkScreen.SCREEN.LEVEL_SELECTOR);
                if (validateFields()) {
                    login();
                }
            }
        });
        this.add(btnLogin).width(BUTTON_WIDTH).pad(PADDING);

        TextButton btnRegister = new TextButton("Register", getSkin());
        btnRegister.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                networkScreen.switchToWindow(INetworkScreenListener.SCREEN.REGISTER);
            }
        });
        this.add(btnRegister).width(BUTTON_WIDTH).pad(PADDING);
    }

    private String getJsonAsString() {
        JsonObject json = new JsonObject();

        json.add("userName", new JsonPrimitive(txtUsername.getText()));
        json.add("password", new JsonPrimitive(txtPassword.getText()));

        Gdx.app.debug("URL", Network.URL + Network.LOGIN_MANAGER);
        Gdx.app.debug("JSON", json.toString());

        return json.toString();
    }

    private boolean validateFields() {
        if (txtUsername.getText().length() == 0 || txtUsername.getText().length() == 0) {
            networkScreen.setStatus("Enter valid username and password!");
            return false;
        }
        return true;
    }

    private void login() {
        final Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(Network.URL + Network.LOGIN_MANAGER);
        httpRequest.setHeader("Content-Type", "application/json");
        httpRequest.setContent(getJsonAsString());
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(final Net.HttpResponse httpResponse) {
//                Gdx.app.debug("Login Result", httpResponse.getResultAsString() + httpResponse.getStatus().getStatusCode());
//                Gdx.app.debug("Login Headers", httpResponse.getHeaders().toString());
                if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
                    final String result = httpResponse.getResultAsString();

                    Gdx.app.postRunnable(new Runnable() {

                        @Override
                        public void run() {
                            if (result == null) {
                                networkScreen.setStatus("Incorrect data from server");
                                return;
                            }

                            networkScreen.switchToWindow(INetworkScreenListener.SCREEN.LEVEL_SELECTOR);
//                            Gdx.app.debug("Login POST", "" + httpResponse.getHeader("Reason"));
//                            Gdx.net.cancelHttpRequest(httpRequest);

                        }
                    });
                } else {
                    String errorMessage = httpResponse.getHeader("Error");
                    if (errorMessage == null) {
                        errorMessage = "Wrong username or password!";
                    }
                    networkScreen.setStatus(errorMessage);
                    Gdx.app.debug("Login POST", "" + errorMessage);
//                    Gdx.net.cancelHttpRequest(httpRequest);
                }
            }

            @Override
            public void failed(Throwable t) {
                networkScreen.setStatus("Server is not responding");
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
}
