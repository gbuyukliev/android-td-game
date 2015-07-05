package bg.ittalents.tower_defense.screens.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bg.ittalents.tower_defense.network.Network;

public class TopPlayersWindow extends Window {

    public static final float PADDING = 10f;
    public static final float BUTTON_WIDTH = 100f;
    public static final float WINDOW_TRANSPARENCY = 0.7f;

    private INetworkScreenListener networkScreenListener;
    private Table table;

    public TopPlayersWindow(Skin skin, final INetworkScreenListener networkScreenListener) {
        super("Top Players", skin);
        this.setMovable(false);
        this.setColor(1, 1, 1, WINDOW_TRANSPARENCY);
        this.networkScreenListener = networkScreenListener;
        requestTopPlayersList();

        table = new Table();
        ScrollPane scrollPane = new ScrollPane(table);

        this.add(scrollPane);
        this.row();

        TextButton textButton = new TextButton("Close", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                networkScreenListener.switchToWindow(INetworkScreenListener.SCREEN.LEVEL_SELECTOR);
            }
        });
        this.add(textButton);
    }

    private void buildTable(String json) {
//        parseJson(json);
        table.clear();
        table.add(new TextArea(json, getSkin())).width(640).height(360);
    }
    private void parseJson(String json) {
        JsonParser parser = new JsonParser();
        JsonObject jo = (JsonObject) parser.parse(json);
//        JsonElement je = jo.get("some_array");

        //Parsing back the string as Array
        JsonArray ja = (JsonArray) parser.parse(jo.get("User").getAsString());
        for (JsonElement je : ja) {
            JsonObject j = (JsonObject) jo;

            Gdx.app.debug("Json", j.toString());
            // Your Code, Access json elements as j.get("some_element")
        }
    }

    private void requestTopPlayersList() {
        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.GET);
        httpRequest.setUrl(Network.URL + Network.ALL_HIGH_SCORE_MANAGER);
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(final Net.HttpResponse httpResponse) {
//                Gdx.app.debug("URL", Network.URL + Network.ALL_HIGH_SCORE_MANAGER);
//                Gdx.app.debug("Login Headers", httpResponse.getResultAsString());
                if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
                    final String result = httpResponse.getResultAsString();

                    Gdx.app.postRunnable(new Runnable() {

                        @Override
                        public void run() {
                            buildTable(result);
                        }
                    });
                }
            }

            @Override
            public void failed(Throwable t) {
            }

            @Override
            public void cancelled() {
            }
        });
    }
}
