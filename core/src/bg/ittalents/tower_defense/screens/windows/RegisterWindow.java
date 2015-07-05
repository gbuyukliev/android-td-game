package bg.ittalents.tower_defense.screens.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.HashMap;
import java.util.Map;

import bg.ittalents.tower_defense.network.Network;

public class RegisterWindow extends Window {

    private static final String REGISTER_NICKNAME = "Nickname";
    private static final String REGISTER_USERNAME = "Username";
    private static final String REGISTER_PASSWORD = "Password";
    private static final String REGISTER_PASSWORD2 = "Repeat Password";
    private static final String REGISTER_EMAIL = "Email";
    private static final String[] REGISTER_FIELDS = {REGISTER_NICKNAME, REGISTER_USERNAME, REGISTER_PASSWORD, REGISTER_PASSWORD2, REGISTER_EMAIL};

    public static final float PADDING = 10f;
    public static final float BUTTON_WIDTH = 100f;
    public static final float WINDOW_TRANSPARENCY = 0.7f;
    public static final String TITLE = "Register";

    private Map<String, TextField> textFields;

    private CheckBox checkBox;
    private INetworkScreenListener networkScreen;

    public RegisterWindow(Skin skin, INetworkScreenListener networkScreen) {
        super(TITLE, skin);
        this.setMovable(false);
        this.networkScreen = networkScreen;
        textFields = new HashMap<String, TextField>();
        buildRegisterWindow();
    }

    private static void addFiledRowsToTable(Map fields, Table table, Skin skin, String... fieldNames) {
        for (String fieldName : fieldNames) {
            table.add(new Label(fieldName + ": ", skin)).pad(PADDING);
            TextField textField = new TextField("", skin);
            if (fieldName.toLowerCase().contains("password")) {
                textField.setPasswordMode(true);
                textField.setPasswordCharacter('*');
            }
            table.add(textField).pad(PADDING);
            table.row();
            fields.put(fieldName, textField);
        }
    }

    private void buildRegisterWindow() {
        // Make window slightly transparent
        this.setColor(1, 1, 1, WINDOW_TRANSPARENCY);
        this.center();
        Table table = new Table();
        ScrollPane scrollPane = new ScrollPane(table, getSkin());
        this.add(scrollPane).colspan(2);

        addFiledRowsToTable(textFields, table, getSkin(), REGISTER_FIELDS);

        checkBox = new CheckBox("Send notification when\nsomeone beats your record! ", getSkin());
        checkBox.setChecked(true);
        table.add(checkBox).colspan(2).pad(PADDING);
        this.row();
        TextButton btnRegister = new TextButton("Register", getSkin());
        btnRegister.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.debug("Login", "Username: " + textFields.get(REGISTER_USERNAME).getText() + ", Password" + textFields.get(REGISTER_PASSWORD).getText());
                if (textFields.get(REGISTER_PASSWORD).getText().equals(textFields.get(REGISTER_PASSWORD2).getText())) {
                    register();
                } else {
                    networkScreen.setStatus("Passwords do not match");
                }
            }
        });
        this.add(btnRegister).pad(PADDING).width(BUTTON_WIDTH);

        TextButton btnCancel = new TextButton("Cancel", getSkin());
        btnCancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                networkScreen.switchToWindow(INetworkScreenListener.SCREEN.LOGIN);
            }
        });
        this.add(btnCancel).pad(PADDING).width(BUTTON_WIDTH);
    }

    private void register() {
        JsonObject json = new JsonObject();
        json.add("userName", new JsonPrimitive(textFields.get(REGISTER_USERNAME).getText()));
        json.add("nickName", new JsonPrimitive(textFields.get(REGISTER_NICKNAME).getText()));
        json.add("password", new JsonPrimitive(textFields.get(REGISTER_PASSWORD).getText()));
        json.add("email", new JsonPrimitive(textFields.get(REGISTER_EMAIL).getText()));
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
                if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
//                            Gdx.net.cancelHttpRequest(httpRequest);
                            networkScreen.switchToWindow(INetworkScreenListener.SCREEN.LOGIN);
                        }
                    });
                } else {
                    networkScreen.setStatus(httpResponse.getHeader(null));
//                    Gdx.app.debug("Login POST", "" + httpResponse.getHeaders().toString());
//                    Gdx.app.debug("Login POST", "" + httpResponse.getStatus().getStatusCode());
                }
            }

            @Override
            public void failed(Throwable t) {
                networkScreen.setStatus("Server is not responding");
                Gdx.app.debug("Login POST", "" + "FAILED");
            }

            @Override
            public void cancelled() {
                Gdx.app.debug("Login POST", "" + "CANCELLED");
            }
        });
    }
}
