package bg.ittalents.tower_defense.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

class Online implements INetwork {

    INetworkLevelListener networkLevelListener;

    @Override
    public void getLevelData(String username, int levelToPlay) {
        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.GET);
        httpRequest.setUrl(Network.URL + Network.LEVEL_MANAGER);
        httpRequest.setContent("userName=" + username + "&levelToPlay=" + levelToPlay);
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                Gdx.app.debug("Level Data", httpResponse.getResultAsString());
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        });
    }

    @Override
    public void saveScore(String username, int level, int score) {
        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(Network.URL + Network.LEVEL_END_MANAGER);

        JsonObject json = new JsonObject();

        json.add("userName", new JsonPrimitive(username));
        json.add("level", new JsonPrimitive(level));
        json.add("score", new JsonPrimitive(score));

        Gdx.app.debug("Save URL", Network.URL + Network.LEVEL_END_MANAGER);
        Gdx.app.debug("Save Json", json.toString());

        httpRequest.setContent(json.toString());
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                Gdx.app.debug("Save Score Response", httpResponse.getResultAsString());
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        });
    }

    @Override
    public void getTopPlayers() {
        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.GET);
        httpRequest.setUrl(Network.URL + Network.LEVEL_MANAGER);
        httpRequest.setContent("");
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                Gdx.app.debug("Top Players Data", httpResponse.getResultAsString());
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        });
    }

    @Override
    public void setListener(INetworkLevelListener networkLevelListener) {
        this.networkLevelListener = networkLevelListener;
    }
}
